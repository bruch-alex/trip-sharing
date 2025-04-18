package alex.bruch.tripsharing.dto;

import alex.bruch.tripsharing.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripDTO {
    private LocalDateTime plannedDepartureDateTime;
    private LocalDateTime plannedArrivalDateTime;

    private String email;
    private Integer seats;

    private Address origin;
    private Address destination;
}

