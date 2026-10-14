/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.signature.CryptoBinary;
import org.opensaml.xmlsec.signature.DSAKeyValue;
import org.opensaml.xmlsec.signature.G;
import org.opensaml.xmlsec.signature.J;
import org.opensaml.xmlsec.signature.P;
import org.opensaml.xmlsec.signature.PgenCounter;
import org.opensaml.xmlsec.signature.Q;
import org.opensaml.xmlsec.signature.Seed;
import org.opensaml.xmlsec.signature.Y;

public class DSAKeyValueImpl
extends AbstractXMLObject
implements DSAKeyValue {
    private P p;
    private Q q;
    private G g;
    private Y y;
    private J j;
    private Seed seed;
    private PgenCounter pgenCounter;

    protected DSAKeyValueImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
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
    public G getG() {
        return this.g;
    }

    @Override
    public void setG(G newG) {
        this.g = this.prepareForAssignment(this.g, newG);
    }

    @Override
    public Y getY() {
        return this.y;
    }

    @Override
    public void setY(Y newY) {
        this.y = this.prepareForAssignment(this.y, newY);
    }

    @Override
    public J getJ() {
        return this.j;
    }

    @Override
    public void setJ(J newJ) {
        this.j = this.prepareForAssignment(this.j, newJ);
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
        if (this.g != null) {
            children.add(this.g);
        }
        if (this.y != null) {
            children.add(this.y);
        }
        if (this.j != null) {
            children.add(this.j);
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

