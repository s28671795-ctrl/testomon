/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.errors.XmlParserException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;
import org.simpleframework.xml.stream.Format;

public class Xml {
    public static String marshal(Object source2) throws XmlParserException {
        try {
            Persister serializer = new Persister((Strategy)new AnnotationStrategy(), new Format(0));
            StringWriter writer = new StringWriter();
            serializer.write(source2, writer);
            return writer.toString();
        }
        catch (Exception e) {
            throw new XmlParserException(e);
        }
    }

    public static <T> T unmarshal(Class<? extends T> type, Reader source2) throws XmlParserException {
        try {
            Persister serializer = new Persister(new AnnotationStrategy());
            return (T)serializer.read(type, source2);
        }
        catch (Exception e) {
            throw new XmlParserException(e);
        }
    }

    public static <T> T unmarshal(Class<? extends T> type, String source2) throws XmlParserException {
        try {
            Persister serializer = new Persister(new AnnotationStrategy());
            return (T)serializer.read(type, (Reader)new StringReader(source2));
        }
        catch (Exception e) {
            throw new XmlParserException(e);
        }
    }

    public static boolean validate(Class type, String source2) throws XmlParserException {
        try {
            Persister serializer = new Persister(new AnnotationStrategy());
            return serializer.validate(type, source2);
        }
        catch (Exception e) {
            throw new XmlParserException(e);
        }
    }
}

