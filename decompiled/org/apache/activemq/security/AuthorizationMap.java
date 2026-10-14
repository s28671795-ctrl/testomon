/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.security;

import java.util.Set;
import org.apache.activemq.command.ActiveMQDestination;

public interface AuthorizationMap {
    public Set<?> getTempDestinationAdminACLs();

    public Set<?> getTempDestinationReadACLs();

    public Set<?> getTempDestinationWriteACLs();

    public Set<?> getAdminACLs(ActiveMQDestination var1);

    public Set<?> getReadACLs(ActiveMQDestination var1);

    public Set<?> getWriteACLs(ActiveMQDestination var1);
}

