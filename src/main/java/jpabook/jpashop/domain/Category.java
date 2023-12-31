package jpabook.jpashop.domain;


import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name ="category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),    // 중간테이블에 있는 category_id
            inverseJoinColumns = @JoinColumn(name = "item_id")      // 중간테이블에 있는 item_id
    )    private List<Item> items = new ArrayList<>();

    // 카테고리의 계층구조를 설계할 때
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();


    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
