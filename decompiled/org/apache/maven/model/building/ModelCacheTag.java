/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.building;

import org.apache.maven.model.DependencyManagement;
import org.apache.maven.model.Model;
import org.apache.maven.model.building.ModelData;

interface ModelCacheTag<T> {
    public static final ModelCacheTag<ModelData> RAW = new ModelCacheTag<ModelData>(){

        @Override
        public String getName() {
            return "raw";
        }

        @Override
        public Class<ModelData> getType() {
            return ModelData.class;
        }

        @Override
        public ModelData intoCache(ModelData data) {
            Model model = data.getModel() != null ? data.getModel().clone() : null;
            return new ModelData(data.getSource(), model, data.getGroupId(), data.getArtifactId(), data.getVersion());
        }

        @Override
        public ModelData fromCache(ModelData data) {
            return this.intoCache(data);
        }
    };
    public static final ModelCacheTag<DependencyManagement> IMPORT = new ModelCacheTag<DependencyManagement>(){

        @Override
        public String getName() {
            return "import";
        }

        @Override
        public Class<DependencyManagement> getType() {
            return DependencyManagement.class;
        }

        @Override
        public DependencyManagement intoCache(DependencyManagement data) {
            return data != null ? data.clone() : null;
        }

        @Override
        public DependencyManagement fromCache(DependencyManagement data) {
            return this.intoCache(data);
        }
    };

    public String getName();

    public Class<T> getType();

    public T intoCache(T var1);

    public T fromCache(T var1);
}

