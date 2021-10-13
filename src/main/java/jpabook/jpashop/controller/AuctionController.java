package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Bid;
import jpabook.jpashop.domain.BidItem;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.BidService;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.web.BidForm;
import jpabook.jpashop.web.BookForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.Binding;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuctionController {

    private final BidService bidService;
    private final ItemService itemService;

    @PostMapping("/bid/{id}")
    public String createBid(@ModelAttribute OrderItem orderItem,
                            @PathVariable Long id,
                            @SessionAttribute(name = "loginMember", required = false) Member loginedMember,
                            @ModelAttribute BidItem bidItem) {
log.info("bidItem.getBidPrice()={}", bidItem.getBidPrice());
        Item item = itemService.findOne(id);
bidService.addBidBudget(loginedMember.getSeq_id(), item.getId(), bidItem.getBidPrice());
        return "redirect:/bids/bidsListTest";

    }

    @GetMapping("/bids/bidsListTest")
    public String BidList(Model model) {
        log.info("BidList Call");
        List<BidItem> bids = bidService.findBids();
        model.addAttribute("bids", bids);
        return "/bids/bidsListTest";
    }
}
