package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final MemberRepository memberRepository;


    /**
     * @return null이면 로그인 실패
     */

    public Member login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .filter(m -> m.getPass().equals(password))
                .orElse(null);

        return member;
    }
    public String loginGetName(String email, Member loginMember ) {
        if (loginMember != null) {
            List<Member> nameByEmail = memberRepository.findNameByEmail(email);

            return nameByEmail.get(0).getName();
        }
        return "로그인하세용";
        }

//    public String loginEmailReturn(String email) {
////
//
//        }
    }



