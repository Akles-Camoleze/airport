package ifmg.camoleze.structs.graphs;

import ifmg.camoleze.requirements.Methods;

public class Vertex<T extends Methods> {
    private T data;
    private int entryDegree;
    private int exitDegree;
    private boolean visited;
    private double distance;

    public Vertex(T data) {
        this.data = data;
        this.entryDegree = 0;
        this.exitDegree = 0;
        this.visited = false;
        this.distance = Double.POSITIVE_INFINITY;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getEntryDegree() {
        return entryDegree;
    }

    public void setEntryDegree(int entryDegree) {
        this.entryDegree = entryDegree;
    }

    public int getExitDegree() {
        return exitDegree;
    }

    public void setExitDegree(int exitDegree) {
        this.exitDegree = exitDegree;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "data=" + data +
                ", degree=" + entryDegree +
                ", visited=" + visited +
                ", distance=" + distance +
                '}';
    }
}
