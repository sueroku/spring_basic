package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// MyMemberRepository가 되기 위해서는 JpaRepository(이게 SpringDataJpa 기능이 구현된 인터페이스야) 를 상속해야하고, 상속 시에 Entity명과 entity의 pk타입을 명시
// MyMemberRepository는 JpaRepository를 상속함으로서 JpaRepository의 주요 기능(save 등등 리턴타입마저도 정해져 있어)을 상속
// JpaRepository 가 인터페이스 임에도 해당 기능을 상속해서 사용할 수 있는 이유는 hibernate 구현체가 미리 구현되어 있기 때문.
// 런타임시점에 사용자가 인터페이스에 정의한 메서드를 (프록시(대리인)객체가) 리플렉션 기술을 통해 메서드를 구현
public interface MyMemberRepository extends JpaRepository<Member, Long> { // 인터페이스인 jpare~ 를 상속받았는데 구현이 없는데 어케 이게 작동하니? -> 어딘가에 구현체가 있고(hibernate) 런타임시 주입
//    메서드 추가할 수 있어~~ 적어놓으면 런타임시점에 실시간으로 만들어줘?!=리플렉션 (주요 메서드는 미리 만들어져 있고)
    Optional<Member> findByEmail(String email);
    Optional<Member> findByNameAndEmail(String name, String email); // 근데 만들때 함수명등은 정해진 규칙이 있어용
}

//public interface MemberSpringDataRepository extends MemberRepository, JpaRepository<Member, Long> { // 에러 에러 두 부모의 리턴타입이 안맞아용 그래서 충돌해용
//}
