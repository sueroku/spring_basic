package com.beyond.basic.service;

import com.beyond.basic.domain.*;
import com.beyond.basic.repository.NewMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//       input값의 검증 및 실질적인 비지니스 로직은 서비스 계층에서 수행
@Service // 서비스 계층임을 표현함과 동시에 싱글톤 객체로 생성
@Transactional (readOnly = true) // 조회성능향상 // 많이들 이렇게 한디야 근데 유난히 트랜잭션 롤백 처리가 필요한메서드에 붙이는 것도 효율적이야
// Transca~ 어노테이션을 통해 모든 메서드에 트랜잭션을 적용하고,(각 메서드 마다 하나의 트랜잭션으로 묶는다는 뜻))
// ==> 만약 에러가 발생시 롤백처리 자동화
public class MemberService {


//    다형성으로 가자
//    private final MemberRepository memberRepository; // final : 두번 할당이 안되용 // 레포파일에 Repo~ 어노테이션 없이 이 경우 생성자에 new로 객체 만들어야한다.
//
//    @Autowired // 싱글톤 객체를 주입 (DepencyInjection 의존성 주입=객체를주입) 받는다라는 것을 의미
//    public MemberService(MemberJpaRepository memoryRepository){  // MemberJpaRepo~~ 부분 바꾸면 나머지 레포 작업들 가능
//        this.memberRepository =  memoryRepository;
//    }

    private final NewMemberRepository memberRepository; // final : 두번 할당이 안되용 // repo 어노테이션 없이 이 경우 생성자에 new로 객체 만들어야한다.


    @Autowired // 싱글톤 객체를 주입 (DepencyInjection 의존성 주입=객체를주입) 받는다라는 것을 의미
    public MemberService(NewMemberRepository mRepository){  // MemberJpaRepo~~ 부분 바꾸면 나머지 레포 작업들 가능
        this.memberRepository = mRepository;
    } // 다형성 연습하던거.


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









    public void memberCreate(MemberReqDto dto){
        if(dto.getPassword().length()<=8){
            throw new IllegalArgumentException("비밀번호가 너무 짧습니다.");
        }
        if(memberRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 email입니다.");
        }

        Member member = dto.toEntity();
        memberRepository.save(member);

    }

//
//    public MemberReqDto memberCreate(MemberReqDto dto){
//        if(dto.getPassword().length()<=8){
//            throw new IllegalArgumentException("비밀번호가 너무 짧습니다.");
////            System.out.println("짧기는 개뿔!");
//        }
//        Member member = dto.toEntity();
//        memberRepository.save(member);
//        return dto;
//    }

////    @Transactional //
//    public void memberCreate(MemberReqDto dto){
//        if(dto.getPassword().length()<=8){
//            throw new IllegalArgumentException("비밀번호가 너무 짧습니다.");
////            System.out.println("짧기는 개뿔!");
//        }
//        Member member = dto.toEntity();
////        memberRepository.save(member);
//
//
//
//        if(member.getName().equals("kim")){
//            throw  new IllegalArgumentException("예외입니다");
//        }
//        memberRepository.save(member);
////        @Transactional(메서드든, 클래스든 어디에든 붇을때) 롤백처리 테스트 걍 다시 해봐 == 있으면 예외던지고 디비에 저장안된다. 없으면 예외는 던져지면서 디비에 저장된다.
//        if(member.getName().equals("kim")){
//            throw  new IllegalArgumentException("예외입니다");
//        } // 저장 전에 있어야 맞지 않니...?
//
//
//    }




    public MemberDetResDto memberDetail(Long id){ // MemberResDto // Member
//        MemberResDto memberResDto = new MemberResDto();
//        memberResDto.setId(member.getId());
//        memberResDto.setName(member.getName());
//        memberResDto.setEmail(member.getEmail());

//        Member member = memberRepository.findById(id);

        Optional<Member> optMember = memberRepository.findById(id);
//        MemberDetResDto memberDetResDto = new MemberDetResDto();
//        이런식으로 옵션널로 줘서 예외 터뜨리는 이유
//        클라이언트에게 적절한 예외메세지와 상태코드를 주는 것이 주요 목적
//        또한, 예외를 강제 발생시킴으로서 적절한 롤백처리 하는 것도 주요 목적 (이럴려면 @Transactional 이게 있어야 롤백)
        Member member = optMember.orElseThrow(()->new EntityNotFoundException("없는 회원 입니다."));
//        MemberDetResDto memberDetResDto = member.detFromEntity();

//        System.out.println("글쓴이의 쓴 글의 개수" + member.getPosts().size());
//        for(Post p : member.getPosts()){
//            System.out.println("글의 제목" + p.getTitle());
//        }

//        memberDetResDto.setId(member.getId());
//        memberDetResDto.setName(member.getName());
//        memberDetResDto.setEmail(member.getEmail());
//        memberDetResDto.setPassword(member.getPassword());
//        LocalDateTime createTime = member.getCreatedTime();
//        String value = createTime.getYear() + "년" + createTime.getMonthValue()+"월"+createTime.getDayOfMonth()+"일";
//        memberDetResDto.setCreatedTime(value);

//       return memberDetResDto; // memberResDto // member
        return member.detFromEntity();
    }

    public List<MemberResDto> memberList(){
        List<MemberResDto> memberResDtos = new ArrayList<>();
//        List<Member> memberList = memberRepository.findall();
        List<Member> memberList = memberRepository.findAll();
        for(Member m : memberList){
//            MemberResDto memberRes = new MemberResDto();
//            memberRes.setId(m.getId());
//            memberRes.setName(m.getName());
//            memberRes.setEmail(m.getEmail());
//            memberResDtos.add(memberRes);
            memberResDtos.add(m.listFromEntity());
        }
        return memberResDtos;
    }


    public void pwUpdate(MemberPwUpdateDto dto){
        // 사실 예외처리 다 해줘야해용 비밀번호 짧거나 그런거
        Member member = memberRepository.findById(dto.getId()).orElseThrow(()->new EntityNotFoundException("member is not found")); //  무조건 orElseThrow
        member.updatePw(dto.getPassword());

//        기존 객체를 조회후 수정한 다음에 save시에는 jpa update실행
        memberRepository.save(member); // 추가와 수정은 둘다 save 와우 대신 findById 해서 id 인식 시켜줭
    }

    public void memberDelete(Long id){
        Member member = memberRepository.findById(id).orElseThrow(()->new EntityNotFoundException("member is not found"));
        memberRepository.delete(member); // 완전 삭제 // 잘 안씁니다.

//        member.updateDelYN("Y");
//        memberRepository.save(member); //  일반적으로 이렇게 수정해용
    }

}
