/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.webapp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.jetty.util.TopologicalSort;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.FragmentDescriptor;
import org.eclipse.jetty.webapp.MetaData;
import org.eclipse.jetty.webapp.Ordering;

public class RelativeOrdering
implements Ordering {
    protected MetaData _metaData;

    public RelativeOrdering(MetaData metaData) {
        this._metaData = metaData;
    }

    @Override
    public List<Resource> order(List<Resource> jars) {
        TopologicalSort<Resource> sort = new TopologicalSort<Resource>();
        ArrayList<Resource> sorted2 = new ArrayList<Resource>(jars);
        HashSet<Resource> others = new HashSet<Resource>();
        HashSet<Resource> beforeOthers = new HashSet<Resource>();
        HashSet<Resource> afterOthers = new HashSet<Resource>();
        block10: for (Resource jar : jars) {
            FragmentDescriptor fragment = this._metaData.getFragmentDescriptorForJar(jar);
            if (fragment == null) {
                others.add(jar);
                continue;
            }
            switch (fragment.getOtherType()) {
                case None: {
                    others.add(jar);
                    continue block10;
                }
                case Before: {
                    beforeOthers.add(jar);
                    continue block10;
                }
                case After: {
                    afterOthers.add(jar);
                    continue block10;
                }
            }
            throw new IllegalStateException(fragment.toString());
        }
        HashSet<Resource> referenced = new HashSet<Resource>();
        for (Resource jar : jars) {
            FragmentDescriptor fragment = this._metaData.getFragmentDescriptorForJar(jar);
            if (fragment != null) {
                for (String name : fragment.getAfters()) {
                    Resource after = this._metaData.getJarForFragmentName(name);
                    sort.addDependency(jar, after);
                    referenced.add(after);
                }
                for (String name : fragment.getBefores()) {
                    Resource before = this._metaData.getJarForFragmentName(name);
                    sort.addDependency(before, jar);
                    referenced.add(before);
                }
                switch (fragment.getOtherType()) {
                    case None: {
                        break;
                    }
                    case Before: {
                        Consumer<Resource> addBefore = other -> {
                            if (!referenced.contains(other)) {
                                sort.addDependency((Resource)other, jar);
                            }
                        };
                        others.forEach(addBefore);
                        afterOthers.forEach(addBefore);
                        break;
                    }
                    case After: {
                        Consumer<Resource> addAfter = other -> {
                            if (!referenced.contains(other)) {
                                sort.addDependency(jar, other);
                            }
                        };
                        beforeOthers.forEach(addAfter);
                        others.forEach(addAfter);
                        break;
                    }
                    default: {
                        throw new IllegalStateException(fragment.toString());
                    }
                }
            }
            referenced.clear();
        }
        sort.sort(sorted2);
        return sorted2;
    }
}

