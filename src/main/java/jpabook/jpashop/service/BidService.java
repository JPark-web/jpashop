package jpabook.jpashop.service;

import jpabook.jpashop.domain.Bid;
import jpabook.jpashop.domain.BidItem;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.BidRepository;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BidService {

    private final ItemRepository itemRepository;
    private final BidRepository bidRepository;


    public List<BidItem> findBids(Long id) {
        return bidRepository.findAll(id);
    }

    public BidItem findOne(Long memberId) {
        return bidRepository.findOne(memberId);
    }

    public List<BidItem> findAll(Long id) {
        return bidRepository.findAll(id);
    }

    @Transactional
    public BidItem addBidBudget(BidItem bidItem, Long itemId , int bidPrice, long memberId) {

        Item item = itemRepository.findOne(itemId);

        bidItem.setItem(item);
        bidItem.setMemberId(memberId);
        bidItem.setBidDate(LocalDateTime.now());
        bidRepository.save(bidItem);

        return bidItem;
    }

    public BidItem betweenDays(Item item, BidItem bidItem) {

        String bidEndTime = item.getBidEndTime();
        int year = Integer.parseInt(bidEndTime.substring(0, 4));
        int month = Integer.parseInt(bidEndTime.substring(5,7));
        int day = Integer.parseInt(bidEndTime.substring(8,10));
        int hour = Integer.parseInt(bidEndTime.substring(11,13));
        int minute = Integer.parseInt(bidEndTime.substring(14,16));

        LocalDateTime dateNow = LocalDateTime.now();
        LocalDateTime dateTarget = LocalDateTime.of(year, month, day, hour, minute, 0);

        long betweenMinutes = ChronoUnit.MINUTES.between(dateNow, dateTarget);

        Duration duration = Duration.ofMinutes(betweenMinutes);

        long days = duration.toDaysPart();
        bidItem.setRemainDays(days);

        long hours = duration.toHoursPart();
        bidItem.setRemainHours(hours);

        long minutes = duration.toMinutesPart();
        bidItem.setRemainMinutes(minutes);

        return bidItem;

    }

}
