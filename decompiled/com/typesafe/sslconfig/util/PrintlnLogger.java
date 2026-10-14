/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.util;

import com.typesafe.sslconfig.util.LoggerFactory;
import com.typesafe.sslconfig.util.NoDepsLogger;
import com.typesafe.sslconfig.util.PrintlnLogger$;
import scala.Predef$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005u;Q!\u0004\b\t\u0002]1Q!\u0007\b\t\u0002iAQ!I\u0001\u0005\u0002\tBQaI\u0001\u0005\u0002\u00112A!\u0007\b\u0003Q!AA\u0006\u0002B\u0001B\u0003%Q\u0006C\u0003\"\t\u0011\u0005\u0001\bC\u0003<\t\u0011\u0005C\bC\u0003C\t\u0011\u00053\tC\u0003F\t\u0011\u0005c\tC\u0003I\t\u0011\u0005\u0013\nC\u0003I\t\u0011\u00053\nC\u0003Y\t\u0011\u0005\u0013,A\u0007Qe&tG\u000f\u001c8M_\u001e<WM\u001d\u0006\u0003\u001fA\tA!\u001e;jY*\u0011\u0011CE\u0001\ngNd7m\u001c8gS\u001eT!a\u0005\u000b\u0002\u0011QL\b/Z:bM\u0016T\u0011!F\u0001\u0004G>l7\u0001\u0001\t\u00031\u0005i\u0011A\u0004\u0002\u000e!JLg\u000e\u001e7o\u0019><w-\u001a:\u0014\u0005\u0005Y\u0002C\u0001\u000f \u001b\u0005i\"\"\u0001\u0010\u0002\u000bM\u001c\u0017\r\\1\n\u0005\u0001j\"AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0002/\u00059a-Y2u_JLH#A\u0013\u0011\u0005a1\u0013BA\u0014\u000f\u00055aunZ4fe\u001a\u000b7\r^8ssN\u0011A!\u000b\t\u00031)J!a\u000b\b\u0003\u00199{G)\u001a9t\u0019><w-\u001a:\u0002\t9\fW.\u001a\t\u0003]Ur!aL\u001a\u0011\u0005AjR\"A\u0019\u000b\u0005I2\u0012A\u0002\u001fs_>$h(\u0003\u00025;\u00051\u0001K]3eK\u001aL!AN\u001c\u0003\rM#(/\u001b8h\u0015\t!T\u0004\u0006\u0002:uA\u0011\u0001\u0004\u0002\u0005\u0006Y\u0019\u0001\r!L\u0001\u0006I\u0016\u0014Wo\u001a\u000b\u0003{\u0001\u0003\"\u0001\b \n\u0005}j\"\u0001B+oSRDQ!Q\u0004A\u00025\n1!\\:h\u0003\u0011IgNZ8\u0015\u0005u\"\u0005\"B!\t\u0001\u0004i\u0013\u0001B<be:$\"!P$\t\u000b\u0005K\u0001\u0019A\u0017\u0002\u000b\u0015\u0014(o\u001c:\u0015\u0005uR\u0005\"B!\u000b\u0001\u0004iCcA\u001fM\u001b\")\u0011i\u0003a\u0001[!)aj\u0003a\u0001\u001f\u0006IA\u000f\u001b:po\u0006\u0014G.\u001a\t\u0003!Vs!!U*\u000f\u0005A\u0012\u0016\"\u0001\u0010\n\u0005Qk\u0012a\u00029bG.\fw-Z\u0005\u0003-^\u0013\u0011\u0002\u00165s_^\f'\r\\3\u000b\u0005Qk\u0012AD5t\t\u0016\u0014WoZ#oC\ndW\rZ\u000b\u00025B\u0011AdW\u0005\u00039v\u0011qAQ8pY\u0016\fg\u000e")
public final class PrintlnLogger
extends NoDepsLogger {
    private final String name;

    public static LoggerFactory factory() {
        return PrintlnLogger$.MODULE$.factory();
    }

    @Override
    public void debug(String msg) {
        Predef$.MODULE$.println(new StringBuilder(10).append("[DEBUG][").append(this.name).append("] ").append(msg).toString());
    }

    @Override
    public void info(String msg) {
        Predef$.MODULE$.println(new StringBuilder(9).append("[INFO][").append(this.name).append("] ").append(msg).toString());
    }

    @Override
    public void warn(String msg) {
        Predef$.MODULE$.println(new StringBuilder(9).append("[WARN][").append(this.name).append("] ").append(msg).toString());
    }

    @Override
    public void error(String msg) {
        Predef$.MODULE$.println(new StringBuilder(10).append("[ERROR][").append(this.name).append("] ").append(msg).toString());
    }

    @Override
    public void error(String msg, Throwable throwable) {
        Predef$.MODULE$.println(new StringBuilder(10).append("[ERROR][").append(this.name).append("] ").append(msg).toString());
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    public PrintlnLogger(String name) {
        this.name = name;
    }
}

