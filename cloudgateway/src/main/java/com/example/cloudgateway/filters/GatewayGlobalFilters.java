package com.example.cloudgateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class GatewayGlobalFilters implements GlobalFilter, Ordered {

    private final Logger logger = LoggerFactory.getLogger(GatewayGlobalFilters.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("Pre Filter");
        if(!(exchange.getRequest().getHeaders().containsKey("AccessToken")
                && exchange.getRequest().getHeaders().getFirst("AccessToken").equals("Pass"))){
            logger.error("You dont Have the Access");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return Mono.error(() -> new IllegalStateException("UNAUTHORIZED"));
        }
        exchange.getRequest().mutate().headers(header -> header.add("Gateway", "Benvenuto"));
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            logger.info("Post Filter");
            Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("Gateway"))
                    .ifPresent(value -> exchange.getResponse().getHeaders().add("Gateway", value));
            exchange.getResponse().addCookie(ResponseCookie.from("Cookie", "Oreo").build());
            //exchange.getResponse().setStatusCode(HttpStatus.OK);
        }));
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
