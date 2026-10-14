/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common.assertion;

import java.util.Collections;
import java.util.Map;
import javax.annotation.concurrent.NotThreadSafe;
import net.shibboleth.utilities.java.support.collection.LazyMap;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

@NotThreadSafe
public class ValidationContext {
    private Map<String, Object> staticParameters;
    private Map<String, Object> dynamicParameters;
    private String validationFailureMessage;

    public ValidationContext() {
        this(null);
    }

    public ValidationContext(Map<String, Object> newStaticParameters) {
        this.staticParameters = newStaticParameters == null ? Collections.unmodifiableMap(Collections.EMPTY_MAP) : Collections.unmodifiableMap(newStaticParameters);
        this.dynamicParameters = new LazyMap<String, Object>();
    }

    public Map<String, Object> getStaticParameters() {
        return this.staticParameters;
    }

    public Map<String, Object> getDynamicParameters() {
        return this.dynamicParameters;
    }

    public String getValidationFailureMessage() {
        return this.validationFailureMessage;
    }

    public void setValidationFailureMessage(String message) {
        this.validationFailureMessage = StringSupport.trimOrNull(message);
    }
}

