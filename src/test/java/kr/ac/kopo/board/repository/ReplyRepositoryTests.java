package kr.ac.kopo.board.repository;

import kr.ac.kopo.board.entity.Board;
import kr.ac.kopo.board.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertReplies() {
        Random random = new Random();
        long boardCount = boardRepository.count();

        if (boardCount < 100) {
            System.out.println("게시글이 충분하지 않습니다. 먼저 게시글을 생성하세요.");
            return;
        }

        IntStream.rangeClosed(1, 100).forEach(i -> {
            long boardId = random.nextInt((int) boardCount) + 1;
            Board board = boardRepository.findById(boardId).orElse(null);

            if (board != null) {
                Reply reply = Reply.builder()
                        .text("Reply " + i)
                        .board(board)
                        .replyer("guest" + i)
                        .build();
                replyRepository.save(reply);
            }
        });

        long replyCount = replyRepository.count();
        System.out.println("저장된 총 댓글 수: " + replyCount);
        assert replyCount >= 100 : "댓글이 올바르게 저장되지 않았습니다!";
    }
}
