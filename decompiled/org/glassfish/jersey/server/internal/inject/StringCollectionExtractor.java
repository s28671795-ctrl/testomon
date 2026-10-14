/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.inject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.ws.rs.core.MultivaluedMap;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractor;

abstract class StringCollectionExtractor
implements MultivaluedParameterExtractor<Collection<String>> {
    private final String parameter;
    private final String defaultValue;

    protected StringCollectionExtractor(String parameterName, String defaultValue) {
        this.parameter = parameterName;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getName() {
        return this.parameter;
    }

    @Override
    public String getDefaultValueString() {
        return this.defaultValue;
    }

    @Override
    public Collection<String> extract(MultivaluedMap<String, String> parameters) {
        List stringList = (List)parameters.get(this.parameter);
        Collection<String> collection = this.newCollection();
        if (stringList != null) {
            collection.addAll(stringList);
        } else if (this.defaultValue != null) {
            collection.add(this.defaultValue);
        }
        return collection;
    }

    protected abstract Collection<String> newCollection();

    public static StringCollectionExtractor getInstance(Class<?> collectionType, String parameterName, String defaultValue) {
        if (List.class == collectionType) {
            return new ListString(parameterName, defaultValue);
        }
        if (Set.class == collectionType) {
            return new SetString(parameterName, defaultValue);
        }
        if (SortedSet.class == collectionType) {
            return new SortedSetString(parameterName, defaultValue);
        }
        throw new RuntimeException("Unsupported collection type: " + collectionType.getName());
    }

    private static final class SortedSetString
    extends StringCollectionExtractor {
        public SortedSetString(String parameter, String defaultValue) {
            super(parameter, defaultValue);
        }

        protected SortedSet<String> newCollection() {
            return new TreeSet<String>();
        }
    }

    private static final class SetString
    extends StringCollectionExtractor {
        public SetString(String parameter, String defaultValue) {
            super(parameter, defaultValue);
        }

        protected Set<String> newCollection() {
            return new HashSet<String>();
        }
    }

    private static final class ListString
    extends StringCollectionExtractor {
        public ListString(String parameter, String defaultValue) {
            super(parameter, defaultValue);
        }

        protected List<String> newCollection() {
            return new ArrayList<String>();
        }
    }
}

