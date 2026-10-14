/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.saml1.core.AuthenticationQuery;
import org.opensaml.saml.saml1.core.impl.SubjectQueryImpl;

public class AuthenticationQueryImpl
extends SubjectQueryImpl
implements AuthenticationQuery {
    private String authenticationMethod;

    protected AuthenticationQueryImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getAuthenticationMethod() {
        return this.authenticationMethod;
    }

    @Override
    public void setAuthenticationMethod(String method2) {
        this.authenticationMethod = this.prepareForAssignment(this.authenticationMethod, method2);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> list = new ArrayList<XMLObject>();
        if (super.getOrderedChildren() != null) {
            list.addAll(super.getOrderedChildren());
        }
        if (list.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(list);
    }
}

