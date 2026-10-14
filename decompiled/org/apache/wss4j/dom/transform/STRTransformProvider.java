/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.transform;

import java.security.Provider;

public class STRTransformProvider
extends Provider {
    private static final long serialVersionUID = -9148982936620100249L;

    public STRTransformProvider() {
        super("STRTransform", 2.2, "Security Token Reference Transform Provider");
        this.put("TransformService.http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform", "org.apache.wss4j.dom.transform.STRTransform");
        this.put("TransformService.http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform MechanismType", "DOM");
    }
}

