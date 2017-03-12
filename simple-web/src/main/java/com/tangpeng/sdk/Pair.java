/*
 * $Id: Pair.java 1262 2012-07-18 02:09:41Z songhz $
 * �ļ�����: Pair.java
 * �ļ�����: ��
 * ��Ȩ����: ��Ȩ����(C)2001-2011
 * ��       ˾: ����������ͨѶ�ɷ����޹�˾
 * ����ժҪ: ��
 * ����˵��: ��
 * ��������: 2011-4-25
 * ��������: $Date:: 2012-07-18 10:09:41 +0800#$:
 * �޸ļ�¼1: // �޸���ʷ��¼�������޸����ڡ��޸��߼��޸�����
 *    �޸����ڣ�
 *    �� �� �ţ�
 *    �� �� �ˣ�
 *    �޸����ݣ�
 * �޸ļ�¼2����
 */
package com.tangpeng.sdk;

import java.io.Serializable;

/**
 * ��Ԫ��
 * @param <T1> ����1����
 * @param <T2> ����2����
 * @author gaoyuan
 * @version $Rev: 1262 $
 * @since V3_00_50P2B1
 */
public class Pair<T1, T2> implements Serializable
{
    /**
     * ���캯��
     * @param fst ����1
     * @param snd ����2
     */
    public Pair(T1 fst, T2 snd)
    {
        this.fst = fst;
        this.snd = snd;
    }

    @Override
    public String toString()
    {
        return "Pair(" + String.valueOf(fst) + ", " + String.valueOf(snd) + ")";
    }
    
    @Override
    public int hashCode()
    {
        if (fst == null)
            return snd != null ? snd.hashCode() + 1 : 0;
        if (snd == null)
            return fst.hashCode() + 2;
        else
            return fst.hashCode() * 17 + snd.hashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Pair< ? , ? >)
        {
            Pair< ? , ? > other = (Pair< ? , ? >) o;
            return objectEquals(this.fst, other.fst) && objectEquals(this.snd, other.snd);
        }
        return false;
    }

    public boolean equalsTo(T1 o1, T2 o2)
    {
        return objectEquals(fst, o1) && objectEquals(snd, o2);
    }
    
    /**
     * �ж����������Ƿ���ȵķ������ɴ���null
     * @param obj1 ����1
     * @param obj2 ����1
     * @return ��Ϊnull �� obj1.equals(obj2)Ϊtrue
     */
    protected boolean objectEquals(Object obj1, Object obj2)
    {
        return obj1 == null && obj2 == null || obj1 != null && obj1.equals(obj2);
    }
    
    /**����1 */
    public T1 fst;
    /**����2 */
    public T2 snd;
    
    private static final long serialVersionUID = 2815044214343796339L;
}
