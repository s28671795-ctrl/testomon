/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.plugins.extensions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.plugin.commons.plugins.extensions.VezhaExtensionLifecycleAction;

public record VezhaExtensionLifecycleEventDTO(@JsonProperty(value="action") VezhaExtensionLifecycleAction action) {
}

