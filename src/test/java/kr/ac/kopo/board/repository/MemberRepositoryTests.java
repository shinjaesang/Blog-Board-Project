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

    @Test //Member 객체를 100 추가하는 테스트 코드
    public void insertMembers(){
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@gmail.com")
                    .password("0000")
                    .name("user" + i)
                    .build();

            memberRepository.save(member);
        });
    }
}
