/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.webapp;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.jetty.webapp.Descriptor;
import org.eclipse.jetty.webapp.DescriptorProcessor;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlParser;

public abstract class IterativeDescriptorProcessor
implements DescriptorProcessor {
    public static final Class<?>[] __signature = new Class[]{WebAppContext.class, Descriptor.class, XmlParser.Node.class};
    protected Map<String, Method> _visitors = new HashMap<String, Method>();

    public abstract void start(WebAppContext var1, Descriptor var2);

    public abstract void end(WebAppContext var1, Descriptor var2);

    public void registerVisitor(String nodeName, Method m4) {
        this._visitors.put(nodeName, m4);
    }

    @Override
    public void process(WebAppContext context, Descriptor descriptor) throws Exception {
        if (descriptor == null) {
            return;
        }
        this.start(context, descriptor);
        XmlParser.Node root = descriptor.getRoot();
        Iterator iter = root.iterator();
        XmlParser.Node node = null;
        while (iter.hasNext()) {
            Object o = iter.next();
            if (!(o instanceof XmlParser.Node)) continue;
            node = (XmlParser.Node)o;
            this.visit(context, descriptor, node);
        }
        this.end(context, descriptor);
    }

    protected void visit(WebAppContext context, Descriptor descriptor, XmlParser.Node node) throws Exception {
        String name = node.getTag();
        Method m4 = this._visitors.get(name);
        if (m4 != null) {
            m4.invoke((Object)this, context, descriptor, node);
        }
    }
}

