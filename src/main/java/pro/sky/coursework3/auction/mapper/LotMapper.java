package pro.sky.coursework3.auction.mapper;

import org.springframework.stereotype.Component;
import pro.sky.coursework3.auction.dto.Bid;
import pro.sky.coursework3.auction.dto.CreateLot;
import pro.sky.coursework3.auction.dto.Status;
import pro.sky.coursework3.auction.entity.Lot;


@Component
public class LotMapper {

    public Bid toDto(pro.sky.coursework3.auction.entity.Bid bid) {
        Bid bidDto = new Bid();
        bidDto.setBidDate(bid.getDateTime());
        bidDto.setBidderName(bid.getName());
        return bidDto;
    }
    public Lot toEntity(CreateLot createLot) {
        Lot lot = new Lot();
        lot.setStatus(Status.CREATED);
        lot.setTitle(createLot.getTitle());
        lot.setDescription(createLot.getDescription());
        lot.setStartPrice(createLot.getStartPrice());
        lot.setBidPrice(createLot.getBidPrice());
        return lot;
    }
    public pro.sky.coursework3.auction.dto.Lot toDto(Lot lot) {
        pro.sky.coursework3.auction.dto.Lot lotDto = new pro.sky.coursework3.auction.dto.Lot();
        lotDto.setId(lot.getId());
        lotDto.setTitle(lot.getTitle());
        lotDto.setDescription(lot.getDescription());
        lotDto.setStatus(lot.getStatus());
        lotDto.setBidPrice(lot.getBidPrice());
        lotDto.setStartPrice(lot.getStartPrice());
        return lotDto;
    }
}
