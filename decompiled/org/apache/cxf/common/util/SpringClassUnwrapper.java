/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.aop.TargetSource
 *  org.springframework.aop.framework.Advised
 *  org.springframework.aop.support.AopUtils
 *  org.springframework.beans.factory.BeanCreationException
 *  org.springframework.util.ClassUtils
 */
package org.apache.cxf.common.util;

import org.apache.cxf.common.util.ClassUnwrapper;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.util.ClassUtils;

class SpringClassUnwrapper
implements ClassUnwrapper {
    SpringClassUnwrapper() throws ClassNotFoundException {
        Class.forName("org.springframework.aop.support.AopUtils");
        Class.forName("org.springframework.aop.framework.Advised");
    }

    @Override
    public Class<?> getRealClassFromClass(Class<?> cls) {
        Class<?> superclass;
        if (ClassUtils.isCglibProxyClass(cls) && (superclass = cls.getSuperclass()) != null && (superclass != Object.class || SpringClassUnwrapper.wasCglibEnhanced(cls))) {
            return this.getRealClassFromClass(superclass);
        }
        return cls;
    }

    @Override
    public Object getRealObject(Object o) {
        if (o instanceof Advised) {
            try {
                Advised advised = (Advised)o;
                Object target = advised.getTargetSource().getTarget();
                return this.getRealObject(target);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return o;
    }

    @Override
    public Class<?> getRealClass(Object o) {
        block9: {
            if (AopUtils.isAopProxy((Object)o) && o instanceof Advised) {
                Advised advised = (Advised)o;
                try {
                    TargetSource targetSource = advised.getTargetSource();
                    Object target = null;
                    try {
                        target = targetSource.getTarget();
                    }
                    catch (BeanCreationException ex) {
                        return this.getRealClassFromClass(targetSource.getTargetClass());
                    }
                    if (target == null) {
                        Class targetClass = AopUtils.getTargetClass((Object)o);
                        if (targetClass != null) {
                            return this.getRealClassFromClass(targetClass);
                        }
                        break block9;
                    }
                    return this.getRealClass(target);
                }
                catch (Exception exception) {}
            } else if (ClassUtils.isCglibProxyClass(o.getClass())) {
                return this.getRealClassFromClass(AopUtils.getTargetClass((Object)o));
            }
        }
        return o.getClass();
    }

    private static boolean wasCglibEnhanced(Class<?> cls) {
        return cls.getName().contains("CGLIB");
    }
}

