package alex.bruch.tripsharing.service.mapper;

import alex.bruch.tripsharing.dto.TripDTO;
import alex.bruch.tripsharing.model.Trip;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TripMapper implements Function<Trip, TripDTO> {
    TripDTO tripDTO = new TripDTO();

    @Override
    public TripDTO apply(Trip trip) {
        return new TripDTO(
                trip.getDriver().getEmail(),
                trip.getOrigin(),
                trip.getDestination(),
                trip.getPlannedDepartureDateTime(),
                trip.getPlannedArrivalDateTime()
        );
    }
}
