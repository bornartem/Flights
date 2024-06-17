import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import com.gridnine.testing.service.FlightFilterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class FlightFilterServiceImplTest {

    private FlightFilterServiceImpl flightService;
    private LocalDateTime fixedCurrentTime;

    @BeforeEach
    public void setUp() {
        flightService = new FlightFilterServiceImpl();
        fixedCurrentTime = LocalDateTime.of(2024, 6, 17, 10, 0);
    }

    private Segment createSegment(LocalDateTime departure, LocalDateTime arrival) {
        return new Segment(departure, arrival);
    }

    @Test
    void departureBeforeCurrentTime() {

        Flight futureFlight = new Flight(List.of(
                createSegment(fixedCurrentTime.plusDays(1), fixedCurrentTime.plusDays(1).plusHours(2))
        ));
        Flight pastFlight = new Flight(List.of(
                createSegment(fixedCurrentTime.minusDays(1), fixedCurrentTime.minusDays(1).plusHours(2))
        ));
        Flight mixedFlight = new Flight(List.of(
                createSegment(fixedCurrentTime.minusDays(1), fixedCurrentTime.minusDays(1).plusHours(2)),
                createSegment(fixedCurrentTime.plusDays(1), fixedCurrentTime.plusDays(1).plusHours(2))
        ));

        List<Flight> flightList = List.of(futureFlight, pastFlight, mixedFlight);

        FlightFilterServiceImpl service = new FlightFilterServiceImpl();

        List<Flight> beforeCurrentDayFlights = service.departureBeforeCurrentTime(flightList);

        assertTrue(beforeCurrentDayFlights.contains(pastFlight));
        assertTrue(beforeCurrentDayFlights.contains(mixedFlight));
        assertFalse(beforeCurrentDayFlights.contains(futureFlight));
        assertEquals(2, beforeCurrentDayFlights.size());
    }

    @Test
    public void testArrivalBeforeDeparture() {

        Flight normalFlight = new Flight(List.of(
                createSegment(fixedCurrentTime.minusHours(4), fixedCurrentTime.minusHours(2)),
                createSegment(fixedCurrentTime.plusHours(1), fixedCurrentTime.plusHours(3))
        ));
        Flight abnormalFlight = new Flight(List.of(
                createSegment(fixedCurrentTime.plusHours(3), fixedCurrentTime.plusHours(1))
        ));
        Flight mixedFlight = new Flight(List.of(
                createSegment(fixedCurrentTime.minusHours(4), fixedCurrentTime.minusHours(2)),
                createSegment(fixedCurrentTime.plusHours(3), fixedCurrentTime.plusHours(1))
        ));

        List<Flight> inputFlights = List.of(normalFlight, abnormalFlight, mixedFlight);

        List<Flight> result = flightService.arrivalBeforeDeparture(inputFlights);

        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of(abnormalFlight, mixedFlight)));
    }

    @Test
    public void testTimeThanMoreTwoHours() {

        Flight flightWithoutLongLayovers = new Flight(List.of(
                createSegment(fixedCurrentTime, fixedCurrentTime.plusHours(1)),
                createSegment(fixedCurrentTime.plusHours(3), fixedCurrentTime.plusHours(4))
        ));

        Flight flightWithLongLayover = new Flight(List.of(
                createSegment(fixedCurrentTime, fixedCurrentTime.plusHours(1)),
                createSegment(fixedCurrentTime.plusHours(4), fixedCurrentTime.plusHours(5))
        ));

        List<Flight> inputFlights = List.of(flightWithoutLongLayovers, flightWithLongLayover);

        List<Flight> resultFlights = flightService.timeThanMoreTwoHours(inputFlights);

        assertEquals(1, resultFlights.size());
        assertTrue(resultFlights.contains(flightWithLongLayover));
    }

}
