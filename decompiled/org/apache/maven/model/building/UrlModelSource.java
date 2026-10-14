/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.building;

import java.net.URL;
import org.apache.maven.building.UrlSource;
import org.apache.maven.model.building.ModelSource;

@Deprecated
public class UrlModelSource
extends UrlSource
implements ModelSource {
    public UrlModelSource(URL pomUrl) {
        super(pomUrl);
    }
}

