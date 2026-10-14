/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.webapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebDescriptor;
import org.eclipse.jetty.xml.XmlParser;

public class FragmentDescriptor
extends WebDescriptor {
    public static final String NAMELESS = "@@-NAMELESS-@@";
    protected static int _counter = 0;
    protected OtherType _otherType = OtherType.None;
    protected List<String> _befores = new ArrayList<String>();
    protected List<String> _afters = new ArrayList<String>();
    protected String _name;

    public FragmentDescriptor(Resource xml) throws Exception {
        super(xml);
    }

    public String getName() {
        return this._name;
    }

    @Override
    public void parse(XmlParser parser) throws Exception {
        super.parse(parser);
        this.processName();
    }

    public void processName() {
        String tmp;
        XmlParser.Node root = this.getRoot();
        XmlParser.Node nameNode = root.get("name");
        this._name = NAMELESS + _counter++;
        if (nameNode != null && (tmp = nameNode.toString(false, true)) != null && tmp.length() > 0) {
            this._name = tmp;
        }
    }

    @Override
    public void processOrdering() {
        XmlParser.Node root = this.getRoot();
        XmlParser.Node ordering = root.get("ordering");
        if (ordering == null) {
            return;
        }
        this._isOrdered = true;
        this.processBefores(ordering);
        this.processAfters(ordering);
    }

    public void processBefores(XmlParser.Node ordering) {
        XmlParser.Node before = ordering.get("before");
        if (before == null) {
            return;
        }
        Iterator iter = before.iterator();
        XmlParser.Node node = null;
        while (iter.hasNext()) {
            Object o = iter.next();
            if (!(o instanceof XmlParser.Node)) continue;
            node = (XmlParser.Node)o;
            if (node.getTag().equalsIgnoreCase("others")) {
                if (this._otherType != OtherType.None) {
                    throw new IllegalStateException("Duplicate <other> clause detected in " + this._xml.getURI());
                }
                this._otherType = OtherType.Before;
                continue;
            }
            if (!node.getTag().equalsIgnoreCase("name")) continue;
            this._befores.add(node.toString(false, true));
        }
    }

    public void processAfters(XmlParser.Node ordering) {
        XmlParser.Node after = ordering.get("after");
        if (after == null) {
            return;
        }
        Iterator iter = after.iterator();
        XmlParser.Node node = null;
        while (iter.hasNext()) {
            Object o = iter.next();
            if (!(o instanceof XmlParser.Node)) continue;
            node = (XmlParser.Node)o;
            if (node.getTag().equalsIgnoreCase("others")) {
                if (this._otherType != OtherType.None) {
                    throw new IllegalStateException("Duplicate <other> clause detected in " + this._xml.getURI());
                }
                this._otherType = OtherType.After;
                continue;
            }
            if (!node.getTag().equalsIgnoreCase("name")) continue;
            this._afters.add(node.toString(false, true));
        }
    }

    public List<String> getBefores() {
        return Collections.unmodifiableList(this._befores);
    }

    public List<String> getAfters() {
        return Collections.unmodifiableList(this._afters);
    }

    public OtherType getOtherType() {
        return this._otherType;
    }

    @Override
    public List<String> getOrdering() {
        return null;
    }

    public static enum OtherType {
        None,
        Before,
        After;

    }
}

