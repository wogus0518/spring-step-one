package hello.core.discount;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.order.Order;
import hello.core.order.OrderService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class RateDiscountPolicyTest {
    OrderService orderService;
    MemberService memberService;

    @BeforeEach
    void beforeEach() {
        AppConfig appConfig = new AppConfig();
        orderService = appConfig.orderService();
        memberService = appConfig.memberService();
    }
    @Test
    @DisplayName("VIP는 10% 할인이 적용되어야 한다")
    void vip_o() {
        //given
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);
        //when
        Order order = orderService.createOrder(1L, "itemA", 200000);

        //then
        int discountPrice = order.getDiscountPrice();
        Assertions.assertThat(discountPrice).isEqualTo(20000);
    }

    @Test
    @DisplayName("BASIC은 10% 할인이 적용되면 안된다.")
    void basic_x() {
        //given
        Member member = new Member(1L, "memberA", Grade.BASIC);
        memberService.join(member);
        //when
        Order order = orderService.createOrder(1L, "itemA", 200000);

        //then
        int discountPrice = order.getDiscountPrice();
        Assertions.assertThat(discountPrice).isEqualTo(0);
    }
}