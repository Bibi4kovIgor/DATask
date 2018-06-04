package model;

/**
 * This model is using to process
 * "Popularity" companies statistics
 *
 * */
public class PopularityStatisticsModel {
    private int likeCount = 0;
    private int retweetCount = 0;
    private int likeRetweetSum = 0;

    public PopularityStatisticsModel(int likeCount, int retweetCount) {
        this.likeCount = likeCount;
        this.retweetCount = retweetCount;
        this.likeRetweetSum = likeCount + retweetCount;

    }

    public PopularityStatisticsModel() {
    }

    public void likeRetweetSum(){
        setLikeRetweetSum(getLikeCount() + getRetweetCount());
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount += likeCount;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) { this.retweetCount += retweetCount; }

    public int getLikeRetweetSum() { return likeRetweetSum; }

    public void setLikeRetweetSum(int likeRetweetSum) { this.likeRetweetSum = likeRetweetSum; }
}
