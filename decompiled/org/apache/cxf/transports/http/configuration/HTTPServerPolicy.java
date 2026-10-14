/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transports.http.configuration;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="HTTPServerPolicy")
public class HTTPServerPolicy {
    @XmlAttribute(name="ReceiveTimeout")
    @XmlSchemaType(name="unsignedInt")
    protected Long receiveTimeout;
    @XmlAttribute(name="SuppressClientSendErrors")
    protected Boolean suppressClientSendErrors;
    @XmlAttribute(name="SuppressClientReceiveErrors")
    protected Boolean suppressClientReceiveErrors;
    @XmlAttribute(name="HonorKeepAlive")
    protected Boolean honorKeepAlive;
    @XmlAttribute(name="KeepAliveParameters")
    protected String keepAliveParameters;
    @XmlAttribute(name="RedirectURL")
    protected String redirectURL;
    @XmlAttribute(name="CacheControl")
    protected String cacheControl;
    @XmlAttribute(name="ContentLocation")
    protected String contentLocation;
    @XmlAttribute(name="ContentType")
    protected String contentType;
    @XmlAttribute(name="ContentEncoding")
    protected String contentEncoding;
    @XmlAttribute(name="ServerType")
    protected String serverType;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();
    @XmlTransient
    protected PropertyChangeSupport propertyListener = new PropertyChangeSupport(this);

    public String getKeepAliveParameters() {
        return this.keepAliveParameters;
    }

    public void setKeepAliveParameters(String value) {
        this.propertyListener.firePropertyChange("keepAliveParameters", this.keepAliveParameters, value);
        this.keepAliveParameters = value;
    }

    public boolean isSetKeepAliveParameters() {
        return this.keepAliveParameters != null;
    }

    public String getRedirectURL() {
        return this.redirectURL;
    }

    public void setRedirectURL(String value) {
        this.propertyListener.firePropertyChange("redirectURL", this.redirectURL, value);
        this.redirectURL = value;
    }

    public boolean isSetRedirectURL() {
        return this.redirectURL != null;
    }

    public String getCacheControl() {
        return this.cacheControl;
    }

    public void setCacheControl(String value) {
        this.propertyListener.firePropertyChange("cacheControl", this.cacheControl, value);
        this.cacheControl = value;
    }

    public boolean isSetCacheControl() {
        return this.cacheControl != null;
    }

    public String getContentLocation() {
        return this.contentLocation;
    }

    public void setContentLocation(String value) {
        this.propertyListener.firePropertyChange("contentLocation", this.contentLocation, value);
        this.contentLocation = value;
    }

    public boolean isSetContentLocation() {
        return this.contentLocation != null;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String value) {
        this.propertyListener.firePropertyChange("contentType", this.contentType, value);
        this.contentType = value;
    }

    public boolean isSetContentType() {
        return this.contentType != null;
    }

    public String getContentEncoding() {
        return this.contentEncoding;
    }

    public void setContentEncoding(String value) {
        this.propertyListener.firePropertyChange("contentEncoding", this.contentEncoding, value);
        this.contentEncoding = value;
    }

    public boolean isSetContentEncoding() {
        return this.contentEncoding != null;
    }

    public String getServerType() {
        return this.serverType;
    }

    public void setServerType(String value) {
        this.propertyListener.firePropertyChange("serverType", this.serverType, value);
        this.serverType = value;
    }

    public boolean isSetServerType() {
        return this.serverType != null;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public void setReceiveTimeout(long value) {
        this.propertyListener.firePropertyChange("receiveTimeout", this.receiveTimeout, value);
        this.receiveTimeout = value;
    }

    public void unsetReceiveTimeout() {
        this.receiveTimeout = null;
    }

    public boolean isSetReceiveTimeout() {
        return this.receiveTimeout != null;
    }

    public long getReceiveTimeout() {
        if (null == this.receiveTimeout) {
            return 30000L;
        }
        return this.receiveTimeout;
    }

    public void setSuppressClientSendErrors(boolean value) {
        this.propertyListener.firePropertyChange("suppressClientSendErrors", this.suppressClientSendErrors, (Object)value);
        this.suppressClientSendErrors = value;
    }

    public void unsetSuppressClientSendErrors() {
        this.suppressClientSendErrors = null;
    }

    public boolean isSetSuppressClientSendErrors() {
        return this.suppressClientSendErrors != null;
    }

    public boolean isSuppressClientSendErrors() {
        if (null == this.suppressClientSendErrors) {
            return false;
        }
        return this.suppressClientSendErrors;
    }

    public void setSuppressClientReceiveErrors(boolean value) {
        this.propertyListener.firePropertyChange("suppressClientReceiveErrors", this.suppressClientReceiveErrors, (Object)value);
        this.suppressClientReceiveErrors = value;
    }

    public void unsetSuppressClientReceiveErrors() {
        this.suppressClientReceiveErrors = null;
    }

    public boolean isSetSuppressClientReceiveErrors() {
        return this.suppressClientReceiveErrors != null;
    }

    public boolean isSuppressClientReceiveErrors() {
        if (null == this.suppressClientReceiveErrors) {
            return false;
        }
        return this.suppressClientReceiveErrors;
    }

    public void setHonorKeepAlive(boolean value) {
        this.propertyListener.firePropertyChange("honorKeepAlive", this.honorKeepAlive, (Object)value);
        this.honorKeepAlive = value;
    }

    public void unsetHonorKeepAlive() {
        this.honorKeepAlive = null;
    }

    public boolean isSetHonorKeepAlive() {
        return this.honorKeepAlive != null;
    }

    public boolean isHonorKeepAlive() {
        if (null == this.honorKeepAlive) {
            return true;
        }
        return this.honorKeepAlive;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.propertyListener.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.propertyListener.removePropertyChangeListener(listener);
    }
}

