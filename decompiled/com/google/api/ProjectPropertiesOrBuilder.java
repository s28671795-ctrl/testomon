/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.Property;
import com.google.api.PropertyOrBuilder;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface ProjectPropertiesOrBuilder
extends MessageOrBuilder {
    public List<Property> getPropertiesList();

    public Property getProperties(int var1);

    public int getPropertiesCount();

    public List<? extends PropertyOrBuilder> getPropertiesOrBuilderList();

    public PropertyOrBuilder getPropertiesOrBuilder(int var1);
}

