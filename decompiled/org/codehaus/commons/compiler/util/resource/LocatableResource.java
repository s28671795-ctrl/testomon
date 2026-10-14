/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.resource;

import java.io.IOException;
import java.net.URL;
import org.codehaus.commons.compiler.util.resource.Resource;

public interface LocatableResource
extends Resource {
    public URL getLocation() throws IOException;
}

