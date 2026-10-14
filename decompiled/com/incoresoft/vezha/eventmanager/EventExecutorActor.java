/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.eventmanager;

import akka.actor.AbstractActor;
import com.incoresoft.extensions.ExtensionsManager;
import com.incoresoft.middleware.plugin.EventManagerExtension;
import com.incoresoft.vezha.eventmanager.EventNodeContextImpl;
import com.incoresoft.vezha.eventmanager.EventNodeImpl;
import com.incoresoft.vezha.eventmanager.EventScheme;
import com.incoresoft.vezha.eventmanager.logic.actors.InputParamsMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventExecutorActor
extends AbstractActor {
    private static final Logger log = LoggerFactory.getLogger(EventExecutorActor.class);
    private final Map<String, EventManagerExtension> eventManagerExtensions;
    private final Map<String, EventNodeImpl> nodeMap = new HashMap<String, EventNodeImpl>();
    private final EventScheme eventScheme;
    private final TerminationListener terminationListener;

    public EventExecutorActor(@NotNull EventScheme eventScheme, @NotNull ExtensionsManager extensionsManager, @Nullable TerminationListener listener) {
        boolean oneStartNodePresent;
        this.terminationListener = listener;
        this.eventScheme = Objects.requireNonNull(eventScheme);
        eventScheme.getNodes().stream().map(EventNodeImpl::new).forEach(n -> this.nodeMap.put(n.getId(), (EventNodeImpl)n));
        boolean bl = oneStartNodePresent = eventScheme.getNodes().stream().filter(n -> n.getType().equals("start")).count() == 1L;
        if (!oneStartNodePresent) {
            throw new IllegalArgumentException("Event must contain one start node");
        }
        this.eventManagerExtensions = extensionsManager.getAll(EventManagerExtension.class).stream().collect(Collectors.toUnmodifiableMap(EventManagerExtension::getExtensionId, Function.identity()));
    }

    @Override
    public AbstractActor.Receive createReceive() {
        return this.receiveBuilder().match(InputParamsMessage.class, inputParamsMessage -> {
            String nextNodeId;
            Optional<EventNodeImpl> startNodeOptional = this.nodeMap.values().stream().filter(n -> n.getExtensionId().equals("start")).findAny();
            if (startNodeOptional.isEmpty()) {
                log.error("Error during event start: event does not contain start node. Event terminated");
                this.context().stop(this.self());
                return;
            }
            EventNodeImpl currentNode = startNodeOptional.get();
            do {
                Integer outputId;
                EventManagerExtension extension;
                if ((extension = this.eventManagerExtensions.get(currentNode.getExtensionId())) == null) {
                    log.error("No extension found for node \"{}\" of type \"{}\" in event \"{}\". Event terminated.", currentNode.getId(), currentNode.getExtensionId(), this.eventScheme.getUuid());
                    this.context().stop(this.self());
                    return;
                }
                EventNodeContextImpl eventNodeContext = new EventNodeContextImpl(currentNode, inputParamsMessage.getParams());
                try {
                    outputId = extension.execute(eventNodeContext);
                }
                catch (Throwable e) {
                    log.error("Exception during execution of node \"{}\" in event \"{}\". Event terminated", currentNode.getId(), this.eventScheme.getUuid(), e);
                    this.context().stop(this.self());
                    return;
                }
                nextNodeId = currentNode.getOutputs().get(outputId);
                if (nextNodeId != null) continue;
                this.context().stop(this.self());
                return;
            } while ((currentNode = this.nodeMap.get(nextNodeId)) != null);
            log.error("Node with id \"{}\" does not exist in event \"{}\". Event terminated.", (Object)nextNodeId, (Object)this.eventScheme.getUuid());
            this.context().stop(this.self());
        }).build();
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
        if (this.terminationListener != null) {
            this.terminationListener.onTerminate();
        }
    }

    public static interface TerminationListener {
        public void onTerminate();
    }
}

