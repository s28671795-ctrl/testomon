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
import net.shibboleth.utilities.java.support.xml.DOMTypeSupport;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.io.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class UnmarshallerFactory {
    private final Logger log = LoggerFactory.getLogger(UnmarshallerFactory.class);
    private final Map<QName, Unmarshaller> unmarshallers = new ConcurrentHashMap<QName, Unmarshaller>();

    @Nullable
    public Unmarshaller getUnmarshaller(@Nullable QName key) {
        if (key == null) {
            return null;
        }
        return this.unmarshallers.get(key);
    }

    @Nullable
    public Unmarshaller getUnmarshaller(@Nullable Element domElement) {
        Unmarshaller unmarshaller = this.getUnmarshaller(DOMTypeSupport.getXSIType(domElement));
        if (unmarshaller == null) {
            unmarshaller = this.getUnmarshaller(QNameSupport.getNodeQName(domElement));
        }
        return unmarshaller;
    }

    @Nonnull
    public Map<QName, Unmarshaller> getUnmarshallers() {
        return Collections.unmodifiableMap(this.unmarshallers);
    }

    public void registerUnmarshaller(@Nonnull QName key, @Nonnull Unmarshaller unmarshaller) {
        Constraint.isNotNull(key, "Unmarshaller key cannot be null");
        Constraint.isNotNull(unmarshaller, "Unmarshaller cannot be null");
        this.log.debug("Registering unmarshaller, {}, for object type, {}", (Object)unmarshaller.getClass().getName(), (Object)key);
        this.unmarshallers.put(key, unmarshaller);
    }

    @Nullable
    public Unmarshaller deregisterUnmarshaller(@Nonnull QName key) {
        this.log.debug("Deregistering marshaller for object type {}", (Object)key);
        if (key != null) {
            return this.unmarshallers.remove(key);
        }
        return null;
    }
}

