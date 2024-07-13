package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberJpaRepository implements MemberRepository{
//    EntityManager는 JPA의 핵심클래스(객체)
//    Entity(이를테면 DOMAIN의 MEMBER) 의 생명주기를 관리, 데이터베이스와의 모든 인터페이싱을 책임
//    즉, 엔티티를 대상으로 CRUD하는 기능을 제공

    @Autowired
    private EntityManager entityManager;

    @Override
    public Member save(Member member) {
//        persist : 전달된 entity(member)가 엔티티매니저의 관리상태가 되도록 만들어 주고, 트랜잭션이 커밋될때 데이터베이스에 저장. insert
        entityManager.persist(member);
        return null;
    }

    @Override
    public List<Member> findAll() {
//        jpql : jpa의 raw쿼리 문법(정확하게는 객체지향쿼리문법)
//        jpa 에서는 jpql 문법 컴파일에러가 나오지 않으나, springdatajpa에서는 컴파일 에러 발생
        List<Member> memberList = entityManager.createQuery("select m from Member m", Member.class).getResultList();
        return memberList;

//        List<Member> memberList = new ArrayList<>();
//        entityManager.
//        memberList.add();  // 혼자 시도
//        return null;
    }

//    public Member findByEmail(String email){
//        Member member =entityManager.createQuery("select m from Member m where m.email= :email", Member.class).setParameter("email",email).getSingleResult(); // email은 유니크라 싱글리절트로 가져와~ 네임이면 리스트로 받아와 중복이름 있을 수 있으니~
//        return member;
//    }

    @Override
//    public Member findById(Long id) {
    public Optional<Member> findById(Long id) {
//        entityManager를 통해 find(리턴타입클래스 지정 및 매개변수로 PK 필요)
        Member member = entityManager.find(Member.class, id);
//        return member;
        return Optional.ofNullable(member);
    }
//    pk 이외의 컬럼으로 조회할때
//    jpql 문법으로 raw쿼리 비슷하게 작성해야한다.
}
