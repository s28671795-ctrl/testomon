/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.core.Contact;
import org.simpleframework.xml.core.Decorator;
import org.simpleframework.xml.core.NamespaceDecorator;
import org.simpleframework.xml.stream.OutputNode;

class Qualifier
implements Decorator {
    private NamespaceDecorator decorator = new NamespaceDecorator();

    public Qualifier(Contact contact) {
        this.scan(contact);
    }

    @Override
    public void decorate(OutputNode node) {
        this.decorator.decorate(node);
    }

    @Override
    public void decorate(OutputNode node, Decorator secondary) {
        this.decorator.decorate(node, secondary);
    }

    private void scan(Contact contact) {
        this.namespace(contact);
        this.scope(contact);
    }

    private void namespace(Contact contact) {
        Namespace primary = contact.getAnnotation(Namespace.class);
        if (primary != null) {
            this.decorator.set(primary);
            this.decorator.add(primary);
        }
    }

    private void scope(Contact contact) {
        NamespaceList scope = contact.getAnnotation(NamespaceList.class);
        if (scope != null) {
            for (Namespace name : scope.value()) {
                this.decorator.add(name);
            }
        }
    }
}

