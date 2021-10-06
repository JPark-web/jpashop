package jpabook.jpashop.domain.item;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryCode {
    private String code;
    private String displayName;
}

// 배송 방식은 DeliveryCode 클래스 이용. code는 'FAST' 같은 시스템에 전달하는 값
    // displayName은 '빠른 배송' 같은 고객에게 보여지는 값