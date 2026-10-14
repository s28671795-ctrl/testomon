/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change;

public @interface DatabaseChangeNote {
    public String database() default "";

    public String notes() default "";
}

