package com.project.andosyndicate;

import com.facebook.Session;
import com.facebook.SessionState;

/**
 * Callback interface to handle Facebook session state changes.
 */
public interface SessionStateChangeListener {

    void onSessionStateChange(Session session, SessionState state, Exception exception);

}
