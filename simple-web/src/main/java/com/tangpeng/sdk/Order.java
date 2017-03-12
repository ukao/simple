package com.tangpeng.sdk;

import java.io.Serializable;

/**
 * �������ʽ���ࡣ
 * @author tangpeng
 */
public class Order implements Serializable
{
    /** serialVersionUID  */
    private static final long serialVersionUID = -1122294888667947823L;
    
    /** ������ */
    public static final int ASC = 0;
    /** ������ */
    public static final int DESC = 1;

    /**
     * ����һ���������Ŀ�ݷ�ʽ
     * @param fieldName �ֶ���
     * @return Order, not null
     */
    public static Order asc(String fieldName)
    {

        return new Order(fieldName, ASC);
    }

    /**
     * ����һ���������Ŀ�ݷ�ʽ
     * @param fieldName �ֶ���
     * @return Order, not null
     */
    public static Order desc(String fieldName)
    {
        return new Order(fieldName, DESC);
    }

    /**
     * ��bool�ͱ��������Ĺ��췽ʽ��
     * @param fieldName �ֶ���
     * @param asc true ��ʾ����, false ��ʾ����
     * @return Order, not null
     */
    public static Order by(String fieldName, boolean asc)
    {
        return new Order(fieldName, (asc) ? ASC : DESC);
    }

    public static Order by(String fieldName, int dir)
    {
        return new Order(fieldName, dir);
    }

    /**
     * ˽�й���.
     * @param fieldName �ֶ���
     * @param dir ����Ӧ���� {@link #ASC}, {@link #DESC}֮һ
     */
    private Order(String fieldName, int dir)
    {
        if (dir != ASC && dir != DESC)
        {
            throw new IllegalArgumentException("Sort direction should in [" + ASC + "," + DESC + "]");
        }
        this.fieldName = fieldName;
        this.dir = dir;
    }

    /**
     * �ֶ���
     * @return <code>String</code> �ֶ���
     */
    public String getFieldName()
    {
        return fieldName;
    }

    /**
     * ������
     * @return int  {@link #ASC} OR {@link #DESC}
     */
    public int getDir()
    {
        return dir;
    }

    /**
     * �������Ƿ�Ϊ����
     * @return boolean, result = this.dir eq ASC
     */
    public boolean isAsc()
    {
        return dir == ASC;
    }

    /** �ֶ��� */
    private String fieldName;
    /** ���� */
    private int dir;
    
    
}
