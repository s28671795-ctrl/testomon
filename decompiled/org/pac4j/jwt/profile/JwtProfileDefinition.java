/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.jwt.profile;

import java.util.Map;
import org.pac4j.core.profile.AttributeLocation;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.profile.definition.CommonProfileDefinition;
import org.pac4j.jwt.profile.JwtProfile;

public class JwtProfileDefinition
extends CommonProfileDefinition {
    private boolean keepNestedAttributes = true;

    public JwtProfileDefinition() {
        super(x -> new JwtProfile());
        this.setRestoreProfileFromTypedId(true);
    }

    @Override
    public void convertAndAdd(UserProfile profile, AttributeLocation attributeLocation, String name, Object value) {
        if (value instanceof Map) {
            Map jsonObject = (Map)value;
            jsonObject.forEach((key, objectValue) -> super.convertAndAdd(profile, attributeLocation, (String)key, objectValue));
            if (this.keepNestedAttributes) {
                super.convertAndAdd(profile, attributeLocation, name, value);
            }
        } else {
            super.convertAndAdd(profile, attributeLocation, name, value);
        }
    }

    public void setKeepNestedAttributes(boolean keepNestedAttributes) {
        this.keepNestedAttributes = keepNestedAttributes;
    }
}

