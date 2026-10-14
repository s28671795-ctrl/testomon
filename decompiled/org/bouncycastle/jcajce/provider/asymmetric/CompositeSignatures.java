/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.jcajce.provider.asymmetric;

import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.jcajce.provider.asymmetric.compositesignatures.CompositeSignaturesConstants;
import org.bouncycastle.jcajce.provider.asymmetric.compositesignatures.KeyFactorySpi;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;

public class CompositeSignatures {
    private static final String PREFIX = "org.bouncycastle.jcajce.provider.asymmetric.compositesignatures.";
    private static final Map<String, String> compositesAttributes = new HashMap<String, String>();

    static {
        compositesAttributes.put("SupportedKeyClasses", "org.bouncycastle.jcajce.CompositePublicKey|org.bouncycastle.jcajce.CompositePrivateKey");
        compositesAttributes.put("SupportedKeyFormats", "PKCS#8|X.509");
    }

    public static class Mappings
    extends AsymmetricAlgorithmProvider {
        @Override
        public void configure(ConfigurableProvider configurableProvider) {
            for (ASN1ObjectIdentifier aSN1ObjectIdentifier : CompositeSignaturesConstants.supportedIdentifiers) {
                CompositeSignaturesConstants.CompositeName compositeName = CompositeSignaturesConstants.ASN1IdentifierAlgorithmNameMap.get(aSN1ObjectIdentifier);
                configurableProvider.addAlgorithm("KeyFactory." + compositeName.getId(), "org.bouncycastle.jcajce.provider.asymmetric.compositesignatures.KeyFactorySpi");
                configurableProvider.addAlgorithm("Alg.Alias.KeyFactory", aSN1ObjectIdentifier, compositeName.getId());
                configurableProvider.addAlgorithm("KeyPairGenerator." + compositeName.getId(), "org.bouncycastle.jcajce.provider.asymmetric.compositesignatures.KeyPairGeneratorSpi$" + (Object)((Object)compositeName));
                configurableProvider.addAlgorithm("Alg.Alias.KeyPairGenerator", aSN1ObjectIdentifier, compositeName.getId());
                configurableProvider.addAlgorithm("Signature." + compositeName.getId(), "org.bouncycastle.jcajce.provider.asymmetric.compositesignatures.SignatureSpi$" + (Object)((Object)compositeName));
                configurableProvider.addAlgorithm("Alg.Alias.Signature", aSN1ObjectIdentifier, compositeName.getId());
                configurableProvider.addKeyInfoConverter(aSN1ObjectIdentifier, new KeyFactorySpi());
            }
        }
    }
}

