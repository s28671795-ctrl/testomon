/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io.input;

import java.io.Reader;
import org.apache.commons.io.input.AbstractCharacterFilterReader;

public class CharacterFilterReader
extends AbstractCharacterFilterReader {
    private final int skip;

    public CharacterFilterReader(Reader reader, int skip) {
        super(reader);
        this.skip = skip;
    }

    @Override
    protected boolean filter(int ch) {
        return ch == this.skip;
    }
}

