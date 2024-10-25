package kr.ac.kopo.board.repository;

import kr.ac.kopo.board.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertMembers() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@gmail.com")
                    .password("0000")
                    .name("user" + i)
                    .build();

            memberRepository.save(member);
        });
        long memberCount = memberRepository.count();
        System.out.println("저장된 총 회원 수: " + memberCount);
        assert memberCount == 100 : "회원이 올바르게 저장되지 않았습니다!";
    }
}
