package ifmg.camoleze.structs.graphs;

import ifmg.camoleze.structs.lists.ArrayList;
import ifmg.camoleze.structs.queue.LinkedQueue;


public class ArrayGraph<K, V> implements Graph<K, V, ArrayList<V>> {
    private final ArrayList<Vertex<K>> vertices;
    private final ArrayList<ArrayList<V>> edges;
    private final boolean targeted;

    public ArrayGraph(boolean targeted) {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.targeted = targeted;
    }

    /**
     * Construtor dedicado a grafos com vertices compartilhados.
     */
    public ArrayGraph(ArrayList<Vertex<K>> vertices, boolean targeted) {
        this.vertices = vertices;
        this.edges = new ArrayList<>();
        this.targeted = targeted;
    }

    @Override
    public void addVertex(Vertex<K> vertex) {
        if (vertices.indexOf(vertex) == -1) {
            vertices.add(vertex);
        }

        this.edges.forEach(edge -> edge.add(null));

        ArrayList<V> edge = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            edge.add(null);
        }
        edges.add(edge);
    }

    @Override
    public void addEdge(Vertex<K> source, Vertex<K> destination, V value) {
        int sourceIndex = vertices.indexOf(source);
        int destinationIndex = vertices.indexOf(destination);

        if (sourceIndex == -1 || destinationIndex == -1) return;

        edges.get(sourceIndex).set(destinationIndex, value);
        source.setExitDegree(source.getExitDegree() + 1);
        destination.setEntryDegree(destination.getEntryDegree() + 1);

        if (!targeted) {
            edges.get(destinationIndex).set(sourceIndex, value);
            destination.setExitDegree(destination.getExitDegree() + 1);
            source.setEntryDegree(source.getEntryDegree() + 1);
        }
    }

    @Override
    public ArrayList<Vertex<K>> getVertices() {
        return vertices;
    }

    @Override
    public ArrayList<ArrayList<V>> getEdges() {
        return edges;
    }

    @Override
    public ArrayList<V> getEdgesFromVertex(Vertex<K> vertex) {
        int index = vertices.indexOf(vertex);

        if (index == -1) {
            throw new IndexOutOfBoundsException("Vertice não encontrado");
        }

        return edges.get(vertices.indexOf(vertex));
    }

    @Override
    public void processEdges(EdgeProcessor<K, V> edgeProcessor) {
        for (int i = 0; i < edges.size(); i++) {
            for (int j = 0; j < edges.get(i).size(); j++) {
                if (i != j && edges.get(i).get(j) != null) {
                    K source = vertices.get(i).getData();
                    K destin = vertices.get(j).getData();
                    edgeProcessor.process(source, edges.get(i).get(j), destin);
                }
            }
        }
    }

    public ArrayList<Vertex<K>> findPath(Vertex<K> start, Vertex<K> end) {
        int startIndex = vertices.indexOf(start);
        int endIndex = vertices.indexOf(end);
        return findPath(startIndex, endIndex);
    }

    public ArrayList<Vertex<K>> findPath(int startIndex, int endIndex) {
        boolean[] visited = new boolean[vertices.size()];
        int[] predecessor = new int[vertices.size()];
        LinkedQueue<Vertex<K>> linkedQueue = new LinkedQueue<>();

        if (startIndex == -1) {
            throw new IllegalArgumentException("Vértice inicial não encontrado no grafo.");
        }

        if (endIndex == -1) {
            throw new IllegalArgumentException("Vértice final não encontrado no grafo.");
        }

        linkedQueue.enqueue(vertices.get(startIndex));
        visited[startIndex] = true;
        predecessor[startIndex] = -1;

        while (!linkedQueue.isEmpty()) {
            Vertex<K> currentVertex = linkedQueue.dequeue();
            int currentIndex = vertices.indexOf(currentVertex);

            for (int i = 0; i < vertices.size(); i++) {
                if (!visited[i] && edges.get(currentIndex).get(i) != null) {
                    linkedQueue.enqueue(vertices.get(i));
                    visited[i] = true;
                    predecessor[i] = currentIndex;

                    if (i == endIndex) {
                        return buildPath(predecessor, startIndex, endIndex);
                    }
                }
            }
        }

        return new ArrayList<>();
    }

    private ArrayList<Vertex<K>> buildPath(int[] predecessor, int start, int end) {
        ArrayList<Vertex<K>> path = new ArrayList<>();
        int current = end;

        while (current != start) {
            path.add(0, vertices.get(current));
            current = predecessor[current];
        }

        path.add(0, vertices.get(start));

        return path;
    }

}

