package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor            // 이건 final에 있는 필드만을 가지고 생성자를 만들어주고 주입도 해준다(생성자주입).
public class MemberService {

    // 얘는 이제 바뀌지 않으니 final 로 해주면 좋다
    private final MemberRepository memberRepository;        // 이렇게 쓰면 필드로 되어 있어서 바꿀 수가 없다.

//    @Autowired
//    public MemberService(MemberRepository memberRepository){            // 그래서 생성자 인젝션을 하면 좋다... 테스트케이스 작성할 때 직접 주입을 해줘야하는걸 명확하게 알 수가 있으니!
//        this.memberRepository = memberRepository;
//    }

    // 회원 가입
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);        // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
         member.setName(name);

    }

    // 회원 전체 조회

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }



    private void validateDuplicateMember(Member member) {
        // EXCEPTION
        List<Member> findmembers = memberRepository.findByName(member.getName());       // 사실 이렇게 해도 문제가 되는데.. 예를 들어 똑같은 이름 둘이 동시에 insert 를 하게 되면 둘 다 통과될 수도 있다.. 그래서 이때 한번더 방어하기 위해 DB 에 멤버의 이름을 유니크제약조건으로 잡안주는게 안전하다
        if(!findmembers.isEmpty()){                                                 // 최적화를 위해선 이거말고 == 0 이렇게 하는게 더 좋긴 하다.
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }



}
