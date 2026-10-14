/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.xml;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import javax.xml.namespace.NamespaceContext;
import net.shibboleth.utilities.java.support.annotation.constraint.NullableElements;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

@ThreadSafe
public class SimpleNamespaceContext
implements NamespaceContext {
    private final ImmutableBiMap<String, String> mappings;

    public SimpleNamespaceContext() {
        this.mappings = this.getMappingsBuilder().build();
    }

    public SimpleNamespaceContext(@Nullable @NullableElements Map<String, String> prefixToUriMappings) {
        ImmutableBiMap.Builder<String, String> mappingBuilder = this.getMappingsBuilder();
        if (prefixToUriMappings == null || prefixToUriMappings.isEmpty()) {
            this.mappings = mappingBuilder.build();
            return;
        }
        for (String key : prefixToUriMappings.keySet()) {
            String trimmedUri;
            String trimmedPrefix = StringSupport.trimOrNull(key);
            if (trimmedPrefix == null || (trimmedUri = StringSupport.trimOrNull(prefixToUriMappings.get(key))) == null) continue;
            mappingBuilder.put((Object)trimmedPrefix, (Object)trimmedUri);
        }
        this.mappings = mappingBuilder.build();
    }

    @Override
    @Nullable
    public String getNamespaceURI(@Nonnull String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("Prefix can not be null");
        }
        String uri = (String)this.mappings.get(prefix);
        if (uri == null) {
            return "";
        }
        return uri;
    }

    @Override
    @Nullable
    public String getPrefix(@Nonnull String namespaceURI) {
        if (namespaceURI == null) {
            throw new IllegalArgumentException("Namespace URI can not be null");
        }
        return (String)((ImmutableMap)((Object)this.mappings.inverse())).get(namespaceURI);
    }

    @Override
    @Nonnull
    public Iterator<String> getPrefixes(String namespaceURI) {
        if (namespaceURI == null) {
            throw new IllegalArgumentException("Namespace URI can not be null");
        }
        String prefix = (String)((ImmutableMap)((Object)this.mappings.inverse())).get(namespaceURI);
        if (prefix == null) {
            return Collections.emptyList().iterator();
        }
        return Collections.singletonList(prefix).iterator();
    }

    @Nonnull
    private ImmutableBiMap.Builder<String, String> getMappingsBuilder() {
        ImmutableBiMap.Builder<String, String> mappingBuilder = new ImmutableBiMap.Builder<String, String>();
        mappingBuilder.put((Object)"xml", (Object)"http://www.w3.org/XML/1998/namespace");
        mappingBuilder.put((Object)"xmlns", (Object)"http://www.w3.org/2000/xmlns/");
        return mappingBuilder;
    }
}

