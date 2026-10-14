/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.resource;

import java.io.IOException;
import org.codehaus.commons.compiler.util.resource.Resource;
import org.codehaus.commons.compiler.util.resource.ResourceFinder;
import org.codehaus.commons.nullanalysis.Nullable;

public abstract class ListableResourceFinder
extends ResourceFinder {
    @Nullable
    public abstract Iterable<Resource> list(String var1, boolean var2) throws IOException;
}

