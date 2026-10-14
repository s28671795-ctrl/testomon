/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics;

import com.incoresoft.middleware.analytics.metadata.AnalyticsToken;
import com.incoresoft.middleware.analytics.metadata.AnalyticsTopic;
import java.util.Objects;

public abstract class MetadataListener<T>
implements AnalyticsTopic {
    public abstract String pluginName();

    public abstract Class<T> metadataClass();

    public String getToken() {
        return this.metadataClass().getDeclaredAnnotation(AnalyticsToken.class).value();
    }

    public abstract void onMetadata(T var1);

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MetadataListener)) {
            return false;
        }
        MetadataListener otherListener = (MetadataListener)o;
        return this.getClass().getSimpleName().equals(otherListener.getClass().getSimpleName()) && this.analyticsTopic().equals(otherListener.analyticsTopic()) && this.pluginName().equals(otherListener.pluginName()) && this.getToken().equals(otherListener.getToken());
    }

    public int hashCode() {
        return Objects.hash(this.getClass().getSimpleName(), this.pluginName(), this.analyticsTopic(), this.getToken());
    }
}

