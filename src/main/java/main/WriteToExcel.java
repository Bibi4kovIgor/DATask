package main;

import model.PopularityStatisticsModel;
import model.SatisfactionMarksStatisticsModel;
import model.SatisfactionPointsStatisticsModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * This class contains methods for
 * working with Excel files
 *
 * */
public class WriteToExcel {


    /**
     *
     * This method using for writing Market Share statistics
     * to MarketShare sheet in the Excel file
     *
     * */
    public static void hashMapMarketShareToExcel(HashMap<String, Long> data, String fileName) throws IOException {
        FileInputStream fsIP = new FileInputStream(new File(fileName));
        XSSFWorkbook workbook = new XSSFWorkbook(fsIP);
        XSSFSheet sheet = workbook.getSheet("Market_Share");

        int rowStartIndex = 4;
        int cellStartIndex = 2;
        for (Map.Entry<String, Long> entry : data.entrySet()){
            Row row = sheet.getRow(rowStartIndex);

            Cell cellCompanyName = row.getCell(cellStartIndex);
            Cell cellCompanyValue =row.getCell(++cellStartIndex);


            // Create the cells if it doesn't exist
            if (cellCompanyName == null)
                cellCompanyName = row.createCell(cellStartIndex);

            if (cellCompanyValue == null)
                cellCompanyValue = row.createCell(cellStartIndex++);

            cellCompanyName.setCellType(CellType.STRING);
            cellCompanyName.setCellStyle(Utils.getFontStyle(workbook));
            cellCompanyName.setCellValue(entry.getKey());

            cellCompanyValue.setCellType(CellType.STRING);
            cellCompanyValue.setCellStyle(Utils.getFontStyle(workbook));
            cellCompanyValue.setCellValue(entry.getValue());

            rowStartIndex++;
            cellStartIndex--;
        }
        fsIP.close();
        Utils.writeSheetToFile(workbook, fileName);

    }


    /**
     *
     * This method using for writing statistics of
     * the popularity corresponding "E-Learning" companies
     * to the "Popularity" sheet of the Excel file
     *
     * */
    public static void hashMapPopularityToExcel(HashMap<String, PopularityStatisticsModel> data, String fileName) throws IOException {
        FileInputStream fsIP = new FileInputStream(new File(fileName));
        XSSFWorkbook workbook = new XSSFWorkbook(fsIP);
        XSSFSheet sheet = workbook.getSheet("Popularity");

        int rowStartIndex = 6;
        for (Map.Entry<String, PopularityStatisticsModel> entry : data.entrySet()){
            Row row = sheet.getRow(rowStartIndex);

            Cell cellCompanyName = row.getCell(2);
            Cell cellLikesValue = row.getCell(3);
            Cell cellRetweetsValue = row.getCell(4);
            Cell cellSumValue = row.getCell(5);

            // Create the cells if it doesn't exist
            if (cellCompanyName == null)
                cellCompanyName = row.createCell(2);

            if (cellLikesValue == null)
                cellLikesValue = row.createCell(3);

            if (cellRetweetsValue == null)
                cellRetweetsValue = row.createCell(4);

            if (cellLikesValue == null)
                cellLikesValue = row.createCell(5);

            cellCompanyName.setCellType(CellType.STRING);
            cellCompanyName.setCellStyle(Utils.getFontStyle(workbook));
            cellCompanyName.setCellValue(entry.getKey());

            cellLikesValue.setCellType(CellType.NUMERIC);
            cellLikesValue.setCellStyle(Utils.getFontStyle(workbook));
            cellLikesValue.setCellValue(entry.getValue().getLikeCount());

            cellRetweetsValue.setCellType(CellType.NUMERIC);
            cellRetweetsValue.setCellStyle(Utils.getFontStyle(workbook));
            cellRetweetsValue.setCellValue(entry.getValue().getRetweetCount());

            cellSumValue.setCellType(CellType.NUMERIC);
            cellSumValue.setCellStyle(Utils.getFontStyle(workbook));
            cellSumValue.setCellValue(entry.getValue().getLikeRetweetSum());

            rowStartIndex++;
        }
        fsIP.close();
        Utils.writeSheetToFile(workbook, fileName);

    }

