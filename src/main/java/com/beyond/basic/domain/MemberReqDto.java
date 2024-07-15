package com.beyond.basic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.stream.events.DTD;
import java.time.LocalDateTime;
import java.util.spi.LocaleServiceProvider;

@Data
@AllArgsConstructor // 안써도 된다.
@NoArgsConstructor
public class MemberReqDto {
    private String name;
    private String email;
    private String password;

    // Dto에서 entity로 전환
    // 추후에는 빌더패턴으로 변환
    public Member toEntity(){
//        Member member = new Member(this.name, this.email, this.password);
        return new Member(this.name, this.email, this.password);
    }


    // Dto에서 entity로 전환
//    public Member toEntity(){
//        Member member = new Member();
//        member.setName(this.name);
//        member.setEmail(this.email);
//        member.setPassword(this.password);
//        return member;
//    } // setter 쓰지 말자! -> 생성자


}
