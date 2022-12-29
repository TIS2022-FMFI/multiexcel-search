package backend.XLSImportExport;


import backend.DBS;
import backend.Entities.Part;
import backend.Managers.*;
import org.apache.poi.hemf.draw.HemfImageRenderer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Import {

    private static String checkCell(String cell) {
        if (cell == null || cell.equals("") || cell.equals("-"))
            return null;
        return cell;
    }

    private static String nextString(DataFormatter dataFormatter, Iterator<Cell> it) {
        Cell cell = it.next();
        String cellString = dataFormatter.formatCellValue(cell);
        return checkCell(cellString);
    }

    private static Double getDouble(String s) {
        if (s != null)
            try {
                return Double.valueOf(s.split(" ")[1].replace(",", "."));
            } catch (ArrayIndexOutOfBoundsException e) {
                return Double.valueOf(s.replace(",", "."));
            } catch (NumberFormatException ignored) {
            }
        return null;
    }

    private static byte[] convertToPngByteArray(XSSFPictureData pict) throws IOException {
        switch (pict.getMimeType()) {
            case "image/png":
                return pict.getData();
            case "image/x-emf": {
                HemfImageRenderer h = new HemfImageRenderer();
                h.loadImage(pict.getData(), pict.getMimeType());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(h.getImage(), "png", baos);
                return baos.toByteArray();
            }
            default: {
                ByteArrayInputStream bais = new ByteArrayInputStream(pict.getData());
                BufferedImage bufferedImage = ImageIO.read(bais);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", baos);
                return baos.toByteArray();
            }
        }
    }

    private static BigInteger getDrawingId(String image, Integer index, List<XSSFPicture> pics) throws SQLException {
        if (image == null) {
            XSSFPicture pic = pics.stream().filter(x -> {
                XSSFClientAnchor anchor = x.getClientAnchor();
                return anchor.getCol1() == 0 && anchor.getRow1() == index;
            }).findFirst().orElse(null);

            if (pic != null) {
                try {
                    byte[] data = convertToPngByteArray(pic.getPictureData());
                    return DrawingManager.getDrawingId(data);
                } catch (IOException ignored) {
                }
            }
        } else if (image.contains("same as")) {
            String otherPartNumber = image.split(" ")[2];

            BigInteger drawId = PartManager.getDrawingIdFromPartNumber(otherPartNumber);
            if (drawId != null && !drawId.equals(BigInteger.ZERO)) {
                return drawId;
            }
        }
        return null;
    }

    /**
     * Upserts every part from XLSFile into database
     * @param XLSFile - xls file of parts to upsert into database
     * @return true, if all parts were inserted correctly
     */
    private static boolean uploadXLStoDBS(XSSFWorkbook XLSFile) {
        XSSFSheet sheet = XLSFile.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();
        XSSFDrawing dp = sheet.createDrawingPatriarch();
        List<XSSFPicture> pics = dp.getShapes().stream().map(x -> (XSSFPicture) x).collect(Collectors.toList());

        Iterator<Row> rowIterator = sheet.rowIterator();
        rowIterator.next();
        int index = 0;

        try {
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                index++;
                Part part = new Part();

                Iterator<Cell> cellIterator = row.cellIterator();

                String image = nextString(dataFormatter, cellIterator);
                part.setDrawing_id(getDrawingId(image, index, pics));

                part.setPart_number(nextString(dataFormatter, cellIterator));
                Part partInDatabse = PartManager.getPartByPartNumber(part.getPart_number());
                if (partInDatabse != null)
                    part = partInDatabse;

                part.setPart_name_id(PartNameManager.getPartNameId(nextString(dataFormatter, cellIterator)));

                part.setCategory_id(CategoryManager.getCategoryId(nextString(dataFormatter, cellIterator)));

                part.setCustomer_id(CustomerManager.getCustomerId(nextString(dataFormatter, cellIterator)));

                String s;

                s = nextString(dataFormatter, cellIterator);
                if (s != null)
                    part.setRubber(Short.valueOf(s.split(" ")[0]));

                s = nextString(dataFormatter, cellIterator);
                part.setDiameter_AT(getDouble(s));

                s = nextString(dataFormatter, cellIterator);
                if (s != null)
                    part.setDiameter_AT_tol(s);

                s = nextString(dataFormatter, cellIterator);
                part.setLength_L_AT(getDouble(s));

                s = nextString(dataFormatter, cellIterator);
                if (s != null)
                    part.setLength_L_AT_tol(s);

                s = nextString(dataFormatter, cellIterator);
                part.setDiameter_IT(getDouble(s));

                s = nextString(dataFormatter, cellIterator);
                if (s != null)
                    part.setDiameter_IT_tol(s);

                s = nextString(dataFormatter, cellIterator);
                if (s != null)
                    try {
                        part.setLength_L_IT(Double.valueOf(s.replace(",", ".")));
                    } catch (NumberFormatException ignored) {
                    }

                s = nextString(dataFormatter, cellIterator);
                if (s != null)
                    part.setLength_L_IT_tol(s);

                s = nextString(dataFormatter, cellIterator);
                if (s != null)
                    try {
                        part.setDiameter_ZT(Double.valueOf(s));
                    } catch (NumberFormatException ignored) {
                    }

                s = nextString(dataFormatter, cellIterator);
                if (s != null)
                    part.setDiameter_ZT_tol(s);

                s = nextString(dataFormatter, cellIterator);
                if (s != null)
                    try {
                        part.setLength_L_ZT(Double.valueOf(s));
                    } catch (NumberFormatException ignored) {
                    }

                s = nextString(dataFormatter, cellIterator);
                if (s != null)
                    part.setLength_L_ZT_tol(s);

                s = nextString(dataFormatter, cellIterator);
                if (s != null)
                    try {
                        part.setCr_steg(Integer.valueOf(s.split("\\[")[0].replaceAll("[\\u00A0 ]", "")));
                    } catch (NumberFormatException ignored) {
                    }

                s = nextString(dataFormatter, cellIterator);
                if (s != null)
                    try {
                        part.setCr_niere(Short.valueOf(s.split("\\[")[0].replaceAll("[\\u00A0 ]", "")));
                    } catch (NumberFormatException ignored) {
                    }

                s = nextString(dataFormatter, cellIterator);
                if (s != null)
                    try {
                        part.setCa(Short.valueOf(s.split("\\[")[0].replaceAll("[\\u00A0 ]", "")));
                    } catch (NumberFormatException ignored) {
                    }

                s = nextString(dataFormatter, cellIterator);
                if (s != null)
                    try {
                        part.setCt(Double.valueOf(s.split("\\[")[0].replace(",", ".")));
                    } catch (NumberFormatException ignored) {
                    }

                s = nextString(dataFormatter, cellIterator);
                if (s != null)
                    try {
                        part.setCk(Double.valueOf(s.split("\\[")[0].replace(",", ".")));
                    } catch (NumberFormatException ignored) {
                    }

                part.upsert();
            }
        } catch (SQLException ignored) {
            return false;
        }
        return true;
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

            XSSFWorkbook workbook = new XSSFWorkbook(new File("./src/backend/testData.xlsx"));
            uploadXLStoDBS(workbook);
        } catch (IOException | InvalidFormatException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
