/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util.json;

import org.h2.util.json.JSONItemType;
import org.h2.util.json.JSONTarget;

public abstract class JSONValidationTarget
extends JSONTarget<JSONItemType> {
    @Override
    public abstract JSONItemType getResult();
}

