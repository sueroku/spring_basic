package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import java.util.List;

public interface MemberRepository {

    Member save(Member member); // return 타입 void 아니면 Member(확인용(?))

    List<Member> findall();

    Member findById(Long id);


}
