/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import liquibase.GlobalConfiguration;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.ChangeSet;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.util.StreamUtil;

public class PropertyExpandingStream
extends InputStream {
    private InputStream stream;

    public PropertyExpandingStream(ChangeSet changeSet, InputStream stream) {
        try {
            if (changeSet == null) {
                this.stream = stream;
            } else {
                Charset encoding = GlobalConfiguration.FILE_ENCODING.getCurrentValue();
                String streamContent = StreamUtil.readStreamAsString(stream, encoding.toString());
                ChangeLogParameters parameters = changeSet.getChangeLogParameters();
                if (parameters != null) {
                    streamContent = parameters.expandExpressions(streamContent, changeSet.getChangeLog());
                }
                this.stream = new ByteArrayInputStream(streamContent.getBytes(encoding));
            }
        }
        catch (IOException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    @Override
    public int read() throws IOException {
        return this.stream.read();
    }

    @Override
    public int available() throws IOException {
        return this.stream.available();
    }

    @Override
    public boolean markSupported() {
        return this.stream.markSupported();
    }

    @Override
    public synchronized void mark(int readLimit) {
        this.stream.mark(readLimit);
    }

    @Override
    public synchronized void reset() throws IOException {
        this.stream.reset();
    }

    @Override
    public void close() throws IOException {
        this.stream.close();
    }
}

