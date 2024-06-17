package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс FlightFilterServiceImpl предоставляет реализацию интерфейса FlightFilterService,
 * предназначенный для фильтрации списка рейсов на основе различных критериев.
 */
public class FlightFilterServiceImpl implements FlightFilterService {

    @Override
    public void getAllFlights(List<Flight> flightList) {
        flightList.forEach(System.out::println);
    }

    @Override
    public List<Flight> departureBeforeCurrentTime(List<Flight> flightList) {
        List<Flight> beforeCurrentDay = flightList
                .stream()
                .filter(flight -> flight.getSegments()
                        .stream()
                        .anyMatch(segment -> segment.getDepartureDate().isBefore(LocalDateTime.now())))
                .toList();
        beforeCurrentDay
                .forEach(System.out::println);
        return beforeCurrentDay;
    }

    @Override
    public List<Flight> arrivalBeforeDeparture(List<Flight> flightList) {
        List<Flight> arriveBeforeDeparture = flightList
                .stream()
                .filter(flight -> flight.getSegments()
                        .stream()
                        .anyMatch(segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate())))
                .toList();
        arriveBeforeDeparture.forEach(System.out::println);
        return arriveBeforeDeparture;
    }

    @Override
    public List<Flight> timeThanMoreTwoHours(List<Flight> flightList) {
        List<Flight> moreTwoHoursList = new ArrayList<>();
        for (Flight flight : flightList) {
            List<Segment> segments = flight.getSegments();
            for (int i = 0; i < segments.size() - 1; i++) {
                LocalDateTime departureTime = segments.get(i + 1).getDepartureDate();
                LocalDateTime arrivalTime = segments.get(i).getArrivalDate();
                if (departureTime.isAfter(arrivalTime.plusHours(2))) {
                    moreTwoHoursList.add(flight);
                }
            }
        }
        moreTwoHoursList.forEach(System.out::println);
        return moreTwoHoursList;
    }
}
