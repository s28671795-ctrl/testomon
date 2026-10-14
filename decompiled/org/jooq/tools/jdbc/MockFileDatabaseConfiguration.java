/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools.jdbc;

import java.io.File;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import org.jooq.Source;

public final class MockFileDatabaseConfiguration {
    final LineNumberReader in;
    final boolean patterns;
    final String nullLiteral;

    public MockFileDatabaseConfiguration() {
        this(new LineNumberReader(new StringReader("")), false, null);
    }

    private MockFileDatabaseConfiguration(LineNumberReader in, boolean patterns, String nullLiteral) {
        this.in = in;
        this.patterns = patterns;
        this.nullLiteral = nullLiteral;
    }

    public final MockFileDatabaseConfiguration source(File file) {
        return this.source(Source.of(file, "UTF-8"));
    }

    public final MockFileDatabaseConfiguration source(File file, String encoding) {
        return this.source(Source.of(file, encoding));
    }

    public final MockFileDatabaseConfiguration source(InputStream stream) {
        return this.source(Source.of(stream, "UTF-8"));
    }

    public final MockFileDatabaseConfiguration source(InputStream stream, String encoding) {
        return this.source(Source.of(stream, encoding));
    }

    public final MockFileDatabaseConfiguration source(Source source2) {
        return this.source(source2.reader());
    }

    public final MockFileDatabaseConfiguration source(Reader reader) {
        return new MockFileDatabaseConfiguration(new LineNumberReader(reader), this.patterns, this.nullLiteral);
    }

    public final MockFileDatabaseConfiguration source(String string) {
        return this.source(Source.of(string));
    }

    public final MockFileDatabaseConfiguration patterns(boolean newPatterns) {
        return new MockFileDatabaseConfiguration(this.in, newPatterns, this.nullLiteral);
    }

    public final MockFileDatabaseConfiguration nullLiteral(String newNullLiteral) {
        return new MockFileDatabaseConfiguration(this.in, this.patterns, newNullLiteral);
    }
}

