package com.beyond.basic.domain;

import lombok.Data;
import javax.xml.stream.events.DTD;

@Data
public class MemberReqDto {
    private String name;
    private String email;
    private String password;

}
