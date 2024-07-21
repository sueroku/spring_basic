package com.beyond.basic.controller;

import com.beyond.basic.domain.CommonResDto;
import com.beyond.basic.domain.Member;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/response/entity")
public class ResponseEntityController {

//    case1.  @ResponseStatus 어노테이션 방식
    @GetMapping("/annotation1")
//    @ResponseStatus(HttpStatus.CREATED) // 이거 없이 하면 서버가 알아서 헤더에 200
    @ResponseStatus(HttpStatus.OK) // 헤더에 들어가는 거~ 이게 정상적인 버전 200
    public String annotation1(){
        return "ok"; // 오케이는 바디에 들어간다  // 레스폰즈바디에는 뭐가 들어가나 헤더와 바디
    }

    @GetMapping("/annotation2")
    @ResponseStatus(HttpStatus.CREATED) // 컨트롤 눌러서 들어가봐 201 // 없으면 200(에러없이 응답없이~)
    public Member annotation2(){
//        (가정) 객체 생성 후 DB저장 성공
        Member member =new Member("hong", "hong@naver.com", "a1234567890");
        return member; // 바디에 멤버 객체가 제이슨 형태로 들어가용
    }



//  case2. 메서드 체이닝 방식 : ResponseEntity 의 클래스 메서드 사용   // 전부 다 많이 사용돼용 보면 아는 정도는 되어야해용
    @GetMapping("/chaining1")
    public ResponseEntity<Member> chaning1(){
        Member member =new Member("hong", "hong@naver.com", "a1234567890");
//        return ResponseEntity.ok(member); // 헤더에 200
        return ResponseEntity.status(HttpStatus.OK).body(member);
    }

    @GetMapping("/chaining2")
    public ResponseEntity<Member> chaning2(){
        Member member =new Member("hong", "hong@naver.com", "a1234567890");
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }

    @GetMapping("/chaining3")
    public ResponseEntity<Member> chaning3(){
        return ResponseEntity.notFound().build(); // 그냥 404
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("??"); // ?????????????
    }





//    case3. ResonseEntity 객체를 직접 custom하여 생성하는 방식 -- 이게 최최종 이거 써라    csr에서 상태를 전달하는 건 매우 중요
    @GetMapping("/custom1")
    public ResponseEntity<Member> custom1(){
        Member member =new Member("hong", "hong@naver.com", "a1234567890");
        return new ResponseEntity<>(member, HttpStatus.CREATED); // 바디에는 멤버가, 헤더에는 HttpStatus.CREATED 가
    }

//    @GetMapping("/custom2")
//    public ResponseEntity<StatusMember> custom2(){
//        Member member =new Member("hong", "hong@naver.com", "a1234567890");
//        return new ResponseEntity<>(new StatusMember(HttpStatus.CREATED.value(), "ok", member), HttpStatus.CREATED);
//    } // 내가 한거


//    최최종 데이터(+상태) 넘기는 방식
//    도메인 폴더에 CommonResDto
    @GetMapping("/custom2")
    public ResponseEntity<CommonResDto> custom2(){
        Member member =new Member("hong", "hong@naver.com", "a1234567890");
        CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED, "member is successfully created", member); // 여기 HttpStatus.CREATED 바디에 들어가는 거 선택 // 여기 멤버자리에 멤버resdto 들어가야하는 거 아니냐? 엔티티역할 하는 멤버는 안건들이는거 아니냐구.. // result가 리스트 일 경우도 가능
        // 에러나는 경우에는....? CommonErrorDto 만들고~ ...? 그 다음은..?
        return new ResponseEntity<>(commonResDto, HttpStatus.CREATED); // 여기 HttpStatus.CREATED 헤더에 들어가는거 선택이 아니라 필수
    }


}

//@Data
//class StatusMember{ // 내가 한거
//    private int status_code;
//    private String status_message;
//    private Member member;
//    public StatusMember(int sc, String sm, Member m){
//        this.status_code=sc;
//        this.status_message=sm;
//        this.member = m;
//    }
//    public StatusMember(){}
//}
