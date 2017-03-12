/*
 * $Id: New.java 1262 2012-07-18 02:09:41Z songhz $
 * �ļ�����: New.java
 * �ļ�����: ��
 * ��Ȩ����: ��Ȩ����(C)2001-2011
 * ��       ˾: ����������ͨѶ�ɷ����޹�˾
 * ����ժҪ: ��
 * ����˵��: ��
 * ��������: 2011-5-3
 * ��������: $Date:: 2012-07-18 10:09:41 +0800#$:
 * �޸ļ�¼1: // �޸���ʷ��¼�������޸����ڡ��޸��߼��޸�����
 *    �޸����ڣ�
 *    �� �� �ţ�
 *    �� �� �ˣ�
 *    �޸����ݣ�
 * �޸ļ�¼2����
 */
package com.tangpeng.sdk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * ��������Ĺ����ࡣ<br>
 * �ر��Ƿ���ʱ�����Ա��↪�µ���д��ʹ�ô������ࡣ
 * @author gaoyuan
 * @version $Rev: 1262 $
 * @since V3_00_50P2B2
 */
public final class New
{
    /** ����Ϊ0���������� */
    public static final int[] EMPTY_INT_ARRAY = new int[0];

    /**
     * ����һ��arrayList
     * @param <T> type
     * @return arrayList
     */
    public static <T> ArrayList<T> arrayList()
    {
        return new ArrayList<T>();
    }

    /**
     * �ó�ʼ���ݻ�����һ��arrayList
     * @param <T> type
     * @param cap ��ʼ�ݻ�
     * @return arrayList
     */
    public static <T> ArrayList<T> arrayList(int cap)
    {
        return new ArrayList<T>(cap);
    }

    /**
     * ���ݸ�����collection����arrayList
     * @param <T> ����
     * @param c ����
     * @return arrayList
     */
    public static <T> ArrayList<T> arrayList(Collection<T> c)
    {
        return new ArrayList<T>(c);
    }

    /**
     * ����һ��hashMap
     * @param <K> ������
     * @param <V> ֵ����
     * @return hashMap
     */
    public static <K, V> HashMap<K, V> hashMap()
    {
        return new HashMap<K, V>();
    }

    /**
     * ����һ��hashMap
     * @param <K> ������
     * @param <V> ֵ����
     * @param capacity ��ʼ�ռ��С
     * @return hashMap
     */
    public static <K, V> HashMap<K, V> hashMap(int capacity)
    {
        return new HashMap<K, V>(capacity);
    }

    /**
     * ����һ��hashset
     * @param <T> ����
     * @return hashset
     */
    public static <T> HashSet<T> hashSet()
    {
        return new HashSet<T>();
    }

    /**
     * ����һ����Ԫ�����
     * @param <T1>
     * @param <T2>
     * @param fst ����1
     * @param snd ����2
     * @return ��Ԫ�����
     */
    public static <T1, T2> Pair<T1, T2> pair(T1 fst, T2 snd)
    {
        return new Pair<T1, T2>(fst, snd);
    }

    /**
     * ����һ����Ԫ�����
     * @param <T1> ����1����
     * @param <T2> ����2����
     * @param <T3> ����3����
     * @param fst ����1
     * @param snd ����2
     * @param thd ����3
     * @return ��Ԫ�����
     */
    public static <T1, T2, T3> Triple<T1, T2, T3> triple(T1 fst, T2 snd, T3 thd)
    {
        return new Triple<T1, T2, T3>(fst, snd, thd);
    }

    /**
     * ����һ����Ԫ�����
     * @param <T1> ����1����
     * @param <T2> ����2����
     * @param <T3> ����3����
     * @param <T4> ����4����
     * @param fst ����1
     * @param snd ����2
     * @param thd ����3
     * @param fth ����4
     * @return ��Ԫ�����
     */
    public static <T1, T2, T3, T4> Tetrad<T1, T2, T3, T4> tetrad(T1 fst, T2 snd, T3 thd, T4 fth)
    {
        return new Tetrad<T1, T2, T3, T4>(fst, snd, thd, fth);
    }

    /**
     * ����ʵ����
     */
    private New()
    {

    }
}
