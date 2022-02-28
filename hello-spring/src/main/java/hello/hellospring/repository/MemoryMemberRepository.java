package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;  // key 값 생성

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // Java8 버전에서 null 처리
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        // 1개 찾는 경우 리턴 / 없는 경우 optional 에 null 포함되어 리턴
        return store.values().stream()
                .filter(member -> member.getName().equals(name))    // 람다 문법
                .findAny(); // 1개라도 찾는 것
    }

    @Override
    public List<Member> findAll() {
        // store.values() -> member
        return new ArrayList<>(store.values());
    }
}
