/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.databinding.source.mime;

import org.apache.cxf.databinding.source.mime.MimeAttribute;
import org.apache.cxf.databinding.source.mime.MimeSerializer;
import org.apache.ws.commons.schema.extensions.ExtensionRegistry;

public class CustomExtensionRegistry
extends ExtensionRegistry {
    public CustomExtensionRegistry() {
        this.registerSerializer(MimeAttribute.class, new MimeSerializer());
    }
}

