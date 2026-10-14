/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.schema;

import org.ldaptive.schema.Extensions;
import org.ldaptive.schema.SchemaElement;

public abstract class AbstractSchemaElement
implements SchemaElement {
    private String description;
    private Extensions extensions;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String s2) {
        this.description = s2;
    }

    public Extensions getExtensions() {
        return this.extensions;
    }

    public void setExtensions(Extensions e) {
        this.extensions = e;
    }

    public static <T extends AbstractSchemaElement> boolean containsBooleanExtension(T schemaElement, String extensionName) {
        if (schemaElement != null) {
            Extensions exts = schemaElement.getExtensions();
            return exts != null && Boolean.parseBoolean(exts.getValue(extensionName));
        }
        return false;
    }

    public boolean equals(Object o) {
        return super.equals(o);
    }

    public abstract int hashCode();
}

