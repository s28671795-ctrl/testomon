/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.support;

import com.google.common.base.Strings;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.Key;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import net.shibboleth.utilities.java.support.xml.BasicParserPool;
import net.shibboleth.utilities.java.support.xml.ParserPool;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import net.shibboleth.utilities.java.support.xml.XMLParserException;
import org.apache.xml.security.Init;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.XMLRuntimeException;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Marshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.Unmarshaller;
import org.opensaml.core.xml.io.UnmarshallerFactory;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialSupport;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.criteria.KeyAlgorithmCriterion;
import org.opensaml.security.criteria.KeyLengthCriterion;
import org.opensaml.security.criteria.UsageCriterion;
import org.opensaml.xmlsec.DecryptionParameters;
import org.opensaml.xmlsec.algorithm.AlgorithmSupport;
import org.opensaml.xmlsec.encryption.EncryptedData;
import org.opensaml.xmlsec.encryption.EncryptedKey;
import org.opensaml.xmlsec.encryption.EncryptedType;
import org.opensaml.xmlsec.encryption.EncryptionMethod;
import org.opensaml.xmlsec.encryption.MGF;
import org.opensaml.xmlsec.encryption.support.DecryptionException;
import org.opensaml.xmlsec.encryption.support.EncryptedKeyResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoCriterion;
import org.opensaml.xmlsec.signature.DigestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Decrypter {
    private final ParserPool parserPool;
    private final UnmarshallerFactory unmarshallerFactory;
    private final Logger log = LoggerFactory.getLogger(Decrypter.class);
    private KeyInfoCredentialResolver resolver;
    private KeyInfoCredentialResolver kekResolver;
    private EncryptedKeyResolver encKeyResolver;
    private Collection<String> whitelistedAlgorithmURIs;
    private Collection<String> blacklistedAlgorithmURIs;
    private CriteriaSet resolverCriteria = null;
    private CriteriaSet kekResolverCriteria = null;
    private String jcaProviderName;
    private boolean defaultRootInNewDocument = false;

    public Decrypter(DecryptionParameters params) {
        this(params.getDataKeyInfoCredentialResolver(), params.getKEKKeyInfoCredentialResolver(), params.getEncryptedKeyResolver(), params.getWhitelistedAlgorithms(), params.getBlacklistedAlgorithms());
    }

    public Decrypter(@Nullable KeyInfoCredentialResolver newResolver, @Nullable KeyInfoCredentialResolver newKEKResolver, @Nullable EncryptedKeyResolver newEncKeyResolver) {
        this(newResolver, newKEKResolver, newEncKeyResolver, null, null);
    }

    public Decrypter(@Nullable KeyInfoCredentialResolver newResolver, @Nullable KeyInfoCredentialResolver newKEKResolver, @Nullable EncryptedKeyResolver newEncKeyResolver, @Nullable Collection<String> whitelistAlgos, @Nullable Collection<String> blacklistAlgos) {
        this();
        this.resolver = newResolver;
        this.kekResolver = newKEKResolver;
        this.encKeyResolver = newEncKeyResolver;
        this.whitelistedAlgorithmURIs = whitelistAlgos;
        this.blacklistedAlgorithmURIs = blacklistAlgos;
    }

    private Decrypter() {
        this.parserPool = this.buildParserPool();
        this.unmarshallerFactory = XMLObjectProviderRegistrySupport.getUnmarshallerFactory();
    }

    public boolean isRootInNewDocument() {
        return this.defaultRootInNewDocument;
    }

    public void setRootInNewDocument(boolean flag) {
        this.defaultRootInNewDocument = flag;
    }

    @Nullable
    public String getJCAProviderName() {
        return this.jcaProviderName;
    }

    public void setJCAProviderName(@Nullable String providerName) {
        this.jcaProviderName = providerName;
    }

    public CriteriaSet getKeyResolverCriteria() {
        return this.resolverCriteria;
    }

    public void setKeyResolverCriteria(CriteriaSet newCriteria) {
        this.resolverCriteria = newCriteria;
    }

    public CriteriaSet getKEKResolverCriteria() {
        return this.kekResolverCriteria;
    }

    public void setKEKResolverCriteria(CriteriaSet newCriteria) {
        this.kekResolverCriteria = newCriteria;
    }

    @Nonnull
    public XMLObject decryptData(@Nonnull EncryptedData encryptedData) throws DecryptionException {
        return this.decryptData(encryptedData, this.isRootInNewDocument());
    }

    @Nonnull
    public XMLObject decryptData(@Nonnull EncryptedData encryptedData, boolean rootInNewDocument) throws DecryptionException {
        List<XMLObject> xmlObjects = this.decryptDataToList(encryptedData, rootInNewDocument);
        if (xmlObjects.size() != 1) {
            this.log.error("The decrypted data contained more than one top-level XMLObject child");
            throw new DecryptionException("The decrypted data contained more than one XMLObject child");
        }
        return xmlObjects.get(0);
    }

    @Nonnull
    public List<XMLObject> decryptDataToList(@Nonnull EncryptedData encryptedData) throws DecryptionException {
        return this.decryptDataToList(encryptedData, this.isRootInNewDocument());
    }

    @Nonnull
    public List<XMLObject> decryptDataToList(@Nonnull EncryptedData encryptedData, boolean rootInNewDocument) throws DecryptionException {
        LinkedList<XMLObject> xmlObjects = new LinkedList<XMLObject>();
        DocumentFragment docFragment = this.decryptDataToDOM(encryptedData);
        NodeList children = docFragment.getChildNodes();
        for (int i = 0; i < children.getLength(); ++i) {
            XMLObject xmlObject;
            Node node = children.item(i);
            if (node.getNodeType() != 1) {
                this.log.error("Decryption returned a top-level node that was not of type Element: " + node.getNodeType());
                throw new DecryptionException("Top-level node was not of type Element");
            }
            Element element = (Element)node;
            if (rootInNewDocument) {
                Document newDoc = null;
                try {
                    newDoc = this.parserPool.newDocument();
                }
                catch (XMLParserException e) {
                    this.log.error("There was an error creating a new DOM Document", e);
                    throw new DecryptionException("Error creating new DOM Document", e);
                }
                newDoc.adoptNode(element);
                newDoc.appendChild(element);
            }
            try {
                Unmarshaller unmarshaller = this.unmarshallerFactory.getUnmarshaller(element);
                if (unmarshaller == null) {
                    unmarshaller = this.unmarshallerFactory.getUnmarshaller(XMLObjectProviderRegistrySupport.getDefaultProviderQName());
                    if (unmarshaller == null) {
                        String errorMsg = "No unmarshaller available for " + QNameSupport.getNodeQName(element);
                        this.log.error(errorMsg);
                        throw new UnmarshallingException(errorMsg);
                    }
                    this.log.debug("No unmarshaller was registered for {}. Using default unmarshaller.", (Object)QNameSupport.getNodeQName(element));
                }
                xmlObject = unmarshaller.unmarshall(element);
            }
            catch (UnmarshallingException e) {
                this.log.error("There was an error during unmarshalling of the decrypted element", e);
                throw new DecryptionException("Unmarshalling error during decryption", e);
            }
            xmlObjects.add(xmlObject);
        }
        return xmlObjects;
    }

    @Nonnull
    public DocumentFragment decryptDataToDOM(@Nonnull EncryptedData encryptedData) throws DecryptionException {
        String algorithm;
        Constraint.isNotNull(encryptedData, "EncryptedData cannot be null");
        if (this.resolver == null && this.encKeyResolver == null) {
            this.log.error("Decryption can not be attempted, required resolvers are not available");
            throw new DecryptionException("Unable to decrypt EncryptedData, required resolvers are not available");
        }
        DocumentFragment docFrag = null;
        if (this.resolver != null) {
            docFrag = this.decryptUsingResolvedKey(encryptedData);
            if (docFrag != null) {
                return docFrag;
            }
            this.log.debug("Failed to decrypt EncryptedData using standard KeyInfo resolver");
        }
        if (Strings.isNullOrEmpty(algorithm = encryptedData.getEncryptionMethod().getAlgorithm())) {
            String msg = "EncryptedData's EncryptionMethod Algorithm attribute was empty, key decryption could not be attempted";
            this.log.error(msg);
            throw new DecryptionException(msg);
        }
        if (this.encKeyResolver != null) {
            docFrag = this.decryptUsingResolvedEncryptedKey(encryptedData, algorithm);
            if (docFrag != null) {
                return docFrag;
            }
            this.log.debug("Failed to decrypt EncryptedData using EncryptedKeyResolver");
        }
        this.log.error("Failed to decrypt EncryptedData using either EncryptedData KeyInfoCredentialResolver or EncryptedKeyResolver + EncryptedKey KeyInfoCredentialResolver");
        throw new DecryptionException("Failed to decrypt EncryptedData");
    }

    @Nonnull
    public DocumentFragment decryptDataToDOM(@Nonnull EncryptedData encryptedData, @Nonnull Key dataEncKey) throws DecryptionException {
        XMLCipher xmlCipher;
        Constraint.isNotNull(encryptedData, "EncryptedData cannot be null");
        Constraint.isNotNull(dataEncKey, "Data decryption key cannot be null");
        if (!"http://www.w3.org/2001/04/xmlenc#Element".equals(encryptedData.getType())) {
            this.log.error("EncryptedData was of unsupported type '" + encryptedData.getType() + "', could not attempt decryption");
            throw new DecryptionException("EncryptedData of unsupported type was encountered");
        }
        this.validateAlgorithms(encryptedData);
        try {
            this.checkAndMarshall(encryptedData);
        }
        catch (DecryptionException e) {
            this.log.error("Error marshalling EncryptedData for decryption", e);
            throw e;
        }
        Element targetElement = encryptedData.getDOM();
        try {
            xmlCipher = this.getJCAProviderName() != null ? XMLCipher.getProviderInstance(this.getJCAProviderName()) : XMLCipher.getInstance();
            xmlCipher.init(2, dataEncKey);
        }
        catch (XMLEncryptionException e) {
            this.log.error("Error initialzing cipher instance on data decryption", e);
            throw new DecryptionException("Error initialzing cipher instance on data decryption", e);
        }
        byte[] bytes = null;
        try {
            bytes = xmlCipher.decryptToByteArray(targetElement);
        }
        catch (XMLEncryptionException e) {
            this.log.error("Error decrypting the encrypted data element", e);
            throw new DecryptionException("Error decrypting the encrypted data element", e);
        }
        catch (Exception e) {
            throw new DecryptionException("Probable runtime exception on decryption:" + e.getMessage(), e);
        }
        if (bytes == null) {
            throw new DecryptionException("EncryptedData could not be decrypted");
        }
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentFragment docFragment = this.parseInputStream(input, encryptedData.getDOM().getOwnerDocument());
        return docFragment;
    }

    @Nonnull
    public Key decryptKey(@Nonnull EncryptedKey encryptedKey, @Nonnull String algorithm) throws DecryptionException {
        if (this.kekResolver == null) {
            this.log.warn("No KEK KeyInfo credential resolver is available, cannot attempt EncryptedKey decryption");
            throw new DecryptionException("No KEK KeyInfo resolver is available for EncryptedKey decryption");
        }
        if (Strings.isNullOrEmpty(algorithm)) {
            this.log.error("Algorithm of encrypted key not supplied, key decryption cannot proceed.");
            throw new DecryptionException("Algorithm of encrypted key not supplied, key decryption cannot proceed.");
        }
        CriteriaSet criteriaSet = this.buildCredentialCriteria(encryptedKey, this.kekResolverCriteria);
        try {
            for (Credential cred : this.kekResolver.resolve(criteriaSet)) {
                try {
                    return this.decryptKey(encryptedKey, algorithm, CredentialSupport.extractDecryptionKey(cred));
                }
                catch (DecryptionException e) {
                    String msg = "Attempt to decrypt EncryptedKey using credential from KEK KeyInfo resolver failed: ";
                    this.log.debug(msg, e);
                }
            }
        }
        catch (ResolverException e) {
            this.log.error("Error resolving credentials from EncryptedKey KeyInfo", e);
        }
        this.log.error("Failed to decrypt EncryptedKey, valid decryption key could not be resolved");
        throw new DecryptionException("Valid decryption key for EncryptedKey could not be resolved");
    }

    @Nonnull
    public Key decryptKey(@Nonnull EncryptedKey encryptedKey, @Nonnull String algorithm, @Nonnull Key kek) throws DecryptionException {
        org.apache.xml.security.encryption.EncryptedKey encKey;
        XMLCipher xmlCipher;
        if (kek == null) {
            this.log.error("Data encryption key was null");
            throw new IllegalArgumentException("Data encryption key cannot be null");
        }
        if (Strings.isNullOrEmpty(algorithm)) {
            this.log.error("Algorithm of encrypted key not supplied, key decryption cannot proceed.");
            throw new DecryptionException("Algorithm of encrypted key not supplied, key decryption cannot proceed.");
        }
        this.validateAlgorithms(encryptedKey);
        try {
            this.checkAndMarshall(encryptedKey);
        }
        catch (DecryptionException e) {
            this.log.error("Error marshalling EncryptedKey for decryption", e);
            throw e;
        }
        this.preProcessEncryptedKey(encryptedKey, algorithm, kek);
        try {
            xmlCipher = this.getJCAProviderName() != null ? XMLCipher.getProviderInstance(this.getJCAProviderName()) : XMLCipher.getInstance();
            xmlCipher.init(4, kek);
        }
        catch (XMLEncryptionException e) {
            this.log.error("Error initialzing cipher instance on key decryption", e);
            throw new DecryptionException("Error initialzing cipher instance on key decryption", e);
        }
        try {
            Element targetElement = encryptedKey.getDOM();
            encKey = xmlCipher.loadEncryptedKey(targetElement.getOwnerDocument(), targetElement);
        }
        catch (XMLEncryptionException e) {
            this.log.error("Error when loading library native encrypted key representation", e);
            throw new DecryptionException("Error when loading library native encrypted key representation", e);
        }
        try {
            Key key = xmlCipher.decryptKey(encKey, algorithm);
            if (key == null) {
                throw new DecryptionException("Key could not be decrypted");
            }
            return key;
        }
        catch (XMLEncryptionException e) {
            this.log.error("Error decrypting encrypted key", e);
            throw new DecryptionException("Error decrypting encrypted key", e);
        }
        catch (Exception e) {
            throw new DecryptionException("Probable runtime exception on decryption:" + e.getMessage(), e);
        }
    }

    protected void preProcessEncryptedKey(@Nonnull EncryptedKey encryptedKey, @Nonnull String algorithm, @Nonnull Key kek) throws DecryptionException {
    }

    @Nullable
    private DocumentFragment decryptUsingResolvedKey(@Nonnull EncryptedData encryptedData) {
        if (this.resolver != null) {
            CriteriaSet criteriaSet = this.buildCredentialCriteria(encryptedData, this.resolverCriteria);
            try {
                for (Credential cred : this.resolver.resolve(criteriaSet)) {
                    try {
                        return this.decryptDataToDOM(encryptedData, CredentialSupport.extractDecryptionKey(cred));
                    }
                    catch (DecryptionException e) {
                        String msg = "Decryption attempt using credential from standard KeyInfo resolver failed: ";
                        this.log.debug(msg, e);
                    }
                }
            }
            catch (ResolverException e) {
                this.log.error("Error resolving credentials from EncryptedData KeyInfo", e);
            }
        }
        return null;
    }

    @Nullable
    private DocumentFragment decryptUsingResolvedEncryptedKey(@Nonnull EncryptedData encryptedData, @Nonnull String algorithm) {
        if (this.encKeyResolver != null) {
            for (EncryptedKey encryptedKey : this.encKeyResolver.resolve(encryptedData)) {
                try {
                    Key decryptedKey = this.decryptKey(encryptedKey, algorithm);
                    return this.decryptDataToDOM(encryptedData, decryptedKey);
                }
                catch (DecryptionException e) {
                    String msg = "Attempt to decrypt EncryptedData using key extracted from EncryptedKey failed: ";
                    this.log.debug(msg, e);
                }
            }
        }
        return null;
    }

    @Nonnull
    private DocumentFragment parseInputStream(@Nonnull InputStream input, @Nonnull Document owningDocument) throws DecryptionException {
        Document newDocument = null;
        try {
            newDocument = this.parserPool.parse(input);
        }
        catch (XMLParserException e) {
            this.log.error("Error parsing decrypted input stream", e);
            throw new DecryptionException("Error parsing input stream", e);
        }
        Element element = newDocument.getDocumentElement();
        owningDocument.adoptNode(element);
        DocumentFragment container = owningDocument.createDocumentFragment();
        container.appendChild(element);
        return container;
    }

    @Nonnull
    private CriteriaSet buildCredentialCriteria(@Nonnull EncryptedType encryptedType, @Nullable CriteriaSet staticCriteria) {
        CriteriaSet newCriteriaSet = new CriteriaSet();
        newCriteriaSet.add(new KeyInfoCriterion(encryptedType.getKeyInfo()));
        Set<Criterion> keyCriteria = this.buildKeyCriteria(encryptedType);
        if (keyCriteria != null && !keyCriteria.isEmpty()) {
            newCriteriaSet.addAll(keyCriteria);
        }
        if (staticCriteria != null && !staticCriteria.isEmpty()) {
            newCriteriaSet.addAll(staticCriteria);
        }
        if (!newCriteriaSet.contains(UsageCriterion.class)) {
            newCriteriaSet.add(new UsageCriterion(UsageType.ENCRYPTION));
        }
        return newCriteriaSet;
    }

    @Nullable
    private Set<Criterion> buildKeyCriteria(@Nonnull EncryptedType encryptedType) {
        KeyLengthCriterion lengthCrit;
        EncryptionMethod encMethod = encryptedType.getEncryptionMethod();
        if (encMethod == null) {
            return null;
        }
        String encAlgorithmURI = StringSupport.trimOrNull(encMethod.getAlgorithm());
        if (encAlgorithmURI == null) {
            return null;
        }
        HashSet<Criterion> critSet = new HashSet<Criterion>(2);
        KeyAlgorithmCriterion algoCrit = this.buildKeyAlgorithmCriteria(encAlgorithmURI);
        if (algoCrit != null) {
            critSet.add(algoCrit);
            this.log.debug("Added decryption key algorithm criteria: {}", (Object)algoCrit.getKeyAlgorithm());
        }
        if ((lengthCrit = this.buildKeyLengthCriteria(encAlgorithmURI)) != null) {
            critSet.add(lengthCrit);
            this.log.debug("Added decryption key length criteria from EncryptionMethod algorithm URI: {}", (Object)lengthCrit.getKeyLength());
        } else if (encMethod.getKeySize() != null && encMethod.getKeySize().getValue() != null) {
            lengthCrit = new KeyLengthCriterion(encMethod.getKeySize().getValue());
            critSet.add(lengthCrit);
            this.log.debug("Added decryption key length criteria from EncryptionMethod/KeySize: {}", (Object)lengthCrit.getKeyLength());
        }
        return critSet;
    }

    @Nullable
    private KeyAlgorithmCriterion buildKeyAlgorithmCriteria(@Nullable String encAlgorithmURI) {
        if (Strings.isNullOrEmpty(encAlgorithmURI)) {
            return null;
        }
        String jcaKeyAlgorithm = AlgorithmSupport.getKeyAlgorithm(encAlgorithmURI);
        if (!Strings.isNullOrEmpty(jcaKeyAlgorithm)) {
            return new KeyAlgorithmCriterion(jcaKeyAlgorithm);
        }
        return null;
    }

    @Nullable
    private KeyLengthCriterion buildKeyLengthCriteria(@Nullable String encAlgorithmURI) {
        if (!Strings.isNullOrEmpty(encAlgorithmURI)) {
            return null;
        }
        Integer keyLength = AlgorithmSupport.getKeyLength(encAlgorithmURI);
        if (keyLength != null) {
            return new KeyLengthCriterion(keyLength);
        }
        return null;
    }

    protected void checkAndMarshall(@Nonnull XMLObject xmlObject) throws DecryptionException {
        Constraint.isNotNull(xmlObject, "XMLObject cannot be null");
        Element targetElement = xmlObject.getDOM();
        if (targetElement == null) {
            Marshaller marshaller = XMLObjectProviderRegistrySupport.getMarshallerFactory().getMarshaller(xmlObject);
            if (marshaller == null && (marshaller = XMLObjectProviderRegistrySupport.getMarshallerFactory().getMarshaller(XMLObjectProviderRegistrySupport.getDefaultProviderQName())) == null) {
                String errorMsg = "No marshaller available for " + xmlObject.getElementQName();
                this.log.error(errorMsg);
                throw new DecryptionException(errorMsg);
            }
            try {
                targetElement = marshaller.marshall(xmlObject);
            }
            catch (MarshallingException e) {
                this.log.error("Error marshalling target XMLObject", e);
                throw new DecryptionException("Error marshalling target XMLObject", e);
            }
        }
    }

    protected ParserPool buildParserPool() {
        BasicParserPool pp = new BasicParserPool();
        HashMap<String, Boolean> features = new HashMap<String, Boolean>();
        pp.setNamespaceAware(true);
        features.put("http://apache.org/xml/features/dom/defer-node-expansion", Boolean.FALSE);
        pp.setExpandEntityReferences(false);
        features.put("http://javax.xml.XMLConstants/feature/secure-processing", true);
        features.put("http://apache.org/xml/features/disallow-doctype-decl", true);
        pp.setBuilderFeatures(features);
        try {
            pp.initialize();
            return pp;
        }
        catch (ComponentInitializationException e) {
            throw new XMLRuntimeException("Problem initializing Decrypter internal ParserPool", e);
        }
    }

    protected void validateAlgorithms(@Nonnull EncryptedKey encryptedKey) throws DecryptionException {
        String encryptionAlgorithm = encryptedKey.getEncryptionMethod().getAlgorithm();
        this.validateAlgorithmURI(encryptionAlgorithm);
        if (AlgorithmSupport.isRSAOAEP(encryptionAlgorithm)) {
            String digestAlgorithm = null;
            List<XMLObject> digestMethods = encryptedKey.getEncryptionMethod().getUnknownXMLObjects(DigestMethod.DEFAULT_ELEMENT_NAME);
            if (digestMethods.size() > 0) {
                DigestMethod digestMethod = (DigestMethod)digestMethods.get(0);
                digestAlgorithm = StringSupport.trimOrNull(digestMethod.getAlgorithm());
            }
            if (digestAlgorithm == null) {
                digestAlgorithm = "http://www.w3.org/2000/09/xmldsig#sha1";
            }
            this.validateAlgorithmURI(digestAlgorithm);
            String mgfAlgorithm = null;
            List<XMLObject> mgfs = encryptedKey.getEncryptionMethod().getUnknownXMLObjects(MGF.DEFAULT_ELEMENT_NAME);
            if (mgfs.size() > 0) {
                MGF mgf = (MGF)mgfs.get(0);
                mgfAlgorithm = StringSupport.trimOrNull(mgf.getAlgorithm());
            }
            if (mgfAlgorithm == null) {
                mgfAlgorithm = "http://www.w3.org/2009/xmlenc11#mgf1sha1";
            }
            this.validateAlgorithmURI(mgfAlgorithm);
        }
    }

    protected void validateAlgorithms(@Nonnull EncryptedData encryptedData) throws DecryptionException {
        this.validateAlgorithmURI(encryptedData.getEncryptionMethod().getAlgorithm());
    }

    protected void validateAlgorithmURI(@Nonnull String algorithmURI) throws DecryptionException {
        this.log.debug("Validating algorithm URI against whitelist and blacklist: algorithm: {}, whitelist: {}, blacklist: {}", algorithmURI, this.whitelistedAlgorithmURIs, this.blacklistedAlgorithmURIs);
        if (!AlgorithmSupport.validateAlgorithmURI(algorithmURI, this.whitelistedAlgorithmURIs, this.blacklistedAlgorithmURIs)) {
            throw new DecryptionException("Algorithm failed whitelist/blacklist validation: " + algorithmURI);
        }
    }

    static {
        if (!Init.isInitialized()) {
            Init.init();
        }
    }
}

