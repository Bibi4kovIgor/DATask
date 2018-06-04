package main;

import model.PopularityStatisticsModel;
import model.SatisfactionMarksStatisticsModel;
import model.SatisfactionPointsStatisticsModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.MarksValuesEnum;
import utils.PointsValuesEnum;
import utils.Utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class is using for processing statistics
 * values which was obtained from the statistics Data
 *
 * */
public class Statistics {

    /**
     *
     * This method is obtain statistics by companies
     *
     *
     * */
    public static HashMap<String, Long> getStatisticsByCompanies(XSSFWorkbook workbook, List<String> twitterChannelsUnique) {
        // Get Statistics
        HashMap<String, Long> usageStatistics = new HashMap<>();
        usageStatistics.put("MHEducation", (long) 0);
        usageStatistics.put("Pearson", (long) 0);
        for (String twitterChannel : twitterChannelsUnique) {
            Iterator<Row> rowIterator = workbook.getSheet("Data").iterator();
            rowIterator = Utils.removeHeader(rowIterator);
            Utils.getUniqUserQuan(twitterChannel, rowIterator).forEach((key, value) -> {
                        if(key.equals("MHEducation") || key.equals("mhhighered")) usageStatistics.put("MHEducation", usageStatistics.get("MHEducation") + value);
                        if(key.equals("PearsonSupport") || key.equals("pearson")) usageStatistics.put("Pearson", usageStatistics.get("Pearson") + value);

                    }

            );
        }
        return usageStatistics;
    }

    /**
     *
     * Get popularity statistics from data
     *
     * */
    public static HashMap<String, PopularityStatisticsModel> getPopularityStatistics(XSSFWorkbook workbook, List<String> twitterChannelsUnique){
        HashMap<String, PopularityStatisticsModel> popularityResult = new HashMap<>();
        HashMap<String, PopularityStatisticsModel> popularityResultByCompanies = new HashMap<>();
        Iterator<Row> rowIterator = workbook.getSheet("Data").iterator();

        rowIterator = Utils.removeHeader(rowIterator);

        // Initialize HashMap by twitterChannels
        twitterChannelsUnique.forEach(channel -> popularityResult.put(channel, new PopularityStatisticsModel()));
        while(rowIterator.hasNext()){
            Row row = rowIterator.next();

            String companyName = "";
            int retweetsQuant = 0;
            int likesQuant = 0;

            Iterator<Cell> cellIterator = row.iterator();
            while(cellIterator.hasNext()){
                Cell cell = cellIterator.next();

                if (cell.getColumnIndex() == 1){ companyName = cell.getStringCellValue(); }
                if (cell.getColumnIndex() == 5){ retweetsQuant = (int)cell.getNumericCellValue(); }
                if (cell.getColumnIndex() == 6){ likesQuant = (int)cell.getNumericCellValue(); }
            }

            popularityResult.get(companyName).setLikeCount(likesQuant);
            popularityResult.get(companyName).setRetweetCount(retweetsQuant);
            popularityResult.get(companyName).likeRetweetSum();

        }

        // Get Popularity statistics by companies
        popularityResultByCompanies.put("MHEducation", new PopularityStatisticsModel());
        popularityResultByCompanies.put("Pearson", new PopularityStatisticsModel());
        for (Map.Entry<String, PopularityStatisticsModel> entry : popularityResult.entrySet()){
            if (entry.getKey().equals("MHEducation") || entry.getKey().equals("mhhighered")){
                popularityResultByCompanies.get("MHEducation").setRetweetCount(entry.getValue().getRetweetCount());
                popularityResultByCompanies.get("MHEducation").setLikeCount(entry.getValue().getLikeCount());
                popularityResultByCompanies.get("MHEducation").likeRetweetSum();
            }
        }

        for (Map.Entry<String, PopularityStatisticsModel> entry : popularityResult.entrySet()){
            if (entry.getKey().equals("PearsonSupport") || entry.getKey().equals("pearson")){
                popularityResultByCompanies.get("Pearson").setRetweetCount(entry.getValue().getRetweetCount());
                popularityResultByCompanies.get("Pearson").setLikeCount(entry.getValue().getLikeCount());
                popularityResultByCompanies.get("Pearson").likeRetweetSum();
            }
        }

        return popularityResultByCompanies;
    }


