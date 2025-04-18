package alex.bruch.tripsharing.service;

import alex.bruch.tripsharing.dto.TripDTO;
import alex.bruch.tripsharing.model.Address;
import alex.bruch.tripsharing.model.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TripService {
    void createTrip(TripDTO tripDTO);

    Trip findById(Long id);

    void delete(Trip trip);

    void update(Trip trip);

    Page<Trip> searchTrips(Address origin, Address destination, Pageable pageable);

    Page<Trip> findByDriverEmail(String driverEmail, Pageable pageable);

    void addPassengerToTrip(String email, Long tripId);
}
