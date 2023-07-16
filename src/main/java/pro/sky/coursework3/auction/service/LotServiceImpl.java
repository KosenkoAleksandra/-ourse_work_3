package pro.sky.coursework3.auction.service;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import jakarta.persistence.Tuple;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import pro.sky.coursework3.auction.dto.*;
import pro.sky.coursework3.auction.entity.Lot;
import pro.sky.coursework3.auction.exceptions.LotNotFoundException;
import pro.sky.coursework3.auction.exceptions.LotNotStartedYetException;
import pro.sky.coursework3.auction.mapper.LotMapper;
import pro.sky.coursework3.auction.repository.BidRepository;
import pro.sky.coursework3.auction.repository.LotRepository;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Service
public class LotServiceImpl implements LotService {
    private final LotRepository lotRepository;
    private final BidRepository bidRepository;
    private final LotMapper lotMapper;

    @Override
    public Bid getFirstBidder(int lotId) {
        return bidRepository.findFirstByLot_IdOrderByDateTimeAsc(lotId)
                .map(lotMapper::toDto)
                .orElseThrow(LotNotFoundException::new);
    }

    @Override
    public Bid getMostFrequentBidder(int lotId) {
        return bidRepository.findTheMostFrequentBidder(lotId)
                .orElseThrow(LotNotFoundException::new);
    }

    @Override
    public FullLot getFullLot(int lotId) {
        Tuple tuple = lotRepository.getFullLot(lotId)
                .orElseThrow(LotNotFoundException::new);
        Bid lastBid = new Bid(
                tuple.get("bidderName", String.class),
                tuple.get("bidDate", OffsetDateTime.class)
        );
        if (lastBid.getBidDate() == null && lastBid.getBidderName() == null) {
            lastBid = null;
        }
        return new FullLot(
                tuple.get("id", Integer.class),
                Status.valueOf(tuple.get("status", String.class)),
                tuple.get("title", String.class),
                tuple.get("description", String.class),
                tuple.get("startPrice", Integer.class),
                tuple.get("bidPrice", Integer.class),
                tuple.get("currentPrice", Long.class).intValue(),
                lastBid
//                new Bid(
//                        tuple.get("bidderName", String.class),
//                        tuple.get("bidDate", OffsetDateTime.class)
//                )
        );
    }

    @Override
    public void startBidding(int lotId) {
        changeLotStatus(lotId, Status.STARTED);
    }

    @Override
    public void createBid(int lotId, Bidder bidder) {
        Lot lot = lotRepository.findById(lotId)
                .orElseThrow(LotNotFoundException::new);
        if (lot.getStatus() == Status.CREATED || lot.getStatus() == Status.STOPPED) {
            throw new LotNotStartedYetException();
        }
        bidRepository.save(new pro.sky.coursework3.auction.entity.Bid(bidder.getName(), lot));
    }

    @Override
    public void stopBidding(int lotId) {
        changeLotStatus(lotId, Status.STOPPED);
    }

    private void changeLotStatus(int lotId, Status status) {
        Lot lot = lotRepository.findById(lotId)
                .orElseThrow(LotNotFoundException::new);
        lot.setStatus(status);
        lotRepository.save(lot);
    }

    @Override
    public pro.sky.coursework3.auction.dto.Lot createLot(CreateLot createLot) {
        return lotMapper.toDto(lotRepository.save(lotMapper.toEntity(createLot)));
    }

    @Override
    public List<pro.sky.coursework3.auction.dto.Lot> findLots(@Nullable Status status, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return Optional.ofNullable(status)
                .map(st -> lotRepository.findAllByStatus(st, pageable))
                .orElseGet(() -> lotRepository.findAll(pageable)).stream()
                .map(lotMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void getCSVFile()  {
        List<Lot> lotCSV = lotRepository.findAll();
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(FullLot.class)
                .withColumnSeparator(';')
                .withoutQuoteChar()
                .withHeader();
        ObjectWriter writer = mapper.writer(schema);
        try {
            writer.writeValue(new FileWriter("test.csv", StandardCharsets.UTF_8), lotCSV);
        } catch (IOException e) {
            throw new LotNotFoundException();
        }
    }

}
