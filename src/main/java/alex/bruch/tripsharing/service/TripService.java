package alex.bruch.tripsharing.service;

import alex.bruch.tripsharing.model.Trip;
import alex.bruch.tripsharing.dto.TripFormDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TripService {
    void createTrip(TripFormDTO tripFormDTO);

    Trip findById(Long id);

    void delete(Trip trip);

    void update(Trip trip);

    Page<Trip> searchTrips(String origin, String destination, Pageable pageable);
}
