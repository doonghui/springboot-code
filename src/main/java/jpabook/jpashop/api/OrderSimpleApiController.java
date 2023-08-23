package jpabook.jpashop.api;


import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  xToOne
 *  Order
 *  Order -> Member
 *  Order -> Delivery
 * **/


@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;


    @GetMapping("/api/v1/simple-orders")        // 양방향 연관관계가 생긴다. Order <--> Member
    public List<Order> orderV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        return all;
    }

    @GetMapping("/api/v2/simple-orders")        // Lazy 로딩으로 인한 너무 많은 쿼리가 호출된다.
    public List<SimpleOrderDto> orderV2() {
        // order 2개
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());

        return result;
    }


    @GetMapping("/api/v3/simple-orders")        // fetch join 활용
    public List<SimpleOrderDto> orderV3() {
       List<Order> orders =orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());

        return result;
    }


    @GetMapping("/api/v4/simple-orders")        // fetch join 활용
    public List<SimpleOrderDto> orderV4() {
        List<Order> orders =orderRepository.findOrderDtos();
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());

        return result;
    }



    @Data
    static class SimpleOrderDto {
            private Long orderId;
            private String name;
            private LocalDateTime orderDate;
            private OrderStatus orderStatus;
            private Address address;

            public SimpleOrderDto(Order order) {
                orderId= order.getId();
                name = order.getMember().getName();     // Lazy 초기화
                orderStatus = order.getStatus();
                address = order.getDelivery().getAddress();  // Lazy 초기화 :
            }


    }


}


