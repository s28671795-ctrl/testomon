/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.NotificationCommonConfiguration;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="CloudFunctionConfiguration", strict=false)
public class CloudFunctionConfiguration
extends NotificationCommonConfiguration {
    @Element(name="CloudFunction")
    private String cloudFunction;

    public String cloudFunction() {
        return this.cloudFunction;
    }

    public void setCloudFunction(String cloudFunction) {
        this.cloudFunction = cloudFunction;
    }
}

