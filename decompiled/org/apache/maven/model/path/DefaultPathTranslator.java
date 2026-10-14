/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.path;

import java.io.File;
import org.apache.maven.model.path.PathTranslator;
import org.codehaus.plexus.component.annotations.Component;

@Component(role=PathTranslator.class)
public class DefaultPathTranslator
implements PathTranslator {
    @Override
    public String alignToBaseDirectory(String path, File basedir) {
        String result = path;
        if (path != null && basedir != null) {
            File file = new File(path = path.replace('\\', File.separatorChar).replace('/', File.separatorChar));
            result = file.isAbsolute() ? file.getPath() : (file.getPath().startsWith(File.separator) ? file.getAbsolutePath() : new File(new File(basedir, path).toURI().normalize()).getAbsolutePath());
        }
        return result;
    }
}

