package ifmg.camoleze.application;

import ifmg.camoleze.entities.AirNetwork;
import ifmg.camoleze.entities.Airport;
import ifmg.camoleze.entities.Flight;
import ifmg.camoleze.entities.Route;
import ifmg.camoleze.structs.graphs.ArrayGraph;
import ifmg.camoleze.structs.graphs.LinkedGraph;
import ifmg.camoleze.structs.graphs.Vertex;
import ifmg.camoleze.structs.graphs.algorithms.CriticalVertexAlgorithm;
import ifmg.camoleze.structs.graphs.algorithms.DijkstraAlgorithm;
import ifmg.camoleze.structs.graphs.algorithms.DijkstraProcessor;
import ifmg.camoleze.structs.graphs.algorithms.HamiltonianCircuit;
import ifmg.camoleze.structs.lists.ArrayList;
import ifmg.camoleze.structs.map.HashMap;

import java.util.function.Predicate;


public class Menu {

    private final ArrayList<String> options;

    private LinkedGraph<Airport, Flight> flightLinkedGraph;

    ArrayGraph<Airport, Route> routeArrayGraph;

    ArrayList<Vertex<Airport>> vertices;
    private int option;

    Menu(AirNetwork airNetwork) {
        initialize(airNetwork);
        options = new ArrayList<>();
        options.add("Caminho entre aeroportos");
        options.add("Voos diretos");
        options.add("Caminho menos custoso");
        options.add("Caminhos disponíveis");
        options.add("Caminho Hamiltoniano");
    }

    private void initialize(AirNetwork airNetwork) {
        flightLinkedGraph = airNetwork.getFlights();
        routeArrayGraph = airNetwork.getRoutes();
        vertices = airNetwork.getVertices();
    }

    public void setOption(int option) {
        this.option = option;
    }

    public void run() {
        switch (option) {
            case 1: {
                int sourceIndex = getVertexIndex("ABQ");
                int destinIndex = getVertexIndex("SFO");
                System.out.println(routeArrayGraph.findPath(sourceIndex, destinIndex));
            }
            break;
            case 2: {
                Vertex<Airport> source = getVertex("ABQ");
                Vertex<Airport> destin = getVertex("SFO");
                Predicate<Flight> flightPredicate = flight -> flight.getStops() == 0;
                Predicate<ArrayList<Flight>> predicate = list -> list.filterReferenced(list, flightPredicate).size() > 0;
                HashMap<Vertex<Airport>, ArrayList<Flight>> flights = flightLinkedGraph.getEdgesFromVertex(source, predicate);
                System.out.println(flights);
            }
            break;
            case 3:
                try {
                    Vertex<Airport> source = getVertex("ABQ");
                    Vertex<Airport> destin = getVertex("SFO");
                    DijkstraProcessor<Route> processor = Route::getDistance;
                    DijkstraAlgorithm<Airport, Route> dijkstraAlgorithm = new DijkstraAlgorithm<>(routeArrayGraph, processor);
                    System.out.println(dijkstraAlgorithm.dijkstra(source, destin));
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 4: {
                try {
                    Vertex<Airport> source = getVertex("ABQ");
                    Vertex<Airport> destin = getVertex("SFO");
                    DijkstraProcessor<Route> processor = Route::getDistance;
                    DijkstraAlgorithm<Airport, Route> dijkstraAlgorithm = new DijkstraAlgorithm<>(routeArrayGraph, processor);
                    CriticalVertexAlgorithm<Airport, Route> algorithm = new CriticalVertexAlgorithm<>(dijkstraAlgorithm);
                    System.out.println(algorithm.findCriticalAirports(source));
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
            }
            break;
            case 5: {
                Vertex<Airport> source = getVertex("ABQ");
                HamiltonianCircuit<Airport, Route, ArrayList<Route>> circuit = new HamiltonianCircuit<>(routeArrayGraph);
                System.out.println(circuit.findHamiltonianCircuit(source));
            }
            break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
    }

    private Vertex<Airport> getVertex(String abbr) {
        return vertices.find(element -> element.getData().getAbbreviation().equals(abbr));
    }

    private int getVertexIndex(String abbr) {
        return vertices.findIndex(element -> element.getData().getAbbreviation().equals(abbr));
    }

    @Override
    public String toString() {
        StringBuilder br = new StringBuilder();
        for (int i = 0; i < options.size(); i++) {
            br.append(i + 1).append(" - ").append(options.get(i)).append('\n');
        }
        return br.toString();
    }
}
