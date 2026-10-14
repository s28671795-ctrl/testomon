/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.building;

import java.io.File;
import org.apache.maven.model.Model;
import org.apache.maven.model.building.ModelBuildingException;
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.building.ModelBuildingResult;
import org.apache.maven.model.building.Result;

public interface ModelBuilder {
    public ModelBuildingResult build(ModelBuildingRequest var1) throws ModelBuildingException;

    public ModelBuildingResult build(ModelBuildingRequest var1, ModelBuildingResult var2) throws ModelBuildingException;

    public Result<? extends Model> buildRawModel(File var1, int var2, boolean var3);
}

