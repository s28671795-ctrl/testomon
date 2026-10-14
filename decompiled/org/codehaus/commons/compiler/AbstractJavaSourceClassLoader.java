/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.StringTokenizer;
import org.codehaus.commons.compiler.CompilerFactoryFactory;
import org.codehaus.commons.compiler.util.resource.ResourceFinder;
import org.codehaus.commons.nullanalysis.NotNullByDefault;
import org.codehaus.commons.nullanalysis.Nullable;

public abstract class AbstractJavaSourceClassLoader
extends ClassLoader {
    @Nullable
    protected ProtectionDomainFactory protectionDomainFactory;

    public AbstractJavaSourceClassLoader() {
    }

    public AbstractJavaSourceClassLoader(ClassLoader parentClassLoader) {
        super(parentClassLoader);
    }

    @Override
    @NotNullByDefault(value=false)
    public InputStream getResourceAsStream(String name) {
        return super.getParent().getResourceAsStream(name);
    }

    public abstract void setSourcePath(File[] var1);

    public abstract void setSourceFinder(ResourceFinder var1);

    public void setSourceFileCharacterEncoding(@Nullable String charsetName) {
        this.setSourceCharset(charsetName == null ? Charset.defaultCharset() : Charset.forName(charsetName));
    }

    public abstract void setSourceCharset(Charset var1);

    public abstract void setDebuggingInfo(boolean var1, boolean var2, boolean var3);

    public final void setProtectionDomainFactory(@Nullable ProtectionDomainFactory protectionDomainFactory) {
        this.protectionDomainFactory = protectionDomainFactory;
    }

    public static void main(String[] args2) throws Exception {
        Method mainMethod;
        String arg;
        int i;
        File[] sourcePath = null;
        String characterEncoding2 = null;
        boolean debuggingInfoLines = false;
        boolean debuggingInfoVars = false;
        boolean debuggingInfoSource = false;
        boolean haveDebuggingInfo = false;
        for (i = 0; i < args2.length && (arg = args2[i]).startsWith("-"); ++i) {
            if ("-sourcepath".equals(arg)) {
                sourcePath = AbstractJavaSourceClassLoader.splitPath(args2[++i]);
                continue;
            }
            if ("-encoding".equals(arg)) {
                characterEncoding2 = args2[++i];
                continue;
            }
            if ("-g".equals(arg)) {
                debuggingInfoLines = true;
                debuggingInfoVars = true;
                debuggingInfoSource = true;
                haveDebuggingInfo = true;
                continue;
            }
            if ("-g:none".equals(arg)) {
                debuggingInfoLines = false;
                debuggingInfoVars = false;
                debuggingInfoSource = false;
                haveDebuggingInfo = true;
                continue;
            }
            if ("-g:".startsWith(arg)) {
                debuggingInfoLines = arg.indexOf("lines") != -1;
                debuggingInfoVars = arg.indexOf("vars") != -1;
                debuggingInfoSource = arg.indexOf("source") != -1;
                haveDebuggingInfo = true;
                continue;
            }
            if ("-help".equals(arg)) {
                System.out.println("Usage:");
                System.out.println("  java " + AbstractJavaSourceClassLoader.class.getName() + " { <option> } <class-name> { <argument> }");
                System.out.println("Loads the named class by name and invoke its \"main(String[])\" method, passing");
                System.out.println("the given <argument>s.");
                System.out.println("  <option>:");
                System.out.println("    -sourcepath <" + File.pathSeparator + "-separated-list-of-source-directories>");
                System.out.println("    -encoding <character-encoding>");
                System.out.println("    -g                     Generate all debugging info");
                System.out.println("    -g:none                Generate no debugging info");
                System.out.println("    -g:{source,lines,vars} Generate only some debugging info");
                System.exit(0);
                continue;
            }
            System.err.println("Invalid command line option \"" + arg + "\"; try \"-help\"");
            System.exit(1);
        }
        if (i == args2.length) {
            System.err.println("No class name given, try \"-help\"");
            System.exit(1);
        }
        String className = args2[i++];
        String[] mainArgs = new String[args2.length - i];
        System.arraycopy(args2, i, mainArgs, 0, args2.length - i);
        AbstractJavaSourceClassLoader ajscl = CompilerFactoryFactory.getDefaultCompilerFactory(AbstractJavaSourceClassLoader.class.getClassLoader()).newJavaSourceClassLoader();
        if (haveDebuggingInfo) {
            ajscl.setDebuggingInfo(debuggingInfoLines, debuggingInfoVars, debuggingInfoSource);
        }
        if (characterEncoding2 != null) {
            ajscl.setSourceFileCharacterEncoding(characterEncoding2);
        }
        if (sourcePath != null) {
            ajscl.setSourcePath(sourcePath);
        }
        Class<?> clazz = ajscl.loadClass(className);
        try {
            mainMethod = clazz.getMethod("main", String[].class);
        }
        catch (NoSuchMethodException ex) {
            System.err.println("Class \"" + className + "\" has not public method \"main(String[])\".");
            System.exit(1);
            return;
        }
        mainMethod.invoke(null, new Object[]{mainArgs});
    }

    private static File[] splitPath(String string) {
        ArrayList<File> l = new ArrayList<File>();
        StringTokenizer st = new StringTokenizer(string, File.pathSeparator);
        while (st.hasMoreTokens()) {
            l.add(new File(st.nextToken()));
        }
        return l.toArray(new File[l.size()]);
    }

    public static interface ProtectionDomainFactory {
        public ProtectionDomain getProtectionDomain(String var1);
    }
}

