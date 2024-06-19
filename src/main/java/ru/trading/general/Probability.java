package ru.trading.general;

public class Probability {
    private float value;

    public Probability(float value) {
        if (!isProbability(value)) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        if (!isProbability(value)) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    private boolean isProbability(float value) {
        return value <= 1 && value >= 0;
    }
}
