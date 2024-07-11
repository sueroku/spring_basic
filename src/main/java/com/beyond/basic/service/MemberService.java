package com.beyond.basic.service;

import com.beyond.basic.controller.MemberController;
import com.beyond.basic.domain.Member;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.repository.MemberJdbcRepository;
import com.beyond.basic.repository.MemberMemoryRepository;
import com.beyond.basic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//       input값의 검증 및 실질적인 비지니스 로직은 서비스 계층에서 수행
@Service // 서비스 계층임을 표현함과 동시에 싱글톤 객체로 생성
public class MemberService {
    private final MemberRepository memberRepository; // final : 두번 할당이 안되용 // repo 어노테이션 없이 이 경우 생성자에 new로 객체 만들어야한다.

    @Autowired // 싱글톤 객체를 주입 (DepencyInjection 의존성 주입=객체를주입) 받는다라는 것을 의미
    public MemberService(MemberJdbcRepository memoryRepository){
        this.memberRepository =  memoryRepository;
    }

//    @Autowired
//    private MemberMemoryRepository memoryRepository; // 이 경우에는 인터페이스 (= new 함수) 를 사용할 수 없다. 다형성 구현 안돼용

//    순환참조방지 확인하기 위해 작성
//    @Autowired
//    private MemberController memberController;


    public void memberCreate(MemberReqDto dto){
        if(dto.getPassword().length()<=8){
            throw new IllegalArgumentException("비밀번호가 너무 짧습니다.");
//            System.out.println("짧기는 개뿔!");
        }
        Member member = new Member();
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        member.setPassword(dto.getPassword());
        memberRepository.save(member);
    }

    public Member memberDetail(Long id){
       return memberRepository.findById(id);
    }

    public List<MemberResDto> memberList(){
        List<MemberResDto> memberResDtos = new ArrayList<>();
        List<Member> memberList = memberRepository.findall();
        for(Member m : memberList){
            MemberResDto memberRes = new MemberResDto();
            memberRes.setName(m.getName());
            memberRes.setEmail(m.getEmail());
            memberResDtos.add(memberRes);
        }
        return memberResDtos;
    }

}
