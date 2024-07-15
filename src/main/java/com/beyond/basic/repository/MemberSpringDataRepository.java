//package com.beyond.basic.repository;
//
//import com.beyond.basic.domain.Member;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface MemberSpringDataRepository extends MemberRepository, JpaRepository<Member, Long> { // 에러 에러 두 부모의 리턴타입이 안맞아용 그래서 충돌해용
//
//    Optional<Member> findByEmail(String email);
//    List<Member> findByName(String name);
//}
