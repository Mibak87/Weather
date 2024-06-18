package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SaveLocationDto {
    private String cityName;
    private String lon;
    private String lat;
    private String login;
}
