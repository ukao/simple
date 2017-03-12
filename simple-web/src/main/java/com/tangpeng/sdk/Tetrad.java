/*
 * $Id: Tetrad.java 1531 2012-07-23 07:11:39Z likai193736 $
 * �ļ�����: Tetrad.java
 * �ļ�����: ��
 * ��Ȩ����: ��Ȩ����(C)2001-2011
 * ��       ˾: ����������ͨѶ�ɷ����޹�˾
 * ����ժҪ: ��
 * ����˵��: ��
 * ��������: 2011-5-3
 * ��������: $Date:: 2012-07-23 15:11:39 +0800#$:
 * �޸ļ�¼1: // �޸���ʷ��¼�������޸����ڡ��޸��߼��޸�����
 *    �޸����ڣ�
 *    �� �� �ţ�
 *    �� �� �ˣ�
 *    �޸����ݣ�
 * �޸ļ�¼2����
 */
package com.tangpeng.sdk;

/**
 * ������Ԫ��
 * @param <T1> ����1����
 * @param <T2> ����2����
 * @param <T3> ����3����
 * @param <T4> ����4����
 *@author gaoyuan
 *@version $Rev: 1531 $
 *@since V3_00_50P1B1
 */
public class Tetrad<T1, T2, T3, T4> extends Triple<T1, T2, T3>
{
	private static final long serialVersionUID = 8460643466884778349L;


	/**
     * ���캯��
     * @param fst ����1
     * @param snd ����2
     * @param thd ����3
     * @param fth ����4
     */
    public Tetrad(T1 fst, T2 snd, T3 thd, T4 fth)
    {
        super(fst, snd, thd);
        this.fth = fth;
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((fth == null) ? 0 : fth.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        return super.objectEquals(this.fth, ((Tetrad<?,?,?,?>)obj).fth);
    }
    

    /**����4 */
    public T4 fth;
}
