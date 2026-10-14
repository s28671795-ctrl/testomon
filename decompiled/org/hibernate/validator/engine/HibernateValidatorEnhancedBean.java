/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.engine;

import org.hibernate.validator.Incubating;

@Incubating
public interface HibernateValidatorEnhancedBean {
    public static final String GET_FIELD_VALUE_METHOD_NAME = "$$_hibernateValidator_getFieldValue";
    public static final String GET_GETTER_VALUE_METHOD_NAME = "$$_hibernateValidator_getGetterValue";

    public Object $$_hibernateValidator_getFieldValue(String var1);

    public Object $$_hibernateValidator_getGetterValue(String var1);
}

