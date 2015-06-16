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
public class MySessionRepository extends MapSessionRepository {

    private static final Logger logger = Logger.getLogger(MySessionRepository.class.getName());

    @Override
    public ExpiringSession createSession() {
        logger.error("createSession()");
        return super.createSession();
    }

    @Override
    public void save(ExpiringSession session) {
        logger.error("save(" + "session = [" + session + "]" + ")");
        super.save(session);
    }

    @Override
    public ExpiringSession getSession(String id) {
        logger.error("getSession(" + "id = [" + id + "]" + ")");
        return super.getSession(id);
    }

    @Override
    public void delete(String id) {
        logger.error("delete(" + "id = [" + id + "]" + ")");
        super.delete(id);
    }
}
