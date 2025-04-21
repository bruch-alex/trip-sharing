package alex.bruch.tripsharing.controller.web;

import alex.bruch.tripsharing.dto.TripDTO;
import alex.bruch.tripsharing.model.Trip;
import alex.bruch.tripsharing.service.TripService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/driver")
public class DriverController {

    private final TripService tripService;

    public DriverController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping
    public String loadProfile() {
        return "driver-profile";
    }

    @GetMapping("/trips")
    public String getMyTrips(Model model, Principal principal, @PageableDefault(size = 5) Pageable pageable) {
        if (principal == null) {
            return "redirect:/login";
        }

        Page<Trip> trips = tripService.findAllByDriver(principal.getName(), pageable);
        model.addAttribute("page", trips);
        model.addAttribute("trips", trips.getContent());
        return "trips/list-trips";
    }

    @GetMapping("/trips/create")
    public String getCreateTripPage(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        TripDTO tripDTO = new TripDTO();
        tripDTO.setEmail(principal.getName());
        model.addAttribute("tripDTO", tripDTO);
        return "trips/create-trip";
    }

    @PostMapping("/trips/create")
    public String postCreateTripPage(@ModelAttribute TripDTO tripDTO) {
        tripService.createTrip(tripDTO);
        return "redirect:../trips";
    }


}
