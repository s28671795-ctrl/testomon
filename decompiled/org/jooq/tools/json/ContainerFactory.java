/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools.json;

import java.util.List;
import java.util.Map;

public interface ContainerFactory {
    public Map createObjectContainer();

    public List createArrayContainer();
}

