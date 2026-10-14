/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.saml.bean;

import java.util.ArrayList;
import java.util.List;

public class AudienceRestrictionBean {
    private final List<String> audienceURIs = new ArrayList<String>();

    public AudienceRestrictionBean() {
    }

    public AudienceRestrictionBean(List<String> audienceURIs) {
        if (audienceURIs != null) {
            this.audienceURIs.addAll(audienceURIs);
        }
    }

    public List<String> getAudienceURIs() {
        return this.audienceURIs;
    }

    public void setAudienceURIs(List<String> audienceURIs) {
        this.audienceURIs.clear();
        this.audienceURIs.addAll(audienceURIs);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AudienceRestrictionBean)) {
            return false;
        }
        AudienceRestrictionBean that = (AudienceRestrictionBean)o;
        return this.audienceURIs.equals(that.audienceURIs);
    }

    public int hashCode() {
        int result = 17;
        result = 31 * result + this.audienceURIs.hashCode();
        return result;
    }
}

