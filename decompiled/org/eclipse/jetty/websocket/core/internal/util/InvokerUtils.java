/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal.util;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import org.eclipse.jetty.websocket.core.exception.InvalidSignatureException;
import org.eclipse.jetty.websocket.core.internal.util.ReflectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvokerUtils {
    public static final ParamIdentifier PARAM_IDENTITY = new ParamIdentity();
    private static final Logger LOG = LoggerFactory.getLogger(InvokerUtils.class);

    public static MethodHandle bindTo(MethodHandle methodHandle, Object ... objs) {
        if (methodHandle == null) {
            return null;
        }
        MethodHandle ret = methodHandle;
        for (Object obj : objs) {
            if (!((Class)ret.type().parameterType(0)).isAssignableFrom(obj.getClass())) continue;
            ret = ret.bindTo(obj);
        }
        return ret;
    }

    public static MethodHandle mutatedInvoker(MethodHandles.Lookup lookup, Class<?> targetClass, Method method2, Arg ... callingArgs) {
        return InvokerUtils.mutatedInvoker(lookup, targetClass, true, method2, PARAM_IDENTITY, null, callingArgs);
    }

    public static MethodHandle mutatedInvoker(MethodHandles.Lookup lookup, Class<?> targetClass, Method method2, ParamIdentifier paramIdentifier, String[] namedVariables, Arg ... callingArgs) {
        return InvokerUtils.mutatedInvoker(lookup, targetClass, true, method2, paramIdentifier, namedVariables, callingArgs);
    }

    private static MethodHandle mutatedInvoker(MethodHandles.Lookup lookup, Class<?> targetClass, boolean throwOnFailure, Method method2, ParamIdentifier paramIdentifier, String[] namedVariables, Arg ... rawCallingArgs) {
        Class<?>[] parameterTypes = method2.getParameterTypes();
        Arg[] callingArgs = new Arg[rawCallingArgs.length + (namedVariables == null ? 0 : namedVariables.length)];
        int callingArgIdx = 0;
        if (namedVariables != null) {
            for (String namedVariable : namedVariables) {
                callingArgs[callingArgIdx++] = new Arg(String.class, namedVariable).convertible();
            }
        }
        for (Arg rawCallingArg : rawCallingArgs) {
            callingArgs[callingArgIdx++] = rawCallingArg;
        }
        boolean hasNamedParamArgs = false;
        Arg[] parameterArgs = new Arg[parameterTypes.length + 1];
        parameterArgs[0] = new Arg(targetClass);
        for (int i = 0; i < parameterTypes.length; ++i) {
            Arg arg = paramIdentifier.getParamArg(method2, parameterTypes[i], i);
            if (arg.name != null) {
                hasNamedParamArgs = true;
            }
            parameterArgs[i + 1] = arg;
        }
        if (callingArgs.length < parameterTypes.length) {
            if (!throwOnFailure) {
                return null;
            }
            StringBuilder err = new StringBuilder();
            err.append("Target method ");
            ReflectUtils.append(err, targetClass, method2);
            err.append(" contains too many parameters and cannot be mapped to expected callable args ");
            InvokerUtils.appendTypeList(err, callingArgs);
            throw new InvalidSignatureException(err.toString());
        }
        boolean hasNamedCallingArgs = false;
        boolean hasConvertibleTypes = false;
        ArrayList<Class<?>> cTypes = new ArrayList();
        cTypes.add(targetClass);
        for (int i = 0; i < callingArgs.length; ++i) {
            Arg arg = callingArgs[i];
            if (arg.name != null) {
                hasNamedCallingArgs = true;
            }
            if (arg.convertible) {
                hasConvertibleTypes = true;
            }
            cTypes.add(arg.getType());
        }
        try {
            MethodType callingType = MethodType.methodType(method2.getReturnType(), cTypes);
            MethodType rawType = MethodType.methodType(method2.getReturnType(), method2.getParameterTypes());
            MethodHandle methodHandle = lookup.findVirtual(targetClass, method2.getName(), rawType);
            if (!hasNamedCallingArgs && !hasNamedParamArgs && rawType.equals((Object)callingType)) {
                return methodHandle;
            }
            int[] reorderMap = new int[callingType.parameterCount()];
            Arrays.fill(reorderMap, -1);
            reorderMap[0] = 0;
            boolean[] usedCallingArgs = new boolean[callingArgs.length];
            Arrays.fill(usedCallingArgs, false);
            for (int pi = 1; pi < parameterArgs.length; ++pi) {
                int ref = -1;
                for (int ci = 0; ci < callingArgs.length; ++ci) {
                    if (usedCallingArgs[ci] || !callingArgs[ci].matches(parameterArgs[pi])) continue;
                    ref = ci + 1;
                    usedCallingArgs[ci] = true;
                    break;
                }
                if (ref < 0) {
                    if (!throwOnFailure) {
                        return null;
                    }
                    StringBuilder err = new StringBuilder();
                    err.append("Invalid mapping of type [");
                    err.append(parameterArgs[pi].getType());
                    err.append("] in method ");
                    ReflectUtils.append(err, method2);
                    err.append(" to calling args ");
                    InvokerUtils.appendTypeList(err, callingArgs);
                    throw new InvalidSignatureException(err.toString());
                }
                reorderMap[pi] = ref;
            }
            for (int ri = parameterArgs.length; ri <= reorderMap.length; ++ri) {
                for (int uci = 0; uci < usedCallingArgs.length; ++uci) {
                    if (usedCallingArgs[uci]) continue;
                    if (callingArgs[uci].required) {
                        if (!throwOnFailure) {
                            return null;
                        }
                        StringBuilder err = new StringBuilder();
                        err.append("Missing required argument [");
                        err.append(callingArgs[uci].getType().getName());
                        err.append("] in method ");
                        ReflectUtils.append(err, method2);
                        throw new InvalidSignatureException(err.toString());
                    }
                    reorderMap[ri] = uci + 1;
                    ++ri;
                }
            }
            int idxDrop = parameterArgs.length;
            int dropLength = reorderMap.length - idxDrop;
            if (dropLength > 0) {
                ArrayList dropTypes = new ArrayList();
                for (int i = 0; i < dropLength; ++i) {
                    int callingTypeIdx = reorderMap[idxDrop + i];
                    dropTypes.add((Class<?>)callingType.parameterType(callingTypeIdx));
                }
                methodHandle = MethodHandles.dropArguments(methodHandle, idxDrop, dropTypes);
            }
            if (hasConvertibleTypes) {
                cTypes = new ArrayList();
                cTypes.add(targetClass);
                for (int i = 0; i < callingArgs.length; ++i) {
                    Arg arg = callingArgs[i];
                    cTypes.add(arg.getConvertedType());
                }
                callingType = MethodType.methodType(method2.getReturnType(), cTypes);
            }
            methodHandle = MethodHandles.permuteArguments(methodHandle, callingType, reorderMap);
            return methodHandle;
        }
        catch (IllegalAccessException | NoSuchMethodException e) {
            if (!throwOnFailure) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Unable to obtain MethodHandle for " + method2, e);
                }
                return null;
            }
            throw new InvalidSignatureException("Unable to obtain MethodHandle for " + method2, e);
        }
    }

    public static MethodHandle optionalMutatedInvoker(MethodHandles.Lookup lookup, Class<?> targetClass, Method method2, ParamIdentifier paramIdentifier, String[] namedVariables, Arg ... callingArgs) {
        return InvokerUtils.mutatedInvoker(lookup, targetClass, false, method2, paramIdentifier, namedVariables, callingArgs);
    }

    public static MethodHandle optionalMutatedInvoker(MethodHandles.Lookup lookup, Class<?> targetClass, Method method2, Arg ... callingArgs) {
        return InvokerUtils.mutatedInvoker(lookup, targetClass, false, method2, PARAM_IDENTITY, null, callingArgs);
    }

    private static void appendTypeList(StringBuilder str, Arg[] args2) {
        str.append("(");
        boolean comma = false;
        for (Arg arg : args2) {
            if (comma) {
                str.append(", ");
            }
            str.append(arg.getType().getName());
            comma = true;
        }
        str.append(")");
    }

    private static void appendTypeList(StringBuilder str, Class<?>[] types) {
        str.append("(");
        boolean comma = false;
        for (Class<?> type : types) {
            if (comma) {
                str.append(", ");
            }
            str.append(type.getName());
            comma = true;
        }
        str.append(")");
    }

    public static interface ParamIdentifier {
        public Arg getParamArg(Method var1, Class<?> var2, int var3);
    }

    public static class Arg {
        private final Class<?> type;
        private String name;
        private boolean required = false;
        private boolean convertible = false;
        private Class<?> convertedType;

        public Arg(Class<?> type) {
            this.type = type;
        }

        public Arg(Class<?> type, String name) {
            this.type = type;
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public boolean matches(Arg other) {
            if (this.name != null || other.name != null) {
                if (this.name.equals(other.name)) {
                    if (this.convertible) {
                        this.convertedType = other.type;
                        return true;
                    }
                    if (this.type.isAssignableFrom(other.type)) {
                        return true;
                    }
                    return true;
                }
                return false;
            }
            return other.type.isAssignableFrom(this.type);
        }

        public Arg required() {
            this.required = true;
            return this;
        }

        public Arg convertible() {
            this.convertible = true;
            return this;
        }

        public Class<?> getType() {
            return this.type;
        }

        public Class<?> getConvertedType() {
            if (this.convertible && this.convertedType != null) {
                return this.convertedType;
            }
            return this.type;
        }

        public boolean isRequired() {
            return this.required;
        }

        public boolean isConvertible() {
            return this.convertible;
        }
    }

    private static class ParamIdentity
    implements ParamIdentifier {
        private ParamIdentity() {
        }

        @Override
        public Arg getParamArg(Method method2, Class<?> paramType, int idx) {
            return new Arg(paramType);
        }
    }
}

