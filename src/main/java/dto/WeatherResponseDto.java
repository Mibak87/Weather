package dto;

import dto.elements.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponseDto {
    private Coord coord;
    private Weather weather;
    private Main main;
    private Wind wind;
    private Rain rain;
    private Snow snow;
    private Clouds clouds;
    private String name;
}
