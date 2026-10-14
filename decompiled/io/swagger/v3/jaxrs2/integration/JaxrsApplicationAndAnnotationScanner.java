/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.jaxrs2.integration;

import io.swagger.v3.jaxrs2.integration.JaxrsAnnotationScanner;
import java.util.HashSet;
import java.util.Set;

public class JaxrsApplicationAndAnnotationScanner
extends JaxrsAnnotationScanner<JaxrsApplicationAndAnnotationScanner> {
    @Override
    public Set<Class<?>> classes() {
        Set<Class<?>> classes = super.classes();
        HashSet output = new HashSet();
        if (this.application != null) {
            Set<Object> singletons;
            Set<Class<?>> clzs = this.application.getClasses();
            if (clzs != null) {
                for (Class<?> clz : clzs) {
                    if (this.isIgnored(clz.getName())) continue;
                    output.add(clz);
                }
            }
            if ((singletons = this.application.getSingletons()) != null) {
                for (Object o : singletons) {
                    if (this.isIgnored(o.getClass().getName())) continue;
                    output.add(o.getClass());
                }
            }
        }
        classes.addAll(output);
        return classes;
    }
}

