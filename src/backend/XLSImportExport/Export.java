package backend.XLSImportExport;


import backend.DBS;
import backend.Entities.Category;
import backend.Entities.Customer;
import backend.Entities.Part;
import backend.Entities.Part_name;
import backend.Managers.*;
import javafx.scene.control.Alert;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class Export {
    final static short COLUMN_SIZE = 10000;
    final static short BIAS = 100;
    private final static String TEMPLATE_PATH = "./src/backend/XLSImportExport/template.xlsx";
    private final static String PDF_CONVERT_SCRIPT_PATH = "./src/backend/XLSImportExport/xl2pdf.vbs";

    private static CellStyle getCellStyle(XSSFWorkbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFFont font = workbook.createFont();
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 16);
        cellStyle.setFont(font);
        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle.setBorderTop(BorderStyle.MEDIUM);
        cellStyle.setBorderRight(BorderStyle.MEDIUM);
        cellStyle.setBorderLeft(BorderStyle.MEDIUM);
        return cellStyle;
    }

    private static int poiWidthToPixels(final double widthUnits) {
        if (widthUnits <= 256) {
            return (int) Math.round((widthUnits / 28));
        } else {
            return (int) (Math.round(widthUnits * 7 / 256));
        }
    }

    private static void setPrinterSettings(Sheet sheet) {
        sheet.setFitToPage(true);
        sheet.getPrintSetup().setFitWidth((short) 1);
        sheet.getPrintSetup().setFitHeight((short) 0);
        sheet.getPrintSetup().setLandscape(true);
    }

    public static boolean exportPartsToXLS(List<Part> parts, String path) {
        try {
            File tempalateXLS = new File(TEMPLATE_PATH);
            File newXLS = new File(path);
            Files.copy(tempalateXLS.toPath(), newXLS.toPath(), StandardCopyOption.REPLACE_EXISTING);
            XSSFWorkbook workbook = new XSSFWorkbook(Files.newInputStream(Paths.get(path)));

            int index = 1;
            Sheet sheet = workbook.getSheetAt(0);
            setPrinterSettings(sheet);
            sheet.setColumnWidth(0, COLUMN_SIZE);
            XSSFDrawing drawingPatriarch = (XSSFDrawing) sheet.createDrawingPatriarch();
            CellStyle cellStyle = getCellStyle(workbook);
            for (Part part : parts) {
                Row row = sheet.createRow(index);

                Cell c = row.createCell(0);
                c.setCellStyle(cellStyle);
                if (part.getDrawing_id() != null) {
                    XSSFClientAnchor anchor = new XSSFClientAnchor();
                    anchor.setCol1(0);
                    anchor.setRow1(index);
                    anchor.setDy1(BIAS * 100);
                    anchor.setCol2(1);
                    anchor.setRow2(index + 1);
                    int pic = workbook.addPicture(Objects.requireNonNull(DrawingManager.getDrawing(part.getDrawing_id())).getDrawing(), Workbook.PICTURE_TYPE_PNG);
                    Picture picture = drawingPatriarch.createPicture(anchor, pic);
                    short h = (short) (picture.getImageDimension().height * 15 + BIAS);
                    double scale = (double) poiWidthToPixels(COLUMN_SIZE) / picture.getImageDimension().width;
                    row.setHeight((short) (h * scale));
                    picture.resize();
                    picture.resize(scale);
                } else
                    c.setCellValue("no drawing available");


                c = row.createCell(1);
                c.setCellValue(part.getPart_number());
                c.setCellStyle(cellStyle);

                Part_name partName = PartNameManager.getPartName(part.getPart_name_id());
                c = row.createCell(2);
                c.setCellStyle(cellStyle);
                if (partName != null)
                    c.setCellValue(partName.getPart_name());

                Category category = CategoryManager.getCategory(part.getCategory_id());
                c = row.createCell(3);
                c.setCellStyle(cellStyle);
                if (category != null)
                    c.setCellValue(category.getCategory_name());

                Customer customer = CustomerManager.getCustomer(part.getCustomer_id());
                c = row.createCell(4);
                c.setCellStyle(cellStyle);
                if (customer != null)
                    c.setCellValue(customer.getCustomer_name());

                c = row.createCell(5);
                c.setCellStyle(cellStyle);
                if (part.getRubber() != null)
                    c.setCellValue(part.getRubber() + " ShA");

                c = row.createCell(6);
                c.setCellStyle(cellStyle);
                if (part.getDiameter_AT() != null)
                    c.setCellValue("Ø " + part.getDiameter_AT());

                c = row.createCell(7);
                c.setCellStyle(cellStyle);
                if (part.getDiameter_AT_tol() != null)
                    c.setCellValue(part.getDiameter_AT_tol());

                c = row.createCell(8);
                c.setCellStyle(cellStyle);
                if (part.getLength_L_AT() != null)
                    c.setCellValue(part.getLength_L_AT());

                c = row.createCell(9);
                c.setCellStyle(cellStyle);
                if (part.getLength_L_AT_tol() != null)
                    c.setCellValue(part.getLength_L_AT_tol());

                c = row.createCell(10);
                c.setCellStyle(cellStyle);
                if (part.getDiameter_IT() != null)
                    c.setCellValue("Ø " + part.getDiameter_IT());

                c = row.createCell(11);
                c.setCellStyle(cellStyle);
                if (part.getDiameter_IT_tol() != null)
                    c.setCellValue(part.getDiameter_IT_tol());

                c = row.createCell(12);
                c.setCellStyle(cellStyle);
                if (part.getLength_L_IT() != null)
                    c.setCellValue(part.getLength_L_IT());

                c = row.createCell(13);
                c.setCellStyle(cellStyle);
                if (part.getLength_L_IT_tol() != null)
                    c.setCellValue(part.getLength_L_IT_tol());

                c = row.createCell(14);
                c.setCellStyle(cellStyle);
                if (part.getDiameter_ZT() != null)
                    c.setCellValue("Ø " + part.getDiameter_ZT());

                c = row.createCell(15);
                c.setCellStyle(cellStyle);
                if (part.getDiameter_ZT_tol() != null)
                    c.setCellValue(part.getDiameter_ZT_tol());

                c = row.createCell(16);
                c.setCellStyle(cellStyle);
                if (part.getLength_L_ZT() != null)
                    c.setCellValue(part.getLength_L_ZT());

                c = row.createCell(17);
                c.setCellStyle(cellStyle);
                if (part.getLength_L_ZT_tol() != null)
                    c.setCellValue(part.getLength_L_ZT_tol());

                c = row.createCell(18);
                c.setCellStyle(cellStyle);
                if (part.getCr_steg() != null)
                    c.setCellValue(part.getCr_steg() + " N/mm");

                c = row.createCell(19);
                c.setCellStyle(cellStyle);
                if (part.getCr_niere() != null)
                    c.setCellValue(part.getCr_niere() + " N/mm");

                c = row.createCell(20);
                c.setCellStyle(cellStyle);
                if (part.getCa() != null)
                    c.setCellValue(part.getCa() + " N/mm");

                c = row.createCell(21);
                c.setCellStyle(cellStyle);
                if (part.getCt() != null)
                    c.setCellValue(part.getCt() + " Nm/°");

                c = row.createCell(22);
                c.setCellStyle(cellStyle);
                if (part.getCk() != null)
                    c.setCellValue(part.getCk() + " Nm/°");

                index++;
            }

            workbook.write(Files.newOutputStream(Paths.get(path)));
            return true;
        } catch (Exception e) {
//            throw new RuntimeException(e);
            return false;
        }
    }

    public static void exportXLSToPdf(String inputPath, String outputPath) {
        try {
            Path tempScript = Files.createTempFile("script", ".vbs");
            List<String> script = Files.readAllLines(Paths.get(PDF_CONVERT_SCRIPT_PATH));

            String origPath = Paths.get(inputPath).toAbsolutePath().toString();
            origPath = origPath.replace("\\", "\\\\");
            origPath = origPath.replace("/", "\\\\");

            String pdfPath = Paths.get(outputPath).toAbsolutePath().toString();
            pdfPath = pdfPath.replace("\\", "\\\\");
            pdfPath = pdfPath.replace("/", "\\\\");
            for (int i = 0; i < script.size(); i++) {
                script.set(i, script.get(i).replaceAll("XL_FILE", origPath));
                script.set(i, script.get(i).replaceAll("PDF_FILE", pdfPath));
            }

            Files.write(tempScript, script);

            ProcessBuilder pb = new ProcessBuilder("wscript", tempScript.toString());

            pb.start();
        } catch (Exception e) {
            e.printStackTrace();
            Alert saveAsPdfAlert = new Alert(Alert.AlertType.ERROR);
            saveAsPdfAlert.setTitle("ERROR: Error converting to pdf.");
            saveAsPdfAlert.setHeaderText("Exception message is:");
            saveAsPdfAlert.setContentText(e.getMessage());
            saveAsPdfAlert.showAndWait();
        }
    }


    public static void main(String[] args) {
        try {
            java.util.Properties prop = new Properties();
            prop.loadFromXML(Files.newInputStream(Paths.get("configuration/configuration.xml")));
            Connection connection = DriverManager.getConnection(
                    prop.getProperty("database"),
                    prop.getProperty("user"),
                    prop.getProperty("password"));
            DBS.setConnection(connection);
            Part part = PartManager.getPartByPartNumber("774.226.151.311");
            Part part1 = PartManager.getPartByPartNumber("714.221.138.029");
            Part part2 = PartManager.getPartByPartNumber("712.335.152.205");
            Part part3 = PartManager.getPartByPartNumber("714.126.137.142");
            Part part4 = PartManager.getPartByPartNumber("714.126.137.143");
            Part part5 = PartManager.getPartByPartNumber("714.126.137.144");
            Part part6 = PartManager.getPartByPartNumber("714.221.146.110");
            Part part7 = PartManager.getPartByPartNumber("714.221.148.739");

            System.out.println(exportPartsToXLS(Arrays.asList(part, part1, part2, part3, part4, part5, part6, part7), "./src/backend/export.xlsx"));
            exportXLSToPdf("./src/backend/export.xlsx", "./src/backend/export.pdf");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
