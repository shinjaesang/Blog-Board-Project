package kr.ac.kopo.board.service;

import kr.ac.kopo.board.dto.BoardDTO;
import kr.ac.kopo.board.dto.PageRequestDTO;
import kr.ac.kopo.board.dto.PageResultDTO;
import kr.ac.kopo.board.entity.Board;
import kr.ac.kopo.board.entity.Member;
import kr.ac.kopo.board.repository.BoardRepository;
import kr.ac.kopo.board.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository repository;
    private final ReplyRepository replyRepository;

    @Transactional
    @Override
    public Long register(BoardDTO dto) {
        try {
            Board board = dtoToEntity(dto);
            repository.save(board);
            return board.getBno();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("게시글 등록 중 오류가 발생했습니다.");
        }
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board) en[0], (Member) en[1], (Long) en[2]));
        Page<Object[]> result = repository.getBoardWithReplyCount(pageRequestDTO.getPageable(Sort.by("bno").descending()));
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public BoardDTO get(Long bno) {
        Object result = repository.getBoardByBno(bno);
        if (result == null) {
            throw new RuntimeException("해당 게시글을 찾을 수 없습니다.");
        }
        Object[] arr = (Object[]) result;
        return entityToDTO((Board) arr[0], (Member) arr[1], (Long) arr[2]);
    }

    @Transactional
    @Override
    public void removeWithReplies(Long bno) {
        try {
            // 댓글 삭제
            replyRepository.deleteByBno(bno);
            // 원글 삭제
            repository.deleteById(bno);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("게시글 삭제 중 오류가 발생했습니다.");
        }
    }

    @Transactional
    @Override
    public void modify(BoardDTO boardDTO) {
        try {
            Board board = repository.getReferenceById(boardDTO.getBno());
            if (board == null) {
                throw new RuntimeException("해당 게시글을 찾을 수 없습니다.");
            }
            board.changeTitle(boardDTO.getTitle());
            board.changeContent(boardDTO.getContent());
            repository.save(board);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("게시글 수정 중 오류가 발생했습니다.");
        }
    }
}
