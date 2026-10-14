/*
 * Decompiled with CFR 0.152.
 */
package scala.io;

import scala.io.Position;

public final class Position$
extends Position {
    public static final Position$ MODULE$ = new Position$();

    @Override
    public void checkInput(int line, int column) {
        if (line < 0) {
            throw new IllegalArgumentException(new StringBuilder(4).append(line).append(" < 0").toString());
        }
        if (line == 0 && column != 0) {
            throw new IllegalArgumentException(new StringBuilder(13).append(line).append(",").append(column).append(" not allowed").toString());
        }
        if (column < 0) {
            throw new IllegalArgumentException(new StringBuilder(13).append(line).append(",").append(column).append(" not allowed").toString());
        }
    }

    private Position$() {
    }
}

