/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio.messages;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.minio.messages.CompressionType;
import io.minio.messages.CsvInputSerialization;
import io.minio.messages.FileHeaderInfo;
import io.minio.messages.JsonInputSerialization;
import io.minio.messages.JsonType;
import io.minio.messages.ParquetInputSerialization;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="InputSerialization")
@SuppressFBWarnings(value={"URF_UNREAD_FIELD"})
public class InputSerialization {
    @Element(name="CompressionType", required=false)
    private CompressionType compressionType;
    @Element(name="CSV", required=false)
    private CsvInputSerialization csv;
    @Element(name="JSON", required=false)
    private JsonInputSerialization json;
    @Element(name="Parquet", required=false)
    private ParquetInputSerialization parquet;

    public InputSerialization(CompressionType compressionType, boolean allowQuotedRecordDelimiter, Character comments, Character fieldDelimiter, FileHeaderInfo fileHeaderInfo, Character quoteCharacter, Character quoteEscapeCharacter, Character recordDelimiter) {
        this.compressionType = compressionType;
        this.csv = new CsvInputSerialization(allowQuotedRecordDelimiter, comments, fieldDelimiter, fileHeaderInfo, quoteCharacter, quoteEscapeCharacter, recordDelimiter);
    }

    public InputSerialization(CompressionType compressionType, JsonType type) {
        this.compressionType = compressionType;
        this.json = new JsonInputSerialization(type);
    }

    public InputSerialization() {
        this.parquet = new ParquetInputSerialization();
    }
}

