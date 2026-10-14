/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import org.simpleframework.xml.core.ConstructorException;
import org.simpleframework.xml.core.Detail;
import org.simpleframework.xml.core.ParameterMap;
import org.simpleframework.xml.core.Signature;
import org.simpleframework.xml.core.SignatureScanner;
import org.simpleframework.xml.core.Support;

class ConstructorScanner {
    private List<Signature> signatures = new ArrayList<Signature>();
    private ParameterMap registry = new ParameterMap();
    private Signature primary;
    private Support support;

    public ConstructorScanner(Detail detail, Support support) throws Exception {
        this.support = support;
        this.scan(detail);
    }

    public Signature getSignature() {
        return this.primary;
    }

    public List<Signature> getSignatures() {
        return new ArrayList<Signature>(this.signatures);
    }

    public ParameterMap getParameters() {
        return this.registry;
    }

    private void scan(Detail detail) throws Exception {
        Constructor[] array = detail.getConstructors();
        if (!detail.isInstantiable()) {
            throw new ConstructorException("Can not construct inner %s", detail);
        }
        for (Constructor factory2 : array) {
            if (detail.isPrimitive()) continue;
            this.scan(factory2);
        }
    }

    private void scan(Constructor factory2) throws Exception {
        SignatureScanner scanner = new SignatureScanner(factory2, this.registry, this.support);
        if (scanner.isValid()) {
            List<Signature> list = scanner.getSignatures();
            for (Signature signature : list) {
                int size = signature.size();
                if (size == 0) {
                    this.primary = signature;
                }
                this.signatures.add(signature);
            }
        }
    }
}

