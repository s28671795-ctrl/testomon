/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import java.util.ResourceBundle;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.i18n.UncheckedException;
import org.apache.cxf.ws.policy.AssertionInfo;

public class PolicyException
extends UncheckedException {
    private static final long serialVersionUID = -6384955089085130084L;
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(PolicyException.class, "APIMessages");

    public PolicyException(Message msg, Throwable t2) {
        super(msg, t2);
    }

    public PolicyException(Message message) {
        super(message);
    }

    public PolicyException(Throwable cause) {
        super(cause);
    }

    public PolicyException(AssertionInfo info) {
        super(new Message("ASSERTION_NOT_ASSERTED", BUNDLE, info.getAssertion().getName(), info.getErrorMessage()));
    }
}

