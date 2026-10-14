/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.principal;

import java.io.Serializable;
import java.security.Principal;
import org.w3c.dom.Element;

public class CustomTokenPrincipal
implements Principal,
Serializable {
    private static final long serialVersionUID = -7439821246832337774L;
    private Element tokenElement;
    private String name;
    private Object tokenObject;

    public Object getTokenObject() {
        return this.tokenObject;
    }

    public void setTokenObject(Object tokenObject) {
        this.tokenObject = tokenObject;
    }

    public CustomTokenPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public Element getTokenElement() {
        return this.tokenElement;
    }

    public void setTokenElement(Element tokenElement) {
        this.tokenElement = tokenElement;
    }
}

