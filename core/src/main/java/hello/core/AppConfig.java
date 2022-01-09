package hello.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

@Configuration
public class AppConfig { //공연 기획자(책임 명확) -> AppConfig만 바꾸면됨.
	//리펙토링(중복제거해서 명확하게 분리)
	
	//@Bean orderService -> new MemoryMemberRepository()
	
	//call AppConfig.memberService
	//call AppConfig.memberRepository
	//call AppConfig.memberRepository
	//call AppConfig.orderService
	//call Appconfig.memberRepository
	// => memberRepository가 3번 호출되었어하는데..
	
	//call AppConfig.memberService
	//call AppConfig.memberRepository
	//call AppConfig.orderService
	// => 실제는 한번만 호출됨.
	
	@Bean
	public MemberService memberService() {
		//1번
		System.out.println("call AppConfig.memberService");
		return new MemberServiceImpl(memberRepository());
	}
	
	@Bean
	public MemberRepository memberRepository() {
		//2번? 3번?
		System.out.println("call AppConfig.memberRepository");
		return new MemoryMemberRepository();
	}
	
	@Bean
	public OrderService orderService() {
		//1번
		System.out.println("call AppConfig.orderService");
		return new OrderServiceImpl(memberRepository(), discountPolicy());
	}
	
	@Bean
	public DiscountPolicy discountPolicy() {
		//return new FixDiscountPolicy();
		return new RateDiscountPolicy();
	}
	
}
