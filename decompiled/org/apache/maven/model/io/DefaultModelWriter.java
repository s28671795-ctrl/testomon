/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.io;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Objects;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.ModelWriter;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.util.WriterFactory;

@Component(role=ModelWriter.class)
public class DefaultModelWriter
implements ModelWriter {
    @Override
    public void write(File output, Map<String, Object> options, Model model) throws IOException {
        Objects.requireNonNull(output, "output cannot be null");
        Objects.requireNonNull(model, "model cannot be null");
        output.getParentFile().mkdirs();
        this.write(WriterFactory.newXmlWriter(output), options, model);
    }

    @Override
    public void write(Writer output, Map<String, Object> options, Model model) throws IOException {
        Objects.requireNonNull(output, "output cannot be null");
        Objects.requireNonNull(model, "model cannot be null");
        try (Writer out = output;){
            new MavenXpp3Writer().write(out, model);
        }
    }

    @Override
    public void write(OutputStream output, Map<String, Object> options, Model model) throws IOException {
        Objects.requireNonNull(output, "output cannot be null");
        Objects.requireNonNull(model, "model cannot be null");
        String encoding = model.getModelEncoding();
        if (encoding == null || encoding.length() <= 0) {
            encoding = "UTF-8";
        }
        try (OutputStreamWriter out = new OutputStreamWriter(output, encoding);){
            this.write(out, options, model);
        }
    }
}

