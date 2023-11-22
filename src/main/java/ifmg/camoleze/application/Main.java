package ifmg.camoleze.application;

import ifmg.camoleze.entities.AirNetwork;
import ifmg.camoleze.entities.Airport;
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(airNetwork.getVertices().size());
        }
    }

    public static void main(String[] args) {
        //Fórmula para calcular a distância entre dois pontos: raiz((xb - xa)² + (yb - ya)²)

        String filePath = "MalhaAereaUSA.csv";
        readAirportsFromFile(filePath);

        for (Airport airport : airNetwork.getVertices()) {
            System.out.println(airport);
        }
    }
}