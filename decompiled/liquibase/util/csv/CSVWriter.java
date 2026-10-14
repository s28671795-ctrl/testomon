/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util.csv;

import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

public class CSVWriter
implements AutoCloseable,
Flushable {
    private final ICSVWriter delegate;

    public CSVWriter(Writer writer) {
        this.delegate = new CSVWriterBuilder(writer).build();
    }

    @Override
    public void close() throws Exception {
        this.delegate.close();
    }

    public void writeNext(String[] nextLine) {
        this.delegate.writeNext(nextLine);
    }

    @Override
    public void flush() throws IOException {
        this.delegate.flush();
    }
}

