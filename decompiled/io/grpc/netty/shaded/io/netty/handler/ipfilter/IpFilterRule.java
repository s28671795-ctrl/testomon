/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.ipfilter;

import io.grpc.netty.shaded.io.netty.handler.ipfilter.IpFilterRuleType;
import java.net.InetSocketAddress;

public interface IpFilterRule {
    public boolean matches(InetSocketAddress var1);

    public IpFilterRuleType ruleType();
}

