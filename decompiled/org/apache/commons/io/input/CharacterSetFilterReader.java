/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io.input;

import java.io.Reader;
import java.util.Collections;
import java.util.Set;
import org.apache.commons.io.input.AbstractCharacterFilterReader;

public class CharacterSetFilterReader
extends AbstractCharacterFilterReader {
    private static final Set<Integer> EMPTY_SET = Collections.emptySet();
    private final Set<Integer> skipSet;

    public CharacterSetFilterReader(Reader reader, Set<Integer> skip) {
        super(reader);
        this.skipSet = skip == null ? EMPTY_SET : Collections.unmodifiableSet(skip);
    }

    @Override
    protected boolean filter(int ch) {
        return this.skipSet.contains(ch);
    }
}

