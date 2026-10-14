/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class SecurityFeature
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _SecNoDelegation = 0;
    public static final SecurityFeature SecNoDelegation = new SecurityFeature(0);
    public static final int _SecSimpleDelegation = 1;
    public static final SecurityFeature SecSimpleDelegation = new SecurityFeature(1);
    public static final int _SecCompositeDelegation = 2;
    public static final SecurityFeature SecCompositeDelegation = new SecurityFeature(2);
    public static final int _SecNoProtection = 3;
    public static final SecurityFeature SecNoProtection = new SecurityFeature(3);
    public static final int _SecIntegrity = 4;
    public static final SecurityFeature SecIntegrity = new SecurityFeature(4);
    public static final int _SecConfidentiality = 5;
    public static final SecurityFeature SecConfidentiality = new SecurityFeature(5);
    public static final int _SecIntegrityAndConfidentiality = 6;
    public static final SecurityFeature SecIntegrityAndConfidentiality = new SecurityFeature(6);
    public static final int _SecDetectReplay = 7;
    public static final SecurityFeature SecDetectReplay = new SecurityFeature(7);
    public static final int _SecDetectMisordering = 8;
    public static final SecurityFeature SecDetectMisordering = new SecurityFeature(8);
    public static final int _SecEstablishTrustInTarget = 9;
    public static final SecurityFeature SecEstablishTrustInTarget = new SecurityFeature(9);
    public static final int _SecEstablishTrustInClient = 10;
    public static final SecurityFeature SecEstablishTrustInClient = new SecurityFeature(10);

    public int value() {
        return this.value;
    }

    public static SecurityFeature from_int(int value) {
        switch (value) {
            case 0: {
                return SecNoDelegation;
            }
            case 1: {
                return SecSimpleDelegation;
            }
            case 2: {
                return SecCompositeDelegation;
            }
            case 3: {
                return SecNoProtection;
            }
            case 4: {
                return SecIntegrity;
            }
            case 5: {
                return SecConfidentiality;
            }
            case 6: {
                return SecIntegrityAndConfidentiality;
            }
            case 7: {
                return SecDetectReplay;
            }
            case 8: {
                return SecDetectMisordering;
            }
            case 9: {
                return SecEstablishTrustInTarget;
            }
            case 10: {
                return SecEstablishTrustInClient;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "SecNoDelegation";
            }
            case 1: {
                return "SecSimpleDelegation";
            }
            case 2: {
                return "SecCompositeDelegation";
            }
            case 3: {
                return "SecNoProtection";
            }
            case 4: {
                return "SecIntegrity";
            }
            case 5: {
                return "SecConfidentiality";
            }
            case 6: {
                return "SecIntegrityAndConfidentiality";
            }
            case 7: {
                return "SecDetectReplay";
            }
            case 8: {
                return "SecDetectMisordering";
            }
            case 9: {
                return "SecEstablishTrustInTarget";
            }
            case 10: {
                return "SecEstablishTrustInClient";
            }
        }
        throw new BAD_PARAM();
    }

    protected SecurityFeature(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return SecurityFeature.from_int(this.value());
    }
}

