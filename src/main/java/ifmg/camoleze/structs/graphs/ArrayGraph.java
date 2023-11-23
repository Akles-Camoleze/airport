package ifmg.camoleze.structs.graphs;

import ifmg.camoleze.requirements.Methods;
import ifmg.camoleze.structs.lists.ArrayList;
import ifmg.camoleze.structs.queue.Queue;


public class ArrayGraph<K extends Methods, V extends Methods> implements Graph<K, V, ArrayList<ArrayList<V>>> {
    private final ArrayList<K> vertices;
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
    public ArrayGraph(ArrayList<K> vertices, boolean targeted) {
        this.vertices = vertices;
        this.edges = new ArrayList<>();
        this.targeted = targeted;
    }

    @Override
    public void addVertex(K vertex) {
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
    public void addEdge(K source, K destination, V value) {
        int sourceIndex = vertices.indexOf(source);
        int destinationIndex = vertices.indexOf(destination);

        if (sourceIndex == -1 || destinationIndex == -1) return;

        edges.get(sourceIndex).set(destinationIndex, value);

        if (!targeted) {
            edges.get(destinationIndex).set(sourceIndex, value);
        }
    }

    @Override
    public ArrayList<K> getVertices() {
        return vertices;
    }

    @Override
    public ArrayList<ArrayList<V>> getEdges() {
        return edges;
    }

    @Override
    public void processEdges(EdgeProcessor<K, V> edgeProcessor) {
        for (int i = 0; i < edges.size(); i++) {
            for (int j = 0; j < edges.get(i).size(); j++) {
                if (i != j && edges.get(i).get(j) != null) {
                    K source = vertices.get(i);
                    K destin = vertices.get(j);
                    edgeProcessor.process(source, edges.get(i).get(j), destin);
                }
            }
        }
    }

    public ArrayList<K> findPath(K start, K end) {
        int startIndex = vertices.indexOf(start);
        int endIndex = vertices.indexOf(end);
        return findPath(startIndex, endIndex);
    }

    public ArrayList<K> findPath(int startIndex, int endIndex) {
        boolean[] visited = new boolean[vertices.size()];
        int[] predecessor = new int[vertices.size()];
        Queue<K> queue = new Queue<>();

        if (startIndex == -1) {
            throw new IllegalArgumentException("Vértice inicial não encontrado no grafo.");
        }

        if (endIndex == -1) {
            throw new IllegalArgumentException("Vértice final não encontrado no grafo.");
        }

        queue.enqueue(vertices.get(startIndex));
        visited[startIndex] = true;
        predecessor[startIndex] = -1;

        while (!queue.isEmpty()) {
            K currentVertex = queue.dequeue();
            int currentIndex = vertices.indexOf(currentVertex);

            for (int i = 0; i < vertices.size(); i++) {
                if (!visited[i] && edges.get(currentIndex).get(i) != null) {
                    queue.enqueue(vertices.get(i));
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

    private ArrayList<K> buildPath(int[] predecessor, int start, int end) {
        ArrayList<K> path = new ArrayList<>();
        int current = end;

        while (current != start) {
            path.add(0, vertices.get(current));
            current = predecessor[current];
        }

        // Adiciona o vértice inicial ao caminho
        path.add(0, vertices.get(start));

        return path;
    }

}

