/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy;

import javax.xml.namespace.QName;
import org.apache.wss4j.policy.SP12Constants;

public class SP13Constants
extends SP12Constants {
    private static SP13Constants sp13Constants;
    public static final String SP_NS = "http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200802";
    public static final String SP_PREFIX = "sp13";
    public static final QName CONTENT_SIGNATURE_TRANSFORM;
    public static final QName ATTACHMENT_COMPLETE_SIGNATURE_TRANSFORM;
    public static final QName XPATH2_EXPR;
    public static final QName CREATED;
    public static final QName NONCE;
    public static final QName SCOPE_POLICY_15;
    public static final QName MUST_SUPPORT_INTERACTIVE_CHALLENGE;

    protected SP13Constants() {
    }

    public static synchronized SP13Constants getInstance() {
        if (sp13Constants == null) {
            sp13Constants = new SP13Constants();
        }
        return sp13Constants;
    }

    @Override
    public QName getContentSignatureTransform() {
        return CONTENT_SIGNATURE_TRANSFORM;
    }

    @Override
    public QName getAttachmentCompleteSignatureTransform() {
        return ATTACHMENT_COMPLETE_SIGNATURE_TRANSFORM;
    }

    @Override
    public QName getXPath2Expression() {
        return XPATH2_EXPR;
    }

    @Override
    public QName getCreated() {
        return CREATED;
    }

    @Override
    public QName getNonce() {
        return NONCE;
    }

    @Override
    public QName getScopePolicy15() {
        return SCOPE_POLICY_15;
    }

    @Override
    public QName getMustSupportInteractiveChallenge() {
        return MUST_SUPPORT_INTERACTIVE_CHALLENGE;
    }

    static {
        CONTENT_SIGNATURE_TRANSFORM = new QName(SP_NS, "ContentSignatureTransform", SP_PREFIX);
        ATTACHMENT_COMPLETE_SIGNATURE_TRANSFORM = new QName(SP_NS, "AttachmentCompleteSignatureTransform", SP_PREFIX);
        XPATH2_EXPR = new QName(SP_NS, "Xpath2", SP_PREFIX);
        CREATED = new QName(SP_NS, "Created", SP_PREFIX);
        NONCE = new QName(SP_NS, "Nonce", SP_PREFIX);
        SCOPE_POLICY_15 = new QName(SP_NS, "ScopePolicy15", SP_PREFIX);
        MUST_SUPPORT_INTERACTIVE_CHALLENGE = new QName(SP_NS, "MustSupportInteractiveChallenge", SP_PREFIX);
    }
}

