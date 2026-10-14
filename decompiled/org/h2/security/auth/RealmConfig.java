/*
 * Decompiled with CFR 0.152.
 */
package org.h2.security.auth;

import java.util.ArrayList;
import java.util.List;
import org.h2.security.auth.HasConfigProperties;
import org.h2.security.auth.PropertyConfig;

public class RealmConfig
implements HasConfigProperties {
    private String name;
    private String validatorClass;
    private List<PropertyConfig> properties;

    public String getName() {
        return this.name;
    }

    public void setName(String string) {
        this.name = string;
    }

    public String getValidatorClass() {
        return this.validatorClass;
    }

    public void setValidatorClass(String string) {
        this.validatorClass = string;
    }

    @Override
    public List<PropertyConfig> getProperties() {
        if (this.properties == null) {
            this.properties = new ArrayList<PropertyConfig>();
        }
        return this.properties;
    }
}

