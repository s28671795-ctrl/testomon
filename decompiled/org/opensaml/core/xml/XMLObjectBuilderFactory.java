/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.xml.DOMTypeSupport;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.core.xml.XMLRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class XMLObjectBuilderFactory {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(XMLObjectBuilderFactory.class);
    @Nonnull
    private final Map<QName, XMLObjectBuilder<?>> builders = new ConcurrentHashMap();

    @Nullable
    public XMLObjectBuilder<?> getBuilder(@Nullable QName key) {
        if (key == null) {
            return null;
        }
        return this.builders.get(key);
    }

    @Nullable
    public XMLObjectBuilder<?> getBuilder(@Nullable Element domElement) {
        XMLObjectBuilder<?> builder = this.getBuilder(DOMTypeSupport.getXSIType(domElement));
        if (builder == null) {
            builder = this.getBuilder(QNameSupport.getNodeQName(domElement));
        }
        return builder;
    }

    @Nonnull
    public <XMLObjectType extends XMLObject> XMLObjectBuilder<XMLObjectType> getBuilderOrThrow(@Nonnull QName key) {
        XMLObjectBuilder<?> builder = this.getBuilder(key);
        if (builder == null) {
            throw new XMLRuntimeException("Unable to locate a builder for " + key);
        }
        return builder;
    }

    @Nonnull
    public <XMLObjectType extends XMLObject> XMLObjectBuilder<XMLObjectType> getBuilderOrThrow(@Nonnull Element domElement) {
        XMLObjectBuilder<?> builder = this.getBuilder(domElement);
        if (builder == null) {
            throw new XMLRuntimeException("Unable to locate a builder for " + domElement.getLocalName());
        }
        return builder;
    }

    @Nonnull
    @NotLive
    @Unmodifiable
    public Map<QName, XMLObjectBuilder<?>> getBuilders() {
        return Collections.unmodifiableMap(this.builders);
    }

    public void registerBuilder(@Nonnull QName builderKey, @Nonnull XMLObjectBuilder<?> builder) {
        Constraint.isNotNull(builderKey, "Builder key cannot be null");
        Constraint.isNotNull(builder, "Builder cannot be null");
        this.log.debug("Registering builder {} under key {}", (Object)builder.getClass().getName(), (Object)builderKey);
        this.builders.put(builderKey, builder);
    }

    @Nullable
    public XMLObjectBuilder<?> deregisterBuilder(@Nonnull QName builderKey) {
        Constraint.isNotNull(builderKey, "Builder key QName cannot be null");
        this.log.debug("Deregistering builder for object type {}", (Object)builderKey);
        return this.builders.remove(builderKey);
    }
}

