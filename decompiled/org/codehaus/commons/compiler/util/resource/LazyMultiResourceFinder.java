/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.resource;

import java.util.Iterator;
import org.codehaus.commons.compiler.util.iterator.IteratorCollection;
import org.codehaus.commons.compiler.util.resource.MultiResourceFinder;
import org.codehaus.commons.compiler.util.resource.ResourceFinder;

public class LazyMultiResourceFinder
extends MultiResourceFinder {
    public LazyMultiResourceFinder(Iterator<ResourceFinder> resourceFinders) {
        super(new IteratorCollection<ResourceFinder>(resourceFinders));
    }
}

