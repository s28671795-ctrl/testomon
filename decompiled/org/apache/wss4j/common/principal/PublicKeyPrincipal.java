/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.principal;

import java.security.Principal;
import java.security.PublicKey;

public interface PublicKeyPrincipal
extends Principal {
    public PublicKey getPublicKey();
}

