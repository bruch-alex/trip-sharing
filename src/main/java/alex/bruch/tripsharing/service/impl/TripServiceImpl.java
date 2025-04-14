package alex.bruch.tripsharing.service.impl;

import alex.bruch.tripsharing.model.Trip;
import alex.bruch.tripsharing.dto.TripFormDTO;
import alex.bruch.tripsharing.model.UserLogin;
import alex.bruch.tripsharing.repo.TripRepository;
import alex.bruch.tripsharing.repo.UserLoginRepository;
import alex.bruch.tripsharing.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final UserLoginRepository userLoginRepository;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository, UserLoginRepository userLoginRepository) {
        this.tripRepository = tripRepository;
        this.userLoginRepository = userLoginRepository;
    }

    @Override
    public void createTrip(TripFormDTO tripFormDTO) {
        UserLogin user = userLoginRepository.findByEmail(tripFormDTO.email())
                .orElseThrow(() -> new UsernameNotFoundException(tripFormDTO.email()));

        Trip trip = new Trip();
        trip.setDestination(tripFormDTO.destination());
        trip.setOrigin(tripFormDTO.origin());
        trip.setDriver(user);

        tripRepository.save(trip);
        System.out.println("Trip created");
    }

    @Override
    public Trip findById(Long id) {
        return tripRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Trip not found"));
    }

    @Override
    public void delete(Trip trip) {
        tripRepository.delete(trip);
    }

    @Override
    public void update(Trip trip) {
        tripRepository.save(trip);
    }

    @Override
    public Page<Trip> findAllPageable(Pageable pageable) {
        return tripRepository.findAll(pageable);
    }

    @Override
    public Page<Trip> findAllByDestination(String destination, Pageable pageable) {
        return tripRepository.findAllByDestination(destination, pageable);
    }

    @Override
    public Page<Trip> findAllByOrigin(String origin, Pageable pageable) {
        return tripRepository.findAllByOrigin(origin, pageable);
    }

    @Override
    public Page<Trip> findAllByDestinationAndOrigin(String destination, String origin, Pageable pageable) {
        return tripRepository.findAllByDestinationAndOrigin(destination, origin, pageable);
    }

    public Trip prepareTripForTheDriver(String driverEmail) {
        UserLogin userLogin = userLoginRepository.findByEmail(driverEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Trip trip = new Trip();
        if (userLogin != null) {
            System.out.println("user logged in");
        }
        trip.setDriver(userLogin);
        return trip;
    }

}
