/*
 * Decompiled with CFR 0.152.
 */
package liquibase.integration.spring;

@FunctionalInterface
public interface Customizer<T> {
    public void customize(T var1);

    public static <T> Customizer<T> withDefaults() {
        return t2 -> {};
    }
}

