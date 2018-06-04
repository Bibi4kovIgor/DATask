package main;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 *
 * Class which processing comments and set marks
 *
 * */
public class NLP {
    static final String NLP_PROPERTIES = "src" +File.separator+ "main" +File.separator+ "resources"+File.separator+"MyPropFile.properties";

    static StanfordCoreNLP pipeline;

    /**
     *
     * Initialize NLP library
     * */
    public static void init() {
        Properties properties = new Properties();
        InputStream input;
        try {
            input = new FileInputStream(NLP_PROPERTIES);
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        pipeline = new StanfordCoreNLP(properties);
    }

    /**
     *
     * Processing Sentiments and getting marks
     * */
    public static int findSentiment(String tweet) {

        int mainSentiment = 0;
        if (tweet != null && tweet.length() > 0) {
            int longest = 0;
            Annotation annotation = pipeline.process(tweet);
            for (CoreMap sentence : annotation
                    .get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence
                        .get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                String partText = sentence.toString();
                if (partText.length() > longest) {
                    mainSentiment = sentiment;
                    longest = partText.length();
                }

            }
        }
        return mainSentiment;
    }
}
