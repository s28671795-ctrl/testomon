/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.soap.wstrust.Challenge;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface SignChallengeType
extends AttributeExtensibleXMLObject,
ElementExtensibleXMLObject,
WSTrustObject {
    public static final String TYPE_LOCAL_NAME = "SignChallengeType";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "SignChallengeType", "wst");

    public Challenge getChallenge();

    public void setChallenge(Challenge var1);
}

