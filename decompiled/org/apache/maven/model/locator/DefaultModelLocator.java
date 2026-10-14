/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.locator;

import java.io.File;
import org.apache.maven.model.locator.ModelLocator;
import org.codehaus.plexus.component.annotations.Component;

@Component(role=ModelLocator.class)
public class DefaultModelLocator
implements ModelLocator {
    @Override
    public File locatePom(File projectDirectory) {
        return new File(projectDirectory, "pom.xml");
    }
}

