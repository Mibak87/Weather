package dto.elements;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Weather {
    private long id;
    private String main;
    private String description;
    private String icon;
}
