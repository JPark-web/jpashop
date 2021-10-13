package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // Jpa 와 연동해서 사용할 클래스는 @Entity가 필수
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long seq_id;
    private String email;
    private String pass;
    //private String city;
    private String name;
    private String phone;

    private String postcode;
    private String address;


    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Bid> bids = new ArrayList<>();
}
