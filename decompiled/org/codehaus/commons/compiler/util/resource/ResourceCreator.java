/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.resource;

import java.io.IOException;
import java.io.OutputStream;

public interface ResourceCreator {
    public OutputStream createResource(String var1) throws IOException;

    public boolean deleteResource(String var1);
}

