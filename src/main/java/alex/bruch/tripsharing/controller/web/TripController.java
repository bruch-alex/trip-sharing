package alex.bruch.tripsharing.controller.web;

import alex.bruch.tripsharing.dto.TripDTO;
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
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping
    public String getIndexPage() {
        return "trips/index";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String origin,
                         @RequestParam(required = false) String destination,
                         @PageableDefault(size = 5) Pageable pageable,
                         Model model) {
        Page<TripDTO> resultTrips = tripService.searchTrips(origin, destination, pageable);

        //model.addAttribute("origin", origin);
        //model.addAttribute("destination", destination);
        model.addAttribute("page", resultTrips);
        model.addAttribute("trips", resultTrips.getContent());

        return "trips/list-trips";
    }

    @GetMapping("/create")
    public String getCreateTripPage(Model model, Principal principal) {
        TripDTO tripDTO = new TripDTO();
        tripDTO.setEmail(principal.getName());
        model.addAttribute("tripDTO", tripDTO);
        return "trips/create-trip";
    }

    @PostMapping("/create")
    public String postCreateTripPage(@ModelAttribute TripDTO tripDTO) {
        tripService.createTrip(tripDTO);
        return "redirect:create";
    }


}
