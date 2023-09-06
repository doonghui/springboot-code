package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm",new MemberForm());          // 빈 객체를 만드는 이유는 뭔가 검증이나.. 그런걸 해준다.
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
//    @ApiOperation(value="시험11222", notes="시험입ㄴ디ㅏ..")
    public String create(@Valid MemberForm form, BindingResult result){     // @Valid 를 통해서 form 에 있는 스프링이 관련된 에너테이션(ex) @NotEmpty) 를 쓰는구나를 알 수 있다.

        // BindingResult result 이게 있으면 오류가 생길때 이 코드로 담겨서 실행을 할 수 있게 해준다.
        if(result.hasErrors()){     // validation
            return"members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);

        return "redirect:/";        // 재로딩하면 안좋기때문에


    }


    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }

}
