/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.bus.managers;

import java.util.SortedSet;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.common.util.SortedArraySet;
import org.apache.cxf.extension.BusExtension;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.phase.PhaseManager;

@NoJSR250Annotations
public class PhaseManagerImpl
implements PhaseManager,
BusExtension {
    private SortedSet<Phase> inPhases;
    private SortedSet<Phase> outPhases;

    public PhaseManagerImpl() {
        this.createInPhases();
        this.createOutPhases();
    }

    public PhaseManagerImpl(SortedSet<Phase> in, SortedSet<Phase> out) {
        this.inPhases = in;
        this.outPhases = out;
    }

    @Override
    public Class<?> getRegistrationType() {
        return PhaseManager.class;
    }

    @Override
    public SortedSet<Phase> getInPhases() {
        return this.inPhases;
    }

    @Override
    public SortedSet<Phase> getOutPhases() {
        return this.outPhases;
    }

    final void createInPhases() {
        int i = 0;
        this.inPhases = new SortedArraySet<Phase>();
        this.inPhases.add(new Phase("receive", ++i * 1000));
        this.inPhases.add(new Phase("pre-stream", ++i * 1000));
        this.inPhases.add(new Phase("user-stream", ++i * 1000));
        this.inPhases.add(new Phase("post-stream", ++i * 1000));
        this.inPhases.add(new Phase("read", ++i * 1000));
        this.inPhases.add(new Phase("pre-protocol", ++i * 1000));
        this.inPhases.add(new Phase("pre-protocol-frontend", ++i * 1000));
        this.inPhases.add(new Phase("user-protocol", ++i * 1000));
        this.inPhases.add(new Phase("protocol", ++i * 1000));
        this.inPhases.add(new Phase("post-protocol", ++i * 1000));
        this.inPhases.add(new Phase("pre-unmarshal", ++i * 1000));
        this.inPhases.add(new Phase("unmarshal", ++i * 1000));
        this.inPhases.add(new Phase("post-unmarshal", ++i * 1000));
        this.inPhases.add(new Phase("pre-logical", ++i * 1000));
        this.inPhases.add(new Phase("user-logical", ++i * 1000));
        this.inPhases.add(new Phase("post-logical", ++i * 1000));
        this.inPhases.add(new Phase("pre-invoke", ++i * 1000));
        this.inPhases.add(new Phase("invoke", ++i * 1000));
        this.inPhases.add(new Phase("post-invoke", ++i * 1000));
    }

    final void createOutPhases() {
        this.outPhases = new SortedArraySet<Phase>();
        int i = 0;
        this.outPhases.add(new Phase("setup", ++i * 1000));
        this.outPhases.add(new Phase("pre-logical", ++i * 1000));
        this.outPhases.add(new Phase("user-logical", ++i * 1000));
        this.outPhases.add(new Phase("post-logical", ++i * 1000));
        this.outPhases.add(new Phase("prepare-send", ++i * 1000));
        this.outPhases.add(new Phase("pre-stream", ++i * 1000));
        this.outPhases.add(new Phase("pre-protocol", ++i * 1000));
        this.outPhases.add(new Phase("pre-protocol-frontend", ++i * 1000));
        this.outPhases.add(new Phase("write", ++i * 1000));
        this.outPhases.add(new Phase("pre-marshal", ++i * 1000));
        this.outPhases.add(new Phase("marshal", ++i * 1000));
        this.outPhases.add(new Phase("post-marshal", ++i * 1000));
        this.outPhases.add(new Phase("user-protocol", ++i * 1000));
        this.outPhases.add(new Phase("protocol", ++i * 1000));
        this.outPhases.add(new Phase("post-protocol", ++i * 1000));
        this.outPhases.add(new Phase("user-stream", ++i * 1000));
        this.outPhases.add(new Phase("post-stream", ++i * 1000));
        this.outPhases.add(new Phase("send", ++i * 1000));
        this.outPhases.add(new Phase("send-ending", ++i * 1000));
        this.outPhases.add(new Phase("post-stream-ending", ++i * 1000));
        this.outPhases.add(new Phase("user-stream-ending", ++i * 1000));
        this.outPhases.add(new Phase("post-protocol-ending", ++i * 1000));
        this.outPhases.add(new Phase("user-protocol-ending", ++i * 1000));
        this.outPhases.add(new Phase("marshal-ending", ++i * 1000));
        this.outPhases.add(new Phase("write-ending", ++i * 1000));
        this.outPhases.add(new Phase("pre-protocol-ending", ++i * 1000));
        this.outPhases.add(new Phase("pre-stream-ending", ++i * 1000));
        this.outPhases.add(new Phase("prepare-send-ending", ++i * 1000));
        this.outPhases.add(new Phase("post-logical-ending", ++i * 1000));
        this.outPhases.add(new Phase("user-logical-ending", ++i * 1000));
        this.outPhases.add(new Phase("pre-logical-ending", ++i * 1000));
        this.outPhases.add(new Phase("setup-ending", ++i * 1000));
    }
}

