/*
 * Decompiled with CFR 0.152.
 */
package scala.io;

import scala.Tuple2;
import scala.Tuple3;
import scala.collection.immutable.List;
import scala.collection.immutable.Seq;
import scala.io.StdIn;

public final class StdIn$
implements StdIn {
    public static final StdIn$ MODULE$ = new StdIn$();

    @Override
    public String readLine() {
        return StdIn.readLine$(this);
    }

    @Override
    public String readLine(String text, Seq<Object> args2) {
        return StdIn.readLine$(this, text, args2);
    }

    @Override
    public boolean readBoolean() {
        return StdIn.readBoolean$(this);
    }

    @Override
    public byte readByte() {
        return StdIn.readByte$(this);
    }

    @Override
    public short readShort() {
        return StdIn.readShort$(this);
    }

    @Override
    public char readChar() {
        return StdIn.readChar$(this);
    }

    @Override
    public int readInt() {
        return StdIn.readInt$(this);
    }

    @Override
    public long readLong() {
        return StdIn.readLong$(this);
    }

    @Override
    public float readFloat() {
        return StdIn.readFloat$(this);
    }

    @Override
    public double readDouble() {
        return StdIn.readDouble$(this);
    }

    @Override
    public List<Object> readf(String format) {
        return StdIn.readf$(this, format);
    }

    @Override
    public Object readf1(String format) {
        return StdIn.readf1$(this, format);
    }

    @Override
    public Tuple2<Object, Object> readf2(String format) {
        return StdIn.readf2$(this, format);
    }

    @Override
    public Tuple3<Object, Object, Object> readf3(String format) {
        return StdIn.readf3$(this, format);
    }

    private StdIn$() {
    }
}

