package alex.bruch.tripsharing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private UserLogin driver;

    @ManyToMany
    @JoinTable(
            name = "trip_passenger",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "passenger_id")
    )
    private List<UserLogin> passengers;

    @Column(name = "origin")
    private String origin;

    @Column(name = "destination")
    private String destination;

    @Column(name = "planned_departure_date_time")
    private LocalDateTime plannedDepartureDateTime;

    @Column(name = "planned_arrival_date_time")
    private LocalDateTime plannedArrivalDateTime;

    @Column(name = "total_seats")
    private int totalSeats = 5;

    @Transient
    private Duration duration;

    public void updateDuration() {
        if (plannedDepartureDateTime != null && plannedArrivalDateTime != null) {
            duration = Duration.between(plannedDepartureDateTime, plannedArrivalDateTime);
        }
    }


}
