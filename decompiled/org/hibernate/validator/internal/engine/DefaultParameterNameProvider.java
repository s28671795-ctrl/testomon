/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.validation.ParameterNameProvider;

public class DefaultParameterNameProvider
implements ParameterNameProvider {
    @Override
    public List<String> getParameterNames(Constructor<?> constructor) {
        return this.doGetParameterNames(constructor);
    }

    @Override
    public List<String> getParameterNames(Method method2) {
        return this.doGetParameterNames(method2);
    }

    private List<String> doGetParameterNames(Executable executable) {
        Parameter[] parameters = executable.getParameters();
        ArrayList<String> parameterNames = new ArrayList<String>(parameters.length);
        for (Parameter parameter : parameters) {
            parameterNames.add(parameter.getName());
        }
        return Collections.unmodifiableList(parameterNames);
    }
}

