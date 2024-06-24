package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dto.elements.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponseDto {
    private Coord coord;
    private Weather[] weather;
    private Main main;
    private Wind wind;
    private Rain rain;
    private Snow snow;
    private Clouds clouds;
    private Sys sys;
    private String name;
    private long locationId;
}
