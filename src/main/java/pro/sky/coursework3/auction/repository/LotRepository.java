package pro.sky.coursework3.auction.repository;


import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pro.sky.coursework3.auction.dto.FullLot;
import pro.sky.coursework3.auction.dto.Status;
import pro.sky.coursework3.auction.entity.Lot;



import java.util.Optional;

public interface LotRepository extends JpaRepository<Lot, Integer> {
    @Query(value = """
            SELECT l.id as id,
                   l.status as status,
                   l.title as title,
                   l.description as description,
                   l.start_price as startPrice,
                   l.bid_price as bidPrice,
                   l.start_price + l.bid_price * (SELECT count(b.id) FROM bids b WHERE b.lot_id = :lotId) as currentPrice,
                   q.name as bidderName,
                   q.date_time as bidDate
            FROM lots l LEFT JOIN (SELECT b.name, b.date_time, b.lot_id
                               FROM bids b
                               ORDER BY b.date_time DESC
                               LIMIT 1) q ON q.lot_id = l.id
            WHERE l.id = :lotId
            """, nativeQuery = true)
    Optional<Tuple> getFullLot (@Param("lotId") int lotId);

    Page<Lot> findAllByStatus(Status status, Pageable pageable);

}
