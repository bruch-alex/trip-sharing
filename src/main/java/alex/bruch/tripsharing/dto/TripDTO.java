package alex.bruch.tripsharing.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TripDTO {
    private LocalDateTime plannedDepartureDateTime;
    private LocalDateTime plannedArrivalDateTime;
    private String email;
    private String origin;
    private String destination;

    public TripDTO(String email, String origin, String destination, LocalDateTime plannedDepartureDateTime, LocalDateTime plannedArrivalDateTime) {
        this.email = email;
        this.origin = origin;
        this.destination = destination;
        this.plannedDepartureDateTime = plannedDepartureDateTime;
        this.plannedArrivalDateTime = plannedArrivalDateTime;
    }
}

