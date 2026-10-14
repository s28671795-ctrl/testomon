/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.modules;

import akka.actor.ActorSystem;
import com.google.inject.AbstractModule;
import com.incoresoft.vezha.database.EventsRepository;

public class EventManagerGuiceModule
extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(EventsRepository.class).asEagerSingleton();
        this.bind(ActorSystem.class).toInstance(ActorSystem.create("event-manager"));
    }
}

