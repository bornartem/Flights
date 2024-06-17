package com.gridnine.testing;

import com.gridnine.testing.builder.FlightBuilder;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.service.FlightFilterServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Flight> flights = FlightBuilder.createFlights();

        FlightFilterServiceImpl filterService = new FlightFilterServiceImpl();

        System.out.println("All flights:");
        filterService.getAllFlights(flights);
        System.out.println();

        System.out.println("Flights with departure before current time:");
        filterService.departureBeforeCurrentTime(flights);
        System.out.println();

        System.out.println("Flights with arrival before departure:");
        filterService.arrivalBeforeDeparture(flights);
        System.out.println();

        System.out.println("Flights with time than more two hours:");
        filterService.timeThanMoreTwoHours(flights);
        System.out.println();
    }
}