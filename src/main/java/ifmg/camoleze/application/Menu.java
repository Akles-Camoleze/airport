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

import java.util.Scanner;
import java.util.function.Predicate;


public class Menu {

    private AirNetwork airNetwork;
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
        options.add("Mostrar todos voos com duração");
        options.add("Mostrar todos voos");
        options.add("Mostrar todas rotas");
        options.add("Mostrar todos vertices");
        options.add("Sair");
    }

    private void initialize(AirNetwork airNetwork) {
        this.airNetwork = airNetwork;
        flightLinkedGraph = airNetwork.getFlights();
        routeArrayGraph = airNetwork.getRoutes();
        vertices = airNetwork.getVertices();
    }

    public void setOption(int option) {
        this.option = option;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        switch (option) {
            case 1: {
                try {
                    System.out.print("Origem -> ");
                    int sourceIndex = getVertexIndex(sc.nextLine());
                    System.out.print("Destino -> ");
                    int destinIndex = getVertexIndex(sc.nextLine());
                    System.out.println(routeArrayGraph.findPath(sourceIndex, destinIndex));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            }
            case 2: {
                try {
                    System.out.print("Origem -> ");
                    Vertex<Airport> source = getVertex(sc.nextLine());
                    Predicate<Flight> flightPredicate = flight -> flight.getStops() == 0;
                    Predicate<ArrayList<Flight>> predicate = list -> list.filterReferenced(list, flightPredicate).size() > 0;
                    HashMap<Vertex<Airport>, ArrayList<Flight>> flights = flightLinkedGraph.getEdgesFromVertex(source, predicate);
                    System.out.println(flights);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            }
            case 3:
                try {
                    System.out.print("Origem -> ");
                    Vertex<Airport> source = getVertex(sc.nextLine());
                    System.out.print("Destino -> ");
                    Vertex<Airport> destin = getVertex(sc.nextLine());
                    DijkstraProcessor<Route> processor = Route::getDistance;
                    DijkstraAlgorithm<Airport, Route> dijkstraAlgorithm = new DijkstraAlgorithm<>(routeArrayGraph, processor);
                    System.out.println(dijkstraAlgorithm.dijkstra(source, destin));
                    System.out.println(airNetwork.getFastedFlight(source, destin));
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 4: {
                try {
                    System.out.print("Origem -> ");
                    Vertex<Airport> source = getVertex(sc.nextLine());
                    System.out.print("Destino -> ");
                    Vertex<Airport> destin = getVertex(sc.nextLine());
                    DijkstraProcessor<Route> processor = Route::getDistance;
                    DijkstraAlgorithm<Airport, Route> dijkstraAlgorithm = new DijkstraAlgorithm<>(routeArrayGraph, processor);
                    CriticalVertexAlgorithm<Airport, Route> algorithm = new CriticalVertexAlgorithm<>(dijkstraAlgorithm);
                    System.out.println(algorithm.findCriticalAirports(source));
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
                break;
            }
            case 5: {
                try {
                    System.out.print("Origem -> ");
                    Vertex<Airport> source = getVertex(sc.nextLine());
                    HamiltonianCircuit<Airport, Route, ArrayList<Route>> circuit = new HamiltonianCircuit<>(routeArrayGraph);
                    System.out.println(circuit.findHamiltonianCircuit(source));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            }
            case 6:
                try {
                    airNetwork.calculateAllFlightsDuration();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 7:
                try {
                    flightLinkedGraph.showEdges();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 8:
                try {
                    routeArrayGraph.showEdges();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 9:
                try {
                    System.out.println(vertices);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 0:
                System.exit(0);
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
            br.append((i + 1) % options.size()).append(" - ").append(options.get(i)).append('\n');
        }
        return br.toString();
    }
}
