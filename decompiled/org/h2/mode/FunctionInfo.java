/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mode;

public final class FunctionInfo {
    public final String name;
    public final int type;
    final int parameterCount;
    public final int returnDataType;
    public final boolean nullIfParameterIsNull;
    public final boolean deterministic;

    public FunctionInfo(String string, int n, int n2, int n3, boolean bl, boolean bl2) {
        this.name = string;
        this.type = n;
        this.parameterCount = n2;
        this.returnDataType = n3;
        this.nullIfParameterIsNull = bl;
        this.deterministic = bl2;
    }

    public FunctionInfo(FunctionInfo functionInfo, String string) {
        this.name = string;
        this.type = functionInfo.type;
        this.returnDataType = functionInfo.returnDataType;
        this.parameterCount = functionInfo.parameterCount;
        this.nullIfParameterIsNull = functionInfo.nullIfParameterIsNull;
        this.deterministic = functionInfo.deterministic;
    }
}

