package ifmg.camoleze.application;

import ifmg.camoleze.entities.Airport;
import ifmg.camoleze.structs.ArrayList;

public class Main {
    public static void main(String[] args) {
        String filePath = "MalhaAereaUSA.csv";
        ArrayList<Airport> airports = Airport.readAirportsFromFile(filePath);

        for (Airport airport : airports) {
            System.out.println(airport);
        }
    }
}