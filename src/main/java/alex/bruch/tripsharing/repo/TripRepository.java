package alex.bruch.tripsharing.repo;

import alex.bruch.tripsharing.model.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    Page<Trip> findAllByDestination(String destination, Pageable pageable);

    Page<Trip> findAllByOriginIgnoreCase(String origin, Pageable pageable);

    Page<Trip> findAllByOriginAndDestination(String origin, String destination, Pageable pageable);
}
