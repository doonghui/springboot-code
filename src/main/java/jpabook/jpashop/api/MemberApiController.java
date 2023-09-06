package jpabook.jpashop.api;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;



    @GetMapping("/api/v1/members")        // 위험!
    public List<Member> membersV1() {
        return memberService.findMembers();
    }


    // 멤버 조회
    @GetMapping("/api/v2/members")
//    @ApiOperation(value="시험11", notes="시험입ㄴ디ㅏ..")
    public Result membersV2() {
        List<Member> findMembers =memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());

        return new Result(collect);
    }


    @PostMapping("/api/v1/members")       // 위험!
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);

    }
    // 멤버 생성
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);

        return new CreateMemberResponse(id);
    }
    // 멤버 수정
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberResponse(@PathVariable("id") Long id,@RequestBody @Valid UpdateMemberRequest request ){

        memberService.update(id,request.getName());
        Member findMember = memberService.findOne(id);

        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {

        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

    @Data
    static class UpdateMemberRequest {
        private String name;

    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;

    }


    @Data
    static class CreateMemberRequest {
        @NotEmpty
        private String name;
    }


    @Data
    @AllArgsConstructor
    static class CreateMemberResponse {
        private Long id;
    }


}
