package jpabook.jpashop.web;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty
    @Email(message = "아이디(이메일) 입력은 필수입니다.")
    private String email;

    private String pass;
    private String passCheck;

    private String postcode;

    private String phone;

    private String name;

    private String address;
    private String extraAddress;
    private String detailAddress;
}
