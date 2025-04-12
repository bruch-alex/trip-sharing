package alex.bruch.tripsharing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user_profiles")
public class UserProfile {

    @Id
    private Long id;

    private String firstName;
    private String middleName;
    private String lastName;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private UserLogin userLogin;
}
