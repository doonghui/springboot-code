package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.DeliveryStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)        // 얘의 기본은 EnumType.ORDINAL 인데 이러면 숫자로 들어가서 만약 중간에 값을 넣으려고 하면 순서가 밀려 문제들이 생긴다. 그래서 STRING type 으로 설정하자.
    private DeliveryStatus status;  // [READY, COMP]
}
