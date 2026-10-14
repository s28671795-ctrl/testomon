/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

final class SubqueryCharacteristics {
    static final int DERIVED_TABLE = 1;
    static final int SET_OPERATION = 2;
    static final int SCALAR = 4;
    static final int PREDICAND = 256;
    static final int CORRELATED = 512;

    SubqueryCharacteristics() {
    }
}

