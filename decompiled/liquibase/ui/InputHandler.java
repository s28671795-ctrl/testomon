/*
 * Decompiled with CFR 0.152.
 */
package liquibase.ui;

public interface InputHandler<ReturnType> {
    public ReturnType parseInput(String var1, Class<ReturnType> var2) throws IllegalArgumentException;

    default public boolean shouldAllowEmptyInput() {
        return true;
    }
}

