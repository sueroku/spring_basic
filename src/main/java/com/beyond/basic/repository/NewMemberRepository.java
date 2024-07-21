package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewMemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    List<Member> findByName(String name);

//    jpql문법을 통한 raw 쿼리 작성시 컴파일타임에서 오류 체크 // 그냥 jpa는 체크 안해줘!
//    @Query("select m from Member m")
//    List<Member> myFindAll();
}
