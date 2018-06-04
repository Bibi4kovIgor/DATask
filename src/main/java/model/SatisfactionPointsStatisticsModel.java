package model;

/**
 *
 * This is the model which is used for
 * processing points from marks
 *
 * */
public class SatisfactionPointsStatisticsModel {


    private int positivePointsCount = 0;
    private int negativePointsCount = 0;
    private int balancePoints = 0;

    public SatisfactionPointsStatisticsModel(int positivePointsCount, int negativePointsCount) {
        this.positivePointsCount = positivePointsCount;
        this.negativePointsCount = negativePointsCount;
        this.balancePoints = positivePointsCount - negativePointsCount;
    }

    public SatisfactionPointsStatisticsModel() {
    }

    public int getBallance(){
        setBalancePoints(getPositivePointsCount() + getNegativePointsCount());
        return getBalancePoints();
    }

    public int getPositivePointsCount() {
        return positivePointsCount;
    }

    public void setPositivePointsCount(int positivePointsCount) {
        this.positivePointsCount += positivePointsCount;
    }

    public int getNegativePointsCount() {
        return negativePointsCount;
    }

    public void setNegativePointsCount(int negativePointsCount) {
        this.negativePointsCount += negativePointsCount;
    }

    public int getBalancePoints() {
        return balancePoints;
    }

    public void setBalancePoints(int balancePoints) {
        this.balancePoints = balancePoints;
    }

}
