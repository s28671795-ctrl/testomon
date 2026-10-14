/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import org.simpleframework.xml.core.Parameter;

abstract class TemplateParameter
implements Parameter {
    protected TemplateParameter() {
    }

    @Override
    public boolean isAttribute() {
        return false;
    }

    @Override
    public boolean isText() {
        return false;
    }
}

