package pro.sky.coursework3.auction.controller;


import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.coursework3.auction.dto.*;
import pro.sky.coursework3.auction.service.LotService;

import java.util.List;


@Data
@RestController
@RequestMapping("/lot")
public class LotController {
    private final LotService lotService;

    @GetMapping("/{id}/first")
    public Bid getFirstBidder (@PathVariable int id) {
        return lotService.getFirstBidder(id);

    }
    @GetMapping("/{id}/frequent")
    public Bid getMostFrequentBidder (@PathVariable int id) {
        return lotService.getMostFrequentBidder(id);
    }
    @GetMapping("/{id}")
    public FullLot getFullLot (@PathVariable int id) {
        return lotService.getFullLot(id);
    }
    @PostMapping("/{id}/start")
    public void startBidding (@PathVariable int id) {
        lotService.startBidding(id);
    }
    @PostMapping("/{id}/bid")
    public void createBid (@PathVariable int id, @RequestBody @Valid Bidder bidder) {
        lotService.createBid(id, bidder);
    }
    @PostMapping("/{id}/stop")
    public void stopBidding (@PathVariable int id) {
        lotService.stopBidding(id);
    }
    @PostMapping
    public Lot createLot (@RequestBody @Valid CreateLot createLot) {
        return lotService.createLot(createLot);
    }
    @GetMapping
    public List<Lot> findLots (@RequestParam(value = "status", required = false) Status status,
                               @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        return lotService.findLots(status, page);
    }
    @GetMapping("/export")
    public void getCSVFile() {
        lotService.getCSVFile();
    }

}
