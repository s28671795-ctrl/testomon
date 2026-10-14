/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.presentation.rmi;

import java.security.BasicPermission;

public final class DynamicAccessPermission
extends BasicPermission {
    private static final long serialVersionUID = -5483726771964012090L;

    public DynamicAccessPermission(String name) {
        super(name);
    }

    public DynamicAccessPermission(String name, String actions) {
        super(name, actions);
    }
}

