/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino.util.charstream;

import java.io.EOFException;
import java.io.IOException;
import org.codehaus.janino.util.charstream.UnexpectedCharacterException;

public interface CharStream {
    public static final int EOI = -1;

    public int peek() throws IOException;

    public boolean peek(char var1) throws IOException;

    public int peek(String var1) throws IOException;

    public char read() throws EOFException, IOException;

    public void read(char var1) throws EOFException, UnexpectedCharacterException;

    public int read(String var1) throws EOFException, IOException, UnexpectedCharacterException;

    public boolean peekRead(char var1) throws IOException;

    public int peekRead(String var1) throws IOException;

    public boolean atEoi() throws IOException;

    public void eoi() throws UnexpectedCharacterException;
}

