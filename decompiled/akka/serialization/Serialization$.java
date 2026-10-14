/*
 * Decompiled with CFR 0.152.
 */
package akka.serialization;

import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.ActorRefProvider;
import akka.actor.ActorRefWithCell;
import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.actor.ExtendedActorSystem;
import akka.annotation.InternalApi;
import akka.serialization.Serialization;
import java.io.Serializable;
import scala.Function0;
import scala.MatchError;
import scala.Option;
import scala.util.DynamicVariable;
import scala.util.control.NonFatal$;

public final class Serialization$ {
    public static final Serialization$ MODULE$ = new Serialization$();
    @InternalApi
    private static final DynamicVariable<Serialization.Information> currentTransportInformation = new DynamicVariable<Object>(null);

    public DynamicVariable<Serialization.Information> currentTransportInformation() {
        return currentTransportInformation;
    }

    public String serializedActorPath(ActorRef actorRef2) {
        Serialization.Information information;
        block11: {
            Address address;
            ExtendedActorSystem originalSystem;
            ActorPath path;
            block13: {
                block12: {
                    ExtendedActorSystem extendedActorSystem;
                    path = actorRef2.path();
                    ActorRef actorRef3 = actorRef2;
                    if (actorRef3 instanceof ActorRefWithCell) {
                        ActorRefWithCell actorRefWithCell = (ActorRefWithCell)actorRef3;
                        extendedActorSystem = (ExtendedActorSystem)actorRefWithCell.underlying().system();
                    } else {
                        extendedActorSystem = null;
                    }
                    originalSystem = extendedActorSystem;
                    information = this.currentTransportInformation().value();
                    if (information == null) {
                        String string;
                        ExtendedActorSystem extendedActorSystem2 = originalSystem;
                        if (extendedActorSystem2 == null) {
                            return path.toSerializationFormat();
                        }
                        try {
                            string = path.toSerializationFormatWithAddress(extendedActorSystem2.provider().getDefaultAddress());
                        }
                        catch (Throwable throwable) {
                            Option<Throwable> option;
                            Throwable throwable2 = throwable;
                            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                                string = path.toSerializationFormat();
                            }
                            throw throwable;
                        }
                        return string;
                    }
                    if (information == null) break block11;
                    address = information.address();
                    ActorSystem system = information.system();
                    if (originalSystem == null) break block12;
                    ExtendedActorSystem extendedActorSystem3 = originalSystem;
                    ActorSystem actorSystem = system;
                    if (extendedActorSystem3 != null ? !extendedActorSystem3.equals(actorSystem) : actorSystem != null) break block13;
                }
                return path.toSerializationFormatWithAddress(address);
            }
            ActorRefProvider provider = originalSystem.provider();
            return path.toSerializationFormatWithAddress((Address)provider.getExternalAddressFor(address).getOrElse((Function0<Address> & Serializable)() -> provider.getDefaultAddress()));
        }
        throw new MatchError(information);
    }

    public <T> T withTransportInformation(ExtendedActorSystem system, Function0<T> f) {
        Serialization.Information info = system.provider().serializationInformation();
        if (this.currentTransportInformation().value() == info) {
            return f.apply();
        }
        return this.currentTransportInformation().withValue(info, f);
    }

    public Serialization.Information getCurrentTransportInformation() {
        Serialization.Information information = this.currentTransportInformation().value();
        if (information == null) {
            throw new IllegalStateException("currentTransportInformation is not set, use Serialization.withTransportInformation");
        }
        return information;
    }

    private Serialization$() {
    }
}

