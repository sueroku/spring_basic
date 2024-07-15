package com.beyond.basic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 그 전에는 db 의존적  // 문제는? db 바꾸면.. 등등...
// -> 객체 의존적
// jpa부터는 객체 중심 사상

//@Data
@Getter
@Entity // Entity어노테이션을 통해 엔티티매니저에게 객체관리를 위임 // 해당 클래스명으로 테이블 및 컬럼을 자동생성하고, 각종 설정정보 위임 // jpa에게 네가 관리해야하는 클래스(엔티티-테이블(없으면 만들어))얌 이라고 알려줘야해
//@AllArgsConstructor // 모든 매개변수에 대한 생성자 // 쓸거면 아래도 같이 써
@NoArgsConstructor // 기본 생성자는 JPA에서 필수
public class Member {
    @Id // PK설정, 필수값
//    identity : auto-increament 설정
//    auto : jpa 자동으로 적절한 전략을 선택하도록 맡기는 것.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Long은 bigint로 변환
//    String은 varchar(255)로 기본으로 변환. name 변수명이 name 컬럼명으로 변환.
    private String name;
    @Column(nullable = false, length = 50, unique = true) // nullable = false(기본은true =NULL) : NOT NULL 제약조건, 크기, unique = true : unique 제약조건
    private String email;
//    @Column(name="pw")  // 이렇게 할 수 있으나, 컬럼명과 변수명을 일치시키는 것이 혼선을 줄일 수 있음.
    private String password; // 변수명 passWord-> 컬럼명 pass_word 로 변환해주기는 하는데, 애초에 그냥 싱크를 맞춰라.

    @OneToMany(mappedBy = "member") // 이건 옵션
    private List<Post> posts;




    @CreationTimestamp //  DB에는 current_timestamp가 생성되지 않음
    private LocalDateTime createdTime; // 캐멀케이스 사용시 DB에는 _(언더바) 로 생성
    @UpdateTimestamp
    private LocalDateTime updateTime; //

    public Member(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }

//    password상단에 @Setter를 통해 특정 변수만 세터 사용이 가능하나,
//    일반적으로 의도를 명확하게 한 메서드를 별도로 만들어 사용하는 것을 권장. 사용목적을 명확히 하라.
    public void updatePw(String password){
        this.password = password;
    }

    public MemberDetResDto detFromEntity(){
        LocalDateTime createTime = this.getCreatedTime();
        String value = createTime.getYear() + "년" + createTime.getMonthValue()+"월"+createTime.getDayOfMonth()+"일";
        return new MemberDetResDto(this.id, this.name, this.email, this.password, value);
    }

    public MemberResDto listFromEntity(){
        return new MemberResDto(this.id, this.name, this.email);
    }
}
