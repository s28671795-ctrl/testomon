/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.minio.Time;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;
import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

@Root
@Convert(value=ResponseDateConverter.class)
public class ResponseDate {
    public static final DateTimeFormatter MINIO_RESPONSE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':'mm':'ss'Z'", Locale.US).withZone(Time.UTC);
    private ZonedDateTime zonedDateTime;

    public ResponseDate() {
    }

    public ResponseDate(ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }

    public ZonedDateTime zonedDateTime() {
        return this.zonedDateTime;
    }

    public String toString() {
        return this.zonedDateTime.format(Time.RESPONSE_DATE_FORMAT);
    }

    @JsonCreator
    public static ResponseDate fromString(String responseDateString) {
        try {
            return new ResponseDate(ZonedDateTime.parse(responseDateString, Time.RESPONSE_DATE_FORMAT));
        }
        catch (DateTimeParseException e) {
            return new ResponseDate(ZonedDateTime.parse(responseDateString, MINIO_RESPONSE_DATE_FORMAT));
        }
    }

    public static class ResponseDateConverter
    implements Converter<ResponseDate> {
        @Override
        public ResponseDate read(InputNode node) throws Exception {
            return ResponseDate.fromString(node.getValue());
        }

        @Override
        public void write(OutputNode node, ResponseDate amzDate) {
            node.setValue(amzDate.toString());
        }
    }
}

