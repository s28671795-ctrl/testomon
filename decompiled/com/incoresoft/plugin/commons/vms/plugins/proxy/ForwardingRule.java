/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.vms.plugins.proxy;

import io.javalin.http.HandlerType;
import java.util.Set;

public record ForwardingRule(String matchedPath, String targetPath, Set<HandlerType> methods) {
}

