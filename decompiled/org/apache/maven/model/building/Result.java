/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.building;

import java.util.ArrayList;
import java.util.Collections;
import org.apache.maven.model.building.ModelProblem;

public class Result<T> {
    private final boolean errors;
    private final T value;
    private final Iterable<? extends ModelProblem> problems;

    public static <T> Result<T> success(T model) {
        return Result.success(model, Collections.emptyList());
    }

    public static <T> Result<T> success(T model, Iterable<? extends ModelProblem> problems) {
        assert (!Result.hasErrors(problems));
        return new Result<T>(false, model, problems);
    }

    public static <T> Result<T> success(T model, Result<?> ... results) {
        ArrayList<ModelProblem> problemsList = new ArrayList<ModelProblem>();
        for (Result<?> result1 : results) {
            for (ModelProblem modelProblem : result1.getProblems()) {
                problemsList.add(modelProblem);
            }
        }
        return Result.success(model, problemsList);
    }

    public static <T> Result<T> error(Iterable<? extends ModelProblem> problems) {
        return Result.error(null, problems);
    }

    public static <T> Result<T> error(T model) {
        return Result.error(model, Collections.emptyList());
    }

    public static <T> Result<T> error(Result<?> result) {
        return Result.error(result.getProblems());
    }

    public static <T> Result<T> error(Result<?> ... results) {
        ArrayList<ModelProblem> problemsList = new ArrayList<ModelProblem>();
        for (Result<?> result1 : results) {
            for (ModelProblem modelProblem : result1.getProblems()) {
                problemsList.add(modelProblem);
            }
        }
        return Result.error(problemsList);
    }

    public static <T> Result<T> error(T model, Iterable<? extends ModelProblem> problems) {
        return new Result<T>(true, model, problems);
    }

    public static <T> Result<T> newResult(T model, Iterable<? extends ModelProblem> problems) {
        return new Result<T>(Result.hasErrors(problems), model, problems);
    }

    public static <T> Result<T> addProblem(Result<T> result, ModelProblem problem) {
        return Result.addProblems(result, Collections.singleton(problem));
    }

    public static <T> Result<T> addProblems(Result<T> result, Iterable<? extends ModelProblem> problems) {
        ArrayList<ModelProblem> list = new ArrayList<ModelProblem>();
        for (ModelProblem modelProblem : problems) {
            list.add(modelProblem);
        }
        for (ModelProblem modelProblem : result.getProblems()) {
            list.add(modelProblem);
        }
        return new Result<T>(result.hasErrors() || Result.hasErrors(problems), result.get(), list);
    }

    public static <T> Result<T> addProblems(Result<T> result, Result<?> ... results) {
        ArrayList<ModelProblem> problemsList = new ArrayList<ModelProblem>();
        for (Result<?> result1 : results) {
            for (ModelProblem modelProblem : result1.getProblems()) {
                problemsList.add(modelProblem);
            }
        }
        return Result.addProblems(result, problemsList);
    }

    public static <T> Result<Iterable<T>> newResultSet(Iterable<? extends Result<? extends T>> results) {
        boolean hasErrors = false;
        ArrayList<T> modelsList = new ArrayList<T>();
        ArrayList<ModelProblem> problemsList = new ArrayList<ModelProblem>();
        for (Result<T> result : results) {
            modelsList.add(result.get());
            for (ModelProblem modelProblem : result.getProblems()) {
                problemsList.add(modelProblem);
            }
            if (!result.hasErrors()) continue;
            hasErrors = true;
        }
        return new Result<Iterable<T>>(hasErrors, modelsList, problemsList);
    }

    private static boolean hasErrors(Iterable<? extends ModelProblem> problems) {
        for (ModelProblem modelProblem : problems) {
            if (!modelProblem.getSeverity().equals((Object)ModelProblem.Severity.ERROR) && !modelProblem.getSeverity().equals((Object)ModelProblem.Severity.FATAL)) continue;
            return true;
        }
        return false;
    }

    private Result(boolean errors2, T model, Iterable<? extends ModelProblem> problems) {
        this.errors = errors2;
        this.value = model;
        this.problems = problems;
    }

    public Iterable<? extends ModelProblem> getProblems() {
        return this.problems;
    }

    public T get() {
        return this.value;
    }

    public boolean hasErrors() {
        return this.errors;
    }
}

