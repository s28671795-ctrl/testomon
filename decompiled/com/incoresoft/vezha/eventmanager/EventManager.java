/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.eventmanager;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.inject.Inject;
import com.incoresoft.Utils.CollectionUtils;
import com.incoresoft.extensions.ExtensionsManager;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.eventmanager.Event;
import com.incoresoft.middleware.eventmanager.EventManagerMessage;
import com.incoresoft.middleware.http.exceptions.ValidationException;
import com.incoresoft.middleware.plugin.EventManagerExtension;
import com.incoresoft.middleware.validation.ValidationViolation;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import com.incoresoft.vezha.database.EventsRepository;
import com.incoresoft.vezha.dto.eventmanager.EventSchemeDTO;
import com.incoresoft.vezha.dto.eventmanager.NodeDTO;
import com.incoresoft.vezha.eventmanager.EventExecutorActor;
import com.incoresoft.vezha.eventmanager.EventInUseException;
import com.incoresoft.vezha.eventmanager.EventManagerQueueItem;
import com.incoresoft.vezha.eventmanager.EventNodeImpl;
import com.incoresoft.vezha.eventmanager.EventScheme;
import com.incoresoft.vezha.eventmanager.logic.actors.InputParamsMessage;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventManager {
    private static final Logger log = LoggerFactory.getLogger(EventManager.class);
    private static final String BLOCKING_IO_DISPATCHER = "akka.actor.blocking-io-dispatcher";
    private static final int SIMULTANEOUS_EVENTS = 128;
    private static final Duration MESSAGE_LIFETIME = Duration.ofHours(1L);
    private final Queue<EventManagerQueueItem> messageQueue = new ConcurrentLinkedQueue<EventManagerQueueItem>();
    private final EventsRepository repository;
    private final AnalyticsManager analyticsManager;
    private final ActorSystem system;
    private final ExtensionsManager extensionsManager;
    private final BeanValidator validator;
    private final Map<String, EventScheme> eventsMap = new ConcurrentHashMap<String, EventScheme>();
    private final Map<String, ActorRef> startedEventsMap = new ConcurrentHashMap<String, ActorRef>();

    @Inject
    public void init() {
        List<EventScheme> eventsList = this.repository.getItems();
        eventsList.forEach(eventScheme -> this.eventsMap.put(eventScheme.getUuid(), (EventScheme)eventScheme));
    }

    public EventScheme get(String uuid) {
        return this.eventsMap.get(uuid);
    }

    public EventScheme create(EventScheme eventScheme) {
        return this.add(eventScheme);
    }

    public EventScheme copy(String uuid, String title, String description) {
        EventScheme eventToCopy = Optional.ofNullable(this.eventsMap.get(uuid)).orElseThrow(() -> new IllegalArgumentException(String.format("Event with uuid %s not found", uuid)));
        EventScheme copiedScheme = new EventScheme(null, title, description, eventToCopy.getNodes(), eventToCopy.getClientId());
        return this.add(copiedScheme);
    }

    @NotNull
    private EventScheme add(EventScheme eventScheme) {
        EventScheme added = Optional.ofNullable(eventScheme).map(this.repository::add).orElseThrow(() -> new IllegalArgumentException("Event scheme is null"));
        this.eventsMap.put(added.getUuid(), added);
        return added;
    }

    public void update(EventScheme eventScheme) {
        Optional.ofNullable(eventScheme).map(this.repository::update).ifPresent(updated -> this.eventsMap.put(updated.getUuid(), (EventScheme)updated));
    }

    public void send(Event event, EventManagerMessage message) {
        JSONObject messageJSON = JSON.parseObject(JSON.toJSONString(message));
        messageJSON.putAll((Map<? extends String, ?>)event.getParams());
        this.queue(new EventManagerQueueItem(message.getTimestamp(), event, messageJSON));
    }

    public void send(Event event, JSONObject message) {
        this.queue(new EventManagerQueueItem(Instant.now(), event, message));
    }

    private void queue(EventManagerQueueItem queueItem) {
        this.messageQueue.offer(queueItem);
        if (this.startedEventsMap.size() < 128) {
            this.sendNextEventInQueue();
        }
    }

    private void sendNextEventInQueue() {
        Optional<EventManagerQueueItem> item = this.nextEventInQueue();
        if (item.isEmpty()) {
            return;
        }
        EventManagerQueueItem queueItem = item.get();
        String processId = UUID.randomUUID().toString();
        EventScheme eventScheme = this.eventsMap.get(queueItem.getEventId());
        if (eventScheme == null) {
            log.error("Cannot start event. Event with id {} not found", (Object)queueItem.getEventId());
            return;
        }
        ActorRef eventExecutor = this.system.actorOf(Props.create(EventExecutorActor.class, eventScheme, this.extensionsManager, () -> {
            this.startedEventsMap.remove(processId);
            this.sendNextEventInQueue();
        }).withDispatcher(BLOCKING_IO_DISPATCHER));
        this.startedEventsMap.put(processId, eventExecutor);
        eventExecutor.tell(new InputParamsMessage(queueItem.getMessage()), ActorRef.noSender());
    }

    @NotNull
    private Optional<EventManagerQueueItem> nextEventInQueue() {
        EventManagerQueueItem item = null;
        while (!this.messageQueue.isEmpty() && (item == null || this.itemIsExpired(item))) {
            item = this.messageQueue.poll();
        }
        return Optional.ofNullable(item);
    }

    private boolean itemIsExpired(EventManagerQueueItem item) {
        return item.getTimestamp().plus(MESSAGE_LIFETIME).isBefore(Instant.now());
    }

    public void cancel(String id) {
        Optional.ofNullable(this.startedEventsMap.remove(id)).ifPresent(this.system::stop);
    }

    public Collection<EventScheme> getEventsList() {
        return Collections.unmodifiableCollection(this.eventsMap.values());
    }

    public void delete(@NotNull EventScheme eventScheme) {
        Collection<Analytics> analyticsOptional = this.analyticsManager.getAll(analytics -> analytics.getEventsHolder().getEvents().stream().anyMatch(it -> Objects.equals(it.getId(), eventScheme.getUuid())));
        if (!analyticsOptional.isEmpty()) {
            int analyticsUsingEvent = analyticsOptional.iterator().next().getId();
            log.info("Cannot delete event. Event is used by analytics #{}", (Object)analyticsUsingEvent);
            throw new EventInUseException(analyticsUsingEvent);
        }
        this.repository.delete(eventScheme.getId());
        this.eventsMap.remove(eventScheme.getUuid());
    }

    public void validateEventSchemeOrThrow(EventSchemeDTO eventScheme) {
        LinkedList<ValidationViolation> allViolations = new LinkedList<ValidationViolation>(this.validator.validateObject(eventScheme, new Class[0]));
        if (CollectionUtils.isNotEmpty(eventScheme.getNodes())) {
            allViolations.addAll(this.validateNodes(eventScheme));
        }
        if (!allViolations.isEmpty()) {
            throw new ValidationException(allViolations);
        }
    }

    private List<ValidationViolation> validateNodes(EventSchemeDTO scheme2) {
        LinkedList<ValidationViolation> violationsList = new LinkedList<ValidationViolation>();
        Map eventManagerExtensions = this.extensionsManager.getAll(EventManagerExtension.class).stream().collect(Collectors.toUnmodifiableMap(EventManagerExtension::getExtensionId, Function.identity()));
        for (NodeDTO node : scheme2.getNodes()) {
            String extensionType = node.getType();
            EventManagerExtension extension = (EventManagerExtension)eventManagerExtensions.get(extensionType);
            if (extension == null) {
                log.error("Cannot validate node \"{}\" in event \"{}\". No extension found for type \"{}\".", node.getId(), scheme2.getId(), extensionType);
                continue;
            }
            try {
                List<ValidationViolation> validate = extension.validate(new EventNodeImpl(node)).stream().map(it -> new ValidationViolation(it.getType(), node.getId() + "." + it.getField(), it.getArgs())).toList();
                violationsList.addAll(validate);
            }
            catch (Exception exception) {
                throw new RuntimeException("Cannot validate node \"%s\" in event \"%s\".".formatted(node.getId(), scheme2.getId()), exception);
            }
        }
        return Collections.unmodifiableList(violationsList);
    }

    @Inject
    public EventManager(EventsRepository repository, AnalyticsManager analyticsManager, ActorSystem system, ExtensionsManager extensionsManager, BeanValidator validator) {
        this.repository = repository;
        this.analyticsManager = analyticsManager;
        this.system = system;
        this.extensionsManager = extensionsManager;
        this.validator = validator;
    }
}

