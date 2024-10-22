package kr.ac.kopo.board.repository;

import kr.ac.kopo.board.entity.Board;
import kr.ac.kopo.board.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {
    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @Transactional
    public void insertReplies(){
        Random random = new Random();

        // 먼저 Board 엔티티를 삽입합니다.
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Board board = Board.builder()
                    .title("Title " + i)
                    .content("Content " + i)
                    .build();
            boardRepository.save(board);
        });

        // Board 엔티티가 제대로 삽입되었는지 확인합니다.
        long boardCount = boardRepository.count();
        System.out.println("저장된 총 게시글 수: " + boardCount);
        assert boardCount == 100 : "게시글이 올바르게 저장되지 않았습니다!";

        // 그 다음 Reply 엔티티를 삽입합니다.
        IntStream.rangeClosed(1, 100).forEach(i -> {
            long bno = random.nextInt(100) + 1; // 1~100 사이의 임의의 long 형 정수 값

            // Board 엔티티가 존재하는지 확인합니다.
            Board board = boardRepository.findById(bno).orElse(null);
            if (board == null) {
                System.out.println("Board ID " + bno + " does not exist.");
                return;
            }

            Reply reply = Reply.builder()
                    .text("Reply" + i)
                    .board(board)
                    .replyer("guest")
                    .build();

            replyRepository.save(reply);
        });

        // 선택 사항: 저장된 댓글이 올바르게 저장되었는지 확인하는 assertion 추가
        long replyCount = replyRepository.count();
        System.out.println("저장된 총 댓글 수: " + replyCount);
        assert replyCount == 100 : "댓글이 올바르게 저장되지 않았습니다!";
    }
}
