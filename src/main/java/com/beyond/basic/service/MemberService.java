package com.beyond.basic.service;

import com.beyond.basic.controller.MemberController;
import com.beyond.basic.domain.Member;
import com.beyond.basic.domain.MemberDetResDto;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//       input값의 검증 및 실질적인 비지니스 로직은 서비스 계층에서 수행
@Service // 서비스 계층임을 표현함과 동시에 싱글톤 객체로 생성
@Transactional // Transca~ 어노테이션을 통해 모든 메서드에 트랜잭션을 적용하고,(각 메서드 마다 하나의 트랜잭션으로 묶는다는 뜻))
//만약 에러가 발생시 롤백처리 자동화
public class MemberService {


//    다형성으로 가자
//    private final MemberRepository memberRepository; // final : 두번 할당이 안되용 // repo 어노테이션 없이 이 경우 생성자에 new로 객체 만들어야한다.
//
//    @Autowired // 싱글톤 객체를 주입 (DepencyInjection 의존성 주입=객체를주입) 받는다라는 것을 의미
//    public MemberService(MemberJpaRepository memoryRepository){  // MemberJpaRepo~~ 부분 바꾸면 나머지 레포 작업들 가능
//        this.memberRepository =  memoryRepository;
//    }

    private final MemberRepository memberRepository; // final : 두번 할당이 안되용 // repo 어노테이션 없이 이 경우 생성자에 new로 객체 만들어야한다.

    @Autowired // 싱글톤 객체를 주입 (DepencyInjection 의존성 주입=객체를주입) 받는다라는 것을 의미
    public MemberService(MemberSpringDataRepository mRepository){  // MemberJpaRepo~~ 부분 바꾸면 나머지 레포 작업들 가능
        this.memberRepository =  mRepository;
    } // 여기까지 다형성


//     비다형성설계
//    private final MyMemberRepository memberRepository; // final : 두번 할당이 안되용 // repo 어노테이션 없이 이 경우 생성자에 new로 객체 만들어야한다.
//
//    @Autowired // 싱글톤 객체를 주입 (DepencyInjection 의존성 주입=객체를주입) 받는다라는 것을 의미
//    public MemberService(MyMemberRepository mRepository){  // MemberJpaRepo~~ 부분 바꾸면 나머지 레포 작업들 가능
//        this.memberRepository =  mRepository;
//    }



//    @Autowired
//    private MemberMemoryRepository memoryRepository; // 이 경우에는 인터페이스 (= new 함수) 를 사용할 수 없다. 다형성 구현 안돼용

//    순환참조방지 확인하기 위해 작성
//    @Autowired
//    private MemberController memberController;


    @Transactional
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

    public MemberDetResDto memberDetail(Long id){ // MemberResDto // Member
//        MemberResDto memberResDto = new MemberResDto();
//        memberResDto.setId(member.getId());
//        memberResDto.setName(member.getName());
//        memberResDto.setEmail(member.getEmail());

//        Member member = memberRepository.findById(id);

        Optional<Member> optMember = memberRepository.findById(id);
        MemberDetResDto memberDetResDto = new MemberDetResDto();
//        이런식으로 옵션널로 줘서 예외 터뜨리는 이유
//        클라이언트에게 적절한 예외메세지와 상태코드를 주는 것이 주요 목적
//        또한, 예외를 강제 발생시킴으로서 적절한 롤백처리 하는 것도 주요 목적 (이럴려면 @Transactional 이게 있어야 롤백)
        Member member = optMember.orElseThrow(()->new EntityNotFoundException("없는 회원 입니다."));
        memberDetResDto.setId(member.getId());
        memberDetResDto.setName(member.getName());
        memberDetResDto.setEmail(member.getEmail());
        memberDetResDto.setPassword(member.getPassword());
       return memberDetResDto; // memberResDto // member
    }

    public List<MemberResDto> memberList(){
        List<MemberResDto> memberResDtos = new ArrayList<>();
//        List<Member> memberList = memberRepository.findall();
        List<Member> memberList = memberRepository.findAll();
        for(Member m : memberList){
            MemberResDto memberRes = new MemberResDto();
            memberRes.setId(m.getId());
            memberRes.setName(m.getName());
            memberRes.setEmail(m.getEmail());
            memberResDtos.add(memberRes);
        }
        return memberResDtos;
    }

}
