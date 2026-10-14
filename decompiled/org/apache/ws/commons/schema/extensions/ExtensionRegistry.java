/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema.extensions;

import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.ws.commons.schema.XmlSchemaObject;
import org.apache.ws.commons.schema.extensions.DefaultExtensionDeserializer;
import org.apache.ws.commons.schema.extensions.DefaultExtensionSerializer;
import org.apache.ws.commons.schema.extensions.ExtensionDeserializer;
import org.apache.ws.commons.schema.extensions.ExtensionSerializer;
import org.w3c.dom.Node;

public class ExtensionRegistry {
    private Map<Class<?>, ExtensionSerializer> extensionSerializers = new HashMap();
    private Map<QName, ExtensionDeserializer> extensionDeserializers = new HashMap<QName, ExtensionDeserializer>();
    private ExtensionSerializer defaultExtensionSerializer = new DefaultExtensionSerializer();
    private ExtensionDeserializer defaultExtensionDeserializer = new DefaultExtensionDeserializer();

    public ExtensionSerializer getDefaultExtensionSerializer() {
        return this.defaultExtensionSerializer;
    }

    public void setDefaultExtensionSerializer(ExtensionSerializer defaultExtensionSerializer) {
        this.defaultExtensionSerializer = defaultExtensionSerializer;
    }

    public ExtensionDeserializer getDefaultExtensionDeserializer() {
        return this.defaultExtensionDeserializer;
    }

    public void setDefaultExtensionDeserializer(ExtensionDeserializer defaultExtensionDeserializer) {
        this.defaultExtensionDeserializer = defaultExtensionDeserializer;
    }

    public void registerDeserializer(QName name, ExtensionDeserializer deserializer) {
        this.extensionDeserializers.put(name, deserializer);
    }

    public void registerSerializer(Class<?> classOfType, ExtensionSerializer serializer) {
        this.extensionSerializers.put(classOfType, serializer);
    }

    public void unregisterSerializer(Class<?> classOfType) {
        this.extensionSerializers.remove(classOfType);
    }

    public void unregisterDeserializer(QName name) {
        this.extensionDeserializers.remove(name);
    }

    public void serializeExtension(XmlSchemaObject parentSchemaObject, Class<?> classOfType, Node node) {
        ExtensionSerializer serializerObject = this.extensionSerializers.get(classOfType);
        if (serializerObject != null) {
            ExtensionSerializer ser = serializerObject;
            ser.serialize(parentSchemaObject, classOfType, node);
        } else if (this.defaultExtensionSerializer != null) {
            this.defaultExtensionSerializer.serialize(parentSchemaObject, classOfType, node);
        }
    }

    public void deserializeExtension(XmlSchemaObject parentSchemaObject, QName name, Node rawNode) {
        ExtensionDeserializer deserializerObject = this.extensionDeserializers.get(name);
        if (deserializerObject != null) {
            ExtensionDeserializer deser = deserializerObject;
            deser.deserialize(parentSchemaObject, name, rawNode);
        } else if (this.defaultExtensionDeserializer != null) {
            this.defaultExtensionDeserializer.deserialize(parentSchemaObject, name, rawNode);
        }
    }
}

