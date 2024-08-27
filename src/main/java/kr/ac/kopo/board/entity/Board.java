package kr.ac.kopo.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Board extends BaseEntity{

    @Id //기본키 설정(primary key)
    @GeneratedValue(strategy = GenerationType.IDENTITY)//1씩 자동 증가(auto-increment)
    private Long bno;
    private String title;
    private String content;

}
