/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.function.LongConsumer;
import java.util.stream.Collectors;
import org.eclipse.jetty.http.BadMessageException;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.annotation.ManagedAttribute;
import org.eclipse.jetty.util.annotation.ManagedObject;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.websocket.core.Behavior;
import org.eclipse.jetty.websocket.core.Extension;
import org.eclipse.jetty.websocket.core.ExtensionConfig;
import org.eclipse.jetty.websocket.core.Frame;
import org.eclipse.jetty.websocket.core.IncomingFrames;
import org.eclipse.jetty.websocket.core.OutgoingFrames;
import org.eclipse.jetty.websocket.core.WebSocketComponents;
import org.eclipse.jetty.websocket.core.exception.WebSocketException;
import org.eclipse.jetty.websocket.core.internal.DemandChain;
import org.eclipse.jetty.websocket.core.internal.WebSocketCoreSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedObject(value="Extension Stack")
public class ExtensionStack
implements IncomingFrames,
OutgoingFrames,
Dumpable {
    private static final Logger LOG = LoggerFactory.getLogger(ExtensionStack.class);
    private final WebSocketComponents components;
    private final Behavior behavior;
    private List<Extension> extensions;
    private IncomingFrames incoming;
    private OutgoingFrames outgoing;
    private final Extension[] rsvClaims = new Extension[3];
    private LongConsumer lastDemand;
    private DemandChain demandChain = n -> this.lastDemand.accept(n);

    public ExtensionStack(WebSocketComponents components, Behavior behavior) {
        this.components = components;
        this.behavior = behavior;
    }

    public void close() {
        for (Extension ext : this.extensions) {
            try {
                ext.close();
            }
            catch (Throwable t2) {
                if (!LOG.isDebugEnabled()) continue;
                LOG.debug("Extension Error During Close", t2);
            }
        }
    }

    @ManagedAttribute(name="Extension List", readonly=true)
    public List<Extension> getExtensions() {
        return this.extensions;
    }

    public List<ExtensionConfig> getNegotiatedExtensions() {
        if (this.extensions == null) {
            return Collections.emptyList();
        }
        return this.extensions.stream().filter(e -> !e.getName().startsWith("@")).map(Extension::getConfig).collect(Collectors.toList());
    }

    @ManagedAttribute(name="Next Incoming Frames Handler", readonly=true)
    public IncomingFrames getNextIncoming() {
        return this.incoming;
    }

    @ManagedAttribute(name="Next Outgoing Frames Handler", readonly=true)
    public OutgoingFrames getNextOutgoing() {
        return this.outgoing;
    }

    public boolean hasNegotiatedExtensions() {
        return this.extensions != null && this.extensions.size() > 0;
    }

    @Override
    public void onFrame(Frame frame, Callback callback) {
        if (this.incoming == null) {
            throw new IllegalStateException();
        }
        this.incoming.onFrame(frame, callback);
    }

    public void negotiate(List<ExtensionConfig> offeredConfigs, List<ExtensionConfig> negotiatedConfigs) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Extension Configs={}", (Object)negotiatedConfigs);
        }
        this.extensions = new ArrayList<Extension>();
        for (ExtensionConfig config : negotiatedConfigs) {
            Extension ext;
            try {
                ext = this.components.getExtensionRegistry().newInstance(config, this.components);
            }
            catch (Throwable t2) {
                switch (this.behavior) {
                    case SERVER: {
                        String parameterizedName = config.getParameterizedName();
                        for (ExtensionConfig offered : offeredConfigs) {
                            if (!offered.getParameterizedName().equals(parameterizedName)) continue;
                            throw new BadMessageException("could not instantiate offered extension", t2);
                        }
                        throw new WebSocketException("could not instantiate negotiated extension", t2);
                    }
                    case CLIENT: {
                        String parameterizedName = config.getParameterizedName();
                        for (ExtensionConfig offered : offeredConfigs) {
                            if (!offered.getParameterizedName().equals(parameterizedName)) continue;
                            throw new WebSocketException("could not instantiate offered extension", t2);
                        }
                        throw new BadMessageException("could not instantiate negotiated extension", t2);
                    }
                }
                throw new IllegalStateException();
            }
            if (ext == null) continue;
            if (ext.isRsv1User() && this.rsvClaims[0] != null) {
                if (!LOG.isDebugEnabled()) continue;
                LOG.debug("Not adding extension {}. Extension {} already claimed RSV1", (Object)config, (Object)this.rsvClaims[0]);
                continue;
            }
            if (ext.isRsv2User() && this.rsvClaims[1] != null) {
                if (!LOG.isDebugEnabled()) continue;
                LOG.debug("Not adding extension {}. Extension {} already claimed RSV2", (Object)config, (Object)this.rsvClaims[1]);
                continue;
            }
            if (ext.isRsv3User() && this.rsvClaims[2] != null) {
                if (!LOG.isDebugEnabled()) continue;
                LOG.debug("Not adding extension {}. Extension {} already claimed RSV3", (Object)config, (Object)this.rsvClaims[2]);
                continue;
            }
            this.extensions.add(ext);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Adding Extension: {}", (Object)config);
            }
            if (ext.isRsv1User()) {
                this.rsvClaims[0] = ext;
            }
            if (ext.isRsv2User()) {
                this.rsvClaims[1] = ext;
            }
            if (!ext.isRsv3User()) continue;
            this.rsvClaims[2] = ext;
        }
        if (this.extensions != null && this.extensions.size() > 0) {
            Extension ext;
            ListIterator<Extension> exts = this.extensions.listIterator();
            while (exts.hasNext()) {
                ext = exts.next();
                ext.setNextOutgoingFrames(this.outgoing);
                this.outgoing = ext;
                if (!(ext instanceof DemandChain)) continue;
                DemandChain demandingExtension = (DemandChain)((Object)ext);
                demandingExtension.setNextDemand(this.demandChain::demand);
                this.demandChain = demandingExtension;
            }
            while (exts.hasPrevious()) {
                ext = exts.previous();
                ext.setNextIncomingFrames(this.incoming);
                this.incoming = ext;
            }
        }
    }

    @Override
    public void sendFrame(Frame frame, Callback callback, boolean batch) {
        if (this.outgoing == null) {
            throw new IllegalStateException();
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Extending out {} {} {}", frame, callback, batch);
        }
        this.outgoing.sendFrame(frame, callback, batch);
    }

    public void initialize(IncomingFrames incoming, OutgoingFrames outgoing, WebSocketCoreSession coreSession) {
        if (this.extensions == null) {
            throw new IllegalStateException();
        }
        if (this.extensions.isEmpty()) {
            this.incoming = incoming;
            this.outgoing = outgoing;
        } else {
            this.extensions.get(0).setNextOutgoingFrames(outgoing);
            this.extensions.get(this.extensions.size() - 1).setNextIncomingFrames(incoming);
        }
        for (Extension extension : this.extensions) {
            extension.setCoreSession(coreSession);
        }
    }

    public void demand(long n) {
        this.demandChain.demand(n);
    }

    public void setLastDemand(LongConsumer lastDemand) {
        this.lastDemand = lastDemand;
    }

    public Extension getRsv1User() {
        return this.rsvClaims[0];
    }

    public Extension getRsv2User() {
        return this.rsvClaims[1];
    }

    public Extension getRsv3User() {
        return this.rsvClaims[2];
    }

    public boolean isRsv1Used() {
        return this.rsvClaims[0] != null;
    }

    public boolean isRsv2Used() {
        return this.rsvClaims[1] != null;
    }

    public boolean isRsv3Used() {
        return this.rsvClaims[2] != null;
    }

    @Override
    public String dump() {
        return Dumpable.dump(this);
    }

    @Override
    public void dump(Appendable out, String indent) throws IOException {
        Dumpable.dumpObjects(out, indent, this, this.extensions == null ? Collections.emptyList() : this.extensions);
    }

    @Override
    public String dumpSelf() {
        return String.format("%s@%x[size=%d]", this.getClass().getSimpleName(), this.hashCode(), this.extensions.size());
    }

    public String toString() {
        StringBuilder s2 = new StringBuilder();
        s2.append("ExtensionStack[extensions=");
        if (this.extensions == null) {
            s2.append("<null>");
        } else {
            s2.append('[');
            boolean delim = false;
            for (Extension ext : this.extensions) {
                if (delim) {
                    s2.append(',');
                }
                if (ext == null) {
                    s2.append("<null>");
                } else {
                    s2.append(ext.getName());
                }
                delim = true;
            }
            s2.append(']');
        }
        s2.append(",incoming=").append(this.incoming == null ? "<null>" : this.incoming.getClass().getName());
        s2.append(",outgoing=").append(this.outgoing == null ? "<null>" : this.outgoing.getClass().getName());
        s2.append("]");
        return s2.toString();
    }
}

