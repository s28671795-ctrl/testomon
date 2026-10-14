/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.common;

import org.eclipse.jetty.websocket.api.ExtensionConfig;
import org.eclipse.jetty.websocket.common.JettyExtensionConfig;
import org.eclipse.jetty.websocket.core.ExtensionConfig;

public class ExtensionConfigParser
implements ExtensionConfig.Parser {
    @Override
    public JettyExtensionConfig parse(String parameterizedName) {
        return new JettyExtensionConfig(ExtensionConfig.parse(parameterizedName));
    }
}

