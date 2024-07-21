package com.beyond.basic.controller;

import com.beyond.basic.domain.*;
import com.beyond.basic.repository.NewMemberRepository;
import com.beyond.basic.service.MemberService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/rest")
@Api(tags = "회원관리서비스") // 협업시
public class MemberRestController {

    private final MemberService memberService;
    @Autowired
    public MemberRestController(MemberService memberService, NewMemberRepository newMemberRepository){
        this.memberService = memberService;
        this.newMemberRepository = newMemberRepository;
    }

    @GetMapping("/member/text")
    public String memberText(){
        return "ok";
    }

    @GetMapping("/member/list")
    public ResponseEntity<Object> memberList(){
        List<MemberResDto> memberList = memberService.memberList();
        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "member is successfully referred", memberList);
        return new ResponseEntity<>(commonResDto, HttpStatus.OK);
    }

    @GetMapping("/member/detail/{id}")
    public ResponseEntity<Object> memberDetail(@PathVariable Long id){
        try {
            MemberDetResDto memberDetResDto = memberService.memberDetail(id);
            CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "member is successfully referred", memberDetResDto);
            return new ResponseEntity<>(commonResDto,HttpStatus.OK);
        }catch (EntityNotFoundException e){
            CommonErrorDto errorDto = new CommonErrorDto(HttpStatus.NOT_FOUND,e.getMessage());
            return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/member/create")
    public ResponseEntity<Object> memberCreatePost(@RequestBody MemberReqDto dto){
        try {
            memberService.memberCreate(dto);
//            CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED, "member is successfully created", memberReqDto);
            CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED, "member is successfully created", null); // null 도 객체니까 괜찮아!
            return new ResponseEntity<>(commonResDto,HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            CommonErrorDto errorDto = new CommonErrorDto(HttpStatus.BAD_REQUEST,e.getMessage());
            return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
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





    private final NewMemberRepository newMemberRepository;
//    lazy(지연로딩), eager(즉시로딩) 테스트
    @GetMapping("/member/post/all")
    public void memberPostAll(){
        List<Member> memberList = newMemberRepository.findAll();
        System.out.println(memberList);

//        for(Member m : memberList){
//            System.out.println(m.getPosts().size());
//        }

//        System.out.println(newMemberRepository.findAll());
    }

}





//package com.beyond.basic.controller;
//
//import com.beyond.basic.domain.MemberDetResDto;
//import com.beyond.basic.domain.MemberPwUpdateDto;
//import com.beyond.basic.domain.MemberReqDto;
//import com.beyond.basic.domain.MemberResDto;
//import com.beyond.basic.service.MemberService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController //@ResponseBody + @Controller // 이 어노테이션의 경우 모든 메서드 상단에 @ResponseBody 가 붙는 효과 발생
//@RequestMapping("/rest")
//public class MemberRestController {
//
//    private final MemberService memberService;
//    @Autowired
//    public MemberRestController(MemberService memberService){
//        this.memberService = memberService;
//    }
//
//    @GetMapping("/member/list")
//    public List<MemberResDto> memberList(){
////        List<MemberResDto> memberList = memberService.memberList();
////        model.addAttribute("memberList", memberList);
//        return memberService.memberList();
//    }
//
//    @GetMapping("/member/detail/{id}")
//    public MemberDetResDto memberDetail(@PathVariable Long id){
////        MemberDetResDto memberDetResDto = memberService.memberDetail(id);
////        model.addAttribute("member", memberDetResDto);
//        return memberService.memberDetail(id);
//    }
//
//
////    @GetMapping("/member/create") //  왜 안해요? 지금 당장은 GET할것도 없고 화면 필요 없어서??/??
//    @PostMapping("/member/create")
//    public String memberCreatePost(@RequestBody MemberReqDto dto){ // @ModelAttribute  + 객채 // @RequestBody  json
//        try{
//            memberService.memberCreate(dto);
//            return "ok";
//        }catch (IllegalArgumentException e){
//            e.printStackTrace();
//            return "error!!";
//        }
//    }
//
////    수정은 2가지 요청 방식 : PUT, PATCH 요청   // POST로도 하기도 함.
////    PATCH : 부분 수정  // PUT : 덮어쓰기
//    @PatchMapping("/member/pw/update") // @PutMapping       // 의미만 가질뿐 기능을 가지는 건 아니다. 기능은 밑의 구현이 한다. //   통신규약에 맞는 거뿐이야 이건 싱크만 맞출뿐이야 약속일 뿐이야.
//    public String memberPwUpdate(@RequestBody MemberPwUpdateDto dto){ // @RequestParam 어찌구~~ String password ~~~ 어찌구이거나
//        memberService.pwUpdate(dto);
//        return "ok";
//    }
//
//    @DeleteMapping("/member/delete/{id}")
//    public String memberDelete(@PathVariable Long id){
//        memberService.memberDelete(id);
//        return "ok";
//    }
//
//}
