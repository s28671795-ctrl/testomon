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
import scala.reflect.OptManifest;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000594A\u0001C\u0005\u0005\u001d!Aq\u0005\u0001B\u0001B\u0003%\u0001\u0006\u0003\u00053\u0001\t\u0015\r\u0011\"\u00014\u0011!\u0001\u0005A!A!\u0002\u0013!\u0004\u0002C!\u0001\u0005\u000b\u0007I\u0011\t\"\t\u00119\u0003!\u0011!Q\u0001\n\rCQa\u0014\u0001\u0005\u0002ACQa\u0019\u0001\u0005B\u0011\u0014\u0011c\u00117bgN$\u0016\u0010]3NC:Lg-Z:u\u0015\tQ1\"A\u0004sK\u001adWm\u0019;\u000b\u00031\tQa]2bY\u0006\u001c\u0001!\u0006\u0002\u0010=M\u0019\u0001\u0001\u0005\u000b\u0011\u0005E\u0011R\"A\u0006\n\u0005MY!AB!osJ+g\rE\u0002\u00163qq!AF\f\u000e\u0003%I!\u0001G\u0005\u0002\u000fA\f7m[1hK&\u0011!d\u0007\u0002\u000e\u00072\f7o]'b]&4Wm\u001d;\u000b\u0005aI\u0001CA\u000f\u001f\u0019\u0001!Qa\b\u0001C\u0002\u0001\u0012\u0011\u0001V\t\u0003C\u0011\u0002\"!\u0005\u0012\n\u0005\rZ!a\u0002(pi\"Lgn\u001a\t\u0003#\u0015J!AJ\u0006\u0003\u0007\u0005s\u00170\u0001\u0004qe\u00164\u0017\u000e\u001f\t\u0004#%Z\u0013B\u0001\u0016\f\u0005\u0019y\u0005\u000f^5p]B\u0012A\u0006\r\t\u0004-5z\u0013B\u0001\u0018\n\u0005-y\u0005\u000f^'b]&4Wm\u001d;\u0011\u0005u\u0001D!C\u0019\u0002\u0003\u0003\u0005\tQ!\u0001!\u0005\u0011yFe\r\u0019\u0002\u0019I,h\u000e^5nK\u000ec\u0017m]:\u0016\u0003Q\u0002$!\u000e \u0011\u0007YZT(D\u00018\u0015\tA\u0014(\u0001\u0003mC:<'\"\u0001\u001e\u0002\t)\fg/Y\u0005\u0003y]\u0012Qa\u00117bgN\u0004\"!\b \u0005\u0013}\u001a\u0011\u0011!A\u0001\u0006\u0003\u0001#\u0001B0%gE\nQB];oi&lWm\u00117bgN\u0004\u0013!\u0004;za\u0016\f%oZ;nK:$8/F\u0001D!\r!e)\u0013\b\u0003#\u0015K!\u0001G\u0006\n\u0005\u001dC%\u0001\u0002'jgRT!\u0001G\u00061\u0005)c\u0005c\u0001\f.\u0017B\u0011Q\u0004\u0014\u0003\n\u001b\u0016\t\t\u0011!A\u0003\u0002\u0001\u0012Aa\u0018\u00134e\u0005qA/\u001f9f\u0003J<W/\\3oiN\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0003R%bk\u0006c\u0001\f\u00019!)qE\u0002a\u0001'B\u0019\u0011#\u000b+1\u0005U;\u0006c\u0001\f.-B\u0011Qd\u0016\u0003\ncI\u000b\t\u0011!A\u0003\u0002\u0001BQA\r\u0004A\u0002e\u0003$A\u0017/\u0011\u0007YZ4\f\u0005\u0002\u001e9\u0012Iq\bWA\u0001\u0002\u0003\u0015\t\u0001\t\u0005\u0006\u0003\u001a\u0001\rA\u0018\t\u0004\t\u001a{\u0006G\u00011c!\r1R&\u0019\t\u0003;\t$\u0011\"T/\u0002\u0002\u0003\u0005)\u0011\u0001\u0011\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012!\u001a\t\u0003m\u0019L!aZ\u001c\u0003\rM#(/\u001b8hQ\u0011\u0001\u0011\u000e\\7\u0011\u0005EQ\u0017BA6\f\u0005A\u0019VM]5bYZ+'o]5p]VKE)A\u0003wC2,XMH\u0001\u0002\u0001")
public class ClassTypeManifest<T>
implements ClassTag<T> {
    private static final long serialVersionUID = 1L;
    private final Option<OptManifest<?>> prefix;
    private final Class<?> runtimeClass;
    private final List<OptManifest<?>> typeArguments;

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
    public boolean canEqual(Object x) {
        return ClassTag.canEqual$(this, x);
    }

    @Override
    public boolean equals(Object x) {
        return ClassTag.equals$(this, x);
    }

    @Override
    public int hashCode() {
        return ClassTag.hashCode$(this);
    }

    @Override
    public Class<?> erasure() {
        return ClassManifestDeprecatedApis.erasure$(this);
    }

    @Override
    public boolean $less$colon$less(ClassTag<?> that) {
        return ClassManifestDeprecatedApis.$less$colon$less$(this, that);
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
    public ClassTag<Object> arrayManifest() {
        return ClassManifestDeprecatedApis.arrayManifest$(this);
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
    public Class<?> runtimeClass() {
        return this.runtimeClass;
    }

    @Override
    public List<OptManifest<?>> typeArguments() {
        return this.typeArguments;
    }

    @Override
    public String toString() {
        return new StringBuilder(0).append(this.prefix.isEmpty() ? "" : new StringBuilder(1).append(this.prefix.get().toString()).append("#").toString()).append((Object)(this.runtimeClass().isArray() ? "Array" : this.runtimeClass().getName())).append(this.argString()).toString();
    }

    public ClassTypeManifest(Option<OptManifest<?>> prefix, Class<?> runtimeClass, List<OptManifest<?>> typeArguments) {
        this.prefix = prefix;
        this.runtimeClass = runtimeClass;
        this.typeArguments = typeArguments;
    }
}

