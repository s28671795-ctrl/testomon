/*
 * Decompiled with CFR 0.152.
 */
package javax.rmi.CORBA;

import java.security.PrivilegedAction;

public class GetPropertyAction
implements PrivilegedAction<Object> {
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
    public Object run() {
        String value = System.getProperty(this.theProp);
        return value == null ? this.defaultVal : value;
    }
}

