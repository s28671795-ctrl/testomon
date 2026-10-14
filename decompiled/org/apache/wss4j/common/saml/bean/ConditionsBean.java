/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.saml.bean;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import org.apache.wss4j.common.saml.bean.AudienceRestrictionBean;
import org.apache.wss4j.common.saml.bean.DelegateBean;
import org.apache.wss4j.common.saml.bean.ProxyRestrictionBean;
import org.joda.time.DateTime;

public class ConditionsBean {
    private DateTime notBefore;
    private DateTime notAfter;
    private long tokenPeriodSeconds;
    private List<AudienceRestrictionBean> audienceRestrictions;
    private boolean oneTimeUse;
    private ProxyRestrictionBean proxyRestriction;
    private List<DelegateBean> delegates;

    public ConditionsBean() {
    }

    public ConditionsBean(DateTime notBefore, DateTime notAfter) {
        this.notBefore = notBefore;
        this.notAfter = notAfter;
    }

    public ConditionsBean(Instant notBefore, Instant notAfter) {
        if (notBefore != null) {
            this.notBefore = new DateTime(Date.from(notBefore));
        }
        if (notAfter != null) {
            this.notAfter = new DateTime(Date.from(notAfter));
        }
    }

    public ConditionsBean(int tokenPeriodMinutes) {
        this.tokenPeriodSeconds = (long)tokenPeriodMinutes * 60L;
    }

    public DateTime getNotBefore() {
        return this.notBefore;
    }

    public void setNotBefore(DateTime notBefore) {
        this.notBefore = notBefore;
    }

    public void setNotBefore(Instant notBefore) {
        this.notBefore = notBefore != null ? new DateTime(Date.from(notBefore)) : null;
    }

    public DateTime getNotAfter() {
        return this.notAfter;
    }

    public void setNotAfter(DateTime notAfter) {
        this.notAfter = notAfter;
    }

    public void setNotAfter(Instant notAfter) {
        this.notAfter = notAfter != null ? new DateTime(Date.from(notAfter)) : null;
    }

    public int getTokenPeriodMinutes() {
        return (int)(this.tokenPeriodSeconds / 60L);
    }

    public void setTokenPeriodMinutes(int tokenPeriodMinutes) {
        this.tokenPeriodSeconds = (long)tokenPeriodMinutes * 60L;
    }

    public long getTokenPeriodSeconds() {
        return this.tokenPeriodSeconds;
    }

    public void setTokenPeriodSeconds(long tokenPeriodSeconds) {
        this.tokenPeriodSeconds = tokenPeriodSeconds;
    }

    public List<AudienceRestrictionBean> getAudienceRestrictions() {
        return this.audienceRestrictions;
    }

    public void setAudienceRestrictions(List<AudienceRestrictionBean> audienceRestrictions) {
        this.audienceRestrictions = audienceRestrictions;
    }

    public boolean isOneTimeUse() {
        return this.oneTimeUse;
    }

    public void setOneTimeUse(boolean oneTimeUse) {
        this.oneTimeUse = oneTimeUse;
    }

    public ProxyRestrictionBean getProxyRestriction() {
        return this.proxyRestriction;
    }

    public void setProxyRestriction(ProxyRestrictionBean proxyRestriction) {
        this.proxyRestriction = proxyRestriction;
    }

    public List<DelegateBean> getDelegates() {
        return this.delegates;
    }

    public void setDelegates(List<DelegateBean> delegates) {
        this.delegates = delegates;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConditionsBean)) {
            return false;
        }
        ConditionsBean that = (ConditionsBean)o;
        if (this.tokenPeriodSeconds != that.tokenPeriodSeconds) {
            return false;
        }
        if (this.notBefore == null && that.notBefore != null) {
            return false;
        }
        if (this.notBefore != null && !this.notBefore.equals(that.notBefore)) {
            return false;
        }
        if (this.notAfter == null && that.notAfter != null) {
            return false;
        }
        if (this.notAfter != null && !this.notAfter.equals(that.notAfter)) {
            return false;
        }
        if (this.audienceRestrictions == null && that.audienceRestrictions != null) {
            return false;
        }
        if (this.audienceRestrictions != null && !this.audienceRestrictions.equals(that.audienceRestrictions)) {
            return false;
        }
        if (this.oneTimeUse != that.oneTimeUse) {
            return false;
        }
        if (this.proxyRestriction == null && that.proxyRestriction != null) {
            return false;
        }
        if (this.proxyRestriction != null && !this.proxyRestriction.equals(that.proxyRestriction)) {
            return false;
        }
        if (this.delegates == null && that.delegates != null) {
            return false;
        }
        return this.delegates == null || this.delegates.equals(that.delegates);
    }

    public int hashCode() {
        int result = (int)this.tokenPeriodSeconds;
        if (this.notBefore != null) {
            result = 31 * result + this.notBefore.hashCode();
        }
        if (this.notAfter != null) {
            result = 31 * result + this.notAfter.hashCode();
        }
        if (this.audienceRestrictions != null) {
            result = 31 * result + this.audienceRestrictions.hashCode();
        }
        result = 31 * result + (this.oneTimeUse ? 1 : 0);
        if (this.proxyRestriction != null) {
            result = 31 * result + this.proxyRestriction.hashCode();
        }
        if (this.delegates != null) {
            result = 31 * result + this.delegates.hashCode();
        }
        return result;
    }
}

