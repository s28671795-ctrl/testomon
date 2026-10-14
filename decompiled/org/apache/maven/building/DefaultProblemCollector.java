/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.building;

import java.util.ArrayList;
import java.util.List;
import org.apache.maven.building.DefaultProblem;
import org.apache.maven.building.Problem;
import org.apache.maven.building.ProblemCollector;

class DefaultProblemCollector
implements ProblemCollector {
    private List<Problem> problems;
    private String source;

    DefaultProblemCollector(List<Problem> problems) {
        this.problems = problems != null ? problems : new ArrayList();
    }

    @Override
    public List<Problem> getProblems() {
        return this.problems;
    }

    @Override
    public void setSource(String source2) {
        this.source = source2;
    }

    @Override
    public void add(Problem.Severity severity, String message, int line, int column, Exception cause) {
        DefaultProblem problem = new DefaultProblem(message, severity, this.source, line, column, cause);
        this.problems.add(problem);
    }
}

