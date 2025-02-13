//package com.example.project.circuitbreakertest;
//
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.concurrent.TimeUnit;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import io.github.resilience4j.circuitbreaker.CircuitBreaker;
//import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
//import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
//
//@SpringBootTest
//public class CircuitBreakerTests {
//	@Autowired
//    private CircuitBreakerRegistry circuitBreakerRegistry;
//
//    @Test
//    public void testCircuitBreakerState() {
//    	
//    	CircuitBreakerConfig config = CircuitBreakerConfig.custom()
//                .failureRateThreshold(50) // 50% failure rate
//                .minimumNumberOfCalls(5)
//                .slidingWindowSize(5)
//                .build();
//
//        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);
//        CircuitBreaker circuitBreaker = registry.circuitBreaker("CircuitBreakerTests");
//     //   CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("userService");
//
//        // Simulate a failure
//        circuitBreaker.onError(1000, TimeUnit.MILLISECONDS, new RuntimeException("Service failure"));
//
//        // Assert CircuitBreaker state
//        assertThat(circuitBreaker.getState()).isEqualTo(CircuitBreaker.State.CLOSED);
//
//        // Simulate multiple failures to cross the threshold
//        for (int i = 0; i <6; i++) {
//            circuitBreaker.onError(1000, TimeUnit.MILLISECONDS, new RuntimeException("Service failure " + i));
//        }
//
//        // State should transition to OPEN
//        assertThat(circuitBreaker.getState()).isEqualTo(CircuitBreaker.State.OPEN);
//    }
//}
