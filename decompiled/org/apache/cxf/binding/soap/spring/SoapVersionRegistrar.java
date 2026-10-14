/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.PropertyEditorRegistrar
 *  org.springframework.beans.PropertyEditorRegistry
 */
package org.apache.cxf.binding.soap.spring;

import java.beans.PropertyEditor;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.binding.soap.SoapVersionEditor;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;

public class SoapVersionRegistrar
implements PropertyEditorRegistrar {
    public void registerCustomEditors(PropertyEditorRegistry registry) {
        registry.registerCustomEditor(SoapVersion.class, (PropertyEditor)new SoapVersionEditor());
    }
}

