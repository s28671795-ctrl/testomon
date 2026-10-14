/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.path;

import java.io.File;
import org.apache.maven.model.Model;
import org.apache.maven.model.building.ModelBuildingRequest;

public interface ModelPathTranslator {
    public void alignToBaseDirectory(Model var1, File var2, ModelBuildingRequest var3);
}

