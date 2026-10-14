/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.vms.vezhaintegration;

import com.incoresoft.middleware.model.user.User;
import java.net.URI;

public interface VezhaIntegrationManager {
    public URI getVezhaApiUri();

    public String getAdminToken();

    public String getToken(User var1);
}

