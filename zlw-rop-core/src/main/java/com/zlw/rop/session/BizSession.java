package com.zlw.rop.session;

import com.rop.session.AbstractSession;

import java.io.Serializable;
import java.util.UUID;


public class BizSession extends AbstractSession implements Serializable {
    private String sessionId;

    public BizSession() {
        sessionId = UUID.randomUUID().toString();
    }

    public BizSession(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
