package utils;

/**
 *
 * This Enum is needed to proceed
 * marks, which was obtained from the mark service
 *
 * */

public enum MarksValuesEnum {
    VERY_NEGATIVE_MARK(0),
    NEGATIVE_MARK(1),
    NEUTRAL_MARK(2),
    POSITIVE_MARK(3),
    VERY_POSITIVE_MARK(4);

    private int markValue;

    MarksValuesEnum(int markValue) {
        this.markValue = markValue;
    }

    public int markValue() {
        return this.markValue;
    }

}
