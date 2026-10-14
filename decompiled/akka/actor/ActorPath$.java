/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorPath;
import akka.actor.ActorPathExtractor$;
import akka.actor.Address;
import akka.actor.InvalidActorNameException;
import akka.actor.RootActorPath;
import akka.actor.RootActorPath$;
import java.io.Serializable;
import java.net.MalformedURLException;
import scala.Option;
import scala.Predef$;
import scala.Tuple2;
import scala.collection.StringOps$;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.Iterable;
import scala.collection.immutable.Nil$;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Nothing$;

public final class ActorPath$
implements Serializable {
    public static final ActorPath$ MODULE$ = new ActorPath$();
    private static final Iterable<String> emptyActorPath = new $colon$colon<Nothing$>((Nothing$)((Object)""), Nil$.MODULE$);

    public ActorPath fromString(String s2) {
        Option<Tuple2<Address, Iterable<String>>> option;
        String string = s2;
        if (string != null && !(option = ActorPathExtractor$.MODULE$.unapply(string)).isEmpty()) {
            Address address = option.get()._1();
            Iterable<String> elems = option.get()._2();
            return new RootActorPath(address, RootActorPath$.MODULE$.apply$default$2()).$div(elems);
        }
        throw new MalformedURLException(new StringBuilder(27).append("cannot parse as ActorPath: ").append(s2).toString());
    }

    private final String ValidSymbols() {
        return "-_.*$+:@&=,!~';";
    }

    private final int ValidPathCode() {
        return -1;
    }

    private final int EmptyPathCode() {
        return -2;
    }

    public final void validatePathElement(String element) {
        this.validatePathElement(element, null);
    }

    public final void validatePathElement(String element, String fullPath) {
        int n = this.findInvalidPathElementCharPosition(element);
        switch (n) {
            case -1: {
                return;
            }
            case -2: {
                throw new InvalidActorNameException(new StringBuilder(37).append("Actor path element must not be empty ").append(ActorPath$.fullPathMsg$1(fullPath)).toString());
            }
        }
        throw new InvalidActorNameException(new StringBuilder(195).append("Invalid actor path element [").append(element).append("]").append(ActorPath$.fullPathMsg$1(fullPath)).append(", illegal character [").append(StringOps$.MODULE$.apply$extension(Predef$.MODULE$.augmentString(element), n)).append("] at position: ").append(n).append(". ").append("Actor paths MUST: ").append("not start with `$`, ").append("include only ASCII letters and can only contain these special characters: ").append("-_.*$+:@&=,!~';").append(".").toString());
    }

    public final boolean isValidPathElement(String s2) {
        return this.findInvalidPathElementCharPosition(s2) == -1;
    }

    private final int findInvalidPathElementCharPosition(String s2) {
        if (s2.isEmpty()) {
            return -2;
        }
        int len = s2.length();
        if (len > 0 && s2.charAt(0) != '$') {
            return this.validate$1(0, len, s2);
        }
        return 0;
    }

    public final Iterable<String> emptyActorPath() {
        return emptyActorPath;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ActorPath$.class);
    }

    private static final String fullPathMsg$1(String fullPath$1) {
        if (fullPath$1 != null) {
            return new StringBuilder(13).append(" (in path [").append(fullPath$1).append("])").toString();
        }
        return "";
    }

    private static final boolean isValidChar$1(char c) {
        return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9' || "-_.*$+:@&=,!~';".indexOf(c) != -1;
    }

    private static final boolean isHexChar$1(char c) {
        return c >= 'a' && c <= 'f' || c >= 'A' && c <= 'F' || c >= '0' && c <= '9';
    }

    private final int validate$1(int pos, int len$1, String s$1) {
        while (pos < len$1) {
            char c = s$1.charAt(pos);
            if (ActorPath$.isValidChar$1(c)) {
                ++pos;
                continue;
            }
            if ('%' == c && pos + 2 < len$1 && ActorPath$.isHexChar$1(s$1.charAt(pos + 1)) && ActorPath$.isHexChar$1(s$1.charAt(pos + 2))) {
                pos += 3;
                continue;
            }
            return pos;
        }
        return -1;
    }

    private ActorPath$() {
    }
}

