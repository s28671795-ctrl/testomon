/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.event.LogSource$;
import akka.event.Logging$;
import akka.util.ManifestInfo$;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.collection.immutable.Map;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.ObjectRef;

@ScalaSignature(bytes="\u0006\u0005\u0005\rv!\u0002\u0015*\u0011\u0003qc!\u0002\u0019*\u0011\u0003\t\u0004BB%\u0002\t\u0003\t9\u0005C\u0005\u0002J\u0005\u0011\r\u0011\"\u0003\u0002L!A\u0011\u0011K\u0001!\u0002\u0013\ti\u0005C\u0005\u0002T\u0005\u0011\r\u0011\"\u0003\u0002L!A\u0011QK\u0001!\u0002\u0013\ti\u0005C\u0005\u0002X\u0005\u0011\r\u0011\"\u0003\u0002L!A\u0011\u0011L\u0001!\u0002\u0013\ti\u0005C\u0005\u0002\\\u0005\u0011\r\u0011\"\u0003\u0002L!A\u0011QL\u0001!\u0002\u0013\ti\u0005C\u0005\u0002`\u0005\u0011\r\u0011\"\u0003\u0002L!A\u0011\u0011M\u0001!\u0002\u0013\ti\u0005C\u0005\u0002d\u0005\u0011\r\u0011\"\u0003\u0002L!A\u0011QM\u0001!\u0002\u0013\ti\u0005C\u0005\u0002h\u0005\u0011\r\u0011\"\u0003\u0002j!A\u0011\u0011O\u0001!\u0002\u0013\tY\u0007C\u0004\u0002t\u0005!\t%!\u001e\t\u000f\u0005M\u0014\u0001\"\u0011\u0002\u0000!9\u0011\u0011R\u0001\u0005B\u0005-\u0005bBAG\u0003\u0011\u0005\u0013q\u0012\u0004\u0005=\u0006\u0011q\f\u0003\u0005k+\t\u0015\r\u0011\"\u0001l\u0011!aWC!A!\u0002\u0013I\u0006\"B%\u0016\t\u0003i\u0007bB8\u0016\u0005\u0004%I\u0001\u001d\u0005\u0007gV\u0001\u000b\u0011B9\t\u000bQ,B\u0011I;\t\u000bm,B\u0011\t?\t\u000f\u0005-Q\u0003\"\u0011\u0002\u000e!9\u0011qB\u000b\u0005B\u0005E\u0001\u0002CA\u000b\u0003\u0011\u0005\u0011&a%\u0007\tAJ#a\u0010\u0005\t\u0007\u0002\u0012)\u0019!C\u0001\t\"A\u0001\n\tB\u0001B\u0003%Q\tC\u0003JA\u0011\u0005!\nC\u0004MA\t\u0007I\u0011A'\t\u000f\u0005M\u0001\u0005)A\u0005\u001d\"9\u0011Q\u0003\u0011\u0005\u0002\u0005]\u0001bBA\u000bA\u0011\u0005\u0011QG\u0001\r\u001b\u0006t\u0017NZ3ti&sgm\u001c\u0006\u0003U-\nA!\u001e;jY*\tA&\u0001\u0003bW.\f7\u0001\u0001\t\u0003_\u0005i\u0011!\u000b\u0002\r\u001b\u0006t\u0017NZ3ti&sgm\\\n\u0006\u0003IB\u0014\u0011\t\t\u0003gYj\u0011\u0001\u000e\u0006\u0002k\u0005)1oY1mC&\u0011q\u0007\u000e\u0002\u0007\u0003:L(+\u001a4\u0011\u0007ebd(D\u0001;\u0015\tY4&A\u0003bGR|'/\u0003\u0002>u\tYQ\t\u001f;f]NLwN\\%e!\ty\u0003eE\u0002!e\u0001\u0003\"!O!\n\u0005\tS$!C#yi\u0016t7/[8o\u0003\u0019\u0019\u0018p\u001d;f[V\tQ\t\u0005\u0002:\r&\u0011qI\u000f\u0002\u0014\u000bb$XM\u001c3fI\u0006\u001bGo\u001c:TsN$X-\\\u0001\bgf\u001cH/Z7!\u0003\u0019a\u0014N\\5u}Q\u0011ah\u0013\u0005\u0006\u0007\u000e\u0002\r!R\u0001\tm\u0016\u00148/[8ogV\ta\n\u0005\u0003P-fcfB\u0001)U!\t\tF'D\u0001S\u0015\t\u0019V&\u0001\u0004=e>|GOP\u0005\u0003+R\na\u0001\u0015:fI\u00164\u0017BA,Y\u0005\ri\u0015\r\u001d\u0006\u0003+R\u0002\"a\u0014.\n\u0005mC&AB*ue&tw\r\u0005\u0002^+9\u0011q\u0006\u0001\u0002\b-\u0016\u00148/[8o'\r)\"\u0007\u0019\t\u0004C\u001aDW\"\u00012\u000b\u0005\r$\u0017\u0001\u00027b]\u001eT\u0011!Z\u0001\u0005U\u00064\u0018-\u0003\u0002hE\nQ1i\\7qCJ\f'\r\\3\u0011\u0005%,R\"A\u0001\u0002\u000fY,'o]5p]V\t\u0011,\u0001\u0005wKJ\u001c\u0018n\u001c8!)\tAg\u000eC\u0003k1\u0001\u0007\u0011,\u0001\u0003j[BdW#A9\u0011\u0005=\u0012\u0018B\u00010*\u0003\u0015IW\u000e\u001d7!\u0003%\u0019w.\u001c9be\u0016$v\u000e\u0006\u0002wsB\u00111g^\u0005\u0003qR\u00121!\u00138u\u0011\u0015Q8\u00041\u0001i\u0003\u0015yG\u000f[3s\u0003\u0019)\u0017/^1mgR\u0019Q0!\u0001\u0011\u0005Mr\u0018BA@5\u0005\u001d\u0011un\u001c7fC:Dq!a\u0001\u001d\u0001\u0004\t)!A\u0001p!\r\u0019\u0014qA\u0005\u0004\u0003\u0013!$aA!os\u0006A\u0001.Y:i\u0007>$W\rF\u0001w\u0003!!xn\u0015;sS:<G#A-\u0002\u0013Y,'o]5p]N\u0004\u0013\u0001E2iK\u000e\\7+Y7f-\u0016\u00148/[8o)\u001di\u0018\u0011DA\u000f\u0003cAa!a\u0007'\u0001\u0004I\u0016a\u00039s_\u0012,8\r\u001e(b[\u0016Dq!a\b'\u0001\u0004\t\t#\u0001\u0007eKB,g\u000eZ3oG&,7\u000fE\u0003\u0002$\u00055\u0012,\u0004\u0002\u0002&)!\u0011qEA\u0015\u0003%IW.\\;uC\ndWMC\u0002\u0002,Q\n!bY8mY\u0016\u001cG/[8o\u0013\u0011\ty#!\n\u0003\u0007M+\u0017\u000f\u0003\u0004\u00024\u0019\u0002\r!`\u0001\u000bY><w+\u0019:oS:<G#C?\u00028\u0005e\u00121HA\u001f\u0011\u0019\tYb\na\u00013\"9\u0011qD\u0014A\u0002\u0005\u0005\u0002BBA\u001aO\u0001\u0007Q\u0010\u0003\u0004\u0002@\u001d\u0002\r!`\u0001\u000fi\"\u0014xn^#yG\u0016\u0004H/[8o!\rI\u00141I\u0005\u0004\u0003\u000bR$aE#yi\u0016t7/[8o\u0013\u0012\u0004&o\u001c<jI\u0016\u0014H#\u0001\u0018\u0002\u0013%k\u0007\u000f\u001c+ji2,WCAA'!\r\t\u0017qJ\u0005\u00037\n\f!\"S7qYRKG\u000f\\3!\u0003-IU\u000e\u001d7WKJ\u001c\u0018n\u001c8\u0002\u0019%k\u0007\u000f\u001c,feNLwN\u001c\u0011\u0002\u0015%k\u0007\u000f\u001c,f]\u0012|'/A\u0006J[Bdg+\u001a8e_J\u0004\u0013A\u0003\"v]\u0012dWMT1nK\u0006Y!)\u001e8eY\u0016t\u0015-\\3!\u00035\u0011UO\u001c3mKZ+'o]5p]\u0006q!)\u001e8eY\u00164VM]:j_:\u0004\u0013\u0001\u0004\"v]\u0012dWMV3oI>\u0014\u0018!\u0004\"v]\u0012dWMV3oI>\u0014\b%\u0001\u0007l]><hNV3oI>\u00148/\u0006\u0002\u0002lA1\u00111EA7\u0003\u001bJA!a\u001c\u0002&\t\u00191+\u001a;\u0002\u001b-twn\u001e8WK:$wN]:!\u0003\r9W\r\u001e\u000b\u0004}\u0005]\u0004BB\"\u0012\u0001\u0004\tI\bE\u0002:\u0003wJ1!! ;\u0005-\t5\r^8s'f\u001cH/Z7\u0015\u0007y\n\t\t\u0003\u0004D%\u0001\u0007\u00111\u0011\t\u0004s\u0005\u0015\u0015bAADu\tQ2\t\\1tg&\u001c\u0017i\u0019;peNK8\u000f^3n!J|g/\u001b3fe\u00061An\\8lkB,\u0012!X\u0001\u0010GJ,\u0017\r^3FqR,gn]5p]R\u0019a(!%\t\u000b\r#\u0002\u0019A#\u0015\u0011\u0005U\u00151TAO\u0003?\u0003BaMAL3&\u0019\u0011\u0011\u0014\u001b\u0003\r=\u0003H/[8o\u0011\u0019\tYb\ba\u00013\"9\u0011qD\u0010A\u0002\u0005\u0005\u0002B\u0002' \u0001\u0004\t\t\u000b\u0005\u0003P-fC\u0007")
public final class ManifestInfo
implements Extension {
    private final ExtendedActorSystem system;
    private final Map<String, Version> versions;

    public static ManifestInfo createExtension(ExtendedActorSystem system) {
        return ManifestInfo$.MODULE$.createExtension(system);
    }

    public static ManifestInfo$ lookup() {
        return ManifestInfo$.MODULE$.lookup();
    }

    public static ManifestInfo get(ClassicActorSystemProvider system) {
        return ManifestInfo$.MODULE$.get(system);
    }

    public static ManifestInfo get(ActorSystem system) {
        return ManifestInfo$.MODULE$.get(system);
    }

    public static Extension apply(ClassicActorSystemProvider system) {
        return ManifestInfo$.MODULE$.apply(system);
    }

    public static Extension apply(ActorSystem system) {
        return ManifestInfo$.MODULE$.apply(system);
    }

    public ExtendedActorSystem system() {
        return this.system;
    }

    public Map<String, Version> versions() {
        return this.versions;
    }

    public boolean checkSameVersion(String productName, Seq<String> dependencies, boolean logWarning) {
        return this.checkSameVersion(productName, dependencies, logWarning, this.system().settings().FailMixedVersions());
    }

    public boolean checkSameVersion(String productName, Seq<String> dependencies, boolean logWarning, boolean throwException) {
        Option<String> option = ManifestInfo$.MODULE$.checkSameVersion(productName, dependencies, this.versions());
        if (option instanceof Some) {
            Some some = (Some)option;
            String message = (String)some.value();
            if (logWarning) {
                Logging$.MODULE$.apply(this.system(), ManifestInfo.class, LogSource$.MODULE$.fromAnyClass()).warning(message);
            }
            if (throwException) {
                throw new IllegalStateException(message);
            }
            return false;
        }
        if (None$.MODULE$.equals(option)) {
            return true;
        }
        throw new MatchError(option);
    }

    private final /* synthetic */ void liftedTree1$1(ObjectRef manifests$1) {
        try {
            Enumeration<URL> resources = this.system().dynamicAccess().classLoader().getResources("META-INF/MANIFEST.MF");
            while (resources.hasMoreElements()) {
                try (InputStream ios = resources.nextElement().openStream();){
                    String vendor;
                    String version;
                    String title;
                    block17: {
                        String string;
                        String string2;
                        Attributes attributes;
                        block16: {
                            String string3;
                            block15: {
                                Manifest manifest = new Manifest(ios);
                                attributes = manifest.getMainAttributes();
                                String string4 = attributes.getValue(new Attributes.Name(ManifestInfo$.MODULE$.akka$util$ManifestInfo$$ImplTitle()));
                                switch (string4 == null ? 0 : string4.hashCode()) {
                                    case 0: {
                                        if (string4 != null) break;
                                        string3 = attributes.getValue(new Attributes.Name(ManifestInfo$.MODULE$.akka$util$ManifestInfo$$BundleName()));
                                        break block15;
                                    }
                                }
                                string3 = string4;
                            }
                            title = string3;
                            String string5 = attributes.getValue(new Attributes.Name(ManifestInfo$.MODULE$.akka$util$ManifestInfo$$ImplVersion()));
                            switch (string5 == null ? 0 : string5.hashCode()) {
                                case 0: {
                                    if (string5 != null) break;
                                    string2 = attributes.getValue(new Attributes.Name(ManifestInfo$.MODULE$.akka$util$ManifestInfo$$BundleVersion()));
                                    break block16;
                                }
                            }
                            string2 = string5;
                        }
                        version = string2;
                        String string6 = attributes.getValue(new Attributes.Name(ManifestInfo$.MODULE$.akka$util$ManifestInfo$$ImplVendor()));
                        switch (string6 == null ? 0 : string6.hashCode()) {
                            case 0: {
                                if (string6 != null) break;
                                string = attributes.getValue(new Attributes.Name(ManifestInfo$.MODULE$.akka$util$ManifestInfo$$BundleVendor()));
                                break block17;
                            }
                        }
                        string = vendor = string6;
                    }
                    if (title == null || version == null || vendor == null || !ManifestInfo$.MODULE$.akka$util$ManifestInfo$$knownVendors().apply(vendor)) continue;
                    manifests$1.elem = (Map)((Map)manifests$1.elem).updated(title, new Version(version));
                }
            }
        }
        catch (IOException ioe) {
            Logging$.MODULE$.apply(this.system(), ManifestInfo.class, LogSource$.MODULE$.fromAnyClass()).warning("Could not read manifest information. {}", ioe);
        }
    }

    public ManifestInfo(ExtendedActorSystem system) {
        this.system = system;
        ObjectRef<Object> manifests = ObjectRef.create(Predef$.MODULE$.Map().empty());
        this.liftedTree1$1(manifests);
        this.versions = (Map)manifests.elem;
    }

    public static final class Version
    implements Comparable<Version> {
        private final String version;
        private final akka.util.Version impl;

        public String version() {
            return this.version;
        }

        private akka.util.Version impl() {
            return this.impl;
        }

        @Override
        public int compareTo(Version other) {
            return this.impl().compareTo(other.impl());
        }

        public boolean equals(Object o) {
            Object object = o;
            if (object instanceof Version) {
                Version version = (Version)object;
                return this.impl().equals(version.impl());
            }
            return false;
        }

        public int hashCode() {
            return this.impl().hashCode();
        }

        public String toString() {
            return this.impl().toString();
        }

        public Version(String version) {
            this.version = version;
            this.impl = new akka.util.Version(version);
        }
    }
}

