/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import org.pf4j.processor.ExtensionAnnotationProcessor;

public abstract class ExtensionStorage {
    private static final Pattern COMMENT = Pattern.compile("#.*");
    private static final Pattern WHITESPACE = Pattern.compile("\\s+");
    protected final ExtensionAnnotationProcessor processor;

    public ExtensionStorage(ExtensionAnnotationProcessor processor) {
        this.processor = processor;
    }

    public abstract Map<String, Set<String>> read();

    public abstract void write(Map<String, Set<String>> var1);

    protected Filer getFiler() {
        return this.processor.getProcessingEnvironment().getFiler();
    }

    protected void error(String message, Object ... args2) {
        this.processor.error(message, args2);
    }

    protected void error(Element element, String message, Object ... args2) {
        this.processor.error(element, message, args2);
    }

    protected void info(String message, Object ... args2) {
        this.processor.info(message, args2);
    }

    protected void info(Element element, String message, Object ... args2) {
        this.processor.info(element, message, args2);
    }

    public static void read(Reader reader, Set<String> entries) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(reader);){
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                line = COMMENT.matcher(line).replaceFirst("");
                if ((line = WHITESPACE.matcher(line).replaceAll("")).length() <= 0) continue;
                entries.add(line);
            }
        }
    }
}

