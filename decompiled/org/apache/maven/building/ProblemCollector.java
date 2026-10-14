/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.building;

import java.util.List;
import org.apache.maven.building.Problem;

public interface ProblemCollector {
    public void add(Problem.Severity var1, String var2, int var3, int var4, Exception var5);

    public void setSource(String var1);

    public List<Problem> getProblems();
}