    /**
     *
     * Obtaining marks and process statistics
     *
     * */
    public static HashMap<String, SatisfactionMarksStatisticsModel> getSatisfactionMarksStatistics(HashMap<String, List<String>> statisticsValues, List<String> twitterChannelsUnique){
        HashMap<String, SatisfactionMarksStatisticsModel> satisfactionMarkResult = new HashMap<>();
        twitterChannelsUnique.forEach(channel -> satisfactionMarkResult.put(channel, new SatisfactionMarksStatisticsModel()));

        // Analyzing tweets
        NLP.init();
        for (Map.Entry<String, List<String>> entry : statisticsValues.entrySet()){
            for(String comment : entry.getValue()){
                MarksValuesEnum marksValuesEnum = MarksValuesEnum.values()[NLP.findSentiment(comment)];
                switch (marksValuesEnum){
                    case VERY_NEGATIVE_MARK:
                        satisfactionMarkResult.get(entry.getKey()).veryNegativeCountIncrement();
                        break;

                    case NEGATIVE_MARK:
                        satisfactionMarkResult.get(entry.getKey()).negativeCountIncrement();
                        break;

                    case NEUTRAL_MARK:
                        satisfactionMarkResult.get(entry.getKey()).neutralCountIncrement();
                        break;

                    case POSITIVE_MARK:
                        satisfactionMarkResult.get(entry.getKey()).positiveCountIncrement();
                        break;

                    case VERY_POSITIVE_MARK:
                        satisfactionMarkResult.get(entry.getKey()).veryPositiveCountIncrement();
                        break;

                }

            }
        }

        // Get statistics by companies
        HashMap<String, SatisfactionMarksStatisticsModel> satisfactionMarkResultByCompanies = new HashMap<>();

        // Get Popularity statistics by companies
        satisfactionMarkResultByCompanies.put("MHEducation", new SatisfactionMarksStatisticsModel());
        satisfactionMarkResultByCompanies.put("Pearson", new SatisfactionMarksStatisticsModel());

        for (Map.Entry<String, SatisfactionMarksStatisticsModel> entry : satisfactionMarkResult.entrySet()){
            if (entry.getKey().equals("MHEducation") || entry.getKey().equals("mhhighered")){
                satisfactionMarkResultByCompanies.get("MHEducation").veryNegativeCountSum(entry.getValue().getVeryNegativeCount());
                satisfactionMarkResultByCompanies.get("MHEducation").negativeCountSum(entry.getValue().getNegativeCount());
                satisfactionMarkResultByCompanies.get("MHEducation").neutralCountSum(entry.getValue().getNeutralCount());
                satisfactionMarkResultByCompanies.get("MHEducation").positiveCountSum(entry.getValue().getPositiveCount());
                satisfactionMarkResultByCompanies.get("MHEducation").veryPositiveCountSum(entry.getValue().getVeryPositiveCount());
            }

            if (entry.getKey().equals("PearsonSupport") || entry.getKey().equals("pearson")){
                satisfactionMarkResultByCompanies.get("Pearson").veryNegativeCountSum(entry.getValue().getVeryNegativeCount());
                satisfactionMarkResultByCompanies.get("Pearson").negativeCountSum(entry.getValue().getNegativeCount());
                satisfactionMarkResultByCompanies.get("Pearson").neutralCountSum(entry.getValue().getNeutralCount());
                satisfactionMarkResultByCompanies.get("Pearson").positiveCountSum(entry.getValue().getPositiveCount());
                satisfactionMarkResultByCompanies.get("Pearson").veryPositiveCountSum(entry.getValue().getVeryPositiveCount());
            }
        }

        return satisfactionMarkResultByCompanies;
    }

    /**
     * Get satisfaction processing from marks as points
     *
     * */
    public static HashMap<String, SatisfactionPointsStatisticsModel> getSatisfactionPointsStatistics(HashMap<String, SatisfactionMarksStatisticsModel> satisfactionMarks){
        HashMap<String, SatisfactionPointsStatisticsModel> satisfactionPointsResult = new HashMap<>();
        satisfactionMarks.forEach((key, value) -> satisfactionPointsResult.put(key, new SatisfactionPointsStatisticsModel()));

        // Calculate points
        for(Map.Entry<String, SatisfactionMarksStatisticsModel> satisfactionMark : satisfactionMarks.entrySet()){
            satisfactionPointsResult.get(satisfactionMark.getKey())
                    .setNegativePointsCount(satisfactionMark.getValue().getVeryNegativeCount() * PointsValuesEnum.VERY_NEGATIVE_MARK.value());
            satisfactionPointsResult.get(satisfactionMark.getKey())
                    .setNegativePointsCount(satisfactionMark.getValue().getNegativeCount() * PointsValuesEnum.NEGATIVE_MARK.value());
            satisfactionPointsResult.get(satisfactionMark.getKey())
                    .setPositivePointsCount(satisfactionMark.getValue().getPositiveCount() * PointsValuesEnum.POSITIVE_MARK.value());
            satisfactionPointsResult.get(satisfactionMark.getKey())
                    .setPositivePointsCount(satisfactionMark.getValue().getVeryPositiveCount() * PointsValuesEnum.VERY_POSITIVE_MARK.value());
        }

        return satisfactionPointsResult;
    }

}
