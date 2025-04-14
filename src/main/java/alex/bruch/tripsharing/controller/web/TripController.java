package alex.bruch.tripsharing.controller.web;

import alex.bruch.tripsharing.model.Trip;
import alex.bruch.tripsharing.dto.TripFormDTO;
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
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping
    public String findAll(Model model,
                          @PageableDefault(size = 5) Pageable pageable) {
        Page<Trip> tripPage = tripService.findAllPageable(pageable);
        model.addAttribute("trips", tripPage.getContent());
        model.addAttribute("page", tripPage);
        return "trips";
    }

    @GetMapping("/create")
    public String getCreateTripPage(Model model, Principal principal) {
        model.addAttribute("tripFormDTO", new TripFormDTO("", "", principal.getName()));
        return "create-forms/create-trip";
    }

    @PostMapping("/create")
    public String postCreateTripPage(@ModelAttribute TripFormDTO tripFormDTO) {
        tripService.createTrip(tripFormDTO);
        return "redirect:create";
    }
}
