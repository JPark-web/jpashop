package jpabook.jpashop.controller;

import jpabook.jpashop.domain.BidItem;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.BidService;
import jpabook.jpashop.service.ItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuctionController {

    private final BidService bidService;
    private final ItemService itemService;



    @PostMapping("/items/{id}")
    public String addBid(   @ModelAttribute OrderItem orderItem,
                            @PathVariable Long id,
                            @SessionAttribute(name = "loginMember", required = false) Member loginedMember,
                            @ModelAttribute BidItem bidItem,
//                            @ModelAttribute Item item,
                            Model model) {

        Map<String, String> errors = new HashMap<>();

        Item item = itemService.findOne(id);
        int currentPrice = item.getCurrentPrice();
        int bidMinValue = item.getBidMinValue();

        log.info("item.getCurrentPrice()={}", item.getCurrentPrice());
        log.info("bidItem.getRemainTime()={}", bidItem.getRemainTime());
        if (bidItem.getBidPrice() < item.getBidMinValue() + item.getCurrentPrice()) {
            errors.put("bidMinValue", "1회 입찰 금액은 " + (currentPrice + bidMinValue) + " 원 이상 이어야 합니다.");
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("item", item);
            bidService.betweenDays(item, bidItem);
            return "/test/auctionTest";
        }

        bidService.addBidBudget(bidItem,item.getId() , bidItem.getBidPrice(), loginedMember.getSeq_id());
        item.setCurrentPrice(bidItem.getBidPrice());
        itemService.saveItem(item);
        model.addAttribute("item", item);

        bidService.betweenDays(item, bidItem);
        bidItem.setBidPrice(0);
        return "/test/auctionTest";

    }
    @GetMapping("/bids/bidsListTest/{id}")
    public String BidList(Model model, @PathVariable Long id) {
        log.info("id={}", id);
        List<BidItem> bids = bidService.findAll(id);
        if (bids.size() == 0) {
            return "/bids/nobidsList";
        }
        log.info("bids.size={}", bids.size());
        model.addAttribute("bids", bids);
        return "/bids/bidsListTest";
    }


}
