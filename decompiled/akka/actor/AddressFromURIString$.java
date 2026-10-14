/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Address;
import akka.actor.Address$;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import scala.None$;
import scala.Option;
import scala.Some;

public final class AddressFromURIString$ {
    public static final AddressFromURIString$ MODULE$ = new AddressFromURIString$();

    public Option<Address> unapply(String addr) {
        Option option;
        try {
            option = this.unapply(new URI(addr));
        }
        catch (URISyntaxException uRISyntaxException) {
            option = None$.MODULE$;
        }
        return option;
    }

    public Option<Address> unapply(URI uri) {
        if (uri == null) {
            return None$.MODULE$;
        }
        if (uri.getScheme() == null || uri.getUserInfo() == null && uri.getHost() == null) {
            return None$.MODULE$;
        }
        if (uri.getUserInfo() == null) {
            if (uri.getPort() != -1) {
                return None$.MODULE$;
            }
            return new Some<Address>(Address$.MODULE$.apply(uri.getScheme(), uri.getHost()));
        }
        if (uri.getHost() == null || uri.getPort() == -1) {
            return None$.MODULE$;
        }
        return new Some<Address>(uri.getUserInfo() == null ? Address$.MODULE$.apply(uri.getScheme(), uri.getHost()) : Address$.MODULE$.apply(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort()));
    }

    public Address apply(String addr) {
        Option<Address> option;
        String string = addr;
        if (string != null && !(option = this.unapply(string)).isEmpty()) {
            Address address = option.get();
            return address;
        }
        throw new MalformedURLException(addr);
    }

    public Address parse(String addr) {
        return this.apply(addr);
    }

    private AddressFromURIString$() {
    }
}

