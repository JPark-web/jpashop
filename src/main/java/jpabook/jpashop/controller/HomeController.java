package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final ItemService itemService;
    private final MemberService memberService;

    @GetMapping("/")
    public String indexPage(Model model, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                            @SessionAttribute(name = "loginMember", required = false) Member loginedMember) {

        //메인메뉴 핫아이템 출력 로직 시작
        List<Item> items = itemService.findItems();
        List<Member> members = memberService.findMember();
        model.addAttribute("items", items);
        model.addAttribute("members", members);

        if (loginMember == null) {
            return "index-2";

        }
        model.addAttribute("member", loginMember);
        model.addAttribute("loginedMember", loginedMember);
        return "index-2";
    }


}

