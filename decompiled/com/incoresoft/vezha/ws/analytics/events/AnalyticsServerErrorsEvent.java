/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.ws.analytics.events;

import com.incoresoft.middleware.analytics.dto.analytics.AnalyticsErrorDTO;
import com.incoresoft.vezha.analytics.BaseAnalytics;
import com.incoresoft.vezha.ws.analytics.events.AnalyticsEventType;
import java.util.List;

public class AnalyticsServerErrorsEvent {
    private int id;
    private List<AnalyticsErrorDTO> errors;

    public AnalyticsServerErrorsEvent(BaseAnalytics analytics) {
        this.id = analytics.getId();
        this.errors = analytics.getErrors().stream().map(AnalyticsErrorDTO::new).toList();
    }

    public String getType() {
        return AnalyticsEventType.ANALYTICS_SERVER_ERROR.toString();
    }

    public int getId() {
        return this.id;
    }

    public List<AnalyticsErrorDTO> getErrors() {
        return this.errors;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setErrors(List<AnalyticsErrorDTO> errors2) {
        this.errors = errors2;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AnalyticsServerErrorsEvent)) {
            return false;
        }
        AnalyticsServerErrorsEvent other = (AnalyticsServerErrorsEvent)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getId() != other.getId()) {
            return false;
        }
        List<AnalyticsErrorDTO> this$errors = this.getErrors();
        List<AnalyticsErrorDTO> other$errors = other.getErrors();
        return !(this$errors == null ? other$errors != null : !((Object)this$errors).equals(other$errors));
    }

    protected boolean canEqual(Object other) {
        return other instanceof AnalyticsServerErrorsEvent;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getId();
        List<AnalyticsErrorDTO> $errors = this.getErrors();
        result = result * 59 + ($errors == null ? 43 : ((Object)$errors).hashCode());
        return result;
    }

    public String toString() {
        return "AnalyticsServerErrorsEvent(id=" + this.getId() + ", errors=" + String.valueOf(this.getErrors()) + ")";
    }

    public AnalyticsServerErrorsEvent() {
    }
}

