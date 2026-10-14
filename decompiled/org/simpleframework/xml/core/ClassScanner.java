/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Commit;
import org.simpleframework.xml.core.Complete;
import org.simpleframework.xml.core.ConstructorScanner;
import org.simpleframework.xml.core.Decorator;
import org.simpleframework.xml.core.Detail;
import org.simpleframework.xml.core.Function;
import org.simpleframework.xml.core.MethodDetail;
import org.simpleframework.xml.core.NamespaceDecorator;
import org.simpleframework.xml.core.ParameterMap;
import org.simpleframework.xml.core.Persist;
import org.simpleframework.xml.core.Replace;
import org.simpleframework.xml.core.Resolve;
import org.simpleframework.xml.core.Signature;
import org.simpleframework.xml.core.Support;
import org.simpleframework.xml.core.Validate;

class ClassScanner {
    private NamespaceDecorator decorator;
    private ConstructorScanner scanner;
    private Function commit;
    private Function validate;
    private Function persist;
    private Function complete;
    private Function replace;
    private Function resolve;
    private Support support;
    private Root root;
    private Order order;

    public ClassScanner(Detail detail, Support support) throws Exception {
        this.scanner = new ConstructorScanner(detail, support);
        this.decorator = new NamespaceDecorator();
        this.support = support;
        this.scan(detail);
    }

    public Signature getSignature() {
        return this.scanner.getSignature();
    }

    public List<Signature> getSignatures() {
        return this.scanner.getSignatures();
    }

    public ParameterMap getParameters() {
        return this.scanner.getParameters();
    }

    public Decorator getDecorator() {
        return this.decorator;
    }

    public Order getOrder() {
        return this.order;
    }

    public Root getRoot() {
        return this.root;
    }

    public Function getCommit() {
        return this.commit;
    }

    public Function getValidate() {
        return this.validate;
    }

    public Function getPersist() {
        return this.persist;
    }

    public Function getComplete() {
        return this.complete;
    }

    public Function getReplace() {
        return this.replace;
    }

    public Function getResolve() {
        return this.resolve;
    }

    private void scan(Detail detail) throws Exception {
        DefaultType access = detail.getOverride();
        Class type = detail.getType();
        while (type != null) {
            Detail value = this.support.getDetail(type, access);
            this.namespace(value);
            this.method(value);
            this.definition(value);
            type = value.getSuper();
        }
        this.commit(detail);
    }

    private void definition(Detail detail) throws Exception {
        if (this.root == null) {
            this.root = detail.getRoot();
        }
        if (this.order == null) {
            this.order = detail.getOrder();
        }
    }

    private void namespace(Detail detail) throws Exception {
        NamespaceList scope = detail.getNamespaceList();
        Namespace namespace = detail.getNamespace();
        if (namespace != null) {
            this.decorator.add(namespace);
        }
        if (scope != null) {
            Namespace[] list;
            for (Namespace name : list = scope.value()) {
                this.decorator.add(name);
            }
        }
    }

    private void commit(Detail detail) {
        Namespace namespace = detail.getNamespace();
        if (namespace != null) {
            this.decorator.set(namespace);
        }
    }

    private void method(Detail detail) throws Exception {
        List<MethodDetail> list = detail.getMethods();
        for (MethodDetail entry : list) {
            this.method(entry);
        }
    }

    private void method(MethodDetail detail) {
        Annotation[] list = detail.getAnnotations();
        Method method2 = detail.getMethod();
        for (Annotation label : list) {
            if (label instanceof Commit) {
                this.commit(method2);
            }
            if (label instanceof Validate) {
                this.validate(method2);
            }
            if (label instanceof Persist) {
                this.persist(method2);
            }
            if (label instanceof Complete) {
                this.complete(method2);
            }
            if (label instanceof Replace) {
                this.replace(method2);
            }
            if (!(label instanceof Resolve)) continue;
            this.resolve(method2);
        }
    }

    private void replace(Method method2) {
        if (this.replace == null) {
            this.replace = this.getFunction(method2);
        }
    }

    private void resolve(Method method2) {
        if (this.resolve == null) {
            this.resolve = this.getFunction(method2);
        }
    }

    private void commit(Method method2) {
        if (this.commit == null) {
            this.commit = this.getFunction(method2);
        }
    }

    private void validate(Method method2) {
        if (this.validate == null) {
            this.validate = this.getFunction(method2);
        }
    }

    private void persist(Method method2) {
        if (this.persist == null) {
            this.persist = this.getFunction(method2);
        }
    }

    private void complete(Method method2) {
        if (this.complete == null) {
            this.complete = this.getFunction(method2);
        }
    }

    private Function getFunction(Method method2) {
        boolean contextual = this.isContextual(method2);
        if (!method2.isAccessible()) {
            method2.setAccessible(true);
        }
        return new Function(method2, contextual);
    }

    private boolean isContextual(Method method2) {
        Class<?>[] list = method2.getParameterTypes();
        if (list.length == 1) {
            return Map.class.equals(list[0]);
        }
        return false;
    }
}

