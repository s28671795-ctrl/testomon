/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Address;
import java.io.Serializable;
import scala.Function0;
import scala.Function2;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.Tuple4;
import scala.collection.StringOps$;
import scala.math.Ordering;
import scala.package$;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;
import scala.util.matching.Regex;

public final class Address$
implements Serializable {
    public static final Address$ MODULE$ = new Address$();
    private static final Regex InvalidHostRegex = StringOps$.MODULE$.r$extension(Predef$.MODULE$.augmentString("_[^.]*$"));
    private static final Ordering<Address> addressOrdering = package$.MODULE$.Ordering().fromLessThan((Function2<Address, Address, Object> & Serializable)(a, b) -> BoxesRunTime.boxToBoolean(Address$.$anonfun$addressOrdering$1(a, b)));

    public Regex InvalidHostRegex() {
        return InvalidHostRegex;
    }

    public Address apply(String protocol, String system) {
        return new Address(protocol, system);
    }

    public Address apply(String protocol, String system, String host2, int port) {
        return new Address(protocol, system, new Some<String>(host2), new Some<Object>(BoxesRunTime.boxToInteger(port)));
    }

    public Ordering<Address> addressOrdering() {
        return addressOrdering;
    }

    public Address apply(String protocol, String system, Option<String> host2, Option<Object> port) {
        return new Address(protocol, system, host2, port);
    }

    public Option<Tuple4<String, String, Option<String>, Option<Object>>> unapply(Address x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple4<String, String, Option<String>, Option<Object>>>(new Tuple4<String, String, Option<String>, Option<Object>>(x$0.protocol(), x$0.system(), x$0.host(), x$0.port()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Address$.class);
    }

    public static final /* synthetic */ boolean $anonfun$addressOrdering$1(Address a, Address b) {
        if (a == b) {
            return false;
        }
        String string = a.protocol();
        String string2 = b.protocol();
        if (string == null ? string2 != null : !string.equals(string2)) {
            return a.system().compareTo(b.protocol()) < 0;
        }
        String string3 = a.system();
        String string4 = b.system();
        if (string3 == null ? string4 != null : !string3.equals(string4)) {
            return a.system().compareTo(b.system()) < 0;
        }
        Option<String> option = a.host();
        Option<String> option2 = b.host();
        if (option == null ? option2 != null : !option.equals(option2)) {
            return ((String)a.host().getOrElse((Function0<String> & Serializable)() -> "")).compareTo((String)b.host().getOrElse((Function0<String> & Serializable)() -> "")) < 0;
        }
        Option<Object> option3 = a.port();
        Option<Object> option4 = b.port();
        if (option3 == null ? option4 != null : !option3.equals(option4)) {
            return BoxesRunTime.unboxToInt(a.port().getOrElse(() -> 0)) < BoxesRunTime.unboxToInt(b.port().getOrElse(() -> 0));
        }
        return false;
    }

    private Address$() {
    }
}

