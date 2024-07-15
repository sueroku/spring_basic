package com.beyond.basic.controller;

import com.beyond.basic.domain.MemberDetResDto;
import com.beyond.basic.domain.MemberPwUpdateDto;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest") // 이 어노테이션의 경우 모든 메서드 상단에 @ResponseBody 가 붙는 효과 발생
public class MemberRestController {

    private final MemberService memberService;
    @Autowired
    public MemberRestController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("/member/list")
    public List<MemberResDto> memberList(Model model){
        List<MemberResDto> memberList = memberService.memberList();
//        model.addAttribute("memberList", memberList);
        return memberList;
    }

    @GetMapping("/member/detail/{id}")
    public MemberDetResDto memberDetail(@PathVariable Long id){
//        MemberDetResDto memberDetResDto = memberService.memberDetail(id);
//        model.addAttribute("member", memberDetResDto);
        return memberService.memberDetail(id);
    }


//    @GetMapping("/member/create") //  왜 안해요? 지금 당장은 GET할것도 없고 화면 필요 없어서??/??
    @PostMapping("/member/create")
    public String memberCreatePost(@RequestBody MemberReqDto dto){ // @ModelAttribute  + 객채 // @RequestBody  json
        try{
            memberService.memberCreate(dto);
            return "ok";
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            return "error!!";
        }
    }

//    수정은 2가지 요청 방식 : PUT, PATCH 요청   // POST로도 하기도 함.
//    PATCH : 부분 수정  // PUT : 덮어쓰기
    @PatchMapping("/member/pw/update") // @PutMapping       // 의미만 가질뿐 기능을 가지는 건 아니다. 기능은 밑의 구현이 한다. //   통신규약에 맞는 거뿐이야 이건 싱크만 맞출뿐이야 약속일 뿐이야.
    public String memberPwUpdate(@RequestBody MemberPwUpdateDto dto){ // @RequestParam 어찌구~~ String password ~~~ 어찌구이거나
        memberService.pwUpdate(dto);
        return "ok";
    }

    @DeleteMapping("/member/delete/{id}")
    public String memberDelete(@PathVariable Long id){
        memberService.memberDelete(id);
        return "ok";
    }

}
