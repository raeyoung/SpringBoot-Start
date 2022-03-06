package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// service 는 비즈니스를 처리하는 role 이므로 네이밍 처리 또한 비즈니스용어로 사용

@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    // 테스트 케이스에서도 동일하게 new 로 불러오니 생성자로 변경
    // Dependency Injection (DI) : memberRepository 를 외부에서 넣어줌

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member) {
        // 아래와 같이 축약하여 사용 가능
        validateDuplicateMember(member); // 중복 회원 검증

        memberRepository.save(member);
        return member.getId();
        // 같은 이름이 있는 중복 회원 X
        /*Optional<Member> result = memberRepository.findByName(member.getName());
        // Null이 아닌 값이 존재한다면 (optional 이 있기 때문에 사용 가능)
        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });*/

    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {

        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
