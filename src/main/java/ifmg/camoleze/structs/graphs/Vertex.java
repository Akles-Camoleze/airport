package ifmg.camoleze.structs.graphs;


public class Vertex<T> {
    private T data;
    private int entryDegree;
    private int exitDegree;


    public Vertex(T data) {
        this.data = data;
        this.entryDegree = 0;
        this.exitDegree = 0;
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


    @Override
    public String toString() {
        return "Vertex{" +
                "data=" + data +
                ", degree=" + entryDegree +
                '}';
    }
}
