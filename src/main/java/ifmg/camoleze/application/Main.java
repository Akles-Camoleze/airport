package ifmg.camoleze.application;

import ifmg.camoleze.entities.AirNetwork;
import ifmg.camoleze.entities.Airport;
import ifmg.camoleze.entities.Flight;
import ifmg.camoleze.entities.Route;
import ifmg.camoleze.structs.EdgeProcessor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    private static final AirNetwork airNetwork = new AirNetwork();

    public static void readAirportsFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Lendo Aeroportos
            while ((line = br.readLine()) != null) {
                if (line.trim().equals("!")) break;
                if (!line.trim().startsWith("#")) {
                    String[] values = line.split(";");
                    Airport airport = Airport.createAirportFromValues(values);
                    airNetwork.addVertex(airport);
                }
            }

            // Lendo Rotas
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
                        airNetwork.getRoutes().addEdge(source, destin, route);
                    }
                }
            }

            //Lendo Voos
            while ((line = br.readLine()) != null) {
                if (!line.trim().startsWith("#")) {
                     String[] values = line.split(";");

                    Airport source = null, destin = null;
                    for (Airport airport : airNetwork.getVertices()) {
                        if (source != null && destin != null) break;
                        String abbr = airport.getAbbreviation();
                        if (abbr.equals(values[2])) {
                            source = airport;
                        } else if (abbr.equals(values[4])) {
                            destin = airport;
                        }
                    }

                    if (source != null && destin != null) {
                        Flight flight = new Flight(Integer.parseInt(values[1]));
                        flight.setAirline(values[0]);
                        flight.setDeparture(values[3]);
                        flight.setArrival(values[5]);
                        flight.setStops(Integer.parseInt(values[6]));
                        airNetwork.getFlights().addEdge(source, destin, flight);
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
        airNetwork.getFlights().showEdges();
        airNetwork.getRoutes().showEdges();
    }
}