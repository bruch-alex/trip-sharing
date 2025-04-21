package alex.bruch.tripsharing.controller.web;

import alex.bruch.tripsharing.dto.SearchFormAddressDTO;
import alex.bruch.tripsharing.model.Trip;
import alex.bruch.tripsharing.service.TripService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping
    public String getIndexPage(Model model) {
        model.addAttribute("addressDTO", new SearchFormAddressDTO());
        return "trips/index";
    }

    @GetMapping("/search")
    public String search(@PageableDefault(size = 5) Pageable pageable,
                         @ModelAttribute SearchFormAddressDTO addressDTO,
                         Model model) {
        Page<Trip> resultTrips = tripService.searchTrips(addressDTO, pageable);
        model.addAttribute("page", resultTrips);
        model.addAttribute("trips", resultTrips.getContent());

        return "trips/list-trips";
    }

    @GetMapping("/myTrips")
    public String getMyTrips(Model model, Principal principal, @PageableDefault(size = 5) Pageable pageable) {
        if (principal == null) {
            return "redirect:/login";
        }

        Page<Trip> trips = tripService.findAllByEmail(principal.getName(), pageable);
        model.addAttribute("page", trips);
        model.addAttribute("trips", trips.getContent());
        return "trips/list-trips";
    }

    @PostMapping("/join-trip")
    public String joinTrip(@RequestParam Long tripId, Principal principal) {
        tripService.addPassengerToTrip(principal.getName(), tripId);
        return "redirect:myTrips";
    }


}
