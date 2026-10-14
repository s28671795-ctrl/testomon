/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.core.LabelMap;

interface Group {
    public boolean isInline();

    public boolean isTextList();

    public Label getLabel(Class var1);

    public LabelMap getElements() throws Exception;

    public Label getText() throws Exception;

    public String toString();
}

