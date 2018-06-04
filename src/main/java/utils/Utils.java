package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Class with different utils
 *
 * */
public class Utils {
    public static Iterator<Row> removeHeader(Iterator<Row> iterator){
        if (iterator.hasNext()) iterator.next();
        return iterator;
    }

    /**
     *  Write the output to a file
     *
     * */
    public static void writeSheetToFile (Workbook workbook, String fileName) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(new File(fileName));
        workbook.write(fileOut);
        fileOut.close();
        System.out.println("Excel file was updated");
    }

    /**
     * Set font style to added cells
     * @return CellStyle style
     * */
    public static CellStyle getFontStyle(Workbook workbook){
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        XSSFFont defaultFont= (XSSFFont) workbook.createFont();
        defaultFont.setFontHeightInPoints((short)14);
        defaultFont.setFontName("Times New Roman");
        defaultFont.setColor(IndexedColors.BLACK.getIndex());
        style.setFont(defaultFont);

        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        return style;
    }

    /**
     * Obtaining quantity of unique clients
     * @return Map<String, Long>
     * */
    public static Map<String, Long> getUniqUserQuan(String twitterChannel, Iterator<Row> rowIterator){
        Map<String, Long> uniqueUsersByCompany = new HashMap<>();

        List<String> uniqueUsers = new ArrayList<>();

        while(rowIterator.hasNext()){
            Row row = rowIterator.next();

            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                if (cell.getColumnIndex() == 1 && !cell.getStringCellValue().equals(twitterChannel)){
                    break;
                }

                if (cell.getColumnIndex() == 2 && !cell.getStringCellValue().equals(twitterChannel)){
                    uniqueUsers.add(cell.getStringCellValue());
                }
            }
        }
        uniqueUsersByCompany.put(twitterChannel, uniqueUsers.stream().distinct().count());


        return uniqueUsersByCompany;
    }

    /**
     * Obtaining clients' comments
     * @return HashMap<String, List<String>>
     * */
    public static HashMap<String, List<String>> getCommentsFromClients(XSSFWorkbook workbook, List<String> twitterChannelsUnique){
        HashMap<String, List<String>> twitterCommentsByCompany = new HashMap<>();
        String twitterComment = "";
        Iterator<Row> rowIterator = workbook.getSheet("Data").iterator();
        rowIterator = Utils.removeHeader(rowIterator);


        twitterChannelsUnique.forEach(channel -> twitterCommentsByCompany.put(channel, new ArrayList<String>()));
        while (rowIterator.hasNext()){
            Row row = rowIterator.next();

            Iterator<Cell> cellIterator = row.cellIterator();

            String companyName = "";
            String clientName = "";

            while (cellIterator.hasNext()){
                Cell cell = cellIterator.next();

                if (cell.getColumnIndex() == 1) companyName = cell.getStringCellValue();
                if (cell.getColumnIndex() == 2) clientName = cell.getStringCellValue();
                if (cell.getColumnIndex() == 8 && !companyName.equals(clientName)) {
                    twitterComment = cell.getStringCellValue();
                }
            }

            if(!twitterComment.equals("")){
                twitterCommentsByCompany.get(companyName).add(twitterComment);
                twitterComment = "";
            }
        }

        return twitterCommentsByCompany;
    }


    /**
     * Get column unique data from Excel file
     * @return List<String>
     *
     */
    public static List<String> getColumnData(XSSFWorkbook workbook, String sheetName, int columnIndex) {
        List<String> resultColumn = new ArrayList<>();
        Sheet sheet = workbook.getSheet(sheetName);
        sheet.forEach(row -> row.forEach(cell -> {
            if(sheet.getSheetName().equals(sheetName) && (cell.getColumnIndex() == columnIndex)){
                resultColumn.add(cell.toString());
            }

        }));

        //remove header
        resultColumn.remove(0);

        return resultColumn;
    }
}
