package alex.bruch.tripsharing.service;

import alex.bruch.tripsharing.dto.TripDTO;
import alex.bruch.tripsharing.model.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TripService {
    void createTrip(TripDTO tripDTO);

    Trip findById(Long id);

    void delete(Trip trip);

    void update(Trip trip);

    Page<TripDTO> searchTrips(String origin, String destination, Pageable pageable);
}
