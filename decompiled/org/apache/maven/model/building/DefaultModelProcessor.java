/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.building;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Map;
import org.apache.maven.model.Model;
import org.apache.maven.model.building.ModelProcessor;
import org.apache.maven.model.io.ModelReader;
import org.apache.maven.model.locator.ModelLocator;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;

@Component(role=ModelProcessor.class)
public class DefaultModelProcessor
implements ModelProcessor {
    @Requirement
    private ModelLocator locator;
    @Requirement
    private ModelReader reader;

    public DefaultModelProcessor setModelLocator(ModelLocator locator) {
        this.locator = locator;
        return this;
    }

    public DefaultModelProcessor setModelReader(ModelReader reader) {
        this.reader = reader;
        return this;
    }

    @Override
    public File locatePom(File projectDirectory) {
        return this.locator.locatePom(projectDirectory);
    }

    @Override
    public Model read(File input, Map<String, ?> options) throws IOException {
        return this.reader.read(input, options);
    }

    @Override
    public Model read(Reader input, Map<String, ?> options) throws IOException {
        return this.reader.read(input, options);
    }

    @Override
    public Model read(InputStream input, Map<String, ?> options) throws IOException {
        return this.reader.read(input, options);
    }
}

