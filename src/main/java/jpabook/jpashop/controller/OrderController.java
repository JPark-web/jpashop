package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    //오다 처리하는 메소드
    @PostMapping("/done/{id}")
    public String orderDoneTest(@PathVariable Long id, @ModelAttribute OrderItem orderItem,
                                @SessionAttribute(name = "loginMember", required = false) Member loginedMember) {

        if (loginedMember == null) {
            return "redirect:/login";
        }
        log.info("oderDone");
        orderService.order(loginedMember.getSeq_id(), id, orderItem.getCount());
        return "/order/orderForm";
    }



    @GetMapping(value = "/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model,
                            @SessionAttribute(name = "loginMember", required = false) Member loginedMember) {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }
//    @GetMapping({"/get", "/saveBid"})
//    public void saveBid(@RequestParam("startPrice") int startPrice) {
//        log.info("startPrice={}", startPrice);
//    }



    @PostMapping(value = "/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);

        return "redirect:/orders";
    }

}
