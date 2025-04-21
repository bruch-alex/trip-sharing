package alex.bruch.tripsharing.repo;

import alex.bruch.tripsharing.model.Address;
import alex.bruch.tripsharing.model.Trip;
import alex.bruch.tripsharing.model.UserLogin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TripRepository extends JpaRepository<Trip, Long> {
    Page<Trip> findAllByDestinationAddress(Address destinationAddress, Pageable pageable);

    Page<Trip> findAllByOriginAddress(Address originAddress, Pageable pageable);

    Page<Trip> findAllByOriginAddressAndDestinationAddress(Address originAddress, Address destinationAddress, Pageable pageable);

    Page<Trip> findAllByDriver(UserLogin driver, Pageable pageable);

    @Query("SELECT t FROM Trip t WHERE :user MEMBER OF t.passengers")
    Page<Trip> findAllByPassenger(@Param("user") UserLogin user, Pageable pageable);

    Page<Trip> findAllByOriginAddress_CityAndDestinationAddress_City(String originAddressCity, String destinationAddressCity, Pageable pageable);

    @Query("SELECT t FROM Trip t WHERE t.originAddress.city = :origin")
    Page<Trip> findAllByOriginAddress_City(@Param("origin") String originAddressCity, Pageable pageable);

    Page<Trip> findAllByDestinationAddress_City(String destinationAddressCity, Pageable pageable);
}
