package kr.ac.kopo.board.entity;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
abstract class BaseEntity {
    @CreatedDate
    @Column(name = "regDate", updatable = false)
    private LocalDateTime regDate; // 등록한 날짜 및 시간

    @LastModifiedDate
    @Column(name = "modDate") // 수정한 날짜는 계속 갱신되어야함. updatable이 true거나 생략
    private LocalDateTime modDate; // 수정한 날짜 및 시간
}
