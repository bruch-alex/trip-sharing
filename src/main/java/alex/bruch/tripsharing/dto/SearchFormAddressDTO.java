package alex.bruch.tripsharing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchFormAddressDTO {
    private String originCity;
    private String originStreet;
    private String originNumber;

    private String destinationCity;
    private String destinationStreet;
    private String destinationNumber;
}
