/*
 * Decompiled with CFR 0.152.
 */
package scala.reflect;

import scala.Option;
import scala.collection.immutable.List;
import scala.collection.mutable.ArrayBuilder;
import scala.collection.mutable.ArraySeq;
import scala.reflect.ClassManifestDeprecatedApis;
import scala.reflect.ClassTag;
import scala.reflect.Manifest;
import scala.reflect.Manifest$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00194Q!\u0003\u0006\u0002\u0002=A\u0001b\n\u0001\u0003\u0006\u0004%\t\u0005\u000b\u0005\ti\u0001\u0011\t\u0011)A\u0005S!)Q\u0007\u0001C\u0001m!)\u0011\b\u0001C!u!)q\n\u0001C!!\")1\u000b\u0001C!)\"9a\u000b\u0001b\u0001\n\u0003:\u0006BB.\u0001A\u0003%\u0001L\u0001\bB]f4\u0016\r\\'b]&4Wm\u001d;\u000b\u0005-a\u0011a\u0002:fM2,7\r\u001e\u0006\u0002\u001b\u0005)1oY1mC\u000e\u0001QC\u0001\t\u001c'\u0011\u0001\u0011#\u0006\u0013\u0011\u0005I\u0019R\"\u0001\u0007\n\u0005Qa!AB!osJ+g\rE\u0002\u0017/ei\u0011AC\u0005\u00031)\u0011\u0001\"T1oS\u001a,7\u000f\u001e\t\u00035ma\u0001\u0001B\u0003\u001d\u0001\t\u0007QDA\u0001U#\tq\u0012\u0005\u0005\u0002\u0013?%\u0011\u0001\u0005\u0004\u0002\b\u001d>$\b.\u001b8h!\t\u0011\"%\u0003\u0002$\u0019\t1\u0011I\\=WC2\u0004\"AE\u0013\n\u0005\u0019b!AB#rk\u0006d7/\u0001\u0005u_N#(/\u001b8h+\u0005I\u0003C\u0001\u00162\u001d\tYs\u0006\u0005\u0002-\u00195\tQF\u0003\u0002/\u001d\u00051AH]8pizJ!\u0001\r\u0007\u0002\rA\u0013X\rZ3g\u0013\t\u00114G\u0001\u0004TiJLgn\u001a\u0006\u0003a1\t\u0011\u0002^8TiJLgn\u001a\u0011\u0002\rqJg.\u001b;?)\t9\u0004\bE\u0002\u0017\u0001eAQaJ\u0002A\u0002%\n\u0001\u0003\n7fgN$3m\u001c7p]\u0012bWm]:\u0015\u0005mr\u0004C\u0001\n=\u0013\tiDBA\u0004C_>dW-\u00198\t\u000b}\"\u0001\u0019\u0001!\u0002\tQD\u0017\r\u001e\u0019\u0003\u0003&\u00032AQ#I\u001d\t12)\u0003\u0002E\u0015\u00059\u0001/Y2lC\u001e,\u0017B\u0001$H\u00055\u0019E.Y:t\u001b\u0006t\u0017NZ3ti*\u0011AI\u0003\t\u00035%#\u0011B\u0013 \u0002\u0002\u0003\u0005)\u0011A&\u0003\t}#\u0013GN\t\u0003=1\u0003\"AE'\n\u00059c!aA!os\u0006A1-\u00198FcV\fG\u000e\u0006\u0002<#\")!+\u0002a\u0001\u0019\u0006)q\u000e\u001e5fe\u00061Q-];bYN$\"aO+\t\u000b}2\u0001\u0019\u0001'\u0002\u0011!\f7\u000f[\"pI\u0016,\u0012\u0001\u0017\t\u0003%eK!A\u0017\u0007\u0003\u0007%sG/A\u0005iCND7i\u001c3fA!\u0012\u0001\"\u0018\t\u0003%yK!a\u0018\u0007\u0003\u0013Q\u0014\u0018M\\:jK:$\b\u0006\u0002\u0001bI\u0016\u0004\"A\u00052\n\u0005\rd!\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=\u0005\t\u0001")
public abstract class AnyValManifest<T>
implements Manifest<T> {
    private static final long serialVersionUID = 1L;
    private final String toString;
    private final transient int hashCode;

    @Override
    public List<Manifest<?>> typeArguments() {
        return Manifest.typeArguments$(this);
    }

    @Override
    public Manifest<Object> arrayManifest() {
        return Manifest.arrayManifest$(this);
    }

    @Override
    public ClassTag<Object> wrap() {
        return ClassTag.wrap$(this);
    }

    @Override
    public Object newArray(int len) {
        return ClassTag.newArray$(this, len);
    }

    @Override
    public Option<T> unapply(Object x) {
        return ClassTag.unapply$(this, x);
    }

    @Override
    public Class<?> erasure() {
        return ClassManifestDeprecatedApis.erasure$(this);
    }

    @Override
    public boolean $greater$colon$greater(ClassTag<?> that) {
        return ClassManifestDeprecatedApis.$greater$colon$greater$(this, that);
    }

    @Override
    public <A> Class<Object> arrayClass(Class<?> tp) {
        return ClassManifestDeprecatedApis.arrayClass$(this, tp);
    }

    @Override
    public Object[] newArray2(int len) {
        return ClassManifestDeprecatedApis.newArray2$(this, len);
    }

    @Override
    public Object[][] newArray3(int len) {
        return ClassManifestDeprecatedApis.newArray3$(this, len);
    }

    @Override
    public Object[][][] newArray4(int len) {
        return ClassManifestDeprecatedApis.newArray4$(this, len);
    }

    @Override
    public Object[][][][] newArray5(int len) {
        return ClassManifestDeprecatedApis.newArray5$(this, len);
    }

    @Override
    public ArraySeq<T> newWrappedArray(int len) {
        return ClassManifestDeprecatedApis.newWrappedArray$(this, len);
    }

    @Override
    public ArrayBuilder<T> newArrayBuilder() {
        return ClassManifestDeprecatedApis.newArrayBuilder$(this);
    }

    @Override
    public String argString() {
        return ClassManifestDeprecatedApis.argString$(this);
    }

    @Override
    public String toString() {
        return this.toString;
    }

    @Override
    public boolean $less$colon$less(ClassTag<?> that) {
        return that == this || that == Manifest$.MODULE$.Any() || that == Manifest$.MODULE$.AnyVal();
    }

    @Override
    public boolean canEqual(Object other) {
        return other instanceof AnyValManifest;
    }

    @Override
    public boolean equals(Object that) {
        return this == that;
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    public AnyValManifest(String toString2) {
        this.toString = toString2;
        this.hashCode = System.identityHashCode(this);
    }
}

