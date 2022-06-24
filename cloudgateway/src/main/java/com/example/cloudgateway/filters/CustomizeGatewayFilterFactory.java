package com.example.cloudgateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//el nombre de la clase del filtro personalizado o mas especifico debe tener como sufijo el nombre "GatewayFilterFactory"
// para ser reconocido por spring en el application.yaml
@Component
public class CustomizeGatewayFilterFactory
        extends AbstractGatewayFilterFactory<CustomizeGatewayFilterFactory.CustomizeFilterConfig> implements Ordered {

    public CustomizeGatewayFilterFactory(){
        super(CustomizeFilterConfig.class);
    }
    private final Logger logger = LoggerFactory.getLogger(CustomizeGatewayFilterFactory.class);

    @Override
    public GatewayFilter apply(CustomizeFilterConfig config) {
        return new GatewayFilter() {

            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                logger.info("Pre Customize Filter");
                if(!(exchange.getRequest().getHeaders().containsKey(config.headerName)
                        && exchange.getRequest().getHeaders().getFirst(config.headerName).equals(config.headerValue))){
                    return Mono.fromRunnable(() -> {
                        logger.error("You Dont Have The Authorization");
                        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                        exchange.getResponse().getHeaders().add("ErrorMessage", "You Arent Authored");
                    });
                }
                return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                    logger.info("Post Customize Filter");
                    exchange.getResponse().addCookie(ResponseCookie.from(config.cookieName, config.cookieValue).build());
                    exchange.getResponse().setStatusCode(HttpStatus.OK);
                }));
            }

        };
    }

    @Override
    public int getOrder() {
        return 2;
    }

    public static class CustomizeFilterConfig{

        private String headerName;
        private String headerValue;
        private String cookieName;
        private String cookieValue;

        public String getHeaderName() {
            return headerName;
        }

        public String getHeaderValue() {
            return headerValue;
        }

        public String getCookieName() {
            return cookieName;
        }

        public String getCookieValue() {
            return cookieValue;
        }

        public void setHeaderName(String headerName) {
            this.headerName = headerName;
        }

        public void setHeaderValue(String headerValue) {
            this.headerValue = headerValue;
        }

        public void setCookieName(String cookieName) {
            this.cookieName = cookieName;
        }

        public void setCookieValue(String cookieValue) {
            this.cookieValue = cookieValue;
        }
    }
}
