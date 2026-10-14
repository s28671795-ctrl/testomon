/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation;

import java.util.ArrayList;
import java.util.List;
import org.codehaus.plexus.interpolation.ValueSource;

public abstract class AbstractValueSource
implements ValueSource {
    private final List feedback;

    protected AbstractValueSource(boolean usesFeedback) {
        this.feedback = usesFeedback ? new ArrayList() : null;
    }

    @Override
    public void clearFeedback() {
        if (this.feedback != null) {
            this.feedback.clear();
        }
    }

    @Override
    public List getFeedback() {
        return this.feedback;
    }

    protected void addFeedback(String message) {
        this.feedback.add(message);
    }

    protected void addFeedback(String message, Throwable cause) {
        this.feedback.add(message);
        this.feedback.add(cause);
    }
}

