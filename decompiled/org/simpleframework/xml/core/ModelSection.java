/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.util.ArrayList;
import java.util.Iterator;
import org.simpleframework.xml.core.Expression;
import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.core.LabelMap;
import org.simpleframework.xml.core.Model;
import org.simpleframework.xml.core.ModelList;
import org.simpleframework.xml.core.ModelMap;
import org.simpleframework.xml.core.Section;

class ModelSection
implements Section {
    private LabelMap attributes;
    private LabelMap elements;
    private ModelMap models;
    private Model model;

    public ModelSection(Model model) {
        this.model = model;
    }

    @Override
    public String getName() {
        return this.model.getName();
    }

    @Override
    public String getPrefix() {
        return this.model.getPrefix();
    }

    @Override
    public String getPath(String name) throws Exception {
        Expression path = this.model.getExpression();
        if (path == null) {
            return name;
        }
        return path.getElement(name);
    }

    @Override
    public String getAttribute(String name) throws Exception {
        Expression path = this.model.getExpression();
        if (path == null) {
            return name;
        }
        return path.getAttribute(name);
    }

    @Override
    public Iterator<String> iterator() {
        ArrayList<String> list = new ArrayList<String>();
        for (String element : this.model) {
            list.add(element);
        }
        return list.iterator();
    }

    @Override
    public boolean isSection(String name) throws Exception {
        return this.getModels().get(name) != null;
    }

    public ModelMap getModels() throws Exception {
        if (this.models == null) {
            this.models = this.model.getModels();
        }
        return this.models;
    }

    @Override
    public Label getText() throws Exception {
        return this.model.getText();
    }

    @Override
    public LabelMap getAttributes() throws Exception {
        if (this.attributes == null) {
            this.attributes = this.model.getAttributes();
        }
        return this.attributes;
    }

    @Override
    public LabelMap getElements() throws Exception {
        if (this.elements == null) {
            this.elements = this.model.getElements();
        }
        return this.elements;
    }

    @Override
    public Label getElement(String name) throws Exception {
        return this.getElements().getLabel(name);
    }

    @Override
    public Section getSection(String name) throws Exception {
        Model model;
        ModelMap map = this.getModels();
        ModelList list = (ModelList)map.get(name);
        if (list != null && (model = list.take()) != null) {
            return new ModelSection(model);
        }
        return null;
    }
}

