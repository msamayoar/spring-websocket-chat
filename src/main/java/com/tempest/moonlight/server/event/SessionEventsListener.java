package com.tempest.moonlight.server.event;

import com.tempest.moonlight.server.domain.contacts.GenericContact;
import com.tempest.moonlight.server.domain.presence.PresenceMessage;
import com.tempest.moonlight.server.domain.presence.PresenceStatus;
import com.tempest.moonlight.server.services.contacts.ContactsService;
import com.tempest.moonlight.server.services.users.ActiveUsersService;
import com.tempest.moonlight.server.util.CollectionsUtils;
import com.tempest.moonlight.server.websockets.ToParticipantSender;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * Listener to track user presence. 
 * Sends notifications to the login destination when a connected event is received
 * and notifications to the logout destination when a disconnect event is received
 */
public class SessionEventsListener implements ApplicationListener<ApplicationEvent> {

	private static final Logger logger = Logger.getLogger(SessionEventsListener.class.getName());

//	private final IdTimestampMessageHeaderInitializer headerInitializer;

    @Autowired
    private ActiveUsersService activeUsersService;

    @Autowired
    private ContactsService contactsService;

//	private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ToParticipantSender toParticipantSender;

//    private String loginDestination;
//	private String logoutDestination;

	private String presenceDestination;
	
//	public SessionEventsListener(SimpMessagingTemplate messagingTemplate) {
//		this.messagingTemplate = messagingTemplate;
//
//		headerInitializer = new IdTimestampMessageHeaderInitializer();
//		headerInitializer.setIdGenerator(new AlternativeJdkIdGenerator());
//		headerInitializer.setEnableTimestamp(true);
//
//		messagingTemplate.setHeaderInitializer(headerInitializer);
//	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if(event instanceof SessionConnectEvent) {
			handleSessionConnected((SessionConnectEvent) event);
		} else if(event instanceof SessionDisconnectEvent) {
			handleSessionDisconnect((SessionDisconnectEvent) event);
		}
	}

	private void handleSessionConnected(SessionConnectEvent event) {
        logger.error("handleSessionConnected()");
		SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
		String username = headers.getUser().getName();

		UserSession userSession = new UserSession(headers.getSessionId(), username);
		if(activeUsersService.addUserSession(userSession)) {
//            PresenceMessage presence = new PresenceMessage(username, PresenceStatus.online);
            broadcastUserPresence(username, PresenceStatus.online);
//            messagingTemplate.convertAndSend(presenceDestination, presence);
        }
	}
	
	private void handleSessionDisconnect(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
//        logger.error("sessionId from SessionDisconnectEvent = " + sessionId);

        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String username = headers.getUser().getName();
//        logger.error("sessionId from Headers = " + headers.getSessionId());

		if(activeUsersService.sessionExists(sessionId)) {
            if(activeUsersService.deleteUserSession(sessionId, username)) {
                broadcastUserPresence(username, PresenceStatus.offline);
            }
		}

//		Optional.ofNullable(participantRepository.getParticipant(event.getSessionId())).ifPresent(login -> {
//					messagingTemplate.convertAndSend(logoutDestination, new LogoutEvent(login.getUsername()));
//					messagingTemplate.convertAndSend(presenceDestination, new PresenceMessage(username, PresenceStatus.online));
//					participantRepository.removeParticipant(event.getSessionId());
//				}
//		);
	}

    private void broadcastUserPresence(String login, PresenceStatus presenceStatus) {
        logger.error("Sending presence message = [user = " + login + ", status = " + presenceStatus + "]");
        toParticipantSender.sendToUsersQueues(
                CollectionsUtils.convertToList(
                        contactsService.getContactsOfUser(login),
                        GenericContact::getContact
                ),
                "presence",
                new PresenceMessage(login, presenceStatus)
        );
    }


//	public ParticipantRepository getParticipantRepository() {
//		return participantRepository;
//	}
//
//	public void setParticipantRepository(ParticipantRepository participantRepository) {
//		this.participantRepository = participantRepository;
//	}
//
//	public SimpMessagingTemplate getMessagingTemplate() {
//		return messagingTemplate;
//	}
//
//	public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
//		this.messagingTemplate = messagingTemplate;
//	}

//	public String getLoginDestination() {
//		return loginDestination;
//	}
//
//	public void setLoginDestination(String loginDestination) {
//		this.loginDestination = loginDestination;
//	}
//
//	public String getLogoutDestination() {
//		return logoutDestination;
//	}
//
//	public void setLogoutDestination(String logoutDestination) {
//		this.logoutDestination = logoutDestination;
//	}

	public String getPresenceDestination() {
		return presenceDestination;
	}

	public SessionEventsListener setPresenceDestination(String presenceDestination) {
		this.presenceDestination = presenceDestination;
		return this;
	}
}
