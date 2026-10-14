/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import org.simpleframework.xml.core.Contact;
import org.simpleframework.xml.core.Context;
import org.simpleframework.xml.core.Converter;
import org.simpleframework.xml.core.Decorator;
import org.simpleframework.xml.core.Expression;
import org.simpleframework.xml.strategy.Type;

interface Label {
    public Decorator getDecorator() throws Exception;

    public Type getType(Class var1) throws Exception;

    public Label getLabel(Class var1) throws Exception;

    public String[] getNames() throws Exception;

    public String[] getPaths() throws Exception;

    public Object getEmpty(Context var1) throws Exception;

    public Converter getConverter(Context var1) throws Exception;

    public String getName() throws Exception;

    public String getPath() throws Exception;

    public Expression getExpression() throws Exception;

    public Type getDependent() throws Exception;

    public String getEntry() throws Exception;

    public Object getKey() throws Exception;

    public Annotation getAnnotation();

    public Contact getContact();

    public Class getType();

    public String getOverride();

    public boolean isData();

    public boolean isRequired();

    public boolean isAttribute();

    public boolean isCollection();

    public boolean isInline();

    public boolean isText();

    public boolean isTextList();

    public boolean isUnion();

    public String toString();
}

