/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.webapp;

import org.eclipse.jetty.webapp.Descriptor;
import org.eclipse.jetty.webapp.WebAppContext;

public interface DescriptorProcessor {
    public void process(WebAppContext var1, Descriptor var2) throws Exception;
}

