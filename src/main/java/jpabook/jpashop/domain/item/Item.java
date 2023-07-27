package jpabook.jpashop.domain.item;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)           // 상속테이블 전략
@DiscriminatorColumn(name = "dtype")        // 예를 들어 구분일 때 book 이면 어떡할거야 구분하기위해 잇움
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;


}