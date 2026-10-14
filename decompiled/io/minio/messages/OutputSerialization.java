/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio.messages;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.minio.messages.CsvOutputSerialization;
import io.minio.messages.JsonOutputSerialization;
import io.minio.messages.QuoteFields;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="OutputSerialization")
@SuppressFBWarnings(value={"URF_UNREAD_FIELD"})
public class OutputSerialization {
    @Element(name="CSV", required=false)
    private CsvOutputSerialization csv;
    @Element(name="JSON", required=false)
    private JsonOutputSerialization json;

    public OutputSerialization(Character fieldDelimiter, Character quoteCharacter, Character quoteEscapeCharacter, QuoteFields quoteFields, Character recordDelimiter) {
        this.csv = new CsvOutputSerialization(fieldDelimiter, quoteCharacter, quoteEscapeCharacter, quoteFields, recordDelimiter);
    }

    public OutputSerialization(Character recordDelimiter) {
        this.json = new JsonOutputSerialization(recordDelimiter);
    }
}

