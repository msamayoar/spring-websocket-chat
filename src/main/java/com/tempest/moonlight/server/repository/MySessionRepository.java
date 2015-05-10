package com.tempest.moonlight.server.repository;

import org.apache.log4j.Logger;
import org.springframework.session.ExpiringSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;

/**
 * Created by Yurii on 2015-05-07.
 */
@Component
public class MySessionRepository implements SessionRepository<ExpiringSession> {

    private static final Logger logger = Logger.getLogger(MySessionRepository.class.getName());

    private MapSessionRepository mapSessionRepository = new MapSessionRepository();

    @Override
    public ExpiringSession createSession() {
        logger.error("createSession()");
        return mapSessionRepository.createSession();
    }

    @Override
    public void save(ExpiringSession session) {
        logger.error("save(" + "session = [" + session + "]" + ")");
        mapSessionRepository.save(session);
    }

    @Override
    public ExpiringSession getSession(String id) {
        logger.error("getSession(" + "id = [" + id + "]" + ")");
        return mapSessionRepository.getSession(id);
    }

    @Override
    public void delete(String id) {
        logger.error("delete(" + "id = [" + id + "]" + ")");
        mapSessionRepository.delete(id);
    }
}
