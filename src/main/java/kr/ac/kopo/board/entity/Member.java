package kr.ac.kopo.board.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "boards")
public class Member extends BaseEntity {

    @Id
    private String email; // 이메일을 기본키로 설정

    private String password; // 비밀번호
    private String name; // 이름

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> boards; // 작성한 게시글 목록
}
