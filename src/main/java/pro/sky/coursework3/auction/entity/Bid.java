package pro.sky.coursework3.auction.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;


@Getter
@Setter
@Entity
@Table(name = "bids")
@AllArgsConstructor
@NoArgsConstructor
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 20)
    private String name;
    @ManyToOne
    @JoinColumn(name = "lot_id")
    private Lot lot;

    @CreationTimestamp
    @Column(nullable = false,name = "date_time", updatable = false)
    private OffsetDateTime dateTime;

    public Bid(String name) {
        this.name = name;
    }
}
