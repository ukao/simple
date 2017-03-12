package com.tangpeng.sdk;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ͨ�ò�ѯ������<br>
 * ĿǰֻĬ��֧�������ֶ�������"AND"��ϵ
 * @author tangpeng
 */
public class Criteria implements Serializable
{
    /** serialVersionUID */
    private static final long serialVersionUID = -7671702635293589697L;

    /**
     * ����һ���ֶ�����
     * @param exp not null
     * @return this
     */
    public Criteria addFieldExp(FieldExp exp)
    {
        fieldExps.add(exp);
        return this;
    }

    /**
     * ��ȡ�ֶ������б�
     * @return �ֶ������б� not null
     */
    public List<FieldExp> getFieldExps()
    {
        return fieldExps;
    }

    /**
     * �����ֶ���Ϊ�������Ƶ��ֶα��ʽ���Ƚ�ΪCaseInsensitive
     * @param fieldName �ֶ��� allow null
     * @return FieldExp OR Null
     */
    public FieldExp getExpByFieldName(String fieldName)
    {
        for(FieldExp exp : fieldExps)
        {
            if(exp.getFieldName().compareToIgnoreCase(fieldName) == 0)
            {
                return exp;
            }
        }
        return null;
    }
    
    
    /**
     * ��¡������
     */
    @Override    
    public Criteria clone(){
        Criteria criteria = new Criteria();
        criteria.setOrder(this.getOrder());
        criteria.fieldExps.addAll(this.getFieldExps());
        return criteria;
    }
    
    /**
     * ��������
     * @return ��������, nullable ��ʾδָ����������
     */
    public Order getOrder()
    {
        return order;
    }

    /**
     * ������������
     * @param order �������� allow null
     * @return this
     */
    public Criteria setOrder(Order order)
    {
        this.order = order;
        return this;
    }

    /** �ֶ������б� */
    private List<FieldExp> fieldExps = new ArrayList<FieldExp>(5);
    /** ���򷽷� */
    private Order order = null;
}
