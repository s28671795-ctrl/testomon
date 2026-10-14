/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.Address$;
import akka.actor.InternalActorRef;
import akka.actor.LocalRef;
import akka.actor.MinimalActorRef;
import akka.actor.RootActorPath;
import akka.actor.SerializedNobody;
import akka.dispatch.sysmsg.SystemMessage;
import java.io.ObjectStreamException;
import scala.Product;
import scala.collection.Iterator;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

public final class Nobody$
extends InternalActorRef
implements MinimalActorRef,
Product {
    public static final Nobody$ MODULE$ = new Nobody$();
    private static final RootActorPath path;
    private static final SerializedNobody serialized;

    static {
        LocalRef.$init$(MODULE$);
        MinimalActorRef.$init$(MODULE$);
        Product.$init$(MODULE$);
        path = new RootActorPath(Address$.MODULE$.apply("akka", "all-systems"), "/Nobody");
        serialized = new SerializedNobody();
    }

    @Override
    public String productElementName(int n) {
        return Product.productElementName$(this, n);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public InternalActorRef getParent() {
        return MinimalActorRef.getParent$(this);
    }

    @Override
    public InternalActorRef getChild(Iterator<String> names) {
        return MinimalActorRef.getChild$(this, names);
    }

    @Override
    public void start() {
        MinimalActorRef.start$(this);
    }

    @Override
    public void suspend() {
        MinimalActorRef.suspend$(this);
    }

    @Override
    public void resume(Throwable causedByFailure) {
        MinimalActorRef.resume$(this, causedByFailure);
    }

    @Override
    public void stop() {
        MinimalActorRef.stop$(this);
    }

    @Override
    public boolean isTerminated() {
        return MinimalActorRef.isTerminated$(this);
    }

    @Override
    public void $bang(Object message, ActorRef sender) {
        MinimalActorRef.$bang$(this, message, sender);
    }

    @Override
    public ActorRef $bang$default$2(Object message) {
        return MinimalActorRef.$bang$default$2$(this, message);
    }

    @Override
    public void sendSystemMessage(SystemMessage message) {
        MinimalActorRef.sendSystemMessage$(this, message);
    }

    @Override
    public void restart(Throwable cause) {
        MinimalActorRef.restart$(this, cause);
    }

    @Override
    public final boolean isLocal() {
        return LocalRef.isLocal$(this);
    }

    @Override
    public RootActorPath path() {
        return path;
    }

    public Nothing$ provider() {
        throw new UnsupportedOperationException("Nobody does not provide");
    }

    private SerializedNobody serialized() {
        return serialized;
    }

    @Override
    public Object writeReplace() throws ObjectStreamException {
        return this.serialized();
    }

    @Override
    public String productPrefix() {
        return "Nobody";
    }

    @Override
    public int productArity() {
        return 0;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof Nobody$;
    }

    private Nobody$() {
    }
}

