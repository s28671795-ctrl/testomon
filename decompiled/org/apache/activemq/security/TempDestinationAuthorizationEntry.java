/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.security;

import org.apache.activemq.security.AuthorizationEntry;

public class TempDestinationAuthorizationEntry
extends AuthorizationEntry {
    public void afterPropertiesSet() throws Exception {
        if (this.adminRoles != null) {
            this.setAdminACLs(this.parseACLs(this.adminRoles));
        }
        if (this.writeRoles != null) {
            this.setWriteACLs(this.parseACLs(this.writeRoles));
        }
        if (this.readRoles != null) {
            this.setReadACLs(this.parseACLs(this.readRoles));
        }
    }
}

