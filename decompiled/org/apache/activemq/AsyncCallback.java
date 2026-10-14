/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import javax.jms.ExceptionListener;

public interface AsyncCallback
extends ExceptionListener {
    public void onSuccess();
}

