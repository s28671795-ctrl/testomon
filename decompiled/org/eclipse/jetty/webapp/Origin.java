/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.webapp;

import java.lang.annotation.Annotation;
import org.eclipse.jetty.webapp.DefaultsDescriptor;
import org.eclipse.jetty.webapp.FragmentDescriptor;
import org.eclipse.jetty.webapp.OverrideDescriptor;
import org.eclipse.jetty.webapp.WebDescriptor;

public enum Origin {
    NotSet,
    WebXml,
    WebDefaults,
    WebOverride,
    WebFragment,
    Annotation,
    API;


    public static Origin of(Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Annotation) {
            return Annotation;
        }
        if (o instanceof FragmentDescriptor) {
            return WebFragment;
        }
        if (o instanceof OverrideDescriptor) {
            return WebOverride;
        }
        if (o instanceof DefaultsDescriptor) {
            return WebDefaults;
        }
        if (o instanceof WebDescriptor) {
            return WebXml;
        }
        return API;
    }
}

