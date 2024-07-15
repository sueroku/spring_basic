package com.beyond.basic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
//@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
//    1:1의 경우 OneToOne 을 설정하고, unique=true로 설정 @OneToOne @JoinColumn(name = "member_id", unique=true)
    @ManyToOne // fk 관리하는 쪽에 붙이는 데........ 필요하면
    @JoinColumn(name = "member_id")
//    JPA 의 영속성(persistence)컨텍스트에 의해 member가 관리
    private Member member;  // spring

    public PostResDto listFromEntity(){
        return new PostResDto(this.id, this.title);
    }
}
