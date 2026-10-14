/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.soap.wstrust.ParticipantType;

public interface Primary
extends ParticipantType {
    public static final String ELEMENT_LOCAL_NAME = "Primary";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Primary", "wst");
}

