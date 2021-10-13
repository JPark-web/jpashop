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

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BidService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final BidRepository bidRepository;



    @Transactional
    public Long saveBid(Long memberId, Long itemId) {

        Member member = memberRepository.findOne(memberId);

        Item item = itemRepository.findOne(itemId);

        BidItem bidItem = BidItem.createBidItem(item);

        Bid bid = Bid.createBid(member, bidItem);

//        bidRepository.save(bid);

        return bid.getId();
    }

//    @Transactional
//    public Long addBidBudget(Long memberId, Long itemId, int bidPrice) {
//
//        Member member = memberRepository.findOne(memberId);
//        BidItem bidItem = findOne(itemId);
//        bidItem.setCurrentPrice(++bidPrice);
//        Bid bid = Bid.createBid(member, bidItem);
//        bidRepository.save(bid);
//        return bid.getId();
//    }

    public List<BidItem> findBids() {
        return bidRepository.findAll();
    }

    public BidItem findOne(Long memberId) {
        return bidRepository.findOne(memberId);
    }
    @Transactional
    public void addBidBudget(Long memberId, Long itemId ,int bidPrice) {
        Member member = memberRepository.findOne(memberId);
        BidItem bidItem = bidRepository.findOne(itemId);
        bidItem.setBidPrice(bidPrice);
        bidItem.setCurrentPrice(++bidPrice);
        Bid bid = Bid.createBid(member, bidItem);
        bidRepository.save(bid);
    }

}
