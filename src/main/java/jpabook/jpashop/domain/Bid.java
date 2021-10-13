package jpabook.jpashop.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bids")
@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bid {

    @Id
    @GeneratedValue
    @Column(name = "bid_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "bid", cascade = CascadeType.ALL)
    private List<BidItem> bidItems = new ArrayList<>();

    private LocalDateTime bidDate;

    public void setMember(Member member) {
        this.member = member;
        member.getBids().add(this);
    }
    public void addBidItem(BidItem bidItem) {
        bidItems.add(bidItem);
        bidItem.setBid(this);
        }

    public static Bid createBid(Member member, BidItem... bidItems) {
        Bid bid = new Bid();
        bid.setMember(member);
        for (BidItem bidItem : bidItems) {
            bid.addBidItem(bidItem);
        }
        bid.setBidDate(LocalDateTime.now());
        return bid;
    }
    }
