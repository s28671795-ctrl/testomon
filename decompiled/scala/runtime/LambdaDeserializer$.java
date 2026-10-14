/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SerializedLambda;
import java.util.Map;
import scala.Predef$;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize$;

public final class LambdaDeserializer$ {
    public static final LambdaDeserializer$ MODULE$ = new LambdaDeserializer$();

    public Object deserializeLambda(MethodHandles.Lookup lookup, Map<String, MethodHandle> cache, Map<String, MethodHandle> targetMethodMap, SerializedLambda serialized) {
        Object result = this.deserializeLambdaOrNull(lookup, cache, targetMethodMap, serialized);
        if (result == null) {
            throw new IllegalArgumentException("Illegal lambda deserialization");
        }
        return result;
    }

    public Object deserializeLambdaOrNull(MethodHandles.Lookup lookup, Map<String, MethodHandle> cache, Map<String, MethodHandle> targetMethodMap, SerializedLambda serialized) {
        Object[] objectArray;
        MethodHandle methodHandle;
        block12: {
            Predef$.MODULE$.assert(targetMethodMap != null);
            ClassLoader loader = lookup.lookupClass().getClassLoader();
            Class<?> implClass = loader.loadClass(serialized.getImplClass().replaceAll("/", "."));
            String key = LambdaDeserialize$.MODULE$.nameAndDescriptorKey(serialized.getImplMethodName(), serialized.getImplMethodSignature());
            if (cache == null) {
                CallSite callSite = LambdaDeserializer$.makeCallSite$1(loader, serialized, implClass, targetMethodMap, key, lookup);
                if (callSite == null) {
                    return null;
                }
                methodHandle = callSite.getTarget();
            } else {
                synchronized (cache) {
                    block11: {
                        MethodHandle methodHandle2;
                        MethodHandle methodHandle3 = cache.get(key);
                        if (methodHandle3 == null) {
                            CallSite callSite = LambdaDeserializer$.makeCallSite$1(loader, serialized, implClass, targetMethodMap, key, lookup);
                            if (callSite == null) break block11;
                            MethodHandle temp = callSite.getTarget();
                            cache.put(key, temp);
                            methodHandle2 = temp;
                        } else {
                            methodHandle2 = methodHandle3;
                        }
                        MethodHandle methodHandle4 = methodHandle2;
                        // MONITOREXIT @DISABLED, blocks:[0, 1, 9] lbl24 : MonitorExitStatement: MONITOREXIT : cache
                        methodHandle = methodHandle4;
                        break block12;
                    }
                    return null;
                }
            }
        }
        MethodHandle factory2 = methodHandle;
        int tabulate_n = serialized.getCapturedArgCount();
        if (tabulate_n <= 0) {
            objectArray = new Object[]{};
        } else {
            Object[] tabulate_array = new Object[tabulate_n];
            for (int tabulate_i = 0; tabulate_i < tabulate_n; ++tabulate_i) {
                Object array_update_value;
                tabulate_array[tabulate_i] = array_update_value = serialized.getCapturedArg(tabulate_i);
                Object var18_17 = null;
            }
            objectArray = tabulate_array;
        }
        Object var16_15 = null;
        Object[] captures = objectArray;
        return factory2.invokeWithArguments(captures);
    }

    private static final String slashDot$1(String name) {
        return name.replaceAll("/", ".");
    }

    private static final MethodType parseDescriptor$1(String s2, ClassLoader loader$1) {
        return MethodType.fromMethodDescriptorString(s2, loader$1);
    }

    private static final CallSite makeCallSite$1(ClassLoader loader$1, SerializedLambda serialized$1, Class implClass$1, Map targetMethodMap$1, String key$1, MethodHandles.Lookup lookup$1) {
        MethodType methodType;
        String parseDescriptor$1_s = serialized$1.getFunctionalInterfaceMethodSignature();
        Object var17_6 = null;
        MethodType funcInterfaceSignature = MethodType.fromMethodDescriptorString(parseDescriptor$1_s, loader$1);
        String parseDescriptor$1_s2 = serialized$1.getInstantiatedMethodType();
        Object var18_8 = null;
        MethodType instantiated = MethodType.fromMethodDescriptorString(parseDescriptor$1_s2, loader$1);
        Class<?> functionalInterfaceClass = loader$1.loadClass(serialized$1.getFunctionalInterfaceClass().replaceAll("/", "."));
        String parseDescriptor$1_s3 = serialized$1.getImplMethodSignature();
        Object var19_11 = null;
        MethodType implMethodSig = MethodType.fromMethodDescriptorString(parseDescriptor$1_s3, loader$1);
        switch (serialized$1.getImplMethodKind()) {
            case 6: 
            case 8: {
                methodType = implMethodSig;
                break;
            }
            default: {
                methodType = implMethodSig.insertParameterTypes(0, implClass$1);
            }
        }
        MethodType withReceiver = methodType;
        int lambdaArity = funcInterfaceSignature.parameterCount();
        int from = withReceiver.parameterCount() - lambdaArity;
        int to = withReceiver.parameterCount();
        MethodType invokedType = withReceiver.dropParameterTypes(from, to).changeReturnType(functionalInterfaceClass);
        if (!targetMethodMap$1.containsKey(key$1)) {
            return null;
        }
        MethodHandle implMethod = (MethodHandle)targetMethodMap$1.get(key$1);
        int flags = 1;
        return LambdaMetafactory.altMetafactory(lookup$1, serialized$1.getFunctionalInterfaceMethodName(), invokedType, funcInterfaceSignature, implMethod, instantiated, flags);
    }

    private LambdaDeserializer$() {
    }

    public static final /* synthetic */ Object $anonfun$deserializeLambdaOrNull$1$adapted(SerializedLambda serialized$1, Object n) {
        return serialized$1.getCapturedArg(BoxesRunTime.unboxToInt(n));
    }
}

