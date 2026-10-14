/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.util;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.collection.LazyList;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.XMLObject;

public class XMLObjectChildrenList<ElementType extends XMLObject>
extends AbstractList<ElementType> {
    private final XMLObject parent;
    private final List<ElementType> elements;

    public XMLObjectChildrenList(@Nonnull XMLObject newParent) {
        Constraint.isNotNull(newParent, "Parent cannot be null");
        this.parent = newParent;
        this.elements = new LazyList();
    }

    public XMLObjectChildrenList(@Nonnull XMLObject newParent, @Nonnull Collection<ElementType> newElements) {
        Constraint.isNotNull(newParent, "Parent cannot be null");
        Constraint.isNotNull(newElements, "Initial collection cannot be null");
        this.parent = newParent;
        this.elements = new LazyList();
        this.addAll(Collections2.filter(newElements, Predicates.notNull()));
    }

    @Override
    public int size() {
        return this.elements.size();
    }

    @Override
    public boolean contains(@Nonnull ElementType element) {
        return this.elements.contains(element);
    }

    @Override
    @Nonnull
    public ElementType get(int index) {
        return (ElementType)((XMLObject)this.elements.get(index));
    }

    @Override
    @Nullable
    public ElementType set(int index, @Nullable ElementType element) {
        if (element == null) {
            return null;
        }
        this.setParent(element);
        XMLObject removedElement = (XMLObject)this.elements.set(index, element);
        if (removedElement != null) {
            removedElement.setParent(null);
            this.parent.getIDIndex().deregisterIDMappings(removedElement.getIDIndex());
        }
        this.parent.getIDIndex().registerIDMappings(element.getIDIndex());
        ++this.modCount;
        return (ElementType)removedElement;
    }

    @Override
    public void add(int index, @Nullable ElementType element) {
        if (element == null || this.elements.contains(element)) {
            return;
        }
        this.setParent(element);
        this.parent.getIDIndex().registerIDMappings(element.getIDIndex());
        ++this.modCount;
        this.elements.add(index, element);
    }

    @Override
    @Nonnull
    public ElementType remove(int index) {
        XMLObject element = (XMLObject)this.elements.remove(index);
        if (element != null) {
            element.releaseParentDOM(true);
            element.setParent(null);
            this.parent.getIDIndex().deregisterIDMappings(element.getIDIndex());
        }
        ++this.modCount;
        return (ElementType)element;
    }

    @Override
    public boolean remove(@Nullable ElementType element) {
        boolean elementRemoved = this.elements.remove(element);
        if (elementRemoved && element != null) {
            element.releaseParentDOM(true);
            element.setParent(null);
            this.parent.getIDIndex().deregisterIDMappings(element.getIDIndex());
        }
        return elementRemoved;
    }

    protected void setParent(@Nonnull ElementType element) {
        XMLObject elemParent = element.getParent();
        if (elemParent != null && elemParent != this.parent) {
            throw new IllegalArgumentException(element.getElementQName() + " is already the child of another XMLObject and may not be inserted into this list");
        }
        element.setParent(this.parent);
        element.releaseParentDOM(true);
    }
}

