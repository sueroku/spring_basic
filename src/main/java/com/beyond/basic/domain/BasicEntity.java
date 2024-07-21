package com.beyond.basic.domain;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 기본적으로 entity는 상속관계가 불가능 하며, 해당 어노에이션을 붙여야 상속관계 성립가능
public abstract class BasicEntity { // id 추
    @CreationTimestamp
    private LocalDateTime createdTime;
    @UpdateTimestamp
    private LocalDateTime updateTime;

}

