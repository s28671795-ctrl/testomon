/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.presentation.rmi;

import java.lang.reflect.Method;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.rmi.CORBA.Tie;
import org.jboss.com.sun.corba.se.impl.orbutil.graph.Graph;
import org.jboss.com.sun.corba.se.impl.orbutil.graph.GraphImpl;
import org.jboss.com.sun.corba.se.impl.orbutil.graph.Node;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.DynamicMethodMarshallerImpl;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.IDLNameTranslatorImpl;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.InvocationHandlerFactoryImpl;
import org.jboss.com.sun.corba.se.spi.orbutil.proxy.InvocationHandlerFactory;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.DynamicMethodMarshaller;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.IDLNameTranslator;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.PresentationManager;

public final class PresentationManagerImpl
implements PresentationManager {
    private Map<Class<?>, PresentationManager.ClassData> classToClassData;
    private Map<Method, DynamicMethodMarshaller> methodToDMM;
    private PresentationManager.StubFactoryFactory staticStubFactoryFactory;
    private PresentationManager.StubFactoryFactory dynamicStubFactoryFactory;
    private boolean useDynamicStubs;

    public PresentationManagerImpl(boolean useDynamicStubs) {
        this.useDynamicStubs = useDynamicStubs;
        this.classToClassData = new HashMap();
        this.methodToDMM = new HashMap<Method, DynamicMethodMarshaller>();
    }

    @Override
    public synchronized DynamicMethodMarshaller getDynamicMethodMarshaller(Method method2) {
        if (method2 == null) {
            return null;
        }
        DynamicMethodMarshaller result = this.methodToDMM.get(method2);
        if (result == null) {
            result = new DynamicMethodMarshallerImpl(method2);
            this.methodToDMM.put(method2, result);
        }
        return result;
    }

    @Override
    public synchronized PresentationManager.ClassData getClassData(Class<?> cls) {
        PresentationManager.ClassData result = this.classToClassData.get(cls);
        if (result == null) {
            result = new ClassDataImpl(cls);
            this.classToClassData.put(cls, result);
        }
        return result;
    }

    @Override
    public PresentationManager.StubFactoryFactory getStubFactoryFactory(boolean isDynamic) {
        if (isDynamic) {
            return this.dynamicStubFactoryFactory;
        }
        return this.staticStubFactoryFactory;
    }

    @Override
    public void setStubFactoryFactory(boolean isDynamic, PresentationManager.StubFactoryFactory sff) {
        if (isDynamic) {
            this.dynamicStubFactoryFactory = sff;
        } else {
            this.staticStubFactoryFactory = sff;
        }
    }

    @Override
    public Tie getTie() {
        return this.dynamicStubFactoryFactory.getTie(null);
    }

    @Override
    public boolean useDynamicStubs() {
        return this.useDynamicStubs;
    }

    private Set<Node> getRootSet(Class<?> target, NodeImpl root, Graph gr) {
        Set<Node> rootSet = null;
        if (target.isInterface()) {
            gr.add(root);
            rootSet = gr.getRoots();
        } else {
            HashSet<NodeImpl> initialRootSet = new HashSet<NodeImpl>();
            for (Class<?> superclass = target; superclass != null && !superclass.equals(Object.class); superclass = superclass.getSuperclass()) {
                NodeImpl node = new NodeImpl(superclass);
                gr.add(node);
                initialRootSet.add(node);
            }
            gr.getRoots();
            gr.removeAll(initialRootSet);
            rootSet = gr.getRoots();
        }
        return rootSet;
    }

    private Class<?>[] getInterfaces(Set<Node> roots2) {
        Class[] classes = new Class[roots2.size()];
        Iterator<Node> iter = roots2.iterator();
        int ctr = 0;
        while (iter.hasNext()) {
            NodeImpl node = (NodeImpl)iter.next();
            classes[ctr++] = node.getInterface();
        }
        return classes;
    }

    private String[] makeTypeIds(NodeImpl root, Graph gr, Set<Node> rootSet) {
        HashSet<Node> nonRootSet = new HashSet<Node>(gr);
        nonRootSet.removeAll(rootSet);
        ArrayList<String> result = new ArrayList<String>();
        if (rootSet.size() > 1) {
            result.add(root.getTypeId());
        }
        this.addNodes(result, rootSet);
        this.addNodes(result, nonRootSet);
        return result.toArray(new String[result.size()]);
    }

    private void addNodes(List<String> resultList, Set<Node> nodeSet) {
        for (NodeImpl nodeImpl : nodeSet) {
            String typeId = nodeImpl.getTypeId();
            resultList.add(typeId);
        }
    }

    private static class NodeImpl
    implements Node {
        private Class<?> interf;

        public Class<?> getInterface() {
            return this.interf;
        }

        public NodeImpl(Class<?> interf) {
            this.interf = interf;
        }

        public String getTypeId() {
            return "RMI:" + this.interf.getName() + ":0000000000000000";
        }

        @Override
        public Set<Node> getChildren() {
            HashSet<Node> result = new HashSet<Node>();
            Class<?>[] interfaces = this.interf.getInterfaces();
            for (int ctr = 0; ctr < interfaces.length; ++ctr) {
                Class<?> cls = interfaces[ctr];
                if (!Remote.class.isAssignableFrom(cls) || Remote.class.equals(cls)) continue;
                result.add(new NodeImpl(cls));
            }
            return result;
        }

        public String toString() {
            return "NodeImpl[" + this.interf + "]";
        }

        public int hashCode() {
            return this.interf.hashCode();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof NodeImpl)) {
                return false;
            }
            NodeImpl other = (NodeImpl)obj;
            return other.interf.equals(this.interf);
        }
    }

    private class ClassDataImpl
    implements PresentationManager.ClassData {
        private Class<?> cls;
        private IDLNameTranslator nameTranslator;
        private String[] typeIds;
        private InvocationHandlerFactory ihfactory;
        private Map<Object, Object> dictionary;

        public ClassDataImpl(Class<?> cls) {
            this.cls = cls;
            GraphImpl gr = new GraphImpl();
            NodeImpl root = new NodeImpl(cls);
            Set rootSet = PresentationManagerImpl.this.getRootSet(cls, root, gr);
            Class[] interfaces = PresentationManagerImpl.this.getInterfaces(rootSet);
            this.nameTranslator = IDLNameTranslatorImpl.get(interfaces);
            this.typeIds = PresentationManagerImpl.this.makeTypeIds(root, gr, rootSet);
            this.ihfactory = new InvocationHandlerFactoryImpl(PresentationManagerImpl.this, this);
            this.dictionary = new HashMap<Object, Object>();
        }

        @Override
        public Class<?> getMyClass() {
            return this.cls;
        }

        @Override
        public IDLNameTranslator getIDLNameTranslator() {
            return this.nameTranslator;
        }

        @Override
        public String[] getTypeIds() {
            return this.typeIds;
        }

        @Override
        public InvocationHandlerFactory getInvocationHandlerFactory() {
            return this.ihfactory;
        }

        @Override
        public Map<Object, Object> getDictionary() {
            return this.dictionary;
        }
    }
}

