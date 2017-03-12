/*
 * $Id: Triple.java 1531 2012-07-23 07:11:39Z likai193736 $
 * �ļ�����: Triple.java
 * �ļ�����: ��
 * ��Ȩ����: ��Ȩ����(C)2001-2011
 * ��       ˾: ����������ͨѶ�ɷ����޹�˾
 * ����ժҪ: ��
 * ����˵��: ��
 * ��������: 2011-4-25
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
 * @author gaoyuan
 * @version $Rev: 1531 $
 * @since V3_00_50P2B1
 */
public class Triple<T1, T2, T3> extends Pair<T1, T2>
{
	private static final long serialVersionUID = 8715013113490003913L;

	/**
     * ���캯��
     * @param fst ����1
     * @param snd ����2
     * @param thd ����3
     */
    public Triple(T1 fst, T2 snd, T3 thd)
    {
        super(fst, snd);
        this.thd = thd;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((thd == null) ? 0 : thd.hashCode());
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
        return super.objectEquals(this.thd, ((Triple< ? , ? , ? >) obj).thd);
    }

    /**����3 */
    public T3 thd;
}
