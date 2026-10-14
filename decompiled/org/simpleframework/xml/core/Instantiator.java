/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.util.List;
import org.simpleframework.xml.core.Creator;
import org.simpleframework.xml.core.Criteria;
import org.simpleframework.xml.core.Parameter;

interface Instantiator {
    public boolean isDefault();

    public Object getInstance() throws Exception;

    public Object getInstance(Criteria var1) throws Exception;

    public Parameter getParameter(String var1);

    public List<Parameter> getParameters();

    public List<Creator> getCreators();
}

