/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio.messages;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.minio.messages.QuoteFields;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="CSV")
@SuppressFBWarnings(value={"URF_UNREAD_FIELD"})
public class CsvOutputSerialization {
    @Element(name="FieldDelimiter", required=false)
    private Character fieldDelimiter;
    @Element(name="QuoteCharacter", required=false)
    private Character quoteCharacter;
    @Element(name="QuoteEscapeCharacter", required=false)
    private Character quoteEscapeCharacter;
    @Element(name="QuoteFields", required=false)
    private QuoteFields quoteFields;
    @Element(name="RecordDelimiter", required=false)
    private Character recordDelimiter;

    public CsvOutputSerialization(Character fieldDelimiter, Character quoteCharacter, Character quoteEscapeCharacter, QuoteFields quoteFields, Character recordDelimiter) {
        this.fieldDelimiter = fieldDelimiter;
        this.quoteCharacter = quoteCharacter;
        this.quoteEscapeCharacter = quoteEscapeCharacter;
        this.quoteFields = quoteFields;
        this.recordDelimiter = recordDelimiter;
    }
}

