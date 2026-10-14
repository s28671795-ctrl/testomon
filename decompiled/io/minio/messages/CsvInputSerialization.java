/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio.messages;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.minio.messages.FileHeaderInfo;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="CSV")
@SuppressFBWarnings(value={"URF_UNREAD_FIELD"})
public class CsvInputSerialization {
    @Element(name="AllowQuotedRecordDelimiter", required=false)
    private boolean allowQuotedRecordDelimiter;
    @Element(name="Comments", required=false)
    private Character comments;
    @Element(name="FieldDelimiter", required=false)
    private Character fieldDelimiter;
    @Element(name="FileHeaderInfo", required=false)
    private FileHeaderInfo fileHeaderInfo;
    @Element(name="QuoteCharacter", required=false)
    private Character quoteCharacter;
    @Element(name="QuoteEscapeCharacter", required=false)
    private Character quoteEscapeCharacter;
    @Element(name="RecordDelimiter", required=false)
    private Character recordDelimiter;

    public CsvInputSerialization(boolean allowQuotedRecordDelimiter, Character comments, Character fieldDelimiter, FileHeaderInfo fileHeaderInfo, Character quoteCharacter, Character quoteEscapeCharacter, Character recordDelimiter) {
        this.allowQuotedRecordDelimiter = allowQuotedRecordDelimiter;
        this.comments = comments;
        this.fieldDelimiter = fieldDelimiter;
        this.fileHeaderInfo = fileHeaderInfo;
        this.quoteCharacter = quoteCharacter;
        this.quoteEscapeCharacter = quoteEscapeCharacter;
        this.recordDelimiter = recordDelimiter;
    }
}