    /**
     *
     * This method is using for writing
     * satisfactions statistics marks of the
     * clients experience in using
     * "E-Learning" services.
     * All data is writing to the "Satisfaction"
     * sheet of the Excel file
     *
     * */
    public static void hashMapSatisfactionMarksToExcel(HashMap<String, SatisfactionMarksStatisticsModel> data, String fileName) throws IOException {
        FileInputStream fsIP = new FileInputStream(new File(fileName));
        XSSFWorkbook workbook = new XSSFWorkbook(fsIP);
        XSSFSheet sheet = workbook.getSheet("Satisfaction");

        int rowStartIndex = 11;
        for (Map.Entry<String, SatisfactionMarksStatisticsModel> entry : data.entrySet()){
            Row row = sheet.getRow(rowStartIndex);

            Cell cellCompanyName = row.getCell(2);
            Cell cellVeryNegativeMarkValue = row.getCell(3);
            Cell cellNegativeMarkValue = row.getCell(4);
            Cell cellNeutralMarkValue = row.getCell(5);
            Cell cellPositiveMarkValue = row.getCell(6);
            Cell cellVeryPositiveMarkValue = row.getCell(7);
            Cell cellSumValue = row.getCell(8);

            // Create the cells if it doesn't exist
            if (cellCompanyName == null)
                cellCompanyName = row.createCell(2);

            if (cellVeryNegativeMarkValue == null)
                cellVeryNegativeMarkValue = row.createCell(3);

            if (cellNegativeMarkValue == null)
                cellNegativeMarkValue = row.createCell(4);

            if (cellNeutralMarkValue == null)
                cellNeutralMarkValue = row.createCell(5);

            if (cellPositiveMarkValue == null)
                cellPositiveMarkValue = row.createCell(6);

            if (cellVeryPositiveMarkValue == null)
                cellVeryPositiveMarkValue = row.createCell(7);

            if (cellSumValue == null)
                cellSumValue = row.createCell(8);

            cellCompanyName.setCellType(CellType.STRING);
            cellCompanyName.setCellStyle(Utils.getFontStyle(workbook));
            cellCompanyName.setCellValue(entry.getKey());

            cellVeryNegativeMarkValue.setCellType(CellType.NUMERIC);
            cellVeryNegativeMarkValue.setCellStyle(Utils.getFontStyle(workbook));
            cellVeryNegativeMarkValue.setCellValue(entry.getValue().getVeryNegativeCount());

            cellNegativeMarkValue.setCellType(CellType.NUMERIC);
            cellNegativeMarkValue.setCellStyle(Utils.getFontStyle(workbook));
            cellNegativeMarkValue.setCellValue(entry.getValue().getNegativeCount());

            cellNeutralMarkValue.setCellType(CellType.NUMERIC);
            cellNeutralMarkValue.setCellStyle(Utils.getFontStyle(workbook));
            cellNeutralMarkValue.setCellValue(entry.getValue().getNeutralCount());

            cellPositiveMarkValue.setCellType(CellType.NUMERIC);
            cellPositiveMarkValue.setCellStyle(Utils.getFontStyle(workbook));
            cellPositiveMarkValue.setCellValue(entry.getValue().getPositiveCount());

            cellVeryPositiveMarkValue.setCellType(CellType.NUMERIC);
            cellVeryPositiveMarkValue.setCellStyle(Utils.getFontStyle(workbook));
            cellVeryPositiveMarkValue.setCellValue(entry.getValue().getVeryPositiveCount());

            cellSumValue.setCellType(CellType.NUMERIC);
            cellSumValue.setCellStyle(Utils.getFontStyle(workbook));
            cellSumValue.setCellValue(entry.getValue().getSummaryMarksCount());

            rowStartIndex++;
        }
        fsIP.close();
        Utils.writeSheetToFile(workbook, fileName);

    }

    /**
     *
     * This method is using for writing
     * satisfactions statistics points of the
     * clients experience in using
     * "E-Learning" services.
     * All data is writing to the "Satisfaction"
     * sheet of the Excel file
     *
     * */
    public static void hashMapSatisfactionPointsToExcel(HashMap<String, SatisfactionPointsStatisticsModel> data, String fileName) throws IOException {
        FileInputStream fsIP = new FileInputStream(new File(fileName));
        XSSFWorkbook workbook = new XSSFWorkbook(fsIP);
        XSSFSheet sheet = workbook.getSheet("Satisfaction");

        int rowStartIndex = 5;
        for (Map.Entry<String, SatisfactionPointsStatisticsModel> entry : data.entrySet()){
            Row row = sheet.getRow(rowStartIndex);

            Cell cellCompanyName = row.getCell(2);
            Cell cellPositivePointsValue = row.getCell(3);
            Cell cellNegativePointsValue = row.getCell(4);
            Cell cellBallancePointsValue = row.getCell(5);

            // Create the cells if it doesn't exist
            if (cellCompanyName == null)
                cellCompanyName = row.createCell(2);

            if (cellPositivePointsValue == null)
                cellPositivePointsValue = row.createCell(3);

            if (cellNegativePointsValue == null)
                cellNegativePointsValue = row.createCell(4);


            if (cellBallancePointsValue == null)
                cellBallancePointsValue = row.createCell(5);


            cellCompanyName.setCellType(CellType.STRING);
            cellCompanyName.setCellStyle(Utils.getFontStyle(workbook));
            cellCompanyName.setCellValue(entry.getKey());

            cellPositivePointsValue.setCellType(CellType.NUMERIC);
            cellPositivePointsValue.setCellStyle(Utils.getFontStyle(workbook));
            cellPositivePointsValue.setCellValue(entry.getValue().getPositivePointsCount());

            cellNegativePointsValue.setCellType(CellType.NUMERIC);
            cellNegativePointsValue.setCellStyle(Utils.getFontStyle(workbook));
            cellNegativePointsValue.setCellValue(entry.getValue().getNegativePointsCount());

            cellBallancePointsValue.setCellType(CellType.NUMERIC);
            cellBallancePointsValue.setCellStyle(Utils.getFontStyle(workbook));
            cellBallancePointsValue.setCellValue(entry.getValue().getBalancePoints());

            rowStartIndex++;
        }
        fsIP.close();
        Utils.writeSheetToFile(workbook, fileName);

    }

}
