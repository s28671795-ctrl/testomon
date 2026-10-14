/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.util;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.collection.LazyList;
import net.shibboleth.utilities.java.support.collection.LazyMap;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.ListView;
import org.opensaml.core.xml.util.XMLObjectChildrenList;

@NotThreadSafe
public class IndexedXMLObjectChildrenList<ElementType extends XMLObject>
extends XMLObjectChildrenList<ElementType> {
    private final Map<QName, List<ElementType>> objectIndex;

    public IndexedXMLObjectChildrenList(@Nonnull XMLObject parent) {
        super(parent);
        this.objectIndex = new LazyMap<QName, List<ElementType>>();
    }

    public IndexedXMLObjectChildrenList(@Nonnull XMLObject parent, @Nonnull Collection<ElementType> col) {
        super(parent);
        Constraint.isNotNull(col, "Initial collection cannot be null");
        this.objectIndex = new LazyMap<QName, List<ElementType>>();
        this.addAll(Collections2.filter(col, Predicates.notNull()));
    }

    @Override
    public void add(int index, @Nullable ElementType element) {
        super.add(index, element);
        this.indexElement(element);
    }

    @Override
    public void clear() {
        super.clear();
        this.objectIndex.clear();
    }

    @Nonnull
    public List<ElementType> get(@Nonnull QName typeOrName) {
        this.checkAndCreateIndex(typeOrName);
        return this.objectIndex.get(typeOrName);
    }

    protected void checkAndCreateIndex(@Nonnull QName index) {
        if (!this.objectIndex.containsKey(index)) {
            this.objectIndex.put(index, new LazyList());
        }
    }

    protected void indexElement(@Nullable ElementType element) {
        if (element == null) {
            return;
        }
        QName type = element.getSchemaType();
        if (type != null) {
            this.indexElement(type, element);
        }
        this.indexElement(element.getElementQName(), element);
    }

    protected void indexElement(@Nonnull QName index, @Nullable ElementType element) {
        List<ElementType> objects = this.get(index);
        objects.add(element);
    }

    @Override
    public boolean remove(@Nullable ElementType element) {
        boolean elementRemoved = super.remove(element);
        if (elementRemoved) {
            this.removeElementFromIndex(element);
        }
        return elementRemoved;
    }

    @Override
    @Nonnull
    public ElementType remove(int index) {
        Object returnValue = super.remove(index);
        this.removeElementFromIndex(returnValue);
        return (ElementType)returnValue;
    }

    protected void removeElementFromIndex(@Nullable ElementType element) {
        if (element == null) {
            return;
        }
        QName type = element.getSchemaType();
        if (type != null) {
            this.removeElementFromIndex(type, element);
        }
        this.removeElementFromIndex(element.getElementQName(), element);
    }

    protected void removeElementFromIndex(@Nonnull QName index, @Nullable ElementType element) {
        List<ElementType> objects = this.get(index);
        objects.remove(element);
    }

    @Override
    @Nullable
    public ElementType set(int index, @Nullable ElementType element) {
        ElementType returnValue = super.set(index, element);
        this.removeElementFromIndex(returnValue);
        this.indexElement(element);
        return returnValue;
    }

    @Nonnull
    public List<? extends ElementType> subList(@Nonnull QName index) {
        this.checkAndCreateIndex(index);
        return new ListView(this, index);
    }
}

