package main;


import model.PopularityStatisticsModel;
import model.SatisfactionMarksStatisticsModel;
import model.SatisfactionPointsStatisticsModel;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Main {

    private static final String FILE_FULL_NAME = "src" +File.separator+ "main" +File.separator+ "resources"+File.separator+"twitter_list.xlsx";

    private static final Statistics statistics = new Statistics();
    private static final WriteToExcel writeToExcel  = new WriteToExcel();

    public static void main(String[] args) throws IOException {


        System.out.println(System.getProperty("user.dir"));
        FileInputStream file = new FileInputStream(new File(FILE_FULL_NAME));

        Workbook workbook = new XSSFWorkbook(file);
        List<String> sheetList = new ArrayList<>();
        workbook.forEach(sheet -> sheetList.add(sheet.getSheetName()));

        List<String> twitterChannels = Utils.getColumnData((XSSFWorkbook) workbook, "Data", 1);
        List<String> twitterUsers = Utils.getColumnData((XSSFWorkbook) workbook, "Data", 2);

        // Get unique data lists
        List<String> twitterChannelsUnique = new ArrayList<>();
        twitterChannels.stream().distinct().forEach(val -> twitterChannelsUnique.add(val));

        List<String> twitterUsersUnique = new ArrayList<>();
        twitterUsers.stream().distinct().forEach(val -> twitterUsersUnique.add(val));

        // Get Market Share statistics by companies and writing them to the *.xlsx file
        HashMap<String, Long> marketShareStatistics = statistics.getStatisticsByCompanies((XSSFWorkbook) workbook, twitterChannelsUnique);
        marketShareStatistics.forEach((key, value) -> System.out.println(key + "\t" + value));
        writeToExcel.hashMapMarketShareToExcel(marketShareStatistics, FILE_FULL_NAME);

        // Get Popularity statistics by companies and printing them to the *.xlsx file
        HashMap<String, PopularityStatisticsModel> popularityStatistics = statistics.getPopularityStatistics((XSSFWorkbook) workbook, twitterChannelsUnique);
        popularityStatistics.forEach((key, value) -> System.out.println(key +
                "\tlikesQuant " + value.getLikeCount() +
                "\tretweetsQuant " + value.getRetweetCount() +
                "\tretweetsLikesSum " + value.getLikeRetweetSum()));
        writeToExcel.hashMapPopularityToExcel(popularityStatistics, FILE_FULL_NAME);

        // Get clients' satisfaction values by comments
        HashMap<String, List<String>> twitterComments = Utils.getCommentsFromClients((XSSFWorkbook) workbook, twitterChannelsUnique);
        HashMap<String, SatisfactionMarksStatisticsModel> twitterCommentsMarks = statistics.getSatisfactionMarksStatistics(twitterComments, twitterChannelsUnique);
        twitterCommentsMarks.forEach((key, value) -> System.out.println(
                key + "\t" +
                value.getVeryNegativeCount() + "\t" +
                value.getNegativeCount() + "\t" +
                value.getNeutralCount() + "\t" +
                value.getPositiveCount() + "\t" +
                value.getVeryPositiveCount()
            )
        );
        writeToExcel.hashMapSatisfactionMarksToExcel(twitterCommentsMarks, FILE_FULL_NAME);

        // Get clients' satisfaction points by comments
        HashMap<String, SatisfactionPointsStatisticsModel> twitterCommentsPoints = statistics.getSatisfactionPointsStatistics(twitterCommentsMarks);
        twitterCommentsPoints.forEach((key, value) -> System.out.println(
                key + "\t" +
                value.getPositivePointsCount() + "\t" +
                value.getNegativePointsCount() + "\t" +
                value.getBallance()
            )
        );
        writeToExcel.hashMapSatisfactionPointsToExcel(twitterCommentsPoints, FILE_FULL_NAME);


        workbook.close();


    }

}
