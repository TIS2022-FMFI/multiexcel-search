package frontend.AdminControllers;

import backend.Models.MutablePair;
import backend.XLSImportExport.Import;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ImportController implements Initializable {
    @FXML
    private Button chooseFilesButton;
    @FXML
    private Button importButton;
    @FXML
    private VBox scrollVBox;

    @FXML
    private Text message;

    private List<File> filesToImport = new ArrayList<>();
    private final List<Text> textsOfFiles = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void chooseFiles() {
        message.setText("");
        scrollVBox.getChildren().clear();
        textsOfFiles.clear();
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        fc.setInitialDirectory(new File("."));
        filesToImport = fc.showOpenMultipleDialog(null);
        if (filesToImport == null)
            return;
        for (File f : filesToImport) {
            Text text = new Text(f.getName());
            scrollVBox.getChildren().add(text);
            textsOfFiles.add(text);
        }
    }

    @FXML
    public void importFiles() {
        for (int i = 0; i < filesToImport.size(); i++) {
            textsOfFiles.get(i).setText(filesToImport.get(i).getName());
        }
        message.setText("Importing...");

        Thread t = new Thread(() -> {
            MutablePair insertUpdate = new MutablePair(0, 0);
            for (int i = 0; i < filesToImport.size(); i++) {
                try {
                    XSSFWorkbook workbook = new XSSFWorkbook(filesToImport.get(i));
                    MutablePair inUp = Import.uploadXLStoDBS(workbook);
                    insertUpdate.first += inUp.first;
                    insertUpdate.second += inUp.second;
                    textsOfFiles.get(i).setFill(Color.GREEN);
                    textsOfFiles.get(i).setText(textsOfFiles.get(i).getText() + " - File imported correctly!");
                } catch (IOException | InvalidFormatException e) {
                    textsOfFiles.get(i).setFill(Color.RED);
                    textsOfFiles.get(i).setText(textsOfFiles.get(i).getText() + " - Unable to read file!");
                } catch (RuntimeException e) {
                    textsOfFiles.get(i).setFill(Color.RED);
                    textsOfFiles.get(i).setText(textsOfFiles.get(i).getText() + " - " + e.getMessage());
                }
            }
            message.setText("Parts inserted: " + insertUpdate.first + " Parts updated: " + insertUpdate.second);
        });
        t.start();
    }
}
