package utils;

/**
 * This Enum is needed to get points by comments,
 * which was got via mark service
 * */

public enum PointsValuesEnum {
    VERY_NEGATIVE_MARK(-2),
    NEGATIVE_MARK(-1),
    NEUTRAL_MARK(0),
    POSITIVE_MARK(1),
    VERY_POSITIVE_MARK(2);

    private int markPoint;

    PointsValuesEnum(int markPoint) {
        this.markPoint = markPoint;
    }

    public int value() {
        return this.markPoint;
    }

}
