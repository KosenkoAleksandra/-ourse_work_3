package pro.sky.coursework3.auction.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pro.sky.coursework3.auction.dto.Status;


@Getter
@Setter
@Entity
@Table(name = "lots")
@AllArgsConstructor
@NoArgsConstructor
public class Lot{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 64)
    private String title;

    @Column(nullable = false, length = 4096)
    private String description;

    @Column(name = "start_price")
    private int startPrice;

    @Column(name = "bid_price")
    private int bidPrice;

    @Enumerated(EnumType.STRING)
    @Column(length = 7)
    private Status status;
}
