/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.webapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.FragmentDescriptor;
import org.eclipse.jetty.webapp.MetaData;
import org.eclipse.jetty.webapp.Ordering;

public class AbsoluteOrdering
implements Ordering {
    public static final String OTHER = "@@-OTHER-@@";
    protected List<String> _order = new ArrayList<String>();
    protected boolean _hasOther = false;
    protected MetaData _metaData;

    public AbsoluteOrdering(MetaData metaData) {
        this._metaData = metaData;
    }

    @Override
    public List<Resource> order(List<Resource> jars) {
        ArrayList<Resource> orderedList = new ArrayList<Resource>();
        ArrayList<Resource> tmp = new ArrayList<Resource>(jars);
        HashMap<String, FragmentDescriptor> others = new HashMap<String, FragmentDescriptor>(this._metaData.getNamedFragmentDescriptors());
        int index = -1;
        for (String item : this._order) {
            if (!item.equals(OTHER)) {
                FragmentDescriptor f = (FragmentDescriptor)others.remove(item);
                if (f == null) continue;
                Resource jar = this._metaData.getJarForFragmentName(item);
                orderedList.add(jar);
                tmp.remove(jar);
                continue;
            }
            index = orderedList.size();
        }
        if (this._hasOther) {
            orderedList.addAll(index < 0 ? 0 : index, tmp);
        }
        return orderedList;
    }

    public void add(String name) {
        this._order.add(name);
    }

    public void addOthers() {
        if (this._hasOther) {
            throw new IllegalStateException("Duplicate <other> element in absolute ordering");
        }
        this._hasOther = true;
        this._order.add(OTHER);
    }
}

