package alex.bruch.tripsharing.mapper;

import alex.bruch.tripsharing.dto.TripDTO;
import alex.bruch.tripsharing.model.Address;
import alex.bruch.tripsharing.model.Trip;
import alex.bruch.tripsharing.repo.AddressRepository;
import org.springframework.stereotype.Component;

@Component
public class TripMapper {

    public static Trip toEntity(TripDTO tripDTO, AddressRepository addressRepository) {
        Trip trip = new Trip();

        trip.setPlannedArrivalDateTime(tripDTO.getPlannedArrivalDateTime());
        trip.setPlannedDepartureDateTime(tripDTO.getPlannedDepartureDateTime());
        trip.setTotalSeats(tripDTO.getSeats());

        Address destination = new Address();
        destination.setCity(tripDTO.getDestination().getCity());
        destination.setStreet(tripDTO.getDestination().getStreet());
        destination.setNumber(tripDTO.getDestination().getNumber());
        trip.setDestinationAddress(destination);

        Address origin = new Address();
        origin.setCity(tripDTO.getOrigin().getCity());
        origin.setStreet(tripDTO.getOrigin().getStreet());
        origin.setNumber(tripDTO.getOrigin().getNumber());
        trip.setOriginAddress(origin);

        return trip;
    }
}
