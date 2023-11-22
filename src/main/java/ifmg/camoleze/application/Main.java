package ifmg.camoleze.application;

import ifmg.camoleze.entities.AirNetwork;
import ifmg.camoleze.entities.Airport;
import ifmg.camoleze.entities.Route;
import ifmg.camoleze.structs.ArrayList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    private static final AirNetwork airNetwork = new AirNetwork();

    public static void readAirportsFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().equals("!")) break;
                if (!line.trim().startsWith("#")) {
                    String[] values = line.split(";");
                    Airport airport = Airport.createAirportFromValues(values);
                    airNetwork.addVertex(airport);
                }
            }
            int id = 0;
            while ((line = br.readLine()) != null) {
                if (line.trim().equals("!")) break;
                if (!line.trim().startsWith("#")) {
                    String[] values = line.split(";");
                    Airport source = null, destin = null;
                    for (Airport airport : airNetwork.getVertices()) {
                        if (source != null && destin != null) break;
                        String abbr = airport.getAbbreviation();
                        if (abbr.equals(values[0])) {
                            source = airport;
                        } else if (abbr.equals(values[1])) {
                            destin = airport;
                        }
                    }
                    if (source != null && destin != null) {
                        //Fórmula para calcular a distância entre dois pontos: raiz((xb - xa)² + (yb - ya)²)

                        double x = Math.pow(destin.getLongitude() - source.getLongitude(), 2);
                        double y = Math.pow(destin.getLatitude() - source.getLatitude(), 2);
                        int distance = (int) Math.sqrt(x + y);
                        Route route = new Route(id++, distance);
                        airNetwork.addEdge(source, destin, route);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(airNetwork.getVertices().size());
        }
    }

    public static void main(String[] args) {

        String filePath = "MalhaAereaUSA.csv";
        readAirportsFromFile(filePath);

        ArrayList<ArrayList<Route>> edges = airNetwork.getRoutes().getEdges();
        ArrayList<Airport> vertices = airNetwork.getRoutes().getVertices();
        for (int i = 0; i < edges.size(); i++) {
            for (int j = 0; j < edges.get(i).size(); j++) {
                if (i != j && edges.get(i).get(j) != null) {
                    String abbrSource = vertices.get(i).getAbbreviation();
                    String abbrDestin = vertices.get(j).getAbbreviation();
                    int distance = edges.get(i).get(j).getDistance();
                    System.out.printf("%s-->%d-->%s\n", abbrSource, distance, abbrDestin);
                }
            }
        }
    }
}