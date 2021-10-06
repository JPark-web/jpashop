package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final ItemService itemService; // 메인메뉴 핫 아이템 출력 위한 DI
    private final MemberService memberService; // 로그인 관련 쿠키, 세션 적용 위한 DI


    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("msg", "class=\'items\'");
        return "index_new";

    }

    @GetMapping("/")
    public String homeLoginAndMenuCall(
        @CookieValue(name = "memberSeqIdCookie", required = false)
                Long seq_id, Model model) {
        //메인메뉴 핫아이템 출력 로직 시작
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        //메인메뉴 핫아이템 출력 로직 끝
        if (seq_id == null) {
            log.info("seq_id == null");
            return "index_new";
        }
        Member loginMember = memberService.findOne(seq_id);
        if (loginMember == null) {
            log.info("memberEmail == null");
            return "index_new";
        }





        model.addAttribute("member", loginMember);
        return "index_new";
    }



    @RequestMapping("/login")
    public String login() {
        return "login";
    }

//    @GetMapping(value = "/index-new")
//    public String hotItemList(Model model) {
//        List<Item> hotItems = itemService.findItems();
//        model.addAttribute("hotitems", hotItems);
//        return ""
//    }
}
