/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.building;

import java.net.URI;
import org.apache.maven.model.building.ModelSource;

public interface ModelSource2
extends ModelSource {
    public ModelSource2 getRelatedSource(String var1);

    public URI getLocationURI();
}

