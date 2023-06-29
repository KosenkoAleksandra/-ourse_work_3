package pro.sky.coursework3.auction.service;

import org.springframework.http.ResponseEntity;
import pro.sky.coursework3.auction.dto.*;

import java.util.List;

public interface LotService {
    Bid getFirstBidder(int lotId);
    Bid getMostFrequentBidder(int lotId);
    FullLot getFullLot(int lotId);
    void startBidding(int id);
    void createBid(int lotId, Bidder bidder);
    void stopBidding(int lotId);

    Lot createLot(CreateLot createLot);

    List<Lot> findLots(Status status, int page);

    void getCSVFile();


}
