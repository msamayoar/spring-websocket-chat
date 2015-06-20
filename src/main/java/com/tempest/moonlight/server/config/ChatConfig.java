package com.tempest.moonlight.server.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//import com.tempest.moonlight.server.domain.SessionProfanity;
import com.tempest.moonlight.server.event.SessionEventsListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.messaging.simp.SimpMessagingTemplate;

//import com.tempest.moonlight.server.util.ProfanityChecker;

@Configuration
public class ChatConfig {
	
	public static class Destinations {
		private Destinations() {}
		
//		private static final String LOGIN  = "/topic/chat.login";
//		private static final String LOGOUT = "/topic/chat.logout";
		private static final String PRESENCE = "/topic/presence";
	}
	

	/*
	@Bean
	@Description("Application event multicaster to process events asynchronously")
	public ApplicationEventMulticaster applicationEventMulticaster() {
		SimpleApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster();
		multicaster.setTaskExecutor(Executors.newFixedThreadPool(10));
		return multicaster;
	}
	*/

	@Bean
	@Description("Tracks user presence (join / leave) and broadcasts it to all connected users")
	public SessionEventsListener presenceEventListener(SimpMessagingTemplate messagingTemplate) {
		SessionEventsListener presence = new SessionEventsListener(messagingTemplate/*, participantRepository()*/);
//		presence.setLoginDestination(Destinations.LOGIN);
//		presence.setLogoutDestination(Destinations.LOGOUT);
		presence.setPresenceDestination(Destinations.PRESENCE);
		return presence;
	}

//	private static final int MAX_PROFANITY_LEVEL = 5;
//
//	@Bean
//	@Scope(value = "websocket", proxyMode = ScopedProxyMode.TARGET_CLASS)
//	@Description("Keeps track of the level of profanity of a websocket session")
//	public SessionProfanity sessionProfanity() {
//		return new SessionProfanity(MAX_PROFANITY_LEVEL);
//	}
//
//	@Bean
//	@Description("Utility class to check the number of profanities and filter them")
//	public ProfanityChecker profanityFilter() {
//		Set<String> profanities = new HashSet<>(Arrays.asList("damn", "crap", "ass"));
//		ProfanityChecker checker = new ProfanityChecker();
//		checker.setProfanities(profanities);
//		return checker;
//	}
}
