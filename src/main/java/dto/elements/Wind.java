package dto.elements;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Wind {
    private long speed;
    private long deg;
    private long gust;
}
