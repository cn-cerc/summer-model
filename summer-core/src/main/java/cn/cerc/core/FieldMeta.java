package cn.cerc.core;

import java.io.Serializable;

import cn.cerc.db.editor.GetSetTextEvent;
import cn.cerc.db.editor.GetTextEvent;
import cn.cerc.db.editor.SetTextEvent;

public final class FieldMeta implements Serializable {
    private static final long serialVersionUID = -6898050783447062943L;
    private String code;
    private String name;
    private FieldType dataType;
    private FieldKind kind;
    private String remark;
    private boolean updateKey;
    private boolean autoincrement;
    private GetTextEvent onGetTextEvent;
    private SetTextEvent onSetTextEvent;

    public enum FieldKind {
        Memory, Storage, Calculated;
    }

    public FieldMeta(String code) {
        super();
        if (code == null || "".equals(code))
            throw new RuntimeException("fieldCode is null!");
        this.code = code;
        this.kind = FieldKind.Memory;
    }

    public FieldMeta(String code, FieldKind kind) {
        super();
        if (code == null || "".equals(code))
            throw new RuntimeException("fieldCode is null!");
        if (kind == null)
            throw new RuntimeException("fieldKind is null!");
        this.code = code;
        this.kind = kind;
    }

    @Override
    public FieldMeta clone() {
        FieldMeta result = new FieldMeta(this.code);
        result.name = this.name;
        if (this.dataType != null)
            result.dataType = this.dataType.clone();
        result.kind = this.kind;
        result.remark = this.remark;
        result.updateKey = this.updateKey;
        result.autoincrement = this.autoincrement;
        result.onGetTextEvent = this.onGetTextEvent;
        result.onSetTextEvent = this.onSetTextEvent;
        return result;
    }

    public final String getCode() {
        return code;
    }

    public final String getName() {
        return name;
    }

    public final FieldMeta setName(String name) {
        this.name = name;
        return this;
    }

    public final String getType() {
        return dataType == null ? null : dataType.toString();
    }

    public final FieldMeta setType(String type) {
        if (type == null) {
            this.dataType = null;
        } else {
            if (this.dataType == null)
                this.dataType = new FieldType();
            this.dataType.setType(type);
        }
        return this;
    }

    public final FieldType setType(Class<?> clazz) {
        return getFieldType().setType(clazz);
    }

    public final FieldType setType(Class<?> clazz, int length) {
        return getFieldType().setType(clazz).setLength(length);
    }

    public final FieldType getFieldType() {
        if (this.dataType == null)
            this.dataType = new FieldType();
        return dataType;
    }

    public final FieldKind getKind() {
        return kind;
    }

    public final FieldMeta setKind(FieldKind type) {
        if (type == null)
            throw new RuntimeException("fieldKind is null!");
        if (type == FieldKind.Storage)
            throw new RuntimeException("Wrong direction of modification");
        this.kind = type;
        return this;
    }

    public final boolean isUpdateKey() {
        return updateKey;
    }

    public final String getRemark() {
        return remark;
    }

    public final FieldMeta setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public final FieldMeta setUpdateKey(boolean updateKey) {
        this.updateKey = updateKey;
        return this;
    }

    public final boolean isAutoincrement() {
        return autoincrement;
    }

    public final FieldMeta setAutoincrement(boolean autoincrement) {
        this.autoincrement = autoincrement;
        return this;
    }

    @Override
    public final int hashCode() {
        return code.hashCode();
    }

    @Override
    public final boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof FieldMeta) {
            FieldMeta meta = (FieldMeta) anObject;
            return code.equals(meta.getCode());
        }
        return false;
    }

    public FieldMeta onGetText(GetTextEvent getTextEvent) {
        this.onGetTextEvent = getTextEvent;
        return this;
    }

    public FieldMeta onSetText(SetTextEvent setTextEvent) {
        this.onSetTextEvent = setTextEvent;
        return this;
    }

    public String getText(DataRow record) {
        if (onGetTextEvent == null)
            return record.getString(code);
        return onGetTextEvent.getText(record, this);
    }

    public Object setText(String value) {
        if (onSetTextEvent == null)
            return value;
        return onSetTextEvent.setText(value);
    }

    public void onGetSetText(GetSetTextEvent getsetTextEvent) {
        this.onGetText(getsetTextEvent);
        this.onSetText(getsetTextEvent);
    }
}
