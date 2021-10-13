package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Entity
@Table(name = "bid_item")
@Getter @Setter
@NoArgsConstructor
@Slf4j
public class BidItem {


    @Id
    @JoinColumn(name = "item_id")
//    @Column(name = "bid_item_id")
    private Long id;

    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bid_id")
    private Bid bid;

    private int bidPrice;
    // 얼마 비딩 할건지
    private int currentPrice;
    // 현재 가격
    public static BidItem createBidItem(Item item) {
        BidItem bidItem = new BidItem();
        bidItem.setId(item.getId());
        bidItem.setItem(item);
        return bidItem;
    }






}
