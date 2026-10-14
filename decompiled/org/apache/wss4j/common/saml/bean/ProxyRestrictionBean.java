/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.saml.bean;

import java.util.ArrayList;
import java.util.List;

public class ProxyRestrictionBean {
    private int count;
    private final List<String> audienceURIs = new ArrayList<String>();

    public ProxyRestrictionBean() {
    }

    public ProxyRestrictionBean(int count, List<String> audienceURIs) {
        this.setCount(count);
        if (audienceURIs != null) {
            this.audienceURIs.addAll(audienceURIs);
        }
    }

    public List<String> getAudienceURIs() {
        return this.audienceURIs;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProxyRestrictionBean)) {
            return false;
        }
        ProxyRestrictionBean that = (ProxyRestrictionBean)o;
        if (this.count != that.count) {
            return false;
        }
        return this.audienceURIs.equals(that.audienceURIs);
    }

    public int hashCode() {
        int result = this.count;
        result = 31 * result + this.audienceURIs.hashCode();
        return result;
    }
}

