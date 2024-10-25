package kr.ac.kopo.board.repository;

import kr.ac.kopo.board.entity.Board;
import kr.ac.kopo.board.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository; // MemberRepository 추가


    @Test
    public void insertBoards() {
        // 미리 생성된 회원을 무작위로 할당하여 게시글 작성자로 설정
        memberRepository.findAll().forEach(member -> {
            IntStream.rangeClosed(1, 5).forEach(i -> {
                Board board = Board.builder()
                        .title("Title " + i + " by " + member.getName())
                        .content("Content " + i)
                        .writer(member)
                        .build();
                boardRepository.save(board);
            });
        });

        long boardCount = boardRepository.count();
        System.out.println("저장된 총 게시글 수: " + boardCount);
        assert boardCount >= 100 : "게시글이 올바르게 저장되지 않았습니다!";
    }

    @Test
    public void insertBoard(){
        IntStream.rangeClosed(1, 100).forEach(i -> {
            // Member를 먼저 저장
            Member member = Member.builder()
                    .email("user" + i + "@gmail.com")
                    .name("user")
                    .build();

            member = memberRepository.save(member); // Member 저장

            // Board 객체 생성
            Board board = Board.builder()
                    .title("Title " + i)
                    .content("Content " + i)
                    .writer(member) // 저장된 Member 사용
                    .build();

            boardRepository.save(board); // Board 저장
        });
    }

    @Transactional
    @Test
    public void testRead(){
        Optional<Board> result = boardRepository.findById(5L);
        Board board = result.get();

        System.out.println(board);
        System.out.println(board.getWriter());
    }

    @Test
    public void testReadWithWriter(){
        Object result = boardRepository.getBoardWithWriter(10L);
        Object[] arr = (Object[]) result;
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testReadWithReply() {
        List<Object[]> result = boardRepository.getBoardWithReply(77L);
        for (Object[] arr: result){
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void testWithReplyCount(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        result.get().forEach(row ->{
            Object[] arr = (Object[]) row;
            System.out.println(Arrays.toString(arr));
        });
    }

    @Test
    public void testRead3(){
        Object result = boardRepository.getBoardByBno(99L);
        Object[] arr = (Object[]) result;
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testSearch1(){
        boardRepository.search1();
    }

    @Test
    public void testSearchPage(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        boardRepository.searchPage("t", "5", pageable);
    }
}
