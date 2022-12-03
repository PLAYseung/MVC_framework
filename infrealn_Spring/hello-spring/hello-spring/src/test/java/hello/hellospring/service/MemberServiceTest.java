package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    // 같은 MemoryMemberRepository를 사용하기 위함 (DI)
    @BeforeEach
    public void beforeEach() {
        // MemoryMemberRepository 생성후 memberRepository에 넣기
        memberRepository = new MemoryMemberRepository();
        // memberRepository를 memberService에 넣어줌
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("Spring");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("Spring");

        Member member2 = new Member();
        member2.setName("Spring");

        // when
        memberService.join(member1);
        IllegalAccessError e = assertThrows(IllegalAccessError.class, () -> memberService.join(member2));
        
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");

//        try {
//            memberService.join(member2);
//            fail();
//        } catch (IllegalAccessError e){
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.123123")
//        }
        // then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}