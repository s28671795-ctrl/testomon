/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import org.simpleframework.xml.core.ConstructorException;
import org.simpleframework.xml.core.Parameter;
import org.simpleframework.xml.core.Signature;

class SignatureBuilder {
    private final ParameterTable table = new ParameterTable();
    private final Constructor factory;

    public SignatureBuilder(Constructor factory2) {
        this.factory = factory2;
    }

    public boolean isValid() {
        int width;
        Class<?>[] types = this.factory.getParameterTypes();
        return types.length == (width = this.table.width());
    }

    public void insert(Parameter value, int index) {
        this.table.insert(value, index);
    }

    public List<Signature> build() throws Exception {
        return this.build(new ParameterTable());
    }

    private List<Signature> build(ParameterTable matrix) throws Exception {
        if (this.table.isEmpty()) {
            return this.create();
        }
        this.build(matrix, 0);
        return this.create(matrix);
    }

    private List<Signature> create() throws Exception {
        ArrayList<Signature> list = new ArrayList<Signature>();
        Signature signature = new Signature(this.factory);
        if (this.isValid()) {
            list.add(signature);
        }
        return list;
    }

    private List<Signature> create(ParameterTable matrix) throws Exception {
        ArrayList<Signature> list = new ArrayList<Signature>();
        int height = matrix.height();
        int width = matrix.width();
        for (int i = 0; i < height; ++i) {
            Signature signature = new Signature(this.factory);
            for (int j = 0; j < width; ++j) {
                Parameter parameter = matrix.get(j, i);
                String path = parameter.getPath();
                Object key = parameter.getKey();
                if (signature.contains(key)) {
                    throw new ConstructorException("Parameter '%s' is a duplicate in %s", path, this.factory);
                }
                signature.add(parameter);
            }
            list.add(signature);
        }
        return list;
    }

    private void build(ParameterTable matrix, int index) {
        this.build(matrix, new ParameterList(), index);
    }

    private void build(ParameterTable matrix, ParameterList signature, int index) {
        ParameterList column = this.table.get(index);
        int height = column.size();
        int width = this.table.width();
        if (width - 1 > index) {
            for (int i = 0; i < height; ++i) {
                ParameterList extended = new ParameterList(signature);
                if (signature == null) continue;
                Parameter parameter = (Parameter)column.get(i);
                extended.add(parameter);
                this.build(matrix, extended, index + 1);
            }
        } else {
            this.populate(matrix, signature, index);
        }
    }

    private void populate(ParameterTable matrix, ParameterList signature, int index) {
        ParameterList column = this.table.get(index);
        int width = signature.size();
        int height = column.size();
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                ParameterList list = matrix.get(j);
                Parameter parameter = (Parameter)signature.get(j);
                list.add(parameter);
            }
            ParameterList list = matrix.get(index);
            Parameter parameter = (Parameter)column.get(i);
            list.add(parameter);
        }
    }

    private static class ParameterList
    extends ArrayList<Parameter> {
        public ParameterList() {
        }

        public ParameterList(ParameterList list) {
            super(list);
        }
    }

    private static class ParameterTable
    extends ArrayList<ParameterList> {
        private int height() {
            int width = this.width();
            if (width > 0) {
                return this.get(0).size();
            }
            return 0;
        }

        private int width() {
            return this.size();
        }

        public void insert(Parameter value, int column) {
            ParameterList list = this.get(column);
            if (list != null) {
                list.add(value);
            }
        }

        @Override
        public ParameterList get(int column) {
            int size;
            for (int i = size = this.size(); i <= column; ++i) {
                ParameterList list = new ParameterList();
                this.add(list);
            }
            return (ParameterList)super.get(column);
        }

        public Parameter get(int column, int row) {
            return (Parameter)this.get(column).get(row);
        }
    }
}

