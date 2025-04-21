package alex.bruch.tripsharing.service.impl;

import alex.bruch.tripsharing.dto.SearchFormAddressDTO;
import alex.bruch.tripsharing.dto.TripDTO;
import alex.bruch.tripsharing.mapper.TripMapper;
import alex.bruch.tripsharing.model.Trip;
import alex.bruch.tripsharing.model.UserLogin;
import alex.bruch.tripsharing.repo.TripRepository;
import alex.bruch.tripsharing.repo.UserLoginRepository;
import alex.bruch.tripsharing.service.TripService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final UserLoginRepository userLoginRepository;

    public TripServiceImpl(TripRepository tripRepository, UserLoginRepository userLoginRepository) {
        this.tripRepository = tripRepository;
        this.userLoginRepository = userLoginRepository;
    }

    @Override
    public void createTrip(TripDTO tripDTO) {
        UserLogin user = userLoginRepository.findByEmail(tripDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(tripDTO.getEmail()));
        Trip trip = TripMapper.toEntity(tripDTO);
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


    public Page<Trip> searchTrips(SearchFormAddressDTO address, Pageable pageable) {
        Page<Trip> tripPage;

        if (!address.getOriginCity().isEmpty() && !address.getDestinationCity().isEmpty()) {
            tripPage = tripRepository.findAllByOriginAddress_CityAndDestinationAddress_City(address.getOriginCity(), address.getDestinationCity(), pageable);
        } else if (!address.getDestinationCity().isEmpty()) {
            tripPage = tripRepository.findAllByDestinationAddress_City(address.getDestinationCity(), pageable);
        } else if (!address.getOriginCity().isEmpty()) {
            tripPage = tripRepository.findAllByOriginAddress_City(address.getOriginCity(), pageable);
        } else {
            tripPage = tripRepository.findAll(pageable);
        }

        return tripPage;
    }

    @Override
    public Page<Trip> findAllByDriver(String email, Pageable pageable) {
        UserLogin user = userLoginRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return tripRepository.findAllByDriver(user, pageable);
    }

    @Override
    public Page<Trip> findAllByEmail(String email, Pageable pageable) {
        UserLogin user = userLoginRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return tripRepository.findAllByPassenger(user, pageable);
    }

    @Override
    public void addPassengerToTrip(String email, Long tripId) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new UsernameNotFoundException("Trip not found"));
        UserLogin passenger = userLoginRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        List<UserLogin> passengers = trip.getPassengers();

        if (passengers.size() > trip.getTotalSeats()) {
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
