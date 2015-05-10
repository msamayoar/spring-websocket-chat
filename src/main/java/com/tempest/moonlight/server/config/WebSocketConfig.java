package com.tempest.moonlight.server.config;

import com.tempest.moonlight.server.websockets.CustomMessageHeadersAccessor;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.session.ExpiringSession;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractSessionWebSocketMessageBrokerConfigurer<ExpiringSession> {

	private static final Logger logger = Logger.getLogger(WebSocketConfig.class.getName());
	
	protected void configureStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws").withSockJS();
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/queue/", "/topic/");
		//registry.enableStompBrokerRelay("/queue/", "/topic/");
		registry.setApplicationDestinationPrefixes("/app");
	}

//	@Override
//	public void configureClientInboundChannel(ChannelRegistration registration) {
//		super.configureClientInboundChannel(registration);
//		registration.setInterceptors(
//				new ChannelInterceptorAdapter() {
//
//					@Override
//					public Message<?> preSend(Message<?> message, MessageChannel channel) {
//						CustomMessageHeadersAccessor accessor = CustomMessageHeadersAccessor.wrap(message);
////						logger.error("\n");
////						logger.error("channel = " + channel);
////						logger.error("payload = " + message.getPayload());
////						logger.error("preSend! Before setImmutable() => id = " + accessor.getId() + ", time = " + accessor.getTimestamp());
//						accessor.setImmutable();
////						logger.error("preSend! After setImmutable() => id = " + accessor.getId() + ", time = " + accessor.getTimestamp());
//						return super.preSend(message, channel);
//					}
//
//					@Override
//					public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
//						CustomMessageHeadersAccessor accessor = CustomMessageHeadersAccessor.wrap(message);
////						logger.error("\n");
////						logger.error("channel = " + channel);
////						logger.error("payload = " + message.getPayload());
////						logger.error("postSend! Before setImmutable() => id = " + accessor.getId() + ", time = " + accessor.getTimestamp());
//						accessor.setImmutable();
//						logger.error("postSend! After setImmutable() => id = " + accessor.getId() + ", time = " + accessor.getTimestamp());
//						super.postSend(message, channel, sent);
//					}
//
//					@Override
//					public Message<?> postReceive(Message<?> message, MessageChannel channel) {
//						CustomMessageHeadersAccessor accessor = CustomMessageHeadersAccessor.wrap(message);
////						logger.error("\n");
////						logger.error("channel = " + channel);
////						logger.error("payload = " + message.getPayload());
////						logger.error("postReceive! Before setImmutable() => id = " + accessor.getId() + ", time = " + accessor.getTimestamp());
//						accessor.setImmutable();
////						logger.error("postReceive! After setImmutable() => id = " + accessor.getId() + ", time = " + accessor.getTimestamp());
//						return super.postReceive(message, channel);
//					}
//				}
//		);
//	}
}