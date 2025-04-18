package alex.bruch.tripsharing.repo;

import alex.bruch.tripsharing.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    Optional<Address> findAddressByCityAndStreetAndNumber(String city, String street, String number);
}
