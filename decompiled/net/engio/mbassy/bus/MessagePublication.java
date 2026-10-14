/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.bus;

import java.util.Collection;
import net.engio.mbassy.bus.BusRuntime;
import net.engio.mbassy.bus.IMessagePublication;
import net.engio.mbassy.bus.common.DeadMessage;
import net.engio.mbassy.bus.common.FilteredMessage;
import net.engio.mbassy.bus.error.PublicationError;
import net.engio.mbassy.subscription.Subscription;

public class MessagePublication
implements IMessagePublication {
    private final Collection<Subscription> subscriptions;
    private final Object message;
    private volatile State state = State.Initial;
    private volatile boolean dispatched = false;
    private final BusRuntime runtime;
    private PublicationError error = null;

    protected MessagePublication(BusRuntime runtime, Collection<Subscription> subscriptions, Object message, State initialState) {
        this.runtime = runtime;
        this.subscriptions = subscriptions;
        this.message = message;
        this.state = initialState;
    }

    public boolean add(Subscription subscription) {
        return this.subscriptions.add(subscription);
    }

    @Override
    public void execute() {
        this.state = State.Running;
        for (Subscription sub : this.subscriptions) {
            sub.publish(this, this.message);
        }
        this.state = State.Finished;
        if (!this.dispatched) {
            if (!this.isFilteredMessage() && !this.isDeadMessage()) {
                this.runtime.getProvider().publish(new FilteredMessage(this.message));
            } else if (!this.isDeadMessage()) {
                this.runtime.getProvider().publish(new DeadMessage(this.message));
            }
        }
    }

    @Override
    public boolean isFinished() {
        return this.state.equals((Object)State.Finished);
    }

    @Override
    public boolean isRunning() {
        return this.state.equals((Object)State.Running);
    }

    @Override
    public boolean isScheduled() {
        return this.state.equals((Object)State.Scheduled);
    }

    @Override
    public boolean hasError() {
        return this.error != null;
    }

    @Override
    public PublicationError getError() {
        return this.error;
    }

    public void markDispatched() {
        this.dispatched = true;
    }

    public void markError(PublicationError error) {
        this.error = error;
    }

    public MessagePublication markScheduled() {
        if (this.state.equals((Object)State.Initial)) {
            this.state = State.Scheduled;
        }
        return this;
    }

    @Override
    public boolean isDeadMessage() {
        return DeadMessage.class.equals(this.message.getClass());
    }

    @Override
    public boolean isFilteredMessage() {
        return FilteredMessage.class.equals(this.message.getClass());
    }

    @Override
    public Object getMessage() {
        return this.message;
    }

    public static class Factory {
        public MessagePublication createPublication(BusRuntime runtime, Collection<Subscription> subscriptions, Object message) {
            return new MessagePublication(runtime, subscriptions, message, State.Initial);
        }
    }

    private static enum State {
        Initial,
        Scheduled,
        Running,
        Finished;

    }
}

