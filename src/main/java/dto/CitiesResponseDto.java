package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CitiesResponseDto {
    private String name;
    private String lat;
    private String lon;
    private String country;
    private String state;
}
