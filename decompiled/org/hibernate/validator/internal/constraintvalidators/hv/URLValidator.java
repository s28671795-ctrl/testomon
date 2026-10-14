/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.hv;

import java.net.MalformedURLException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraints.URL;

public class URLValidator
implements ConstraintValidator<URL, CharSequence> {
    private String protocol;
    private String host;
    private int port;

    @Override
    public void initialize(URL url2) {
        this.protocol = url2.protocol();
        this.host = url2.host();
        this.port = url2.port();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
        java.net.URL url2;
        if (value == null || value.length() == 0) {
            return true;
        }
        try {
            url2 = new java.net.URL(value.toString());
        }
        catch (MalformedURLException e) {
            return false;
        }
        if (this.protocol != null && this.protocol.length() > 0 && !url2.getProtocol().equals(this.protocol)) {
            return false;
        }
        if (this.host != null && this.host.length() > 0 && !url2.getHost().equals(this.host)) {
            return false;
        }
        return this.port == -1 || url2.getPort() == this.port;
    }
}

