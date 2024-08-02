package com.beyond.basic.controller;

import com.beyond.basic.domain.Member;
import com.beyond.basic.domain.MemberDetResDto;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.service.MemberService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.plaf.PanelUI;
import java.util.List;

@Controller // 싱글톤(인지 아닌지 어케 알아? 컨트롤 누르고 확인해봐 @Component있음 싱글톤 생성)
//@RequiredArgsConstructor  // 초기화되어야하는 값들에 대해 초기화가 안되어 있으면 채워준다. 의존성 주입 방법3
public class MemberController {
//    의존성 주입(DI) 방법1. 생성자 주입방식 (가장많이 사용하는 방식)
//    장점 : 1) final(초기화 필요)을 통해 상수로 사용 가능(재할당 불가능-안정성올라감)   2) 다형성 구현 가능(인터페이스기반개발이 좋다)    3) 순환참조방지(중요도는 조금 떨어짐 다른 방식도 해줘서) :
// 생성자가 1개밖에 없을 때에는 Autowired 생략가능
    private final MemberService memberService; //service 어노테이션 없이 이 경우 생성자에 new로 객체 만들어야한다.
    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

//    의존성 주입(DI) 방법2. 필드주입 방식 (Autowired만 사용) -이렇게도 많이 쓴다. 위에거랑 거의 동일 그러나 final 못붙여용 왜? 초기화 못해서 그렇다면 재할당 가능하네용? 안정성이 떨어지네용
//    @Autowired
//    private MemberService memberService;

//    의존성 주입(DI) 방법3. 어노테이션(@RequiredArgs~)을 이용하는 방식  (요즘 많이 쓰는 방식 이긴 한데 다형성 구현에는 또 안되넹)
//    @RequiredArgsConstructor // @NonNull 어노테이션 , final 키워드가 붙어 있는 필드를 대상으로
//    private final MemberService memberService; // 1번하고 같은 결과값을 가진다.
//    @NonNull // 위에 @RequiredArgs~ 없으면 노란줄쳐져용 // final 역할이랑 비스읏 // 널값은 안돼용 초기화해세용 하는거
//    private MemberService memberService;





    @GetMapping("/")
    public String home(){
        return "member/home";
    }

    //    회원 목록 조회    화면명 : member-list    타이틀만 "회원목록조회"
    @GetMapping("/member/list")
    public String memberList(Model model){
        List<MemberResDto> memberList = memberService.memberList();
        model.addAttribute("memberList", memberList);
        return "member/member-list";
    }

//    회원 상세 조회    url(urli) : member/1, member/2   화면명 : member-detail
    @GetMapping("/member/detail/{id}")
//    int 또는 long 으로 받는 경우 스프링에서 알아서 형변환(String->Long)
    public String memberDetail(@PathVariable Long id, Model model){ // String 이 아니라 Long(long아닌 이유 wrapper가 주는 기능이 많다.)인 이유 (윗줄)
//        MemberResDto memberResDto = memberService.memberDetail(id);
//        model.addAttribute("member", memberResDto);
//        Member member = memberService.memberDetail(id);
//        model.addAttribute("member", member);

        MemberDetResDto memberDetResDto = memberService.memberDetail(id);
        model.addAttribute("member", memberDetResDto);

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
//    @ResponseBody // 나중에 화면과 데이터를 분리 시키면
//    public String memberCreatePost(Member member, Model model){
    public String memberCreatePost(MemberReqDto dto, Model model){
        try{
//        System.out.println(member);
            memberService.memberCreate(dto);
//        return "/member/member-list"; // 이건 데이터 빠진 상태로 화면만
//        화면 리턴이 아닌 url 재호출
            return "redirect:/member/list"; //detail/" + member.getId();
        }catch (IllegalArgumentException e){ // 서비스에서 Illegal로 던졌으니(그런식으로 예외던지는거 중요해 디비 저장전에) 여기서도 일리갈로 받아야해
            model.addAttribute("error", e.getMessage());
            return "member/member-error";
//            return e.getMessage(); // 나중에 화면과 데이터를 분리 시키면    // response = axios~  // response.data
        }

    }


}
