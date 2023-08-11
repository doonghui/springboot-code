package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberForm {

    @NotEmpty(message="회원 이름은 필수로 입력돼야 합니다.")       // name 은 무조건 값이 들어가야한다 아니면 오류남
    private String name;
    private String city;
    private String street;
    private String zipcode;
}
