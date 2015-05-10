package com.tempest.moonlight.server.event;

import com.tempest.moonlight.server.websockets.CustomMessageHeadersAccessor;
import com.tempest.moonlight.server.domain.PresenceMessage;
import com.tempest.moonlight.server.domain.PresenceStatus;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.IdTimestampMessageHeaderInitializer;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * Listener to track user presence. 
 * Sends notifications to the login destination when a connected event is received
 * and notifications to the logout destination when a disconnect event is received
 * 
 * @author Sergi Almar
 */
public class PresenceEventListener implements ApplicationListener<ApplicationEvent> {

	private static final Logger logger = Logger.getLogger(PresenceEventListener.class.getName());

	private final IdTimestampMessageHeaderInitializer headerInitializer;

	private ParticipantRepository participantRepository;
	private SimpMessagingTemplate messagingTemplate;
	private String loginDestination;
	private String logoutDestination;

	private String presenceDestination;
	
	public PresenceEventListener(SimpMessagingTemplate messagingTemplate, ParticipantRepository participantRepository) {
		this.messagingTemplate = messagingTemplate;
		this.participantRepository = participantRepository;

		headerInitializer = new IdTimestampMessageHeaderInitializer();
		headerInitializer.setIdGenerator(new AlternativeJdkIdGenerator());
		headerInitializer.setEnableTimestamp(true);

		messagingTemplate.setHeaderInitializer(headerInitializer);
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		
		if(event instanceof SessionConnectEvent) {
			handleSessionConnected((SessionConnectEvent) event);
		} else if(event instanceof SessionDisconnectEvent) {
			handleSessionDisconnect((SessionDisconnectEvent) event);
		}
	}

	private void handleSessionConnect(SessionConnectEvent event) {

		event.getMessage();
	}

	private void handleSessionConnected(SessionConnectEvent event) {
		SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
		String username = headers.getUser().getName();

		logger.error("1: timestamp of message = " + headers.getTimestamp());
		logger.error("1: id of message = " + headers.getId());

		CustomMessageHeadersAccessor headersAccessor = CustomMessageHeadersAccessor.wrap(event.getMessage());

		logger.error("2: timestamp of message = " + headersAccessor.getTimestamp());
		logger.error("2: id of message = " + headersAccessor.getId());


		LoginEvent loginEvent = new LoginEvent(username);
		messagingTemplate.convertAndSend(loginDestination, loginEvent);

		messagingTemplate.convertAndSend(presenceDestination, new PresenceMessage(username, PresenceStatus.ONLINE));
		
		// We store the session as we need to be idempotent in the disconnect event processing
		participantRepository.add(headers.getSessionId(), loginEvent);
	}
	
	private void handleSessionDisconnect(SessionDisconnectEvent event) {

		LoginEvent loginEvent = participantRepository.getParticipant(event.getSessionId());
		if(loginEvent != null) {
			String username = loginEvent.getUsername();
			messagingTemplate.convertAndSend(logoutDestination, new LogoutEvent(username));
			messagingTemplate.convertAndSend(presenceDestination, new PresenceMessage(username, PresenceStatus.OFFLINE));
			participantRepository.removeParticipant(event.getSessionId());
		}

//		Optional.ofNullable(participantRepository.getParticipant(event.getSessionId())).ifPresent(login -> {
//					messagingTemplate.convertAndSend(logoutDestination, new LogoutEvent(login.getUsername()));
//					messagingTemplate.convertAndSend(presenceDestination, new PresenceMessage(username, PresenceStatus.ONLINE));
//					participantRepository.removeParticipant(event.getSessionId());
//				}
//		);
	}


	public ParticipantRepository getParticipantRepository() {
		return participantRepository;
	}

	public void setParticipantRepository(ParticipantRepository participantRepository) {
		this.participantRepository = participantRepository;
	}

	public SimpMessagingTemplate getMessagingTemplate() {
		return messagingTemplate;
	}

	public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	public String getLoginDestination() {
		return loginDestination;
	}

	public void setLoginDestination(String loginDestination) {
		this.loginDestination = loginDestination;
	}

	public String getLogoutDestination() {
		return logoutDestination;
	}

	public void setLogoutDestination(String logoutDestination) {
		this.logoutDestination = logoutDestination;
	}

	public String getPresenceDestination() {
		return presenceDestination;
	}

	public PresenceEventListener setPresenceDestination(String presenceDestination) {
		this.presenceDestination = presenceDestination;
		return this;
	}
}
