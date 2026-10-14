/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.parameternameprovider;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ParameterNameProvider;
import org.hibernate.validator.internal.util.CollectionHelper;

@Deprecated
public class ReflectionParameterNameProvider
implements ParameterNameProvider {
    @Override
    public List<String> getParameterNames(Constructor<?> constructor) {
        return this.getParameterNames(constructor.getParameters());
    }

    @Override
    public List<String> getParameterNames(Method method2) {
        return this.getParameterNames(method2.getParameters());
    }

    private List<String> getParameterNames(Parameter[] parameters) {
        ArrayList<String> parameterNames = CollectionHelper.newArrayList();
        for (Parameter parameter : parameters) {
            parameterNames.add(parameter.getName());
        }
        return parameterNames;
    }
}

