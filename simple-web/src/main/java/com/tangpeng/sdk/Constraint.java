package com.tangpeng.sdk;


/**
 * ����Լ�����ķ�����
 * <p>
 * ��Щ�ж����һ�������Ϊ��������ڲ������ʹ�ã�����Ҫע��������ģ�����õĲ�����黹���ڲ� ������顣�ڲ�����
 * ����Ƽ�ʹ�÷�����ǰ������˵����assertָ�������У�������ʹ����Щ����ʱ�ж�������
 * </p>
 * ���磺
 * 
 * <pre>
 * void doSomething(T1 e, int i)
 * {
 *     assert e != null : &quot;param1 should not be null&quot;;
 *     assert i &gt; 0 : &quot;param0 should be positive&quot;;
 * }
 * </pre>
 * @author gaoyuan
 * @version $Rev: 1262 $
 * @since 2011-7-18
 */
public final class Constraint
{
    /**
     * ȷ�ϸ�����ֵ��Ϊnull�������׳�NullPointerException
     * @param obj ���ж�����
     * @param errorMsg �׳����쳣�е���Ϣ
     * @exception NullPointerException ��objΪnullʱ�׳�
     */
    public static void assureNotNull(Object obj, String errorMsg)
    {
        if (obj == null)
        {
            throw new NullPointerException(errorMsg);
        }
    }

    /**
     * ��������ֵΪnull��trim(s).isEmptyʱ�׳��쳣
     * @param s ���ж�����
     * @param errorMsg �׳����쳣�е���Ϣ
     * @exception IllegalArgumentException
     */
    public static void assureNotEmpty(CharSequence s, String errorMsg)
    {
        if (s == null || s.length() == 0 || s.toString().trim().isEmpty())
        {
            throw new IllegalArgumentException(errorMsg);
        }
    }

    /**
     * ȷ�ϸ����Ķ����Ƿ��ٺ���Ŀ�ѡ��Ŀ�С�<br/>
     * ��֧��null������ж�����equals�������磺<br/>
     * 
     * <pre>
     * assureWithin(timeUnit, &quot;SECOND&quot;, &quot;HOUR&quot;, &quot;DAY&quot;);
     * assureWithin(d, 5, 10, 15);
     * </pre>
     * @param <T> ��������
     * @param v Ҫ�ж���ֵ
     * @param option1 ��һ����ѡֵ��Ŀ�����ڱ���б���Ҫ�������ṩһ����
     * @param options ������ѡ��Ŀ
     * @exception IllegalArgumentException ��v�������κ�һ����ѡ��ʱ
     */
    public static <T> void assureWithin(T v, T option1, T... options)
    {
        if (Constraint.objectEquals(v, options))
        {
            return;
        }
        for (T option : options)
        {
            if (Constraint.objectEquals(v, option))
            {
                return;
            }
        }
        throw new IllegalArgumentException(createMsgForWithin(v, option1, options));
    }

    /**
     * ȷ�ϸ������ַ����Ƿ񡰵��ڡ���ѡ��Ŀ�е�ĳһ������ִ�Сд�������׳��쳣<br/>
     * ����ͬ{@link #assureWithin(Object, Object, Object...)}����Ҫ�ر�ע����Ǳ�
     * �����ڲ�������trim��������÷����д���
     * @param v ���ж���ֵ
     * @param option1 ��һ����ѡ��
     * @param options ������ѡ��
     */
    public static void assureWithinIgnoreCase(String v, String option1, String... options)
    {
        if (stringEqualIgnoreCase(v, option1))
        {
            return;
        }
        for (String option : options)
        {
            if (stringEqualIgnoreCase(option, v))
            {
                return;
            }
        }

        throw new IllegalArgumentException(createMsgForWithin(v, option1, options));
    }

    /**
     * ȷ�ϸ������鲻Ϊ���ҳ���Ϊ�������ȡ����������׳�{@link IllegalArgumentException}
     * @param arr ����
     * @param expectLen ��������
     */
    public static void assureArrayLength(Object[] arr, int expectLen)
    {
        if (arr == null || arr.length != expectLen)
        {
            throw new IllegalArgumentException("Length of array should be " + expectLen);
        }
    }

    /**
     * ȷ�ϸ������鲻Ϊ���ҳ���Ϊ�������ȡ����������Ը�����msg�׳�{@link IllegalArgumentException}
     * @param arr ����
     * @param expectLen ��������
     * @param msg ������Ϣ
     * @exception IllegalArgumentException
     */
    public static void assureArrayLength(Object[] arr, int expectLen, String msg)
    {
        if (arr == null || arr.length != expectLen)
        {
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * ȷ�ϸ�����ֵΪtrue�������׳�IllegalStateException��
     * @param v �ж�ֵ
     * @param errorMsg �׳����쳣�е���Ϣ
     * @exception IllegalStateException ��vΪfalseʱ�׳�
     */
    public static void assureTrue(boolean v, String errorMsg)
    {
        if (!v)
        {
            throw new IllegalStateException(errorMsg);
        }
    }

    /**
     * ȷ�ϸ�����ֵΪfalse�������׳�IllegalStateException��
     * @param v �ж�ֵ
     * @param errorMsg �׳����쳣�е���Ϣ
     * @exception IllegalStateException ��vΪtrueʱ�׳�
     */
    public static void assureFalse(boolean v, String errorMsg)
    {
        if (v)
        {
            throw new IllegalStateException(errorMsg);
        }
    }


    /**
     * ȷ�������в���nullԪ��
     * @param <T> ��������
     * @param arr ���������
     * @param arrName ��������
     */
    public static <T> void assureNoNullELem(T arr[], String arrName)
    {
        if (arr == null)
        {
            throw new NullPointerException(arrName);
        }

        for (int i = 0; i < arr.length; i++)
        {
            if (arr[i] == null)
            {
                throw new IllegalArgumentException("the argument " + arrName + "[" + i + "] is null!");
            }
        }
    }

    /**
     * �ж����������Ƿ���ȣ���֧��null
     * @param o1 ����1 nullable
     * @param o2 ����2 nullable
     * @return (o1 == null and o2 == null) or (o1 != null and o1.equals(o2))
     */
    public static boolean objectEquals(Object o1, Object o2)
    {
        return (o1 == null) ? o2 == null : o1.equals(o2);
    }

    /**
     * �ж������ַ����Ƿ���Դ�Сд��ȣ���ͬΪnull
     * @param s1 �ַ���1 nullable
     * @param s2 �ַ���2 nullable
     * @return ��Ϊnull or s1.equalsIgnoreCase(s2)
     */
    public static boolean stringEqualIgnoreCase(String s1, String s2)
    {
        return (s1 == null) ? s2 == null : s1.equalsIgnoreCase(s2);
    }

    /**
     * ����within�ж����Ѻ��쳣��ʾ
     */
    private static <T> String createMsgForWithin(T v, T option1, T... options)
    {
        //�����ѺõĴ�����ʾ
        StringBuilder sb = new StringBuilder(String.valueOf(v));
        sb.append(" not within scope (");
        sb.append(String.valueOf(option1));
        for (T option : options)
        {
            sb.append(",").append(String.valueOf(option));
        }
        sb.append(")");
        return sb.toString();
    }
}
