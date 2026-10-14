/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.soap.wstrust.Participant;
import org.opensaml.soap.wstrust.Primary;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface Participants
extends ElementExtensibleXMLObject,
WSTrustObject {
    public static final String ELEMENT_LOCAL_NAME = "Participants";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Participants", "wst");
    public static final String TYPE_LOCAL_NAME = "ParticipantsType";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ParticipantsType", "wst");

    public Primary getPrimary();

    public void setPrimary(Primary var1);

    public List<Participant> getParticipants();
}

