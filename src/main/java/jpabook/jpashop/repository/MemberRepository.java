package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em; //스프링이 이 엔티티매니저를 만들어서 주입을 해준다 ->

    public void save(Member member) {
        em.persist(member);  //회원을 em.persist하면 JPA가 이것을 저장함
        //persist시 Context에 member 엔티티 저장하는데 그 때 영속성 context는 키와 밸류가 있는데
        //id 값이 키가 된다.
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id); //JPA가 제공하는 find메서드를 사용
        // 단건조회 첫 번째는 타입 , 두 번째는 PK입력
        //  위 문장은 Member member = em.find(Member.class, id)와 같음 -> 멤버 반환
    }

    public List<Member> findAll() { //단건 조회말고 여러건 조회하려면 JPQL작성해야 함.
        return em.createQuery("select m from Member m", Member.class) //첫 번째 JPQL , 두 번째 반환 타입
                .getResultList();
        //위 문장과 동일
        //List<Member> result = em.createQuery("select m from Member m", Member.class) //첫 번째 JPQL , 두 번째 반환 타입
        //        .getResultList();
        //  return result;
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }//회원을 조회하는데 이름을 통해서 조회
     //파라미터 바인딩해서 특정 이름에 대한 회원만 찾는다.
}
