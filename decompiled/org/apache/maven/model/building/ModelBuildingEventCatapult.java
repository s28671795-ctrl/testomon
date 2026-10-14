/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.building;

import org.apache.maven.model.building.ModelBuildingEvent;
import org.apache.maven.model.building.ModelBuildingListener;

interface ModelBuildingEventCatapult {
    public static final ModelBuildingEventCatapult BUILD_EXTENSIONS_ASSEMBLED = new ModelBuildingEventCatapult(){

        @Override
        public void fire(ModelBuildingListener listener, ModelBuildingEvent event) {
            listener.buildExtensionsAssembled(event);
        }
    };

    public void fire(ModelBuildingListener var1, ModelBuildingEvent var2);
}

