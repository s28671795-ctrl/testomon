/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.credential;

import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.UsageType;

public interface MutableCredential
extends Credential {
    public void setEntityId(String var1);

    public void setUsageType(UsageType var1);

    public void setPublicKey(PublicKey var1);

    public void setPrivateKey(PrivateKey var1);

    public void setSecretKey(SecretKey var1);
}

