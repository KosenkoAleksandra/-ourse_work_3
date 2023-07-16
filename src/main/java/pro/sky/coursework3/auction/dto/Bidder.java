package pro.sky.coursework3.auction.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bidder {
    @NotBlank
    @Size(min = 1, max = 20)
    private String name;
}
