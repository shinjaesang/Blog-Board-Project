package kr.ac.kopo.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer")
public class Board extends BaseEntity {

    @Id // 기본키(primary key) 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 1씩 자동증가(auto-increment)
    private Long bno;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "writer_email", nullable = false) // 작성자는 필수로 설정
    private Member writer; // Foreign Key 설정(참조무결성 유지)

    // 변경된 제목으로 수정
    public void changeTitle(String title) {
        this.title = title;
    }

    // 변경된 내용으로 수정
    public void changeContent(String content) {
        this.content = content;
    }

    // 작성자의 이름을 가져오는 메소드
    public String getWriterName() {
        return writer != null ? writer.getName() : "Unknown"; // 작성자가 없으면 "Unknown" 반환
    }
}
