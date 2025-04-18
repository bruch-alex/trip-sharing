package alex.bruch.tripsharing.service.impl;

import alex.bruch.tripsharing.dto.TripDTO;
import alex.bruch.tripsharing.model.Trip;
import alex.bruch.tripsharing.model.UserLogin;
import alex.bruch.tripsharing.repo.TripRepository;
import alex.bruch.tripsharing.repo.UserLoginRepository;
import alex.bruch.tripsharing.service.TripService;
import alex.bruch.tripsharing.service.mapper.TripMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final UserLoginRepository userLoginRepository;
    private final TripMapper tripMapper;

    public TripServiceImpl(TripRepository tripRepository, UserLoginRepository userLoginRepository, TripMapper tripMapper) {
        this.tripRepository = tripRepository;
        this.userLoginRepository = userLoginRepository;
        this.tripMapper = tripMapper;
    }

    @Override
    public void createTrip(TripDTO tripDTO) {
        UserLogin user = userLoginRepository.findByEmail(tripDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(tripDTO.getEmail()));

        Trip trip = new Trip();
        trip.setDestination(tripDTO.getDestination());
        trip.setOrigin(tripDTO.getOrigin());
        trip.setPlannedArrivalDateTime(tripDTO.getPlannedArrivalDateTime());
        trip.setPlannedDepartureDateTime(tripDTO.getPlannedDepartureDateTime());
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
        Page<Trip> tripPage;

        if (!origin.isEmpty() && !destination.isEmpty()) {
            tripPage = tripRepository.findAllByOriginAndDestination(destination, origin, pageable);
        } else if (!destination.isEmpty()) {
            tripPage = tripRepository.findAllByDestination(destination, pageable);
        } else if (!origin.isEmpty()) {
            tripPage = tripRepository.findAllByOriginIgnoreCase(origin, pageable);
        } else {
            tripPage = tripRepository.findAll(pageable);
        }

        return tripPage;
    }

    @Override
    public Page<Trip> findByDriverEmail(String driverEmail, Pageable pageable) {
        UserLogin user = userLoginRepository.findByEmail(driverEmail)
                .orElseThrow(() -> new UsernameNotFoundException(driverEmail));

        return tripRepository.findAllByDriverOrPassengers(user, pageable);
    }

    public void addPassengerToTrip(String email, Long tripId) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new UsernameNotFoundException("Trip not found"));
        UserLogin passenger = userLoginRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        List<UserLogin> passengers = trip.getPassengers();

        if (passengers.size() > trip.getAvailableSeats()) {
            throw new RuntimeException("All seats are taken");
        }
        if (passengers.contains(passenger)) {
            throw new RuntimeException("Passenger already exists");
        }

        if (trip.getDriver().getEmail().equals(passenger.getEmail())) {
            throw new RuntimeException("Driver can not be passenger");
        }

        passengers.add(passenger);
        tripRepository.save(trip);
    }

}
