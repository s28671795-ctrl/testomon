/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.server.model.AnnotatedMethod;

public final class MethodList
implements Iterable<AnnotatedMethod> {
    private AnnotatedMethod[] methods;

    public MethodList(Class<?> c) {
        this(c, false);
    }

    public MethodList(Class<?> c, boolean declaredMethods) {
        this(declaredMethods ? MethodList.getAllDeclaredMethods(c) : MethodList.getMethods(c));
    }

    private static List<Method> getAllDeclaredMethods(Class<?> c) {
        ArrayList<Method> l = new ArrayList<Method>();
        while (c != null && c != Object.class) {
            l.addAll(AccessController.doPrivileged(ReflectionHelper.getDeclaredMethodsPA(c)));
            c = c.getSuperclass();
        }
        return l;
    }

    private static List<Method> getMethods(Class<?> c) {
        return Arrays.asList(c.getMethods());
    }

    public MethodList(Collection<Method> methods) {
        ArrayList<AnnotatedMethod> l = new ArrayList<AnnotatedMethod>(methods.size());
        for (Method m4 : methods) {
            if (m4.isSynthetic() || m4.getDeclaringClass() == Object.class) continue;
            l.add(new AnnotatedMethod(m4));
        }
        this.methods = new AnnotatedMethod[l.size()];
        this.methods = l.toArray(this.methods);
    }

    public MethodList(Method ... methods) {
        this(Arrays.asList(methods));
    }

    public MethodList(AnnotatedMethod ... methods) {
        this.methods = methods;
    }

    @Override
    public Iterator<AnnotatedMethod> iterator() {
        return Arrays.asList(this.methods).iterator();
    }

    public MethodList isNotPublic() {
        return this.filter(new Filter(){

            @Override
            public boolean keep(AnnotatedMethod m4) {
                return !Modifier.isPublic(m4.getMethod().getModifiers());
            }
        });
    }

    public MethodList hasNumParams(final int paramCount) {
        return this.filter(new Filter(){

            @Override
            public boolean keep(AnnotatedMethod m4) {
                return m4.getParameterTypes().length == paramCount;
            }
        });
    }

    public MethodList hasReturnType(final Class<?> returnType) {
        return this.filter(new Filter(){

            @Override
            public boolean keep(AnnotatedMethod m4) {
                return m4.getMethod().getReturnType() == returnType;
            }
        });
    }

    public MethodList nameStartsWith(final String prefix) {
        return this.filter(new Filter(){

            @Override
            public boolean keep(AnnotatedMethod m4) {
                return m4.getMethod().getName().startsWith(prefix);
            }
        });
    }

    public <T extends Annotation> MethodList withAnnotation(final Class<T> annotation) {
        return this.filter(new Filter(){

            @Override
            public boolean keep(AnnotatedMethod m4) {
                return m4.getAnnotation(annotation) != null;
            }
        });
    }

    public <T extends Annotation> MethodList withMetaAnnotation(final Class<T> annotation) {
        return this.filter(new Filter(){

            @Override
            public boolean keep(AnnotatedMethod m4) {
                for (Annotation a : m4.getAnnotations()) {
                    if (a.annotationType().getAnnotation(annotation) == null) continue;
                    return true;
                }
                return false;
            }
        });
    }

    public <T extends Annotation> MethodList withoutAnnotation(final Class<T> annotation) {
        return this.filter(new Filter(){

            @Override
            public boolean keep(AnnotatedMethod m4) {
                return m4.getAnnotation(annotation) == null;
            }
        });
    }

    public <T extends Annotation> MethodList withoutMetaAnnotation(final Class<T> annotation) {
        return this.filter(new Filter(){

            @Override
            public boolean keep(AnnotatedMethod m4) {
                for (Annotation a : m4.getAnnotations()) {
                    if (a.annotationType().getAnnotation(annotation) == null) continue;
                    return false;
                }
                return true;
            }
        });
    }

    public MethodList filter(Filter filter2) {
        ArrayList<AnnotatedMethod> result = new ArrayList<AnnotatedMethod>();
        for (AnnotatedMethod m4 : this.methods) {
            if (!filter2.keep(m4)) continue;
            result.add(m4);
        }
        return new MethodList(result.toArray(new AnnotatedMethod[result.size()]));
    }

    public static interface Filter {
        public boolean keep(AnnotatedMethod var1);
    }
}

