package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)        // Junit 실행할 때 스프링이랑 같이 엮어서 실행하고 싶을 때!
@SpringBootTest                     // 스프링 부트를 띄운 상태로 실행하고 싶을 때 무조건 있어야 한다.
@Transactional                      // 테스트가 끝나면 다 rollback 을 기본적으로 한다.
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        // given

        Member member = new Member();
        member.setName("park");
        // when
        Long saveId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findOne(saveId));
    }


    @Test(expected = IllegalStateException.class)       // try {} catch {} 말고 이렇게 넣어주면 깔끔하게 된다.
//    @Test
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // when

        memberService.join(member1);
//        try {
//            memberService.join(member2);        // 여기서 예외가 발생해야한다.
//
//        } catch (IllegalStateException e) {
//            return;
//        }
        memberService.join(member2);        // 여기서 예외가 발생해야한다.


        //then

        fail("예외가 발생해야합니다..!"); // 여기까지 코드가 오면 안되는데 만약 오면 이 코드를 통해서 예외를 발생해준다.

    }

}