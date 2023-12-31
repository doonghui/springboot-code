package jpabook.jpashop.domain.item;


import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();


    // ==비즈니스 로직== //
    /**
     * stock 증가
     * **/
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    // ==비즈니스 로직== //
    /**
     * stock 감소
     * **/
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if(restStock <0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;

    }


}
