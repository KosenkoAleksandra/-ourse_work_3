package pro.sky.coursework3.auction.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pro.sky.coursework3.auction.dto.FullLot;
import pro.sky.coursework3.auction.entity.Bid;

import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Integer> {

    Optional<Bid> findFirstByLot_IdOrderByDateTimeAsc(int lotId);

    @Query("""
            SELECT new pro.sky.coursework3.auction.dto.Bid(b.name, b.dateTime) FROM Bid b WHERE b.name = (
            SELECT b.name FROM Bid b GROUP BY b.name ORDER BY count(b.name) DESC LIMIT 1)
            ORDER BY b.dateTime DESC LIMIT 1
            """)
    Optional<pro.sky.coursework3.auction.dto.Bid> findTheMostFrequentBidder(@Param("lotId") int lotId);



}
