package uaic.fii.client.aggregators;


public class Aggregator {
    private double sum;
    private int count;

    public Aggregator() {
        this.sum = 0.0;
        this.count = 0;
    }

    public Aggregator(double initSum, int initCount) {
        this.sum = initSum;
        this.count = initCount;
    }

    public Aggregator addToSum(double value) {
        sum += value;
        count++;
        return this;
    }

    public double getAverage() {
        return count > 0 ? sum / count : 0.0;
    }

    @Override
    public String toString() {
        return "Aggregator{" +
                "sum=" + sum +
                ", count=" + count +
                '}';
    }

    public double getSum() {
        return sum;
    }

    public int getCount() {
        return count;
    }
}