/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriInfo;
import org.glassfish.jersey.message.internal.LanguageTag;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.uri.UriComponent;

@PreMatching
@Priority(value=3000)
public final class UriConnegFilter
implements ContainerRequestFilter {
    protected final Map<String, MediaType> mediaTypeMappings;
    protected final Map<String, String> languageMappings;

    public UriConnegFilter(@Context Configuration rc) {
        this(UriConnegFilter.extractMediaTypeMappings(rc.getProperty("jersey.config.server.mediaTypeMappings")), UriConnegFilter.extractLanguageMappings(rc.getProperty("jersey.config.server.languageMappings")));
    }

    public UriConnegFilter(Map<String, MediaType> mediaTypeMappings, Map<String, String> languageMappings) {
        if (mediaTypeMappings == null) {
            mediaTypeMappings = Collections.emptyMap();
        }
        if (languageMappings == null) {
            languageMappings = Collections.emptyMap();
        }
        this.mediaTypeMappings = mediaTypeMappings;
        this.languageMappings = languageMappings;
    }

    @Override
    public void filter(ContainerRequestContext rc) throws IOException {
        int index;
        String suffix;
        int i;
        UriInfo uriInfo = rc.getUriInfo();
        String path = uriInfo.getRequestUri().getRawPath();
        if (path.indexOf(46) == -1) {
            return;
        }
        List<PathSegment> l = uriInfo.getPathSegments(false);
        if (l.isEmpty()) {
            return;
        }
        PathSegment segment = null;
        for (int i2 = l.size() - 1; i2 >= 0 && (segment = l.get(i2)).getPath().length() <= 0; --i2) {
        }
        if (segment == null) {
            return;
        }
        int length = path.length();
        String[] suffixes = segment.getPath().split("\\.");
        for (i = suffixes.length - 1; i >= 1; --i) {
            MediaType accept;
            suffix = suffixes[i];
            if (suffix.length() == 0 || (accept = this.mediaTypeMappings.get(suffix)) == null) continue;
            rc.getHeaders().putSingle("Accept", accept.toString());
            index = path.lastIndexOf('.' + suffix);
            path = new StringBuilder(path).delete(index, index + suffix.length() + 1).toString();
            suffixes[i] = "";
            break;
        }
        for (i = suffixes.length - 1; i >= 1; --i) {
            String acceptLanguage;
            suffix = suffixes[i];
            if (suffix.length() == 0 || (acceptLanguage = this.languageMappings.get(suffix)) == null) continue;
            rc.getHeaders().putSingle("Accept-Language", acceptLanguage);
            index = path.lastIndexOf('.' + suffix);
            path = new StringBuilder(path).delete(index, index + suffix.length() + 1).toString();
            suffixes[i] = "";
            break;
        }
        if (length != path.length()) {
            rc.setRequestUri(uriInfo.getRequestUriBuilder().replacePath(path).build(new Object[0]));
        }
    }

    private static Map<String, MediaType> extractMediaTypeMappings(Object mappings) {
        return UriConnegFilter.parseAndValidateMappings("jersey.config.server.mediaTypeMappings", mappings, new TypeParser<MediaType>(){

            @Override
            public MediaType valueOf(String value) {
                return MediaType.valueOf(value);
            }
        });
    }

    private static Map<String, String> extractLanguageMappings(Object mappings) {
        return UriConnegFilter.parseAndValidateMappings("jersey.config.server.languageMappings", mappings, new TypeParser<String>(){

            @Override
            public String valueOf(String value) {
                return LanguageTag.valueOf(value).toString();
            }
        });
    }

    private static <T> Map<String, T> parseAndValidateMappings(String property, Object mappings, TypeParser<T> parser) {
        if (mappings == null) {
            return Collections.emptyMap();
        }
        if (mappings instanceof Map) {
            return (Map)mappings;
        }
        HashMap mappingsMap = new HashMap();
        if (mappings instanceof String) {
            UriConnegFilter.parseMappings(property, (String)mappings, mappingsMap, parser);
        } else if (mappings instanceof String[]) {
            String[] mappingsArray;
            for (String aMappingsArray : mappingsArray = (String[])mappings) {
                UriConnegFilter.parseMappings(property, aMappingsArray, mappingsMap, parser);
            }
        } else {
            throw new IllegalArgumentException(LocalizationMessages.INVALID_MAPPING_TYPE(property));
        }
        UriConnegFilter.encodeKeys(mappingsMap);
        return mappingsMap;
    }

    private static <T> void parseMappings(String property, String mappings, Map<String, T> mappingsMap, TypeParser<T> parser) {
        String[] records;
        if (mappings == null) {
            return;
        }
        for (String record : records = mappings.split(",")) {
            String[] mapping = record.split(":");
            if (mapping.length != 2) {
                throw new IllegalArgumentException(LocalizationMessages.INVALID_MAPPING_FORMAT(property, mappings));
            }
            String trimmedSegment = mapping[0].trim();
            String trimmedValue = mapping[1].trim();
            if (trimmedSegment.length() == 0) {
                throw new IllegalArgumentException(LocalizationMessages.INVALID_MAPPING_KEY_EMPTY(property, record));
            }
            if (trimmedValue.length() == 0) {
                throw new IllegalArgumentException(LocalizationMessages.INVALID_MAPPING_VALUE_EMPTY(property, record));
            }
            mappingsMap.put(trimmedSegment, parser.valueOf(trimmedValue));
        }
    }

    private static <T> void encodeKeys(Map<String, T> map) {
        HashMap<String, T> tempMap = new HashMap<String, T>();
        for (Map.Entry<String, T> entry : map.entrySet()) {
            tempMap.put(UriComponent.contextualEncode(entry.getKey(), UriComponent.Type.PATH_SEGMENT), entry.getValue());
        }
        map.clear();
        map.putAll(tempMap);
    }

    private static interface TypeParser<T> {
        public T valueOf(String var1);
    }
}

