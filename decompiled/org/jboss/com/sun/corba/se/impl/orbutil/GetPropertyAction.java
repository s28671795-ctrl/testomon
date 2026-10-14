/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil;

import java.security.PrivilegedAction;

public class GetPropertyAction
implements PrivilegedAction<String> {
    private String theProp;
    private String defaultVal;

    public GetPropertyAction(String theProp) {
        this.theProp = theProp;
    }

    public GetPropertyAction(String theProp, String defaultVal) {
        this.theProp = theProp;
        this.defaultVal = defaultVal;
    }

    @Override
    public String run() {
        String value = System.getProperty(this.theProp);
        return value == null ? this.defaultVal : value;
    }
}

