package cn.cerc.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import com.google.gson.Gson;

import cn.cerc.core.FieldMeta.FieldKind;

public final class FieldDefs implements Serializable, Iterable<FieldMeta> {
    private static final long serialVersionUID = 7478897050846245325L;
    private LinkedHashSet<FieldMeta> items = new LinkedHashSet<>();

    public boolean exists(String fieldCode) {
        return items.contains(new FieldMeta(fieldCode));
    }

    public boolean exists(FieldMeta field) {
        return items.contains(field);
    }

    public List<String> getFields() {
        List<String> result = new ArrayList<>();
        items.forEach(meta -> result.add(meta.getCode()));
        return result;
    }

    public List<String> getFields(FieldKind fieldType) {
        List<String> result = new ArrayList<>();
        for (FieldMeta meta : items) {
            if (fieldType == meta.getKind())
                result.add(meta.getCode());
        }
        return result;
    }

    public FieldMeta add(String fieldCode) {
        FieldMeta item = new FieldMeta(fieldCode);
        return items.add(item) ? item : this.getItem(fieldCode);
    }

    public FieldMeta add(String fieldCode, FieldKind fieldType) {
        FieldMeta item = new FieldMeta(fieldCode, fieldType);
        return items.add(item) ? item : this.getItem(fieldCode);
    }

    public FieldMeta add(FieldMeta item) {
        return items.add(item) ? item : this.getItem(item.getCode());
    }

    @Deprecated
    public void add(String... strs) {
        for (String fieldCode : strs)
            this.add(fieldCode);
    }

    public void clear() {
        items.clear();
    }

    public int size() {
        return items.size();
    }

    @Override
    public Iterator<FieldMeta> iterator() {
        return this.items.iterator();
    }

    public void delete(String fieldCode) {
        FieldMeta field = new FieldMeta(fieldCode);
        items.remove(field);
    }

    public FieldMeta getItem(String fieldCode) {
        for (FieldMeta meta : items) {
            if (fieldCode.equals(meta.getCode()))
                return meta;
        }
        return null;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
