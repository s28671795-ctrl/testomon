/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.cxf.ws.security.sts.provider.model.ParticipantType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ParticipantsType", propOrder={"primary", "participant", "any"})
public class ParticipantsType {
    @XmlElement(name="Primary")
    protected ParticipantType primary;
    @XmlElement(name="Participant")
    protected List<ParticipantType> participant;
    @XmlAnyElement(lax=true)
    protected List<Object> any;

    public ParticipantType getPrimary() {
        return this.primary;
    }

    public void setPrimary(ParticipantType value) {
        this.primary = value;
    }

    public List<ParticipantType> getParticipant() {
        if (this.participant == null) {
            this.participant = new ArrayList<ParticipantType>();
        }
        return this.participant;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }
}

