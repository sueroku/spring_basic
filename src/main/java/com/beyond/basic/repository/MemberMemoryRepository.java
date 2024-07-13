package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository // 해당 클래스가 Repository계층임을 표현함과 동시에 싱글톤객체로 (어딘가에)생성
public class MemberMemoryRepository implements MemberRepository{
    private final List<Member> memberList;

    MemberMemoryRepository(){
         memberList = new ArrayList<>();
    }
    @Override
    public Member save(Member member) {
        memberList.add(member);
        // 사실은 방금 디비에 넣은 멤버를 for문으로 찾아서 그 객체를 리턴해야해
        return null;
    }

    @Override
    public List<Member> findAll() {
        return memberList;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return null;
    }
}
