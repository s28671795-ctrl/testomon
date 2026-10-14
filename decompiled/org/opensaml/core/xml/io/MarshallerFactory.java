/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.io;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.Marshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MarshallerFactory {
    private final Logger log = LoggerFactory.getLogger(MarshallerFactory.class);
    private final Map<QName, Marshaller> marshallers = new ConcurrentHashMap<QName, Marshaller>();

    @Nullable
    public Marshaller getMarshaller(@Nullable QName key) {
        if (key == null) {
            return null;
        }
        return this.marshallers.get(key);
    }

    @Nullable
    public Marshaller getMarshaller(@Nonnull XMLObject xmlObject) {
        Marshaller marshaller = this.getMarshaller(xmlObject.getSchemaType());
        if (marshaller == null) {
            marshaller = this.getMarshaller(xmlObject.getElementQName());
        }
        return marshaller;
    }

    @Nonnull
    public Map<QName, Marshaller> getMarshallers() {
        return Collections.unmodifiableMap(this.marshallers);
    }

    public void registerMarshaller(@Nonnull QName key, @Nonnull Marshaller marshaller) {
        Constraint.isNotNull(key, "Marshaller key cannot be null");
        Constraint.isNotNull(marshaller, "Marshaller cannot be null");
        this.log.debug("Registering marshaller, {}, for object type {}", (Object)marshaller.getClass().getName(), (Object)key);
        this.marshallers.put(key, marshaller);
    }

    @Nullable
    public Marshaller deregisterMarshaller(@Nonnull QName key) {
        this.log.debug("Deregistering marshaller for object type {}", (Object)key);
        if (key != null) {
            return this.marshallers.remove(key);
        }
        return null;
    }
}

