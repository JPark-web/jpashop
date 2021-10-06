package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.login.LoginService;
import jpabook.jpashop.web.LoginForm;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form,
                        BindingResult bindingResult, HttpServletResponse resp) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());



        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다");
            return "login/loginForm";

            //-----------------------로그인 처리 끝-------------------------
        }
        Cookie idCookie = new Cookie("memberSeqIdCookie", String.valueOf(loginMember.getSeq_id()));

        resp.addCookie(idCookie);
        log.info("idCookie.toString(); = {}", idCookie.toString());
        // 쿠키 생성 회원의 id(email) 을 memberId라는 이름으로 담는당
        return "redirect:/";

    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse resp) {
        expireCookie(resp, "memberSeqIdCookie");
        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse resp, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        resp.addCookie(cookie);

    }
}

//2021-10-06
/*
 로그인 쿠키 구현 테스트 완료
 디비 저장시 생성되는 seqid를 이용해서 가입된 유저의 이메일과 비번 찾아서
 일치여부 확인 및 쿠키 구현
 쿠키는 보안상 심각한 결함이 있다네
 그래서 내일은 세션을 구현해야 한다네
 */
