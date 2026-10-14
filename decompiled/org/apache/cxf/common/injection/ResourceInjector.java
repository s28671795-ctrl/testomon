/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.injection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.annotation.Resources;
import org.apache.cxf.common.annotation.AbstractAnnotationVisitor;
import org.apache.cxf.common.annotation.AnnotationProcessor;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.ClassHelper;
import org.apache.cxf.common.util.ReflectionUtil;
import org.apache.cxf.resource.ResourceManager;
import org.apache.cxf.resource.ResourceResolver;

public class ResourceInjector
extends AbstractAnnotationVisitor {
    private static final Logger LOG = LogUtils.getL7dLogger(ResourceInjector.class);
    private static final List<Class<? extends Annotation>> ANNOTATIONS = new ArrayList<Class<? extends Annotation>>();
    private final ResourceManager resourceManager;
    private final List<ResourceResolver> resourceResolvers;

    public ResourceInjector(ResourceManager resMgr) {
        this(resMgr, resMgr == null ? null : resMgr.getResourceResolvers());
    }

    public ResourceInjector(ResourceManager resMgr, List<ResourceResolver> resolvers) {
        super(ANNOTATIONS);
        this.resourceManager = resMgr;
        this.resourceResolvers = resolvers;
    }

    private static Field getField(Class<?> cls, String name) {
        if (cls == null) {
            return null;
        }
        try {
            Field f = ReflectionUtil.getDeclaredField(cls, name);
            if (f == null) {
                f = ResourceInjector.getField(cls.getSuperclass(), name);
            }
            return f;
        }
        catch (Exception ex) {
            return ResourceInjector.getField(cls.getSuperclass(), name);
        }
    }

    public static boolean processable(Class<?> cls, Object o) {
        if (cls.getName().startsWith("java.") || cls.getName().startsWith("javax.")) {
            return false;
        }
        NoJSR250Annotations njsr = cls.getAnnotation(NoJSR250Annotations.class);
        if (njsr != null) {
            for (String s2 : njsr.unlessNull()) {
                try {
                    Field f = ResourceInjector.getField(cls, s2);
                    ReflectionUtil.setAccessible(f);
                    if (f.get(o) != null) continue;
                    return true;
                }
                catch (Exception ex) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public void inject(Object o) {
        this.inject(o, o.getClass());
    }

    public void inject(Object o, Class<?> claz) {
        if (ResourceInjector.processable(claz, o)) {
            AnnotationProcessor processor = new AnnotationProcessor(o);
            processor.accept(this, claz);
        }
    }

    public void construct(Object o) {
        this.setTarget(o);
        if (ResourceInjector.processable(this.targetClass, o)) {
            this.invokePostConstruct();
        }
    }

    public void construct(Object o, Class<?> cls) {
        this.setTarget(o, cls);
        if (ResourceInjector.processable(this.targetClass, o)) {
            this.invokePostConstruct();
        }
    }

    public void destroy(Object o) {
        this.setTarget(o);
        if (ResourceInjector.processable(this.targetClass, o)) {
            this.invokePreDestroy();
        }
    }

    @Override
    public final void visitClass(Class<?> clz, Annotation annotation) {
        assert (annotation instanceof Resource || annotation instanceof Resources) : annotation;
        if (annotation instanceof Resource) {
            this.injectResourceClassLevel((Resource)annotation);
        } else if (annotation instanceof Resources) {
            Resources resources = (Resources)annotation;
            for (Resource resource : resources.value()) {
                this.injectResourceClassLevel(resource);
            }
        }
    }

    private void injectResourceClassLevel(Resource res) {
        if (res.name() == null || "".equals(res.name())) {
            LOG.log(Level.INFO, "RESOURCE_NAME_NOT_SPECIFIED", this.target.getClass().getName());
            return;
        }
        Method setter2 = this.findSetterForResource(res);
        if (setter2 != null) {
            Class<?> type = this.getResourceType(res, setter2);
            Object resource = this.resolveResource(res.name(), type);
            if (resource == null) {
                LOG.log(Level.INFO, "RESOURCE_RESOLVE_FAILED");
                return;
            }
            this.invokeSetter(setter2, resource);
            return;
        }
        Field field2 = this.findFieldForResource(res);
        if (field2 != null) {
            Class<?> type = this.getResourceType(res, field2);
            Object resource = this.resolveResource(res.name(), type);
            if (resource == null) {
                LOG.log(Level.INFO, "RESOURCE_RESOLVE_FAILED");
                return;
            }
            this.injectField(field2, resource);
            return;
        }
        LOG.log(Level.SEVERE, "NO_SETTER_OR_FIELD_FOR_RESOURCE", this.getTarget().getClass().getName());
    }

    @Override
    public final void visitField(Field field2, Annotation annotation) {
        Class<?> type;
        assert (annotation instanceof Resource) : annotation;
        Resource res = (Resource)annotation;
        String name = this.getFieldNameForResource(res, field2);
        Object resource = this.resolveResource(name, type = this.getResourceType(res, field2));
        if (resource == null && "".equals(res.name())) {
            resource = this.resolveResource(null, type);
        }
        if (resource != null) {
            this.injectField(field2, resource);
        } else {
            LOG.log(Level.INFO, "RESOURCE_RESOLVE_FAILED", name);
        }
    }

    @Override
    public final void visitMethod(Method method2, Annotation annotation) {
        Class<?> clz;
        assert (annotation instanceof Resource) : annotation;
        Resource res = (Resource)annotation;
        String resourceName = this.getResourceName(res, method2);
        Object resource = this.resolveResource(resourceName, clz = this.getResourceType(res, method2));
        if (resource == null && "".equals(res.name())) {
            resource = this.resolveResource(null, clz);
        }
        if (resource != null) {
            this.invokeSetter(method2, resource);
        } else {
            LOG.log(Level.FINE, "RESOURCE_RESOLVE_FAILED", new Object[]{resourceName, clz});
        }
    }

    private Field findFieldForResource(Resource res) {
        assert (this.target != null);
        assert (res.name() != null);
        for (Field field2 : this.target.getClass().getFields()) {
            if (!field2.getName().equals(res.name())) continue;
            return field2;
        }
        for (Field field2 : this.target.getClass().getDeclaredFields()) {
            if (!field2.getName().equals(res.name())) continue;
            return field2;
        }
        return null;
    }

    private Method findSetterForResource(Resource res) {
        assert (this.target != null);
        String setterName = this.resourceNameToSetter(res.name());
        Method setterMethod = null;
        for (Method method2 : this.getTarget().getClass().getMethods()) {
            if (!setterName.equals(method2.getName())) continue;
            setterMethod = method2;
            break;
        }
        if (setterMethod != null && setterMethod.getParameterTypes().length != 1) {
            LOG.log(Level.WARNING, "SETTER_INJECTION_WITH_INCORRECT_TYPE", setterMethod);
        }
        return setterMethod;
    }

    private String resourceNameToSetter(String resName) {
        return "set" + Character.toUpperCase(resName.charAt(0)) + resName.substring(1);
    }

    private void invokeSetter(Method method2, Object resource) {
        try {
            ReflectionUtil.setAccessible(method2);
            if (method2.getDeclaringClass().isAssignableFrom(this.getTarget().getClass())) {
                method2.invoke(this.getTarget(), resource);
            } else {
                Method targetMethod = this.getTarget().getClass().getMethod(method2.getName(), method2.getParameterTypes());
                targetMethod.invoke(this.getTarget(), resource);
            }
        }
        catch (IllegalAccessException e) {
            LOG.log(Level.SEVERE, "INJECTION_SETTER_NOT_VISIBLE", method2);
        }
        catch (InvocationTargetException e) {
            LogUtils.log(LOG, Level.SEVERE, "INJECTION_SETTER_RAISED_EXCEPTION", (Throwable)e, (Object)method2);
        }
        catch (SecurityException e) {
            LogUtils.log(LOG, Level.SEVERE, "INJECTION_SETTER_RAISED_EXCEPTION", (Throwable)e, (Object)method2);
        }
        catch (NoSuchMethodException e) {
            LOG.log(Level.SEVERE, "INJECTION_SETTER_METHOD_NOT_FOUND", new Object[]{method2.getName()});
        }
    }

    private String getResourceName(Resource res, Method method2) {
        assert (method2 != null);
        assert (res != null);
        assert (method2.getName().startsWith("set")) : method2;
        if (res.name() == null || res.name().isEmpty()) {
            String name = method2.getName().substring(3);
            name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
            return method2.getDeclaringClass().getCanonicalName() + '/' + name;
        }
        return res.name();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void injectField(Field field2, Object resource) {
        assert (field2 != null);
        assert (resource != null);
        boolean accessible = field2.isAccessible();
        try {
            if (field2.getType().isAssignableFrom(resource.getClass())) {
                ReflectionUtil.setAccessible(field2);
                field2.set(ClassHelper.getRealObject(this.getTarget()), resource);
            }
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
            LOG.severe("FAILED_TO_INJECT_FIELD");
        }
        finally {
            ReflectionUtil.setAccessible(field2, accessible);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void invokePostConstruct() {
        boolean accessible = false;
        for (Method method2 : this.getPostConstructMethods()) {
            PostConstruct pc = method2.getAnnotation(PostConstruct.class);
            if (pc == null) continue;
            try {
                ReflectionUtil.setAccessible(method2);
                method2.invoke(this.target, new Object[0]);
            }
            catch (IllegalAccessException e) {
                LOG.log(Level.WARNING, "INJECTION_COMPLETE_NOT_VISIBLE", method2);
            }
            catch (InvocationTargetException e) {
                LOG.log(Level.WARNING, "INJECTION_COMPLETE_THREW_EXCEPTION", e);
            }
            finally {
                ReflectionUtil.setAccessible(method2, accessible);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void invokePreDestroy() {
        boolean accessible = false;
        for (Method method2 : this.getPreDestroyMethods()) {
            PreDestroy pd = method2.getAnnotation(PreDestroy.class);
            if (pd == null) continue;
            try {
                ReflectionUtil.setAccessible(method2);
                method2.invoke(this.target, new Object[0]);
            }
            catch (IllegalAccessException e) {
                LOG.log(Level.WARNING, "PRE_DESTROY_NOT_VISIBLE", method2);
            }
            catch (InvocationTargetException e) {
                LOG.log(Level.WARNING, "PRE_DESTROY_THREW_EXCEPTION", e);
            }
            finally {
                ReflectionUtil.setAccessible(method2, accessible);
            }
        }
    }

    private Collection<Method> getPostConstructMethods() {
        return this.getAnnotatedMethods(PostConstruct.class);
    }

    private Collection<Method> getPreDestroyMethods() {
        return this.getAnnotatedMethods(PreDestroy.class);
    }

    private Collection<Method> getAnnotatedMethods(Class<? extends Annotation> acls) {
        LinkedList<Method> methods = new LinkedList<Method>();
        this.addAnnotatedMethods(acls, this.getTarget().getClass().getMethods(), methods);
        this.addAnnotatedMethods(acls, ReflectionUtil.getDeclaredMethods(this.getTarget().getClass()), methods);
        if (this.getTargetClass() != this.getTarget().getClass()) {
            this.addAnnotatedMethods(acls, this.getTargetClass().getMethods(), methods);
            this.addAnnotatedMethods(acls, ReflectionUtil.getDeclaredMethods(this.getTargetClass()), methods);
        }
        return methods;
    }

    private void addAnnotatedMethods(Class<? extends Annotation> acls, Method[] methods, Collection<Method> annotatedMethods) {
        for (Method method2 : methods) {
            if (method2.getAnnotation(acls) == null || annotatedMethods.contains(method2)) continue;
            annotatedMethods.add(method2);
        }
    }

    protected Class<?> getResourceType(Resource res, Field field2) {
        assert (res != null);
        Class<?> type = res.type();
        if (res.type() == null || Object.class == res.type()) {
            type = field2.getType();
        }
        return type;
    }

    private Class<?> getResourceType(Resource res, Method method2) {
        return res.type() != null && !Object.class.equals(res.type()) ? res.type() : method2.getParameterTypes()[0];
    }

    private String getFieldNameForResource(Resource res, Field field2) {
        assert (res != null);
        if (res.name() == null || "".equals(res.name())) {
            return field2.getDeclaringClass().getCanonicalName() + "/" + field2.getName();
        }
        return res.name();
    }

    private Object resolveResource(String resourceName, Class<?> type) {
        if (this.resourceManager == null) {
            return null;
        }
        return this.resourceManager.resolveResource(resourceName, type, this.resourceResolvers);
    }

    static {
        ANNOTATIONS.add(Resource.class);
        ANNOTATIONS.add(Resources.class);
    }
}

