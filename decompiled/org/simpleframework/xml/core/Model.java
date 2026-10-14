/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import org.simpleframework.xml.core.Expression;
import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.core.LabelMap;
import org.simpleframework.xml.core.ModelMap;

interface Model
extends Iterable<String> {
    public boolean isEmpty();

    public boolean isModel(String var1);

    public boolean isElement(String var1);

    public boolean isAttribute(String var1);

    public boolean isComposite();

    public void validate(Class var1) throws Exception;

    public void register(Label var1) throws Exception;

    public void registerText(Label var1) throws Exception;

    public void registerElement(Label var1) throws Exception;

    public void registerAttribute(Label var1) throws Exception;

    public void registerElement(String var1) throws Exception;

    public void registerAttribute(String var1) throws Exception;

    public Model register(String var1, String var2, int var3) throws Exception;

    public Model lookup(String var1, int var2);

    public Model lookup(Expression var1);

    public LabelMap getElements() throws Exception;

    public LabelMap getAttributes() throws Exception;

    public ModelMap getModels() throws Exception;

    public Label getText();

    public Expression getExpression();

    public String getPrefix();

    public String getName();

    public int getIndex();
}

