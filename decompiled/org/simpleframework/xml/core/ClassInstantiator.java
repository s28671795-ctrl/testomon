/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.util.ArrayList;
import java.util.List;
import org.simpleframework.xml.core.Creator;
import org.simpleframework.xml.core.Criteria;
import org.simpleframework.xml.core.Detail;
import org.simpleframework.xml.core.Instantiator;
import org.simpleframework.xml.core.Parameter;
import org.simpleframework.xml.core.ParameterMap;
import org.simpleframework.xml.core.PersistenceException;

class ClassInstantiator
implements Instantiator {
    private final List<Creator> creators;
    private final ParameterMap registry;
    private final Creator primary;
    private final Detail detail;

    public ClassInstantiator(List<Creator> creators, Creator primary, ParameterMap registry, Detail detail) {
        this.creators = creators;
        this.registry = registry;
        this.primary = primary;
        this.detail = detail;
    }

    @Override
    public boolean isDefault() {
        int count = this.creators.size();
        if (count <= 1) {
            return this.primary != null;
        }
        return false;
    }

    @Override
    public Object getInstance() throws Exception {
        return this.primary.getInstance();
    }

    @Override
    public Object getInstance(Criteria criteria) throws Exception {
        Creator creator = this.getCreator(criteria);
        if (creator == null) {
            throw new PersistenceException("Constructor not matched for %s", this.detail);
        }
        return creator.getInstance(criteria);
    }

    private Creator getCreator(Criteria criteria) throws Exception {
        Creator result = this.primary;
        double max = 0.0;
        for (Creator instantiator : this.creators) {
            double score = instantiator.getScore(criteria);
            if (!(score > max)) continue;
            result = instantiator;
            max = score;
        }
        return result;
    }

    @Override
    public Parameter getParameter(String name) {
        return (Parameter)this.registry.get(name);
    }

    @Override
    public List<Parameter> getParameters() {
        return this.registry.getAll();
    }

    @Override
    public List<Creator> getCreators() {
        return new ArrayList<Creator>(this.creators);
    }

    public String toString() {
        return String.format("creator for %s", this.detail);
    }
}

