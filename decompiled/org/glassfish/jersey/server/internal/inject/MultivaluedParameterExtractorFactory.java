/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.inject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import javax.inject.Singleton;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.ext.ParamConverter;
import org.glassfish.jersey.internal.inject.ExtractorException;
import org.glassfish.jersey.internal.inject.ParamConverterFactory;
import org.glassfish.jersey.internal.inject.PrimitiveMapper;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.internal.util.collection.ClassTypePair;
import org.glassfish.jersey.internal.util.collection.LazyValue;
import org.glassfish.jersey.model.Parameter;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.internal.inject.CollectionExtractor;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractor;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractorProvider;
import org.glassfish.jersey.server.internal.inject.PrimitiveCharacterExtractor;
import org.glassfish.jersey.server.internal.inject.PrimitiveValueOfExtractor;
import org.glassfish.jersey.server.internal.inject.SingleStringValueExtractor;
import org.glassfish.jersey.server.internal.inject.SingleValueExtractor;
import org.glassfish.jersey.server.internal.inject.StringCollectionExtractor;

@Singleton
final class MultivaluedParameterExtractorFactory
implements MultivaluedParameterExtractorProvider {
    private final LazyValue<ParamConverterFactory> paramConverterFactory;

    public MultivaluedParameterExtractorFactory(LazyValue<ParamConverterFactory> paramConverterFactory) {
        this.paramConverterFactory = paramConverterFactory;
    }

    @Override
    public MultivaluedParameterExtractor<?> get(Parameter p) {
        return this.process((ParamConverterFactory)this.paramConverterFactory.get(), p.getDefaultValue(), p.getRawType(), p.getType(), p.getAnnotations(), p.getSourceName());
    }

    private MultivaluedParameterExtractor<?> process(ParamConverterFactory paramConverterFactory, String defaultValue, Class<?> rawType, Type type, Annotation[] annotations, String parameterName) {
        ParamConverter<?> converter = paramConverterFactory.getConverter(rawType, type, annotations);
        if (converter != null) {
            try {
                return new SingleValueExtractor(converter, parameterName, defaultValue);
            }
            catch (ExtractorException e) {
                throw e;
            }
            catch (Exception e) {
                throw new ProcessingException(LocalizationMessages.ERROR_PARAMETER_TYPE_PROCESSING(rawType), e);
            }
        }
        if (rawType == List.class || rawType == Set.class || rawType == SortedSet.class) {
            ClassTypePair typePair;
            List<ClassTypePair> typePairs = ReflectionHelper.getTypeArgumentAndClass(type);
            ClassTypePair classTypePair = typePair = typePairs.size() == 1 ? typePairs.get(0) : null;
            if (typePair == null || typePair.rawClass() == String.class) {
                return StringCollectionExtractor.getInstance(rawType, parameterName, defaultValue);
            }
            converter = paramConverterFactory.getConverter(typePair.rawClass(), typePair.type(), annotations);
            if (converter == null) {
                return null;
            }
            try {
                return CollectionExtractor.getInstance(rawType, converter, parameterName, defaultValue);
            }
            catch (ExtractorException e) {
                throw e;
            }
            catch (Exception e) {
                throw new ProcessingException(LocalizationMessages.ERROR_PARAMETER_TYPE_PROCESSING(rawType), e);
            }
        }
        if (rawType == String.class) {
            return new SingleStringValueExtractor(parameterName, defaultValue);
        }
        if (rawType == Character.class) {
            return new PrimitiveCharacterExtractor(parameterName, defaultValue, PrimitiveMapper.primitiveToDefaultValueMap.get(rawType));
        }
        if (rawType.isPrimitive()) {
            Class wrappedRaw = PrimitiveMapper.primitiveToClassMap.get(rawType);
            if (wrappedRaw == null) {
                return null;
            }
            if (wrappedRaw == Character.class) {
                return new PrimitiveCharacterExtractor(parameterName, defaultValue, PrimitiveMapper.primitiveToDefaultValueMap.get(wrappedRaw));
            }
            Method valueOf = AccessController.doPrivileged(ReflectionHelper.getValueOfStringMethodPA(wrappedRaw));
            if (valueOf != null) {
                try {
                    return new PrimitiveValueOfExtractor(valueOf, parameterName, defaultValue, PrimitiveMapper.primitiveToDefaultValueMap.get(wrappedRaw));
                }
                catch (Exception e) {
                    throw new ProcessingException(LocalizationMessages.DEFAULT_COULD_NOT_PROCESS_METHOD(defaultValue, valueOf));
                }
            }
        }
        return null;
    }
}

