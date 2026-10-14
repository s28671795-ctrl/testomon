/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.accesscontrol.wsdl;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="DenyReason")
@XmlEnum
public enum DenyReason {
    CREDENTIAL_NOT_ENABLED("CredentialNotEnabled"),
    CREDENTIAL_NOT_ACTIVE("CredentialNotActive"),
    CREDENTIAL_EXPIRED("CredentialExpired"),
    INVALID_PIN("InvalidPIN"),
    NOT_PERMITTED_AT_THIS_TIME("NotPermittedAtThisTime"),
    UNAUTHORIZED("Unauthorized"),
    OTHER("Other");

    private final String value;

    private DenyReason(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static DenyReason fromValue(String v) {
        for (DenyReason c : DenyReason.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

