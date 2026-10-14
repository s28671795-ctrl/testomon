/*
 * Decompiled with CFR 0.152.
 */
package org.h2.security.auth;

import java.util.ArrayList;
import java.util.List;
import org.h2.security.auth.HasConfigProperties;
import org.h2.security.auth.PropertyConfig;

public class UserToRolesMapperConfig
implements HasConfigProperties {
    private String className;
    private List<PropertyConfig> properties;

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String string) {
        this.className = string;
    }

    @Override
    public List<PropertyConfig> getProperties() {
        if (this.properties == null) {
            this.properties = new ArrayList<PropertyConfig>();
        }
        return this.properties;
    }
}

