package com.tangpeng.sdk;

import java.io.Serializable;

/**
 * �ֶβ�ѯ������
 * @author tangpeng
 */
public class FieldExp implements Serializable
{
    /**
     * ���л�
     */
    private static final long serialVersionUID = 5966273849684877019L;

    /**
     * ���캯��.<br>
     * ��opΪINʱ�Ƚ����⣬valueӦ����һ��Object[] <br>
     * ��opΪBTʱ��value��һ��Pair&lt;Object&gt;������value.fst��ʾ�½磬value.snd��ʾ�Ͻ磬����Ϊ[fst,
     * snd] ��opΪTLʱ��value��һ��Pair&lt;Object&gt;������value.fst��ʾ��λ��value.snd��ʾ��ֵ
     * @param fieldName �ֶ���
     * @param op ������
     * @param value ����ֵ
     * @param invert ��ʾ��ת(�Ѿ��������ã�Ĭ��Ϊfalse)
     * @see Pair
     */
    @Deprecated
    public FieldExp(String fieldName, Operator op, Object value, Boolean invert)
    {
        this.fieldName = fieldName;
        this.op = op;
        this.value = value;
        this.invert = invert;
    }

    /**
     * ���캯��.<br>
     * ��opΪINʱ�Ƚ����⣬valueӦ����һ��Object[] <br>
     * ��opΪBTʱ��value��һ��Pair&lt;Object&gt;������value.fst��ʾ�½磬value.snd��ʾ�Ͻ磬����Ϊ[fst,
     * snd] ��opΪTLʱ��value��һ��Pair&lt;Object&gt;������value.fst��ʾ��λ��value.snd��ʾ��ֵ
     * @param fieldName �ֶ���
     * @param op ������
     * @param value ����ֵ
     * @see Pair
     */
    public FieldExp(String fieldName, Operator op, Object value)
    {
        this.fieldName = fieldName;
        this.op = op;
        this.value = value;
        this.invert = false;
    }

    /**
     * Shortcut for {@link FieldExp#FieldExp(String, Operator, Object)
     * FieldExp(fieldName, Op, null)}
     * @see #FieldExp(String, Operator, Object)
     */
    public FieldExp(String fieldName, Operator op)
    {
        this(fieldName, op, null);
    }

    /**
     * �ֶ���
     * @return <code>String</code>
     */
    public String getFieldName()
    {
        return fieldName;
    }

    /**
     * setter of fieldName
     * @param fieldName �ֶ���
     */
    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    /**
     * ������
     * @return <code>Op</code>
     */
    public Operator getOp()
    {
        return op;
    }

    /**
     * setter of op
     * @param Operator ������
     */
    public void setOp(Operator op)
    {
        this.op = op;
    }

    /**
     * ����ֵ
     * @return <code>Object</code>
     */
    public Object getValue()
    {
        return value;
    }

    /**
     * setter of value
     * @param value ����
     */
    public void setValue(Object value)
    {
        this.value = value;
    }

    /**
     * �Ƿ�not
     * @return the invert
     */
    public boolean isInvert()
    {
        return invert;
    }

    /**
     * set not
     * @param invert the invert to set
     */
    public void setInvert(boolean invert)
    {
        this.invert = invert;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(fieldName);
        sb.append(",");
        sb.append(op == null ? "null" : op.name());
        sb.append(",");
        sb.append(value == null ? "null" : value.toString());

        return sb.toString();
    }

    /** �ֶ��� */
    private String fieldName;
    /** ������ */
    private Operator op;
    /** ����ֵ */
    private Object value;
    /** ȡ��(not) */
    private boolean invert;

}
