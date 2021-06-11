package com.yusuf.gatewayservice;

import com.yusuf.gatewayservice.config.AuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@RequiredArgsConstructor
public class GatewayServiceApplication {

    private final AuthFilter authFilter;

    @Value("${gs.book-service.host}")
    private String bookServiceHost;

    @Value("${gs.order-service.host}")
    private String orderServiceHost;

    @Value("${gs.customer-service.host}")
    private String customerServiceHost;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("customer", r -> r.path("/customer/**")
                    .uri(MessageFormat.format("http://{0}:4243", customerServiceHost)))
            .route("order", r -> r.path("/order/**")
                    .filters(f -> f.filter(authFilter.apply(new AuthFilter.Config())))
                    .uri(MessageFormat.format("http://{0}:4242", orderServiceHost)))
            .route("statistics", r -> r.path("/statistics/**")
                    .filters(f -> f.filter(authFilter.apply(new AuthFilter.Config())))
                    .uri(MessageFormat.format("http://{0}:4242", orderServiceHost)))
            .route("statistics", r -> r.path("/book/**")
                    .filters(f -> f.filter(authFilter.apply(new AuthFilter.Config())))
                    .uri(MessageFormat.format("http://{0}:4244", bookServiceHost)))
            .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
}
