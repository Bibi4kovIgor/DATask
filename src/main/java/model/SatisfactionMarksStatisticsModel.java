package model;

/**
 *
 * This model is using for processing marks,
 * which was obtained from the marking service
 *
 * */

public class SatisfactionMarksStatisticsModel {

    public int getVeryNegativeCount() {
        return veryNegativeCount;
    }

    private int veryNegativeCount = 0;
    private int negativeCount = 0;
    private int neutralCount = 0;
    private int positiveCount = 0;
    private int veryPositiveCount = 0;


    public SatisfactionMarksStatisticsModel(int veryNegativeCount, int negativeCount, int neutralCount, int positiveCount, int veryPositiveCount) {
        this.veryNegativeCount = veryNegativeCount;
        this.negativeCount = negativeCount;
        this.neutralCount = neutralCount;
        this.positiveCount = positiveCount;
        this.veryPositiveCount = veryPositiveCount;
    }

    public SatisfactionMarksStatisticsModel() {
    }

    public void veryNegativeCountIncrement(){
        setVeryNegativeCount(++this.veryNegativeCount);
    }

    public void negativeCountIncrement(){
        setNegativeCount(++this.negativeCount);
    }

    public void neutralCountIncrement(){
        setNeutralCount(++this.neutralCount);
    }

    public void positiveCountIncrement(){
        setPositiveCount(++this.positiveCount);
    }

    public void veryPositiveCountIncrement(){
        setVeryPositiveCount(++this.veryPositiveCount);
    }

    public void veryNegativeCountSum(int value){
        this.veryNegativeCount += value;
    }

    public void negativeCountSum(int value){
        this.negativeCount += value;
    }

    public void neutralCountSum(int value){
        this.neutralCount += value;
    }

    public void positiveCountSum(int value){
        this.positiveCount += value;
    }

    public void veryPositiveCountSum(int value){
        this.veryPositiveCount += value;
    }

    public int getSummaryMarksCount(){
        return getVeryNegativeCount() +
               getNegativeCount() +
               getNeutralCount() +
               getPositiveCount() +
               getVeryPositiveCount();

    }


    public void setVeryNegativeCount(int veryNegativeCount) {
        this.veryNegativeCount = veryNegativeCount;
    }

    public int getNegativeCount() {
        return negativeCount;
    }

    public void setNegativeCount(int negativeCount) {
        this.negativeCount = negativeCount;
    }

    public int getNeutralCount() {
        return neutralCount;
    }

    public void setNeutralCount(int neutralCount) {
        this.neutralCount = neutralCount;
    }

    public int getPositiveCount() {
        return positiveCount;
    }

    public void setPositiveCount(int positiveCount) {
        this.positiveCount = positiveCount;
    }

    public int getVeryPositiveCount() {
        return veryPositiveCount;
    }

    public void setVeryPositiveCount(int veryPositiveCount) {
        this.veryPositiveCount = veryPositiveCount;
    }
}
