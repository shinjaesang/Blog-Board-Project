package kr.ac.kopo.board.repository;

import kr.ac.kopo.board.entity.Board;
import kr.ac.kopo.board.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    // 게시글에 속한 댓글 삭제
    @Modifying
    @Transactional
    @Query("delete from Reply r where r.board.bno = :bno")
    void deleteByBno(Long bno);

    // 게시글에 속한 댓글 목록 조회 (삭제 전 확인용)
    @Query("select r from Reply r where r.board.bno = :bno")
    List<Reply> findByBoardBno(Long bno);

    // 게시글에 속한 댓글 수 조회
    @Query("select count(r) from Reply r where r.board.bno = :bno")
    Long countByBoardBno(Long bno);
}