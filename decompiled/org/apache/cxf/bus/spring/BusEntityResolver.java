/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.xml.DelegatingEntityResolver
 *  org.springframework.core.io.ClassPathResource
 *  org.springframework.core.io.support.PropertiesLoaderUtils
 *  org.springframework.util.CollectionUtils
 */
package org.apache.cxf.bus.spring;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.common.logging.LogUtils;
import org.springframework.beans.factory.xml.DelegatingEntityResolver;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.CollectionUtils;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class BusEntityResolver
extends DelegatingEntityResolver {
    private static final Logger LOG = LogUtils.getL7dLogger(BusEntityResolver.class);
    private EntityResolver dtdResolver;
    private EntityResolver schemaResolver;
    private Map<String, String> schemaMappings;
    private ClassLoader classLoader;

    public BusEntityResolver(ClassLoader loader, EntityResolver dr, EntityResolver sr) {
        super(dr, sr);
        this.classLoader = loader;
        this.dtdResolver = dr;
        this.schemaResolver = sr;
        try {
            Properties mappings = PropertiesLoaderUtils.loadAllProperties((String)"META-INF/spring.schemas", (ClassLoader)this.classLoader);
            this.schemaMappings = new ConcurrentHashMap<String, String>(mappings.size());
            CollectionUtils.mergePropertiesIntoMap((Properties)mappings, this.schemaMappings);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        ClassPathResource resource;
        String resourceLocation;
        InputSource source2 = super.resolveEntity(publicId, systemId);
        if (null == source2 && null != systemId) {
            LOG.log(Level.FINE, "Attempting to resolve systemId {0}", systemId);
            source2 = this.schemaResolver.resolveEntity(publicId, systemId);
            if (null == source2) {
                source2 = this.dtdResolver.resolveEntity(publicId, systemId);
            }
            if (null == source2) {
                return null;
            }
        }
        if ((resourceLocation = this.schemaMappings.get(systemId)) != null && publicId == null && (resource = new ClassPathResource(resourceLocation, this.classLoader)).exists()) {
            source2.setPublicId(systemId);
            source2.setSystemId(resource.getURL().toString());
        }
        return source2;
    }
}

