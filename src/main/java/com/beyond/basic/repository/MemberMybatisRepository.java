package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper // 해당 레파지토리를 mybatis기술을 쓰겠다는 표현 // mybatis 의존성 추가 필요
public interface MemberMybatisRepository extends MemberRepository{ // 상속을 하면 아래 코드를 써줄 필요가 없어요~ 다 상속 받으니까~
//    List<Member> findAll();
//    Member save();
//    Member findById(Long id);
}
