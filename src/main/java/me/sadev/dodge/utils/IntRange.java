package me.sadev.dodge.utils;

public class IntRange {
    private final double min;
    private final double max;

    public IntRange(double number1, double number2) {
        super();
        if (number2 <= number1) {
            this.min = number2;
            this.max = number1;
        } else {
            this.min = number1;
            this.max = number2;
        }
    }

    public boolean containsNumber(Number number) {
        if (number == null) {
            return false;
        }
        return containsInteger(number.intValue());
    }

    public boolean containsInteger(int value) {
        return value >= min && value <= max;
    }
}
