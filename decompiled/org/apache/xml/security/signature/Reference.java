/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.signature;

import java.io.IOException;
import java.io.OutputStream;
import java.security.AccessController;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.xml.security.algorithms.Algorithm;
import org.apache.xml.security.algorithms.MessageDigestAlgorithm;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.signature.Manifest;
import org.apache.xml.security.signature.ReferenceNotInitializedException;
import org.apache.xml.security.signature.XMLSignatureException;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.signature.reference.ReferenceData;
import org.apache.xml.security.signature.reference.ReferenceNodeSetData;
import org.apache.xml.security.signature.reference.ReferenceOctetStreamData;
import org.apache.xml.security.signature.reference.ReferenceSubTreeData;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.apache.xml.security.transforms.Transform;
import org.apache.xml.security.transforms.TransformationException;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.transforms.params.InclusiveNamespaces;
import org.apache.xml.security.utils.DigesterOutputStream;
import org.apache.xml.security.utils.ElementProxy;
import org.apache.xml.security.utils.SignatureElementProxy;
import org.apache.xml.security.utils.UnsyncBufferedOutputStream;
import org.apache.xml.security.utils.XMLUtils;
import org.apache.xml.security.utils.resolver.ResourceResolver;
import org.apache.xml.security.utils.resolver.ResourceResolverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class Reference
extends SignatureElementProxy {
    public static final String OBJECT_URI = "http://www.w3.org/2000/09/xmldsig#Object";
    public static final String MANIFEST_URI = "http://www.w3.org/2000/09/xmldsig#Manifest";
    public static final int MAXIMUM_TRANSFORM_COUNT = 5;
    private boolean secureValidation;
    private static boolean useC14N11 = AccessController.doPrivileged(() -> Boolean.getBoolean("org.apache.xml.security.useC14N11"));
    private static final Logger LOG = LoggerFactory.getLogger(Reference.class);
    private Manifest manifest;
    private XMLSignatureInput transformsOutput;
    private Transforms transforms;
    private Element digestMethodElem;
    private Element digestValueElement;
    private ReferenceData referenceData;

    protected Reference(Document doc, String baseURI, String referenceURI, Manifest manifest, Transforms transforms, String messageDigestAlgorithm) throws XMLSignatureException {
        super(doc);
        this.addReturnToSelf();
        this.baseURI = baseURI;
        this.manifest = manifest;
        this.setURI(referenceURI);
        if (transforms != null) {
            this.transforms = transforms;
            this.appendSelf(transforms);
            this.addReturnToSelf();
        }
        Algorithm digestAlgorithm = new Algorithm(this.getDocument(), messageDigestAlgorithm){

            @Override
            public String getBaseNamespace() {
                return "http://www.w3.org/2000/09/xmldsig#";
            }

            @Override
            public String getBaseLocalName() {
                return "DigestMethod";
            }
        };
        this.digestMethodElem = digestAlgorithm.getElement();
        this.appendSelf(this.digestMethodElem);
        this.addReturnToSelf();
        this.digestValueElement = XMLUtils.createElementInSignatureSpace(this.getDocument(), "DigestValue");
        this.appendSelf(this.digestValueElement);
        this.addReturnToSelf();
    }

    protected Reference(Element element, String baseURI, Manifest manifest) throws XMLSecurityException {
        this(element, baseURI, manifest, true);
    }

    protected Reference(Element element, String baseURI, Manifest manifest, boolean secureValidation) throws XMLSecurityException {
        super(element, baseURI);
        this.secureValidation = secureValidation;
        this.baseURI = baseURI;
        Element el = XMLUtils.getNextElement(element.getFirstChild());
        if (el != null && "Transforms".equals(el.getLocalName()) && "http://www.w3.org/2000/09/xmldsig#".equals(el.getNamespaceURI())) {
            this.transforms = new Transforms(el, this.baseURI);
            this.transforms.setSecureValidation(secureValidation);
            if (secureValidation && this.transforms.getLength() > 5) {
                Object[] exArgs = new Object[]{this.transforms.getLength(), 5};
                throw new XMLSecurityException("signature.tooManyTransforms", exArgs);
            }
            el = XMLUtils.getNextElement(el.getNextSibling());
        }
        this.digestMethodElem = el;
        if (this.digestMethodElem == null || !"http://www.w3.org/2000/09/xmldsig#".equals(this.digestMethodElem.getNamespaceURI()) || !"DigestMethod".equals(this.digestMethodElem.getLocalName())) {
            throw new XMLSecurityException("signature.Reference.NoDigestMethod");
        }
        this.digestValueElement = XMLUtils.getNextElement(this.digestMethodElem.getNextSibling());
        if (this.digestValueElement == null || !"http://www.w3.org/2000/09/xmldsig#".equals(this.digestValueElement.getNamespaceURI()) || !"DigestValue".equals(this.digestValueElement.getLocalName())) {
            throw new XMLSecurityException("signature.Reference.NoDigestValue");
        }
        this.manifest = manifest;
    }

    public MessageDigestAlgorithm getMessageDigestAlgorithm() throws XMLSignatureException {
        if (this.digestMethodElem == null) {
            return null;
        }
        String uri = this.digestMethodElem.getAttributeNS(null, "Algorithm");
        if ("".equals(uri)) {
            return null;
        }
        if (this.secureValidation && "http://www.w3.org/2001/04/xmldsig-more#md5".equals(uri)) {
            Object[] exArgs = new Object[]{uri};
            throw new XMLSignatureException("signature.signatureAlgorithm", exArgs);
        }
        return MessageDigestAlgorithm.getInstance(this.getDocument(), uri);
    }

    public void setURI(String uri) {
        if (uri != null) {
            this.setLocalAttribute("URI", uri);
        }
    }

    public String getURI() {
        return this.getLocalAttribute("URI");
    }

    public void setId(String id) {
        if (id != null) {
            this.setLocalIdAttribute("Id", id);
        }
    }

    public String getId() {
        return this.getLocalAttribute("Id");
    }

    public void setType(String type) {
        if (type != null) {
            this.setLocalAttribute("Type", type);
        }
    }

    public String getType() {
        return this.getLocalAttribute("Type");
    }

    public boolean typeIsReferenceToObject() {
        return OBJECT_URI.equals(this.getType());
    }

    public boolean typeIsReferenceToManifest() {
        return MANIFEST_URI.equals(this.getType());
    }

    private void setDigestValueElement(byte[] digestValue) {
        for (Node n = this.digestValueElement.getFirstChild(); n != null; n = n.getNextSibling()) {
            this.digestValueElement.removeChild(n);
        }
        String base64codedValue = XMLUtils.encodeToString(digestValue);
        Text t2 = this.createText(base64codedValue);
        this.digestValueElement.appendChild(t2);
    }

    public void generateDigestValue() throws XMLSignatureException, ReferenceNotInitializedException {
        this.setDigestValueElement(this.calculateDigest(false));
    }

    public XMLSignatureInput getContentsBeforeTransformation() throws ReferenceNotInitializedException {
        try {
            Attr uriAttr = this.getElement().getAttributeNodeNS(null, "URI");
            ResourceResolver resolver2 = ResourceResolver.getInstance(uriAttr, this.baseURI, this.manifest.getPerManifestResolvers(), this.secureValidation);
            resolver2.addProperties(this.manifest.getResolverProperties());
            return resolver2.resolve(uriAttr, this.baseURI, this.secureValidation);
        }
        catch (ResourceResolverException ex) {
            throw new ReferenceNotInitializedException(ex);
        }
    }

    private XMLSignatureInput getContentsAfterTransformation(XMLSignatureInput input, OutputStream os) throws XMLSignatureException {
        try {
            Transforms transforms = this.getTransforms();
            XMLSignatureInput output = null;
            if (transforms != null) {
                this.transformsOutput = output = transforms.performTransforms(input, os);
            } else {
                output = input;
            }
            return output;
        }
        catch (ResourceResolverException ex) {
            throw new XMLSignatureException(ex);
        }
        catch (CanonicalizationException ex) {
            throw new XMLSignatureException(ex);
        }
        catch (InvalidCanonicalizerException ex) {
            throw new XMLSignatureException(ex);
        }
        catch (TransformationException ex) {
            throw new XMLSignatureException(ex);
        }
        catch (XMLSecurityException ex) {
            throw new XMLSignatureException(ex);
        }
    }

    public XMLSignatureInput getContentsAfterTransformation() throws XMLSignatureException {
        XMLSignatureInput input = this.getContentsBeforeTransformation();
        this.cacheDereferencedElement(input);
        return this.getContentsAfterTransformation(input, null);
    }

    public XMLSignatureInput getNodesetBeforeFirstCanonicalization() throws XMLSignatureException {
        try {
            XMLSignatureInput input = this.getContentsBeforeTransformation();
            this.cacheDereferencedElement(input);
            XMLSignatureInput output = input;
            Transforms transforms = this.getTransforms();
            if (transforms != null) {
                Transform t2;
                String uri;
                for (int i = 0; !(i >= transforms.getLength() || (uri = (t2 = transforms.item(i)).getURI()).equals("http://www.w3.org/2001/10/xml-exc-c14n#") || uri.equals("http://www.w3.org/2001/10/xml-exc-c14n#WithComments") || uri.equals("http://www.w3.org/TR/2001/REC-xml-c14n-20010315") || uri.equals("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments") || uri.equals("http://www.w3.org/2006/12/xml-c14n11") || uri.equals("http://www.w3.org/2006/12/xml-c14n11#WithComments")); ++i) {
                    output = t2.performTransform(output, null);
                }
                output.setSourceURI(input.getSourceURI());
            }
            return output;
        }
        catch (IOException ex) {
            throw new XMLSignatureException(ex);
        }
        catch (ResourceResolverException ex) {
            throw new XMLSignatureException(ex);
        }
        catch (CanonicalizationException ex) {
            throw new XMLSignatureException(ex);
        }
        catch (InvalidCanonicalizerException ex) {
            throw new XMLSignatureException(ex);
        }
        catch (TransformationException ex) {
            throw new XMLSignatureException(ex);
        }
        catch (XMLSecurityException ex) {
            throw new XMLSignatureException(ex);
        }
    }

    public String getHTMLRepresentation() throws XMLSignatureException {
        try {
            XMLSignatureInput nodes = this.getNodesetBeforeFirstCanonicalization();
            Transforms transforms = this.getTransforms();
            ElementProxy c14nTransform = null;
            if (transforms != null) {
                for (int i = 0; i < transforms.getLength(); ++i) {
                    Transform t2 = transforms.item(i);
                    String uri = t2.getURI();
                    if (!uri.equals("http://www.w3.org/2001/10/xml-exc-c14n#") && !uri.equals("http://www.w3.org/2001/10/xml-exc-c14n#WithComments")) continue;
                    c14nTransform = t2;
                    break;
                }
            }
            Set<String> inclusiveNamespaces = new HashSet<String>();
            if (c14nTransform != null && c14nTransform.length("http://www.w3.org/2001/10/xml-exc-c14n#", "InclusiveNamespaces") == 1) {
                InclusiveNamespaces in = new InclusiveNamespaces(XMLUtils.selectNode(c14nTransform.getElement().getFirstChild(), "http://www.w3.org/2001/10/xml-exc-c14n#", "InclusiveNamespaces", 0), this.getBaseURI());
                inclusiveNamespaces = InclusiveNamespaces.prefixStr2Set(in.getInclusiveNamespaces());
            }
            return nodes.getHTMLRepresentation(inclusiveNamespaces);
        }
        catch (TransformationException ex) {
            throw new XMLSignatureException(ex);
        }
        catch (InvalidTransformException ex) {
            throw new XMLSignatureException(ex);
        }
        catch (XMLSecurityException ex) {
            throw new XMLSignatureException(ex);
        }
    }

    public XMLSignatureInput getTransformsOutput() {
        return this.transformsOutput;
    }

    public ReferenceData getReferenceData() {
        return this.referenceData;
    }

    protected XMLSignatureInput dereferenceURIandPerformTransforms(OutputStream os) throws XMLSignatureException {
        try {
            XMLSignatureInput output;
            XMLSignatureInput input = this.getContentsBeforeTransformation();
            this.cacheDereferencedElement(input);
            this.transformsOutput = output = this.getContentsAfterTransformation(input, os);
            return output;
        }
        catch (XMLSecurityException ex) {
            throw new ReferenceNotInitializedException(ex);
        }
    }

    private void cacheDereferencedElement(XMLSignatureInput input) {
        if (input.isNodeSet()) {
            try {
                final Set<Node> s2 = input.getNodeSet();
                this.referenceData = new ReferenceNodeSetData(){

                    @Override
                    public Iterator<Node> iterator() {
                        return new Iterator<Node>(){
                            Iterator<Node> sIterator;
                            {
                                this.sIterator = s2.iterator();
                            }

                            @Override
                            public boolean hasNext() {
                                return this.sIterator.hasNext();
                            }

                            @Override
                            public Node next() {
                                return this.sIterator.next();
                            }

                            @Override
                            public void remove() {
                                throw new UnsupportedOperationException();
                            }
                        };
                    }
                };
            }
            catch (Exception e) {
                LOG.warn("cannot cache dereferenced data: " + e);
            }
        } else if (input.isElement()) {
            this.referenceData = new ReferenceSubTreeData(input.getSubNode(), input.isExcludeComments());
        } else if (input.isOctetStream() || input.isByteArray()) {
            try {
                this.referenceData = new ReferenceOctetStreamData(input.getOctetStream(), input.getSourceURI(), input.getMIMEType());
            }
            catch (IOException ioe) {
                LOG.warn("cannot cache dereferenced data: " + ioe);
            }
        }
    }

    public Transforms getTransforms() throws XMLSignatureException, InvalidTransformException, TransformationException, XMLSecurityException {
        return this.transforms;
    }

    public byte[] getReferencedBytes() throws ReferenceNotInitializedException, XMLSignatureException {
        try {
            XMLSignatureInput output = this.dereferenceURIandPerformTransforms(null);
            return output.getBytes();
        }
        catch (IOException ex) {
            throw new ReferenceNotInitializedException(ex);
        }
        catch (CanonicalizationException ex) {
            throw new ReferenceNotInitializedException(ex);
        }
    }

    /*
     * Enabled aggressive exception aggregation
     */
    private byte[] calculateDigest(boolean validating) throws ReferenceNotInitializedException, XMLSignatureException {
        XMLSignatureInput input = this.getContentsBeforeTransformation();
        if (input.isPreCalculatedDigest()) {
            return this.getPreCalculatedDigest(input);
        }
        this.cacheDereferencedElement(input);
        MessageDigestAlgorithm mda = this.getMessageDigestAlgorithm();
        mda.reset();
        try {
            Throwable throwable = null;
            try (DigesterOutputStream diOs = new DigesterOutputStream(mda);){
                byte[] byArray;
                UnsyncBufferedOutputStream os = new UnsyncBufferedOutputStream(diOs);
                Throwable throwable2 = null;
                try {
                    XMLSignatureInput output;
                    this.transformsOutput = output = this.getContentsAfterTransformation(input, os);
                    if (useC14N11 && !validating && !output.isOutputStreamSet() && !output.isOctetStream()) {
                        if (this.transforms == null) {
                            this.transforms = new Transforms(this.getDocument());
                            this.transforms.setSecureValidation(this.secureValidation);
                            this.getElement().insertBefore(this.transforms.getElement(), this.digestMethodElem);
                        }
                        this.transforms.addTransform("http://www.w3.org/2006/12/xml-c14n11");
                        output.updateOutputStream(os, true);
                    } else {
                        output.updateOutputStream(os);
                    }
                    ((OutputStream)os).flush();
                    if (output.getOctetStreamReal() != null) {
                        output.getOctetStreamReal().close();
                    }
                    byArray = diOs.getDigestValue();
                }
                catch (Throwable throwable3) {
                    try {
                        try {
                            throwable2 = throwable3;
                            throw throwable3;
                        }
                        catch (Throwable throwable4) {
                            Reference.$closeResource(throwable2, os);
                            throw throwable4;
                        }
                    }
                    catch (Throwable throwable5) {
                        throwable = throwable5;
                        throw throwable5;
                    }
                }
                Reference.$closeResource(throwable2, os);
                return byArray;
            }
        }
        catch (XMLSecurityException ex) {
            throw new ReferenceNotInitializedException(ex);
        }
        catch (IOException ex) {
            throw new ReferenceNotInitializedException(ex);
        }
    }

    private byte[] getPreCalculatedDigest(XMLSignatureInput input) throws ReferenceNotInitializedException {
        LOG.debug("Verifying element with pre-calculated digest");
        String preCalculatedDigest = input.getPreCalculatedDigest();
        return XMLUtils.decode(preCalculatedDigest);
    }

    public byte[] getDigestValue() throws XMLSecurityException {
        if (this.digestValueElement == null) {
            Object[] exArgs = new Object[]{"DigestValue", "http://www.w3.org/2000/09/xmldsig#"};
            throw new XMLSecurityException("signature.Verification.NoSignatureElement", exArgs);
        }
        String content = XMLUtils.getFullTextChildrenFromNode(this.digestValueElement);
        return XMLUtils.decode(content);
    }

    public boolean verify() throws ReferenceNotInitializedException, XMLSecurityException {
        byte[] calcDig;
        byte[] elemDig = this.getDigestValue();
        boolean equal = MessageDigestAlgorithm.isEqual(elemDig, calcDig = this.calculateDigest(true));
        if (!equal) {
            LOG.warn("Verification failed for URI \"" + this.getURI() + "\"");
            LOG.warn("Expected Digest: " + XMLUtils.encodeToString(elemDig));
            LOG.warn("Actual Digest: " + XMLUtils.encodeToString(calcDig));
        } else {
            LOG.debug("Verification successful for URI \"{}\"", (Object)this.getURI());
        }
        return equal;
    }

    @Override
    public String getBaseLocalName() {
        return "Reference";
    }
}

