package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bid_item")
@Getter @Setter
@Slf4j
@RequiredArgsConstructor
public class BidItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bidNo;

    private Long memberId;

    @ManyToOne
    @JoinColumn(name = "id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bid_id")
    private Bid bid;

    private int bidPrice;

    private LocalDateTime bidDate;

    @Transient
    private long remainDays;
    @Transient
    private long remainHours;
    @Transient
    private long remainMinutes;

    @Transient
    private String remainTime;

    public static BidItem createBidItem(Item item) {
        BidItem bidItem = new BidItem();
        bidItem.setItem(item);
        return bidItem;
    }






}
