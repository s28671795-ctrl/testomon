/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.dvcs;

import java.io.OutputStream;
import org.bouncycastle.asn1.x509.DigestInfo;
import org.bouncycastle.dvcs.DVCSException;
import org.bouncycastle.dvcs.MessageImprint;
import org.bouncycastle.operator.DigestCalculator;

public class MessageImprintBuilder {
    private final DigestCalculator digestCalculator;

    public MessageImprintBuilder(DigestCalculator digestCalculator) {
        this.digestCalculator = digestCalculator;
    }

    public MessageImprint build(byte[] byArray) throws DVCSException {
        try {
            OutputStream outputStream2 = this.digestCalculator.getOutputStream();
            outputStream2.write(byArray);
            outputStream2.close();
            return new MessageImprint(new DigestInfo(this.digestCalculator.getAlgorithmIdentifier(), this.digestCalculator.getDigest()));
        }
        catch (Exception exception) {
            throw new DVCSException("unable to build MessageImprint: " + exception.getMessage(), exception);
        }
    }
}

