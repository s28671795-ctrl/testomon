/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.encryption.DHKeyValue;
import org.opensaml.xmlsec.encryption.Generator;
import org.opensaml.xmlsec.encryption.P;
import org.opensaml.xmlsec.encryption.PgenCounter;
import org.opensaml.xmlsec.encryption.Public;
import org.opensaml.xmlsec.encryption.Q;
import org.opensaml.xmlsec.encryption.Seed;
import org.opensaml.xmlsec.signature.CryptoBinary;

public class DHKeyValueImpl
extends AbstractXMLObject
implements DHKeyValue {
    private P p;
    private Q q;
    private Generator generator;
    private Public publicChild;
    private Seed seed;
    private PgenCounter pgenCounter;

    protected DHKeyValueImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public P getP() {
        return this.p;
    }

    @Override
    public void setP(P newP) {
        this.p = this.prepareForAssignment(this.p, newP);
    }

    @Override
    public Q getQ() {
        return this.q;
    }

    @Override
    public void setQ(Q newQ) {
        this.q = this.prepareForAssignment(this.q, newQ);
    }

    @Override
    public Generator getGenerator() {
        return this.generator;
    }

    @Override
    public void setGenerator(Generator newGenerator) {
        this.generator = this.prepareForAssignment(this.generator, newGenerator);
    }

    @Override
    public Public getPublic() {
        return this.publicChild;
    }

    @Override
    public void setPublic(Public newPublic) {
        this.publicChild = this.prepareForAssignment(this.publicChild, newPublic);
    }

    @Override
    public Seed getSeed() {
        return this.seed;
    }

    @Override
    public void setSeed(Seed newSeed) {
        this.seed = this.prepareForAssignment(this.seed, newSeed);
    }

    @Override
    public PgenCounter getPgenCounter() {
        return this.pgenCounter;
    }

    @Override
    public void setPgenCounter(PgenCounter newPgenCounter) {
        this.pgenCounter = this.prepareForAssignment(this.pgenCounter, newPgenCounter);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<CryptoBinary> children = new ArrayList<CryptoBinary>();
        if (this.p != null) {
            children.add(this.p);
        }
        if (this.q != null) {
            children.add(this.q);
        }
        if (this.generator != null) {
            children.add(this.generator);
        }
        if (this.publicChild != null) {
            children.add(this.publicChild);
        }
        if (this.seed != null) {
            children.add(this.seed);
        }
        if (this.pgenCounter != null) {
            children.add(this.pgenCounter);
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

