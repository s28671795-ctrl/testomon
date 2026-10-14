/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Field;
import org.jooq.conf.TransformUnneededArithmeticExpressions;

interface Transformable<T>
extends Field<T> {
    public Field<?> transform(TransformUnneededArithmeticExpressions var1);
}

