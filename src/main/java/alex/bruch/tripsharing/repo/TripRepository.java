package alex.bruch.tripsharing.repo;

import alex.bruch.tripsharing.model.Trip;
import alex.bruch.tripsharing.model.UserLogin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Driver;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    Page<Trip> findAllByDestination(String destination, Pageable pageable);

    Page<Trip> findAllByOriginIgnoreCase(String origin, Pageable pageable);

    Page<Trip> findAllByOriginAndDestination(String origin, String destination, Pageable pageable);

    Page<Trip> findAllByDriver(UserLogin driver, Pageable pageable);

    @Query("SELECT t FROM Trip t WHERE t.driver = :user OR :user MEMBER OF t.passengers")
    Page<Trip> findAllByDriverOrPassengers(@Param("user") UserLogin user, Pageable pageable);
}
