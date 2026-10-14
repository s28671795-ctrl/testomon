/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.model;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.neethi.Assertion;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyComponent;
import org.apache.neethi.PolicyContainingAssertion;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;

public class AlgorithmSuite
extends AbstractSecurityAssertion
implements PolicyContainingAssertion {
    protected static final Map<String, AlgorithmSuiteType> ALGORITHM_SUITE_TYPES = new HashMap<String, AlgorithmSuiteType>();
    private static final int MAX_SKL = 256;
    private static final int MIN_AKL = 1024;
    private static final int MAX_AKL = 4096;
    private Policy nestedPolicy;
    private AlgorithmSuiteType algorithmSuiteType;
    private C14NType c14n = C14NType.ExclusiveC14N;
    private SOAPNormType soapNormType = SOAPNormType.SOAPNormalizationNone;
    private STRType strType = STRType.STRTransformNone;
    private XPathType xPathType = XPathType.XPathNone;
    private String symmetricSignature = "http://www.w3.org/2000/09/xmldsig#hmac-sha1";
    private String asymmetricSignature = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
    private String computedKey = "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1";
    private String firstInvalidAlgorithmSuite;

    public AlgorithmSuite(SPConstants.SPVersion version, Policy nestedPolicy) {
        super(version);
        this.nestedPolicy = nestedPolicy;
        this.parseNestedPolicy(nestedPolicy, this);
    }

    @Override
    public Policy getPolicy() {
        return this.nestedPolicy;
    }

    @Override
    public QName getName() {
        return this.getVersion().getSPConstants().getAlgorithmSuite();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof AlgorithmSuite)) {
            return false;
        }
        AlgorithmSuite that = (AlgorithmSuite)object;
        if (this.c14n != that.c14n || this.soapNormType != that.soapNormType || this.strType != that.strType || this.xPathType != that.xPathType) {
            return false;
        }
        if (this.algorithmSuiteType != null && !this.algorithmSuiteType.equals(that.algorithmSuiteType) || this.algorithmSuiteType == null && that.algorithmSuiteType != null) {
            return false;
        }
        if (this.symmetricSignature != null && !this.symmetricSignature.equals(that.symmetricSignature) || this.symmetricSignature == null && that.symmetricSignature != null) {
            return false;
        }
        if (this.asymmetricSignature != null && !this.asymmetricSignature.equals(that.asymmetricSignature) || this.asymmetricSignature == null && that.asymmetricSignature != null) {
            return false;
        }
        if (this.computedKey != null && !this.computedKey.equals(that.computedKey) || this.computedKey == null && that.computedKey != null) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        if (this.c14n != null) {
            result = 31 * result + this.c14n.hashCode();
        }
        if (this.soapNormType != null) {
            result = 31 * result + this.soapNormType.hashCode();
        }
        if (this.strType != null) {
            result = 31 * result + this.strType.hashCode();
        }
        if (this.xPathType != null) {
            result = 31 * result + this.xPathType.hashCode();
        }
        if (this.algorithmSuiteType != null) {
            result = 31 * result + this.algorithmSuiteType.hashCode();
        }
        if (this.symmetricSignature != null) {
            result = 31 * result + this.symmetricSignature.hashCode();
        }
        if (this.asymmetricSignature != null) {
            result = 31 * result + this.asymmetricSignature.hashCode();
        }
        if (this.computedKey != null) {
            result = 31 * result + this.computedKey.hashCode();
        }
        return 31 * result + super.hashCode();
    }

    @Override
    public PolicyComponent normalize() {
        return super.normalize(this.getPolicy());
    }

    @Override
    public void serialize(XMLStreamWriter writer) throws XMLStreamException {
        super.serialize(writer, this.getPolicy());
    }

    @Override
    protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
        return new AlgorithmSuite(this.getVersion(), nestedPolicy);
    }

    protected void parseNestedPolicy(Policy nestedPolicy, AlgorithmSuite algorithmSuite) {
        Iterator<List<Assertion>> alternatives = nestedPolicy.getAlternatives();
        if (alternatives.hasNext()) {
            List<Assertion> assertions = alternatives.next();
            for (Assertion assertion : assertions) {
                String assertionName = assertion.getName().getLocalPart();
                String assertionNamespace = assertion.getName().getNamespaceURI();
                if (!this.getVersion().getNamespace().equals(assertionNamespace)) {
                    this.parseCustomAssertion(assertion);
                    continue;
                }
                AlgorithmSuiteType algorithmSuiteType = ALGORITHM_SUITE_TYPES.get(assertionName);
                if (algorithmSuiteType != null) {
                    if (algorithmSuite.getAlgorithmSuiteType() != null) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    AlgorithmSuiteType newAlgorithmSuiteType = new AlgorithmSuiteType(algorithmSuiteType);
                    newAlgorithmSuiteType.setNamespace(this.getVersion().getNamespace());
                    algorithmSuite.setAlgorithmSuiteType(newAlgorithmSuiteType);
                    continue;
                }
                this.firstInvalidAlgorithmSuite = assertionName;
                C14NType c14NType = C14NType.lookUp(assertionName);
                if (c14NType != null) {
                    algorithmSuite.setC14n(c14NType);
                    continue;
                }
                SOAPNormType soapNormType = SOAPNormType.lookUp(assertionName);
                if (soapNormType != null) {
                    if (algorithmSuite.getSoapNormType() == SOAPNormType.SOAPNormalization10) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    algorithmSuite.setSoapNormType(soapNormType);
                    continue;
                }
                STRType strType = STRType.lookUp(assertionName);
                if (strType != null) {
                    if (algorithmSuite.getStrType() == STRType.STRTransform10) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    algorithmSuite.setStrType(strType);
                    continue;
                }
                XPathType xPathType = XPathType.lookUp(assertionName);
                if (xPathType == null) continue;
                if (algorithmSuite.getXPathType() != XPathType.XPathNone) {
                    throw new IllegalArgumentException("Invalid Policy");
                }
                algorithmSuite.setXPathType(xPathType);
            }
        }
    }

    protected void parseCustomAssertion(Assertion assertion) {
    }

    public AlgorithmSuiteType getAlgorithmSuiteType() {
        return this.algorithmSuiteType;
    }

    protected void setAlgorithmSuiteType(AlgorithmSuiteType algorithmSuiteType) {
        this.algorithmSuiteType = algorithmSuiteType;
    }

    public C14NType getC14n() {
        return this.c14n;
    }

    protected void setC14n(C14NType c14n) {
        this.c14n = c14n;
    }

    public SOAPNormType getSoapNormType() {
        return this.soapNormType;
    }

    protected void setSoapNormType(SOAPNormType soapNormType) {
        this.soapNormType = soapNormType;
    }

    public STRType getStrType() {
        return this.strType;
    }

    protected void setStrType(STRType strType) {
        this.strType = strType;
    }

    public XPathType getXPathType() {
        return this.xPathType;
    }

    protected void setXPathType(XPathType xPathType) {
        this.xPathType = xPathType;
    }

    public String getAsymmetricSignature() {
        return this.asymmetricSignature;
    }

    public String getSymmetricSignature() {
        return this.symmetricSignature;
    }

    public String getComputedKey() {
        return this.computedKey;
    }

    public static Collection<String> getSupportedAlgorithmSuiteNames() {
        return ALGORITHM_SUITE_TYPES.keySet();
    }

    public void setSymmetricSignature(String symmetricSignature) {
        this.symmetricSignature = symmetricSignature;
    }

    public void setAsymmetricSignature(String asymmetricSignature) {
        this.asymmetricSignature = asymmetricSignature;
    }

    public String getFirstInvalidAlgorithmSuite() {
        return this.firstInvalidAlgorithmSuite;
    }

    static {
        ALGORITHM_SUITE_TYPES.put("Basic256", new AlgorithmSuiteType("Basic256", "http://www.w3.org/2000/09/xmldsig#sha1", "http://www.w3.org/2001/04/xmlenc#aes256-cbc", "http://www.w3.org/2001/04/xmlenc#kw-aes256", "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 256, 192, 256, 256, 1024, 4096));
        ALGORITHM_SUITE_TYPES.put("Basic192", new AlgorithmSuiteType("Basic192", "http://www.w3.org/2000/09/xmldsig#sha1", "http://www.w3.org/2001/04/xmlenc#aes192-cbc", "http://www.w3.org/2001/04/xmlenc#kw-aes192", "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 192, 192, 192, 256, 1024, 4096));
        ALGORITHM_SUITE_TYPES.put("Basic128", new AlgorithmSuiteType("Basic128", "http://www.w3.org/2000/09/xmldsig#sha1", "http://www.w3.org/2001/04/xmlenc#aes128-cbc", "http://www.w3.org/2001/04/xmlenc#kw-aes128", "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 128, 128, 128, 256, 1024, 4096));
        ALGORITHM_SUITE_TYPES.put("TripleDes", new AlgorithmSuiteType("TripleDes", "http://www.w3.org/2000/09/xmldsig#sha1", "http://www.w3.org/2001/04/xmlenc#tripledes-cbc", "http://www.w3.org/2001/04/xmlenc#kw-tripledes", "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 192, 192, 192, 256, 1024, 4096));
        ALGORITHM_SUITE_TYPES.put("Basic256Rsa15", new AlgorithmSuiteType("Basic256Rsa15", "http://www.w3.org/2000/09/xmldsig#sha1", "http://www.w3.org/2001/04/xmlenc#aes256-cbc", "http://www.w3.org/2001/04/xmlenc#kw-aes256", "http://www.w3.org/2001/04/xmlenc#rsa-1_5", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 256, 192, 256, 256, 1024, 4096));
        ALGORITHM_SUITE_TYPES.put("Basic192Rsa15", new AlgorithmSuiteType("Basic192Rsa15", "http://www.w3.org/2000/09/xmldsig#sha1", "http://www.w3.org/2001/04/xmlenc#aes192-cbc", "http://www.w3.org/2001/04/xmlenc#kw-aes192", "http://www.w3.org/2001/04/xmlenc#rsa-1_5", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 192, 192, 192, 256, 1024, 4096));
        ALGORITHM_SUITE_TYPES.put("Basic128Rsa15", new AlgorithmSuiteType("Basic128Rsa15", "http://www.w3.org/2000/09/xmldsig#sha1", "http://www.w3.org/2001/04/xmlenc#aes128-cbc", "http://www.w3.org/2001/04/xmlenc#kw-aes128", "http://www.w3.org/2001/04/xmlenc#rsa-1_5", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 128, 128, 128, 256, 1024, 4096));
        ALGORITHM_SUITE_TYPES.put("TripleDesRsa15", new AlgorithmSuiteType("TripleDesRsa15", "http://www.w3.org/2000/09/xmldsig#sha1", "http://www.w3.org/2001/04/xmlenc#tripledes-cbc", "http://www.w3.org/2001/04/xmlenc#kw-tripledes", "http://www.w3.org/2001/04/xmlenc#rsa-1_5", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 192, 192, 192, 256, 1024, 4096));
        ALGORITHM_SUITE_TYPES.put("Basic256Sha256", new AlgorithmSuiteType("Basic256Sha256", "http://www.w3.org/2001/04/xmlenc#sha256", "http://www.w3.org/2001/04/xmlenc#aes256-cbc", "http://www.w3.org/2001/04/xmlenc#kw-aes256", "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 256, 192, 256, 256, 1024, 4096));
        ALGORITHM_SUITE_TYPES.put("Basic192Sha256", new AlgorithmSuiteType("Basic192Sha256", "http://www.w3.org/2001/04/xmlenc#sha256", "http://www.w3.org/2001/04/xmlenc#aes192-cbc", "http://www.w3.org/2001/04/xmlenc#kw-aes192", "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 192, 192, 192, 256, 1024, 4096));
        ALGORITHM_SUITE_TYPES.put("Basic128Sha256", new AlgorithmSuiteType("Basic128Sha256", "http://www.w3.org/2001/04/xmlenc#sha256", "http://www.w3.org/2001/04/xmlenc#aes128-cbc", "http://www.w3.org/2001/04/xmlenc#kw-aes128", "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 128, 128, 128, 256, 1024, 4096));
        ALGORITHM_SUITE_TYPES.put("TripleDesSha256", new AlgorithmSuiteType("TripleDesSha256", "http://www.w3.org/2001/04/xmlenc#sha256", "http://www.w3.org/2001/04/xmlenc#tripledes-cbc", "http://www.w3.org/2001/04/xmlenc#kw-tripledes", "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 192, 192, 192, 256, 1024, 4096));
        ALGORITHM_SUITE_TYPES.put("Basic256Sha256Rsa15", new AlgorithmSuiteType("Basic256Sha256Rsa15", "http://www.w3.org/2001/04/xmlenc#sha256", "http://www.w3.org/2001/04/xmlenc#aes256-cbc", "http://www.w3.org/2001/04/xmlenc#kw-aes256", "http://www.w3.org/2001/04/xmlenc#rsa-1_5", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 256, 192, 256, 256, 1024, 4096));
        ALGORITHM_SUITE_TYPES.put("Basic192Sha256Rsa15", new AlgorithmSuiteType("Basic192Sha256Rsa15", "http://www.w3.org/2001/04/xmlenc#sha256", "http://www.w3.org/2001/04/xmlenc#aes192-cbc", "http://www.w3.org/2001/04/xmlenc#kw-aes192", "http://www.w3.org/2001/04/xmlenc#rsa-1_5", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 192, 192, 192, 256, 1024, 4096));
        ALGORITHM_SUITE_TYPES.put("Basic128Sha256Rsa15", new AlgorithmSuiteType("Basic128Sha256Rsa15", "http://www.w3.org/2001/04/xmlenc#sha256", "http://www.w3.org/2001/04/xmlenc#aes128-cbc", "http://www.w3.org/2001/04/xmlenc#kw-aes128", "http://www.w3.org/2001/04/xmlenc#rsa-1_5", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 128, 128, 128, 256, 1024, 4096));
        ALGORITHM_SUITE_TYPES.put("TripleDesSha256Rsa15", new AlgorithmSuiteType("TripleDesSha256Rsa15", "http://www.w3.org/2001/04/xmlenc#sha256", "http://www.w3.org/2001/04/xmlenc#tripledes-cbc", "http://www.w3.org/2001/04/xmlenc#kw-tripledes", "http://www.w3.org/2001/04/xmlenc#rsa-1_5", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 192, 192, 192, 256, 1024, 4096));
    }

    public static enum STRType {
        STRTransformNone(null),
        STRTransform10("STRTransform10");

        private static final Map<String, STRType> LOOKUP;
        private String value;

        public static STRType lookUp(String name) {
            return LOOKUP.get(name);
        }

        public String getValue() {
            return this.value;
        }

        private STRType(String value) {
            this.value = value;
        }

        static {
            LOOKUP = new HashMap<String, STRType>();
            for (STRType u : EnumSet.allOf(STRType.class)) {
                LOOKUP.put(u.name(), u);
            }
        }
    }

    public static enum SOAPNormType {
        SOAPNormalizationNone(null),
        SOAPNormalization10("SoapNormalization10");

        private static final Map<String, SOAPNormType> LOOKUP;
        private String value;

        public static SOAPNormType lookUp(String name) {
            return LOOKUP.get(name);
        }

        public String getValue() {
            return this.value;
        }

        private SOAPNormType(String value) {
            this.value = value;
        }

        static {
            LOOKUP = new HashMap<String, SOAPNormType>();
            for (SOAPNormType u : EnumSet.allOf(SOAPNormType.class)) {
                LOOKUP.put(u.name(), u);
            }
        }
    }

    public static enum C14NType {
        ExclusiveC14N("http://www.w3.org/2001/10/xml-exc-c14n#"),
        InclusiveC14N("http://www.w3.org/TR/2001/REC-xml-c14n-20010315"),
        InclusiveC14N11("http://www.w3.org/2006/12/xml-c14n11");

        private static final Map<String, C14NType> LOOKUP;
        private String value;

        public static C14NType lookUp(String name) {
            return LOOKUP.get(name);
        }

        public String getValue() {
            return this.value;
        }

        private C14NType(String value) {
            this.value = value;
        }

        static {
            LOOKUP = new HashMap<String, C14NType>();
            for (C14NType u : EnumSet.allOf(C14NType.class)) {
                LOOKUP.put(u.name(), u);
            }
        }
    }

    public static enum XPathType {
        XPathNone(null),
        XPath10("http://www.w3.org/TR/1999/REC-xpath-19991116"),
        XPathFilter20("http://www.w3.org/2002/06/xmldsig-filter2"),
        AbsXPath("http://docs.oasis-open.org/...TBD.../AbsXPath");

        private static final Map<String, XPathType> LOOKUP;
        private String value;

        public static XPathType lookUp(String name) {
            return LOOKUP.get(name);
        }

        public String getValue() {
            return this.value;
        }

        private XPathType(String value) {
            this.value = value;
        }

        static {
            LOOKUP = new HashMap<String, XPathType>();
            for (XPathType u : EnumSet.allOf(XPathType.class)) {
                LOOKUP.put(u.name(), u);
            }
        }
    }

    public static final class AlgorithmSuiteType {
        private String name;
        private String digest;
        private String encryption;
        private String symmetricKeyWrap;
        private String asymmetricKeyWrap;
        private String encryptionKeyDerivation;
        private String signatureKeyDerivation;
        private int encryptionDerivedKeyLength;
        private int signatureDerivedKeyLength;
        private int minimumSymmetricKeyLength;
        private int maximumSymmetricKeyLength;
        private int minimumAsymmetricKeyLength;
        private int maximumAsymmetricKeyLength;
        private String mgfAlgo;
        private String ns;
        private String encryptionDigest;

        public AlgorithmSuiteType(String name, String digest, String encryption, String symmetricKeyWrap, String asymmetricKeyWrap, String encryptionKeyDerivation, String signatureKeyDerivation, int encryptionDerivedKeyLength, int signatureDerivedKeyLength, int minimumSymmetricKeyLength, int maximumSymmetricKeyLength, int minimumAsymmetricKeyLength, int maximumAsymmetricKeyLength) {
            this.name = name;
            this.digest = digest;
            this.encryption = encryption;
            this.symmetricKeyWrap = symmetricKeyWrap;
            this.asymmetricKeyWrap = asymmetricKeyWrap;
            this.encryptionKeyDerivation = encryptionKeyDerivation;
            this.signatureKeyDerivation = signatureKeyDerivation;
            this.encryptionDerivedKeyLength = encryptionDerivedKeyLength;
            this.signatureDerivedKeyLength = signatureDerivedKeyLength;
            this.minimumSymmetricKeyLength = minimumSymmetricKeyLength;
            this.maximumSymmetricKeyLength = maximumSymmetricKeyLength;
            this.minimumAsymmetricKeyLength = minimumAsymmetricKeyLength;
            this.maximumAsymmetricKeyLength = maximumAsymmetricKeyLength;
        }

        public AlgorithmSuiteType(AlgorithmSuiteType algorithmSuiteType) {
            this.name = algorithmSuiteType.name;
            this.digest = algorithmSuiteType.digest;
            this.encryption = algorithmSuiteType.encryption;
            this.symmetricKeyWrap = algorithmSuiteType.symmetricKeyWrap;
            this.asymmetricKeyWrap = algorithmSuiteType.asymmetricKeyWrap;
            this.encryptionKeyDerivation = algorithmSuiteType.encryptionKeyDerivation;
            this.signatureKeyDerivation = algorithmSuiteType.signatureKeyDerivation;
            this.encryptionDerivedKeyLength = algorithmSuiteType.encryptionDerivedKeyLength;
            this.signatureDerivedKeyLength = algorithmSuiteType.signatureDerivedKeyLength;
            this.minimumSymmetricKeyLength = algorithmSuiteType.minimumSymmetricKeyLength;
            this.maximumSymmetricKeyLength = algorithmSuiteType.maximumSymmetricKeyLength;
            this.minimumAsymmetricKeyLength = algorithmSuiteType.minimumAsymmetricKeyLength;
            this.maximumAsymmetricKeyLength = algorithmSuiteType.maximumAsymmetricKeyLength;
            this.mgfAlgo = algorithmSuiteType.mgfAlgo;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof AlgorithmSuiteType)) {
                return false;
            }
            AlgorithmSuiteType that = (AlgorithmSuiteType)object;
            if (this.name != null && !this.name.equals(that.name) || this.name == null && that.name != null) {
                return false;
            }
            if (this.digest != null && !this.digest.equals(that.digest) || this.digest == null && that.digest != null) {
                return false;
            }
            if (this.encryption != null && !this.encryption.equals(that.encryption) || this.encryption == null && that.encryption != null) {
                return false;
            }
            if (this.symmetricKeyWrap != null && !this.symmetricKeyWrap.equals(that.symmetricKeyWrap) || this.symmetricKeyWrap == null && that.symmetricKeyWrap != null) {
                return false;
            }
            if (this.asymmetricKeyWrap != null && !this.asymmetricKeyWrap.equals(that.asymmetricKeyWrap) || this.asymmetricKeyWrap == null && that.asymmetricKeyWrap != null) {
                return false;
            }
            if (this.encryptionKeyDerivation != null && !this.encryptionKeyDerivation.equals(that.encryptionKeyDerivation) || this.encryptionKeyDerivation == null && that.encryptionKeyDerivation != null) {
                return false;
            }
            if (this.signatureKeyDerivation != null && !this.signatureKeyDerivation.equals(that.signatureKeyDerivation) || this.signatureKeyDerivation == null && that.signatureKeyDerivation != null) {
                return false;
            }
            if (this.ns != null && !this.ns.equals(that.ns) || this.ns == null && that.ns != null) {
                return false;
            }
            if (this.mgfAlgo != null && !this.mgfAlgo.equals(that.mgfAlgo) || this.mgfAlgo == null && that.mgfAlgo != null) {
                return false;
            }
            if (this.encryptionDigest != null && !this.encryptionDigest.equals(that.encryptionDigest) || this.encryptionDigest == null && that.encryptionDigest != null) {
                return false;
            }
            return this.encryptionDerivedKeyLength == that.encryptionDerivedKeyLength && this.signatureDerivedKeyLength == that.signatureDerivedKeyLength && this.minimumSymmetricKeyLength == that.minimumSymmetricKeyLength && this.maximumSymmetricKeyLength == that.maximumSymmetricKeyLength && this.minimumAsymmetricKeyLength == that.minimumAsymmetricKeyLength && this.maximumAsymmetricKeyLength == that.maximumAsymmetricKeyLength;
        }

        public int hashCode() {
            int result = 17;
            if (this.name != null) {
                result = 31 * result + this.name.hashCode();
            }
            if (this.digest != null) {
                result = 31 * result + this.digest.hashCode();
            }
            if (this.encryption != null) {
                result = 31 * result + this.encryption.hashCode();
            }
            if (this.symmetricKeyWrap != null) {
                result = 31 * result + this.symmetricKeyWrap.hashCode();
            }
            if (this.asymmetricKeyWrap != null) {
                result = 31 * result + this.asymmetricKeyWrap.hashCode();
            }
            if (this.encryptionKeyDerivation != null) {
                result = 31 * result + this.encryptionKeyDerivation.hashCode();
            }
            if (this.signatureKeyDerivation != null) {
                result = 31 * result + this.signatureKeyDerivation.hashCode();
            }
            result = 31 * result + Integer.hashCode(this.encryptionDerivedKeyLength);
            result = 31 * result + Integer.hashCode(this.signatureDerivedKeyLength);
            result = 31 * result + Integer.hashCode(this.minimumSymmetricKeyLength);
            result = 31 * result + Integer.hashCode(this.maximumSymmetricKeyLength);
            result = 31 * result + Integer.hashCode(this.minimumAsymmetricKeyLength);
            result = 31 * result + Integer.hashCode(this.maximumAsymmetricKeyLength);
            if (this.mgfAlgo != null) {
                result = 31 * result + this.mgfAlgo.hashCode();
            }
            if (this.ns != null) {
                result = 31 * result + this.ns.hashCode();
            }
            if (this.encryptionDigest != null) {
                result = 31 * result + this.encryptionDigest.hashCode();
            }
            return 31 * result + super.hashCode();
        }

        public String getName() {
            return this.name;
        }

        public String getDigest() {
            return this.digest;
        }

        public String getEncryption() {
            return this.encryption;
        }

        public String getSymmetricKeyWrap() {
            return this.symmetricKeyWrap;
        }

        public String getAsymmetricKeyWrap() {
            return this.asymmetricKeyWrap;
        }

        public String getEncryptionKeyDerivation() {
            return this.encryptionKeyDerivation;
        }

        public String getSignatureKeyDerivation() {
            return this.signatureKeyDerivation;
        }

        public int getEncryptionDerivedKeyLength() {
            return this.encryptionDerivedKeyLength;
        }

        public int getSignatureDerivedKeyLength() {
            return this.signatureDerivedKeyLength;
        }

        public int getMinimumSymmetricKeyLength() {
            return this.minimumSymmetricKeyLength;
        }

        public int getMaximumSymmetricKeyLength() {
            return this.maximumSymmetricKeyLength;
        }

        public int getMinimumAsymmetricKeyLength() {
            return this.minimumAsymmetricKeyLength;
        }

        public int getMaximumAsymmetricKeyLength() {
            return this.maximumAsymmetricKeyLength;
        }

        public void setNamespace(String ns) {
            this.ns = ns;
        }

        public String getNamespace() {
            return this.ns;
        }

        public void setMGFAlgo(String mgfAlgo) {
            this.mgfAlgo = mgfAlgo;
        }

        public String getMGFAlgo() {
            return this.mgfAlgo;
        }

        public void setEncryptionDigest(String encryptionDigest) {
            this.encryptionDigest = encryptionDigest;
        }

        public String getEncryptionDigest() {
            return this.encryptionDigest;
        }
    }
}

