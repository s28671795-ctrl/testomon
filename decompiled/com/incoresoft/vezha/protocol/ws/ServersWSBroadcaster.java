/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.ws;

import com.google.inject.Inject;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.plugin.commons.http.websocket.WSBroadcaster;

public class ServersWSBroadcaster
extends WSBroadcaster {
    @Inject
    public ServersWSBroadcaster(UsersManager usersManager) {
        super(usersManager, false);
    }
}

