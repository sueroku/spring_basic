package com.beyond.basic.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity // Entity어노테이션을 통해 엔티티매니저에게 객체관리를 위임 // 해당 클래스명으로 테이블 및 컬럼을 자동생성하고, 각종 설정정보 위임 // jpa에게 네가 관리해야하는 클래스(엔티티-테이블(없으면 만들어))얌 이라고 알려줘야해
public class Member {
    @Id // PK설정, 필수값
//    identity : auto-increament 설정
//    auto : jpa 자동으로 적절한 전략을 선택하도록 맡기는 것.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Long은 bigint로 변환
//    String은 varchar(255)로 기본으로 변환. name 변수명이 name 컬럼명으로 변환.
    private String name;
    @Column(nullable = false, length = 50, unique = true) // nullable = false : NOT NULL 제약조건, 크기, unique = true : unique 제약조건
    private String email;
//    @Column(name="pw")  // 이렇게 할 수 있으나, 컬럼명과 변수명을 일치시키는 것이 혼선을 줄일 수 있음.
    private String password; // 변수명 passWord-> 컬럼명 pass_word 로 변환해주기는 하는데, 애초에 그냥 싱크를 맞춰라.
}
