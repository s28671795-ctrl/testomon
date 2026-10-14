/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Address;
import akka.actor.AddressTerminated;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class AddressTerminated$
extends AbstractFunction1<Address, AddressTerminated>
implements Serializable {
    public static final AddressTerminated$ MODULE$ = new AddressTerminated$();

    @Override
    public final String toString() {
        return "AddressTerminated";
    }

    @Override
    public AddressTerminated apply(Address address) {
        return new AddressTerminated(address);
    }

    public Option<Address> unapply(AddressTerminated x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Address>(x$0.address());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(AddressTerminated$.class);
    }

    private AddressTerminated$() {
    }
}

