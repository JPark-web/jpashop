package jpabook.jpashop.web;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BidForm {
    private int startPrice;
    private int endPrice;
    private int minBidAmount;
    private int currentPrice;
}
