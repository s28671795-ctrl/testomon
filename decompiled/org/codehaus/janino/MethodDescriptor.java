/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino;

import java.util.ArrayList;
import java.util.Arrays;
import org.codehaus.commons.compiler.InternalCompilerException;
import org.codehaus.commons.nullanalysis.Nullable;

public class MethodDescriptor {
    public final String[] parameterFds;
    public final String returnFd;

    public MethodDescriptor(String returnFd, String ... parameterFds) {
        this.parameterFds = parameterFds;
        this.returnFd = returnFd;
    }

    public MethodDescriptor(String s2) {
        if (s2.charAt(0) != '(') {
            throw new InternalCompilerException();
        }
        int from = 1;
        ArrayList<String> parameterFDs = new ArrayList<String>();
        while (s2.charAt(from) != ')') {
            int to = from;
            while (s2.charAt(to) == '[') {
                ++to;
            }
            if ("BCDFIJSZ".indexOf(s2.charAt(to)) != -1) {
                ++to;
            } else if (s2.charAt(to) == 'L') {
                ++to;
                while (s2.charAt(to) != ';') {
                    ++to;
                }
                ++to;
            } else {
                throw new InternalCompilerException();
            }
            parameterFDs.add(s2.substring(from, to));
            from = to;
        }
        this.parameterFds = parameterFDs.toArray(new String[parameterFDs.size()]);
        this.returnFd = s2.substring(++from);
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MethodDescriptor)) {
            return false;
        }
        MethodDescriptor that = (MethodDescriptor)obj;
        return Arrays.equals(this.parameterFds, that.parameterFds) && this.returnFd.equals(that.returnFd);
    }

    public int hashCode() {
        return Arrays.hashCode(this.parameterFds) ^ this.returnFd.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        for (String parameterFd : this.parameterFds) {
            sb.append(parameterFd);
        }
        return sb.append(')').append(this.returnFd).toString();
    }

    public MethodDescriptor prependParameter(String parameterFd) {
        String[] tmp = new String[1 + this.parameterFds.length];
        tmp[0] = parameterFd;
        System.arraycopy(this.parameterFds, 0, tmp, 1, this.parameterFds.length);
        return new MethodDescriptor(this.returnFd, tmp);
    }
}

