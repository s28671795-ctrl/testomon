/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor.security;

import java.lang.annotation.Annotation;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.ClassHelper;
import org.apache.cxf.interceptor.security.SimpleAuthorizingInterceptor;

public class SecureAnnotationsInterceptor
extends SimpleAuthorizingInterceptor {
    private static final Logger LOG = LogUtils.getL7dLogger(SecureAnnotationsInterceptor.class);
    private static final String DEFAULT_ANNOTATION_CLASS_NAME = "javax.annotation.security.RolesAllowed";
    private static final Set<String> SKIP_METHODS = new HashSet<String>(Arrays.asList("wait", "notify", "notifyAll", "equals", "toString", "hashCode"));
    private String annotationClassName = "javax.annotation.security.RolesAllowed";

    public SecureAnnotationsInterceptor() {
        this(true);
    }

    public SecureAnnotationsInterceptor(boolean uniqueId) {
        super(uniqueId);
    }

    public void setAnnotationClassName(String name) {
        try {
            ClassLoaderUtils.loadClass(name, SecureAnnotationsInterceptor.class);
            this.annotationClassName = name;
        }
        catch (Throwable ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException("Annotation class " + name + " is not available");
        }
    }

    public void setSecuredObject(Object object) {
        Class<?> cls = ClassHelper.getRealClass(object);
        HashMap<String, String> rolesMap = new HashMap<String, String>();
        this.findRoles(cls, rolesMap);
        if (rolesMap.isEmpty()) {
            LOG.warning("The roles map is empty, the service object is not protected");
        } else if (LOG.isLoggable(Level.FINE)) {
            for (Map.Entry entry : rolesMap.entrySet()) {
                LOG.fine("Method: " + (String)entry.getKey() + ", roles: " + (String)entry.getValue());
            }
        }
        super.setMethodRolesMap(rolesMap);
    }

    protected void findRoles(Class<?> cls, Map<String, String> rolesMap) {
        if (cls == null || cls == Object.class) {
            return;
        }
        String classRolesAllowed = this.getRoles(cls.getAnnotations(), this.annotationClassName);
        for (Method method2 : cls.getMethods()) {
            String theRoles;
            if (SKIP_METHODS.contains(method2.getName())) continue;
            String methodRolesAllowed = this.getRoles(method2.getAnnotations(), this.annotationClassName);
            String string = theRoles = methodRolesAllowed != null ? methodRolesAllowed : classRolesAllowed;
            if (theRoles == null) continue;
            rolesMap.put(method2.getName(), theRoles);
            rolesMap.put(this.createMethodSig(method2), theRoles);
        }
        if (!rolesMap.isEmpty()) {
            return;
        }
        this.findRoles(cls.getSuperclass(), rolesMap);
        if (!rolesMap.isEmpty()) {
            return;
        }
        for (GenericDeclaration genericDeclaration : cls.getInterfaces()) {
            this.findRoles((Class<?>)genericDeclaration, rolesMap);
        }
    }

    private String getRoles(Annotation[] anns, String annName) {
        for (Annotation ann : anns) {
            if (!ann.annotationType().getName().equals(annName)) continue;
            try {
                Method valueMethod = ann.annotationType().getMethod("value", new Class[0]);
                CharSequence[] roles = (String[])valueMethod.invoke((Object)ann, new Object[0]);
                return String.join((CharSequence)" ", roles);
            }
            catch (Exception exception) {
                break;
            }
        }
        return null;
    }
}

