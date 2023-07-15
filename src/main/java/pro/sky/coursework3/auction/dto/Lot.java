package pro.sky.coursework3.auction.dto;

//import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lot {
    private int id;
    private Status status;
    private String title;
    private String description;
    private int startPrice;
    private int bidPrice;



}
