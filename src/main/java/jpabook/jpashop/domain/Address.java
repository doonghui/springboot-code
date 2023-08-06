package jpabook.jpashop.domain;


import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable     // 어딘가에 내장될 수 있다.
@Getter         // 값 타입은 한번 생성되고나서 변경될 일이 없으니 Setter 는 아예 필요없다.
public class Address {


    private String city;
    private String street;
    private String zipcode;


    // 엔티티나 임베디드타입은 리플렉션이나 프록시를 사용해야하는데 기본생성자 없이는 사용이 불가능해서 만들어줘야함. 대신에 얘를 public 으로 하면 막 호출할 수도 있으니 protected 로 해놓으면 보고 JPA 스펙상 만들어뒀구나~ 건들이지말자라고 생각 가능
    protected Address() {}


    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
