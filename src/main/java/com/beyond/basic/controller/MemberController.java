package com.beyond.basic.controller;

import com.beyond.basic.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemberController {

//    회원 목록 조회    화면명 : member-list    타이틀만 "회원목록조회"
    @GetMapping("/member/list")
    public String memberList(){
        return "member/member-list";
    }

//    회원 상세 조회    url(urli) : member/1, member/2   화면명 : member-detail
    @GetMapping("/member/{id}")
//    int 또는 long 으로 받는 경우 스프링에서 알아서 형변환(String->Long)
    public String memberDetail(@PathVariable Long id){ // String 이 아니라 Long(long아닌 이유 wrapper가 주는 기능이 많다.)인 이유 (윗줄)
        return "member/member-detail";
    }


//    회원 가입 화면
//    url : member/create    화면명 : member-create
    @GetMapping("/member/create")
    public String memberCreate(){
        return "member/member-create";
    }

//    회원 가입 데이터 받는다.
//    url : member/create
//    name, email, password
    @PostMapping("/member/create")
    public String memberCreatePost(Member member){
        return null;
    }


}
