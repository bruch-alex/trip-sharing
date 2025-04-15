package alex.bruch.tripsharing.service.impl;

import alex.bruch.tripsharing.dto.TripFormDTO;
import alex.bruch.tripsharing.model.Trip;
import alex.bruch.tripsharing.model.UserLogin;
import alex.bruch.tripsharing.repo.TripRepository;
import alex.bruch.tripsharing.repo.UserLoginRepository;
import alex.bruch.tripsharing.service.TripService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final UserLoginRepository userLoginRepository;

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

    public Page<Trip> searchTrips(String origin, String destination, Pageable pageable) {
        Page<Trip> trips;

        if (!origin.isEmpty() && !destination.isEmpty()) {
            trips = tripRepository.findAllByOriginAndDestination(destination, origin, pageable);
        } else if (!destination.isEmpty()) {
            trips = tripRepository.findAllByDestination(destination, pageable);
        } else if (!origin.isEmpty()) {
            trips = tripRepository.findAllByOrigin(origin, pageable);
        } else {
            trips = tripRepository.findAll(pageable);
        }
        return trips;
    }

}
