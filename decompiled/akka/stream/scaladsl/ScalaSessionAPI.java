/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.scaladsl.ScalaSessionAPI$;
import java.io.Serializable;
import java.security.Principal;
import java.security.cert.Certificate;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import scala.Function0;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Option$;
import scala.Predef$;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005e3qa\u0003\u0007\u0011\u0002\u0007\u00051\u0003C\u0003\u001b\u0001\u0011\u00051\u0004C\u0003 \u0001\u0019\u0005\u0001\u0005C\u0003,\u0001\u0011\u0005A\u0006C\u0003D\u0001\u0011\u0005A\tC\u0003M\u0001\u0011\u0005A\u0006C\u0003N\u0001\u0011\u0005AiB\u0003O\u0019!\u0005qJB\u0003\f\u0019!\u0005\u0011\u000bC\u0003S\u0011\u0011\u00051\u000bC\u0003U\u0011\u0011\u0005QKA\bTG\u0006d\u0017mU3tg&|g.\u0011)J\u0015\tia\"\u0001\u0005tG\u0006d\u0017\rZ:m\u0015\ty\u0001#\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002#\u0005!\u0011m[6b\u0007\u0001\u0019\"\u0001\u0001\u000b\u0011\u0005UAR\"\u0001\f\u000b\u0003]\tQa]2bY\u0006L!!\u0007\f\u0003\r\u0005s\u0017PU3g\u0003\u0019!\u0013N\\5uIQ\tA\u0004\u0005\u0002\u0016;%\u0011aD\u0006\u0002\u0005+:LG/A\u0004tKN\u001c\u0018n\u001c8\u0016\u0003\u0005\u0002\"AI\u0015\u000e\u0003\rR!\u0001J\u0013\u0002\u0007M\u001cHN\u0003\u0002'O\u0005\u0019a.\u001a;\u000b\u0003!\nQA[1wCbL!AK\u0012\u0003\u0015M\u001bFjU3tg&|g.A\tm_\u000e\fGnQ3si&4\u0017nY1uKN,\u0012!\f\t\u0004]YJdBA\u00185\u001d\t\u00014'D\u00012\u0015\t\u0011$#\u0001\u0004=e>|GOP\u0005\u0002/%\u0011QGF\u0001\ba\u0006\u001c7.Y4f\u0013\t9\u0004H\u0001\u0003MSN$(BA\u001b\u0017!\tQ\u0014)D\u0001<\u0015\taT(\u0001\u0003dKJ$(B\u0001 @\u0003!\u0019XmY;sSRL(\"\u0001!\u0002\t)\fg/Y\u0005\u0003\u0005n\u00121bQ3si&4\u0017nY1uK\u0006qAn\\2bYB\u0013\u0018N\\2ja\u0006dW#A#\u0011\u0007U1\u0005*\u0003\u0002H-\t1q\n\u001d;j_:\u0004\"!\u0013&\u000e\u0003uJ!aS\u001f\u0003\u0013A\u0013\u0018N\\2ja\u0006d\u0017\u0001\u00059fKJ\u001cUM\u001d;jM&\u001c\u0017\r^3t\u00035\u0001X-\u001a:Qe&t7-\u001b9bY\u0006y1kY1mCN+7o]5p]\u0006\u0003\u0016\n\u0005\u0002Q\u00115\tAb\u0005\u0002\t)\u00051A(\u001b8jiz\"\u0012aT\u0001\u0006CB\u0004H.\u001f\u000b\u0003-^\u0003\"\u0001\u0015\u0001\t\u000baS\u0001\u0019A\u0011\u0002\u0011}\u001bXm]:j_:\u0004")
public interface ScalaSessionAPI {
    public static ScalaSessionAPI apply(SSLSession _session) {
        return ScalaSessionAPI$.MODULE$.apply(_session);
    }

    public SSLSession session();

    public static /* synthetic */ List localCertificates$(ScalaSessionAPI $this) {
        return $this.localCertificates();
    }

    default public List<Certificate> localCertificates() {
        return (List)Option$.MODULE$.apply(this.session().getLocalCertificates()).map((Function1<Certificate[], List> & Serializable)x$7 -> Predef$.MODULE$.wrapRefArray((Object[])x$7).toList()).getOrElse((Function0<Nil$> & Serializable)() -> Nil$.MODULE$);
    }

    public static /* synthetic */ Option localPrincipal$(ScalaSessionAPI $this) {
        return $this.localPrincipal();
    }

    default public Option<Principal> localPrincipal() {
        return Option$.MODULE$.apply(this.session().getLocalPrincipal());
    }

    public static /* synthetic */ List peerCertificates$(ScalaSessionAPI $this) {
        return $this.peerCertificates();
    }

    default public List<Certificate> peerCertificates() {
        List list;
        try {
            list = (List)Option$.MODULE$.apply(this.session().getPeerCertificates()).map((Function1<Certificate[], List> & Serializable)x$8 -> Predef$.MODULE$.wrapRefArray((Object[])x$8).toList()).getOrElse((Function0<Nil$> & Serializable)() -> Nil$.MODULE$);
        }
        catch (SSLPeerUnverifiedException sSLPeerUnverifiedException) {
            list = Nil$.MODULE$;
        }
        return list;
    }

    public static /* synthetic */ Option peerPrincipal$(ScalaSessionAPI $this) {
        return $this.peerPrincipal();
    }

    default public Option<Principal> peerPrincipal() {
        Option option;
        try {
            option = Option$.MODULE$.apply(this.session().getPeerPrincipal());
        }
        catch (SSLPeerUnverifiedException sSLPeerUnverifiedException) {
            option = None$.MODULE$;
        }
        return option;
    }

    public static void $init$(ScalaSessionAPI $this) {
    }
}

