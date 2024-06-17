package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;

import java.util.List;

public interface FlightFilterService {

    /**
     * Выводит информацию о всех рейсах из предоставленного списка.
     *
     * @param flightList Список рейсов для вывода.
     */
    void getAllFlights(List<Flight> flightList);

    /**
     * Фильтрует и возвращает рейсы, у которых хотя бы один сегмент вылетает до текущего момента времени.
     *
     * @param flightList Список рейсов для фильтрации.
     * @return Список отфильтрованных рейсов, соответствующих критерию.
     */
    List<Flight> departureBeforeCurrentTime(List<Flight> flightList);

    /**
     * Фильтрует и возвращает рейсы, у которых хотя бы один сегмент имеет время прибытия до времени вылета.
     *
     * @param flightList Список рейсов для фильтрации.
     * @return Список отфильтрованных рейсов, соответствующих критерию.
     */
    List<Flight> arrivalBeforeDeparture(List<Flight> flightList);

    /**
     * Фильтрует и возвращает рейсы, у которых время на земле между сегментами превышает два часа.
     *
     * @param flightList Список рейсов для фильтрации.
     * @return Список отфильтрованных рейсов, соответствующих критерию.
     */
    List<Flight> timeThanMoreTwoHours(List<Flight> flightList);
}
