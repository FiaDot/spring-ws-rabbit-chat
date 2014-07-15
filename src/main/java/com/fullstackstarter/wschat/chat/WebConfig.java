package com.fullstackstarter.wschat.chat;




import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;



@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // WebSocket을 /echo 에 연결합니다.
        registry.addHandler(chatHandler(), "/echo");

        // SocketJS 지원 url을 /socketjs/echo에 연결합니다.
       //  registry.addHandler(chatHandler(), "/socketjs/echo").withSockJS();
    }

    
    @Bean
    public WebSocketHandler chatHandler() {
        // return new EchoWebSocketHandler();
    	return new PerConnectionWebSocketHandler(EchoWebSocketHandler.class);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
