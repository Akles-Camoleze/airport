package ifmg.camoleze.application;

import ifmg.camoleze.entities.AirNetwork;
import ifmg.camoleze.entities.Airport;
import ifmg.camoleze.entities.Flight;
import ifmg.camoleze.entities.Route;
import ifmg.camoleze.structs.graphs.*;
import ifmg.camoleze.structs.graphs.algorithms.*;
import ifmg.camoleze.structs.lists.ArrayList;
import ifmg.camoleze.structs.map.HashMap;
import ifmg.camoleze.utils.TimeConverterUtil;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.Predicate;


public class Main {
    private static final AirNetwork airNetwork = new AirNetwork();

    public static void readFlightsFromFile(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.trim().startsWith("#")) {
                String[] values = line.split(";");

                Vertex<Airport> source = airNetwork.getVertices()
                        .find(element -> element.getData().getAbbreviation().equals(values[2]));

                Vertex<Airport> destin = airNetwork.getVertices()
                        .find(element -> element.getData().getAbbreviation().equals(values[4]));

                if (source != null && destin != null) {
                    Flight flight = new Flight(Integer.parseInt(values[1]));
                    flight.setAirline(values[0]);
                    flight.setDeparture(TimeConverterUtil.convertStringToTime(values[3]));
                    flight.setArrival(TimeConverterUtil.convertStringToTime(values[5]));
                    flight.setStops(Integer.parseInt(values[6]));
                    airNetwork.getFlights().addEdge(source, destin, flight);
                }

            }
        }
    }

    public static void readRoutesFromFile(BufferedReader br) throws IOException {
        int id = 0;
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().equals("!")) break;
            if (!line.trim().startsWith("#")) {
                String[] values = line.split(";");

                Vertex<Airport> source = airNetwork.getVertices()
                        .find(element -> element.getData().getAbbreviation().equals(values[0]));

                Vertex<Airport> destin = airNetwork.getVertices()
                        .find(element -> element.getData().getAbbreviation().equals(values[1]));

                if (source != null && destin != null) {
                    double x = Math.pow(destin.getData().getLongitude() - source.getData().getLongitude(), 2);
                    double y = Math.pow(destin.getData().getLatitude() - source.getData().getLatitude(), 2);
                    BigDecimal distance = BigDecimal.valueOf(Math.sqrt(x + y));
                    distance = distance.setScale(2, RoundingMode.HALF_UP);
                    Route route = new Route(id++, distance);
                    airNetwork.getRoutes().addEdge(source, destin, route);
                }
            }
        }
    }

    public static void readAirportsFromFile(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().equals("!")) break;
            if (!line.trim().startsWith("#")) {
                String[] values = line.split(";");
                Airport airport = Airport.createAirportFromValues(values);
                airNetwork.addVertex(airport);
            }
        }
    }

    public static void readFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            readAirportsFromFile(br);
            readRoutesFromFile(br);
            readFlightsFromFile(br);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        String filePath = "MalhaAereaUSA.csv";
        readFromFile(filePath);

        Scanner sc = new Scanner(System.in);
        Menu menu = new Menu(airNetwork);
        String start = "-".repeat(40);
        String title = "Saida";
        String end = start.repeat(2) + "-".repeat(title.length());

        while (true) {
            System.out.println(menu);
            System.out.print("-> ");
            menu.setOption(sc.nextInt());
            System.out.println(start + title + start);
            menu.run();
            System.out.println(end);
        }

    }

}