package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service //스프링이 제공하는 Serive 어노테이션에 ctrl + b하면 @Componenet가 되어 있어서
         //Component Scan의 대상이 되어서 자동으로 bean에 등록
@Transactional(readOnly = true) // 읽기만 할 곳에 사용 , 성능 최적화
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository; //멤버 리포지토리를 사용할 것이기 때문에 선언


    //회원 가입      //트랜잭셔널 클래스 위에 선언해두면 전체 적용, 메서드 위에 선언하면 해당 메서드만
    @Transactional //쓰기할 곳엔 리드온리 X(말 그대로 쓰기가 안됨)
    public  Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증코드 ->같은 이름 x
        memberRepository.save(member);  //멤버리포지토리에서 세이브메서드를 통해 저장
        return member.getId();          //
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName((member.getName()));
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회웡닙니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll(); //회원 전체 반환
    }

    public Member findOne(Long memberId) {  //단건 조회
        return memberRepository.findOne(memberId);
    }

}
