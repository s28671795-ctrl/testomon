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
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.ParamConverter;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.internal.inject.AbstractParamValueExtractor;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractor;

abstract class CollectionExtractor<T>
extends AbstractParamValueExtractor<T>
implements MultivaluedParameterExtractor<Collection<T>> {
    protected CollectionExtractor(ParamConverter<T> converter, String parameterName, String defaultStringValue) {
        super(converter, parameterName, defaultStringValue);
    }

    @Override
    public Collection<T> extract(MultivaluedMap<String, String> parameters) {
        List stringList = (List)parameters.get(this.getName());
        Collection<T> valueList = this.newCollection();
        if (stringList != null) {
            for (String v : stringList) {
                valueList.add(this.fromString(v));
            }
        } else if (this.isDefaultValueRegistered()) {
            valueList.add(this.defaultValue());
        }
        return valueList;
    }

    protected abstract Collection<T> newCollection();

    public static <T> CollectionExtractor getInstance(Class<?> collectionType, ParamConverter<T> converter, String parameterName, String defaultValueString) {
        if (List.class == collectionType) {
            return new ListValueOf<T>(converter, parameterName, defaultValueString);
        }
        if (Set.class == collectionType) {
            return new SetValueOf<T>(converter, parameterName, defaultValueString);
        }
        if (SortedSet.class == collectionType) {
            return new SortedSetValueOf<T>(converter, parameterName, defaultValueString);
        }
        throw new ProcessingException(LocalizationMessages.COLLECTION_EXTRACTOR_TYPE_UNSUPPORTED());
    }

    private static final class SortedSetValueOf<T>
    extends CollectionExtractor<T> {
        SortedSetValueOf(ParamConverter<T> converter, String parameter, String defaultValueString) {
            super(converter, parameter, defaultValueString);
        }

        @Override
        protected SortedSet<T> newCollection() {
            return new TreeSet();
        }
    }

    private static final class SetValueOf<T>
    extends CollectionExtractor<T> {
        SetValueOf(ParamConverter<T> converter, String parameter, String defaultValueString) {
            super(converter, parameter, defaultValueString);
        }

        @Override
        protected Set<T> newCollection() {
            return new HashSet();
        }
    }

    private static final class ListValueOf<T>
    extends CollectionExtractor<T> {
        ListValueOf(ParamConverter<T> converter, String parameter, String defaultValueString) {
            super(converter, parameter, defaultValueString);
        }

        @Override
        protected List<T> newCollection() {
            return new ArrayList();
        }
    }
}

