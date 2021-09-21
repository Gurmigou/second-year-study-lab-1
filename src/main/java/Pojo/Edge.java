package Pojo;

public class Edge {
    private final int number;
    private final double probability;

    public Edge(int number, double probability) {
        if (probability < 0 || probability > 1)
            throw new IllegalArgumentException(probability + " must be >= 0 and <= 1");

        this.number = number;
        this.probability = probability;
    }

    public int getNumber() {
        return number;
    }

    public double getProbability() {
        return probability;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "number=" + number +
                ", probability=" + probability +
                '}';
    }
}
