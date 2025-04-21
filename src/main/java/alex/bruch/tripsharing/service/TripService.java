package alex.bruch.tripsharing.service;

import alex.bruch.tripsharing.dto.SearchFormAddressDTO;
import alex.bruch.tripsharing.dto.TripDTO;
import alex.bruch.tripsharing.model.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TripService {
    void createTrip(TripDTO tripDTO);

    Trip findById(Long id);

    void delete(Trip trip);

    void update(Trip trip);

    Page<Trip> searchTrips(SearchFormAddressDTO address, Pageable pageable);

    Page<Trip> findAllByEmail(String driverEmail, Pageable pageable);

    void addPassengerToTrip(String email, Long tripId);

    Page<Trip> findAllByDriver(String email, Pageable pageable);
}
