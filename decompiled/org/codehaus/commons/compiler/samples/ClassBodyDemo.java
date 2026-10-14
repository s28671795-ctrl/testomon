/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.samples;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.codehaus.commons.compiler.CompilerFactoryFactory;
import org.codehaus.commons.compiler.IClassBodyEvaluator;

public final class ClassBodyDemo {
    public static void main(String[] args2) throws Exception {
        String classBody;
        int i;
        if (args2.length > 0 && "-help".equals(args2[0])) {
            System.out.println("Usage:");
            System.out.println("  ClassBodyDemo <class-body> { <argument> }");
            System.out.println("  ClassBodyDemo -help");
            System.out.println("If <class-body> starts with a '@', then the class body is read");
            System.out.println("from the named file.");
            System.out.println("The <class-body> must declare a method \"public static void main(String[])\"");
            System.out.println("to which the <argument>s are passed. If the return type of that method is");
            System.out.println("not VOID, then the returned value is printed to STDOUT.");
            System.exit(0);
        }
        if ((i = 0) >= args2.length) {
            System.err.println("Class body missing; try \"-help\".");
        }
        if ((classBody = args2[i++]).startsWith("@")) {
            classBody = ClassBodyDemo.readFileToString(classBody.substring(1));
        }
        String[] arguments = new String[args2.length - i];
        System.arraycopy(args2, i, arguments, 0, arguments.length);
        IClassBodyEvaluator cbe = CompilerFactoryFactory.getDefaultCompilerFactory(ClassBodyDemo.class.getClassLoader()).newClassBodyEvaluator();
        cbe.cook(classBody);
        Class<?> c = cbe.getClazz();
        Method m4 = c.getMethod("main", String[].class);
        Object returnValue = m4.invoke(null, new Object[]{arguments});
        if (m4.getReturnType() != Void.TYPE) {
            System.out.println(returnValue instanceof Object[] ? Arrays.toString((Object[])returnValue) : String.valueOf(returnValue));
        }
    }

    private ClassBodyDemo() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static String readFileToString(String fileName) throws IOException {
        try (FileReader r = new FileReader(fileName);){
            int cnt;
            StringBuilder sb = new StringBuilder();
            char[] ca = new char[1024];
            while ((cnt = ((Reader)r).read(ca, 0, ca.length)) != -1) {
                sb.append(ca, 0, cnt);
            }
            String string = sb.toString();
            return string;
        }
    }
}

