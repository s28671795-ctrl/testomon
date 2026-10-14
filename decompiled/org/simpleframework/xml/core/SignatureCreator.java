/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.util.List;
import org.simpleframework.xml.core.Contact;
import org.simpleframework.xml.core.Creator;
import org.simpleframework.xml.core.Criteria;
import org.simpleframework.xml.core.Parameter;
import org.simpleframework.xml.core.Signature;
import org.simpleframework.xml.core.Support;
import org.simpleframework.xml.core.Variable;

class SignatureCreator
implements Creator {
    private final List<Parameter> list;
    private final Signature signature;
    private final Class type;

    public SignatureCreator(Signature signature) {
        this.type = signature.getType();
        this.list = signature.getAll();
        this.signature = signature;
    }

    @Override
    public Class getType() {
        return this.type;
    }

    @Override
    public Signature getSignature() {
        return this.signature;
    }

    @Override
    public Object getInstance() throws Exception {
        return this.signature.create();
    }

    @Override
    public Object getInstance(Criteria criteria) throws Exception {
        Object[] values2 = this.list.toArray();
        for (int i = 0; i < this.list.size(); ++i) {
            values2[i] = this.getVariable(criteria, i);
        }
        return this.signature.create(values2);
    }

    private Object getVariable(Criteria criteria, int index) throws Exception {
        Parameter parameter = this.list.get(index);
        Object key = parameter.getKey();
        Variable variable = criteria.remove(key);
        if (variable != null) {
            return variable.getValue();
        }
        return null;
    }

    @Override
    public double getScore(Criteria criteria) throws Exception {
        Signature match = this.signature.copy();
        for (Object key : criteria) {
            Class actual;
            Object value;
            Class<?> expect;
            Parameter parameter = match.get(key);
            Variable label = criteria.get(key);
            Contact contact = label.getContact();
            if (parameter != null && !Support.isAssignable(expect = (value = label.getValue()).getClass(), actual = parameter.getType())) {
                return -1.0;
            }
            if (!contact.isReadOnly() || parameter != null) continue;
            return -1.0;
        }
        return this.getPercentage(criteria);
    }

    private double getPercentage(Criteria criteria) throws Exception {
        double score = 0.0;
        for (Parameter value : this.list) {
            Object key = value.getKey();
            Variable label = criteria.get(key);
            if (label == null) {
                if (value.isRequired()) {
                    return -1.0;
                }
                if (!value.isPrimitive()) continue;
                return -1.0;
            }
            score += 1.0;
        }
        return this.getAdjustment(score);
    }

    private double getAdjustment(double score) {
        double adjustment = (double)this.list.size() / 1000.0;
        if (score > 0.0) {
            return adjustment + score / (double)this.list.size();
        }
        return score / (double)this.list.size();
    }

    public String toString() {
        return this.signature.toString();
    }
}

