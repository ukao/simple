package com.tangpeng.sdk;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * ��ѯ����<code>FieldExp</code>�б�ת��ΪSQL���ʽ�����в���������ࡣ
 * @author gaoyuan
 * @version $Rev: 1262 $
 * @since V3_00_50P1B1
 */
public class ExpressionSqlBuilder implements Serializable
{

    /**
     * FieldExpת��Ϊ������վλ��"?"��SQL���ʽ�������ֶα��ʽ�䰴AND��ʽ��ϡ�
     * <ul>
     * <li>��exps.size = 0ʱ Ϊ "( 1 &lt; 2)"</li>
     * <li>��exps.size = 1ʱ Ϊ "(f op ?)"</li>
     * <li>��exps.size > 1ʱ������"(f1 op1 ?) AND (f2 op2 ?) AND ..."
     * </ul>
     * @param exps �������ʽ�б�, not null can be empty
     * @return ������վλ����SQL���ʽ, not empty
     */
    public static String buildWhereExp(List<FieldExp> exps)
    {
        final Map<String, String> empty = Collections.emptyMap();
        return buildWhereExp(exps, empty);
    }

    /**
     * ���ֶ�����ת����SQL���ʽת������������ͬ {@link #buildWhereExp(List)}�� ����fieldNameMap����
     * &lt;f1, f2&gt;�������ɵ�SQL�ж�ӦFieldExp.fieldNameΪf1�ı��ʽ ��ʹ��f2��Ϊ�ֶ���������ʹ��ԭf1
     * @param exps �������ʽ�б�, not null
     * @param fieldNameMap �ֶ���ת��ӳ��, not null�����޿���
     * {@link java.util.Collections#emptyMap()}
     * @return ������ռλ����SQL���ʽ, not empty
     */
    public static String buildWhereExp(List<FieldExp> exps, Map<String, String> fieldNameMap)
    {
        ExpressionSqlBuilder builder = new ExpressionSqlBuilder(exps, fieldNameMap);
        return builder.buildWhereExp();
    }

    /**
     * �����ʽ�б��еĲ�����ֵ��������PreparedStatement�У���stmt����ʹ��{@link #buildWhereExp(List)}
     * ������SQL�����ġ�
     * @param exps ���ʽ�б� not null can be empty
     * @param stmt PreparedStatement
     * @param startIndex
     * ������ֵʱ����ʼλ�ã���ͨ���ӦΪ1����stmt�е�SQL������ʹ�õ�"?"��startIndexΪexps��Ӧ��SQLƬ��ǰ"?"�ĸ�����1
     * @see #getParamCount(List)
     */
    public static void assignQueryParameter(List<FieldExp> exps, PreparedStatement stmt, int startIndex)
    {
        final Map<String, String> empty = Collections.emptyMap();
        ExpressionSqlBuilder builder = new ExpressionSqlBuilder(exps, empty);
        builder.assignQueryParameter(stmt, startIndex);
    }

    /**
     * ��ȡ���ʽ���ɵ�SQL�еĲ����ĸ��� Between 2����in�����is nullû�С�����1��
     * @param exps ���ʽ�б� not null can be empty
     * @return "?"�ĸ���
     */
    public static int getParamCount(List<FieldExp> exps)
    {
        final Map<String, String> empty = Collections.emptyMap();
        ExpressionSqlBuilder builder = new ExpressionSqlBuilder(exps, empty);
        return builder.getParamCount();
    }

    /**
     * ת��order����ΪSQL���
     * <ul>
     * <li>orderΪnullʱΪ�մ�</li>
     * <li>order��nullʱΪorder by�Ӿ䣬�����һ�ո�</li>
     * </ul>
     * @param order �����趨 allow null
     * @return �մ� OR Order �Ӿ�
     */
    public static String getOrderBySql(Order order)
    {
        if (order == null)
        {
            return "";
        }
        return ExpressionSqlBuilder.getOrderbyFragment(order.getFieldName(), order.getDir());
    }

    /**
     * �������ʽ��SQL���ɽӿ�
     * @since 2011-8-11
     */
    public static interface ExpSqlGenerator extends Serializable
    {
        /**
         * ���ɴ�exp��SQL���ʽ
         * @param exp ���ʽ
         * @return not null
         */
        String getExpressionSql(FieldExp exp);

        /**
         * �˱��ʽ�а����Ĳ���ռλ��"?"�ĸ���
         * @param exp ���ʽ
         * @return "?"�ĸ�����û�о���0
         */
        int getParamCount(FieldExp exp);

        /**
         * ��PreparedStatement��ֵ����
         * @param exp ���ʽ
         * @param stmt �ɱ�SQlExpressionBuilder���ɵ�PreparedStatement
         * @param startPos �����ʽ�����ĵ�һ��"?"��λ��
         * @return ������Ĳ���λ�ã�����һ��exp��"?"����ʼλ�á� Ӧ��֤ ret = startPos +
         * this.getParamCount
         * @throws SQLException �ײ���쳣�ͷų���������
         */
        int assignParameter(FieldExp exp, PreparedStatement stmt, int startPos) throws SQLException;
    }

    /**
     * ���ʽ �滻�ӿڡ�������ת��ΪSQLʱ��ĳЩ�ض�ԭʼExp�����滻��
     */
    public static interface ExpSubstituter
    {
        /**
         * ���˷���������ʱ���ɸ��ݴ����exp�����µ�exp�����ԭexp����sql���ɡ� <br/>
         * <ul>
         * <li>Result not nullʱ���÷���ֵ����ת����ԭ����exp�����ٲ���SQL����</li>
         * <li>������÷��ǽ������exp�����¾ͷ�����������������ֱ����ԭexp�Ͻ����޸ģ������µ�Ϊ�ã�
         * �Ա����޸ġ����˴��롱��ԭʼ���ʽ��</li>
         * <li>Result is nullʱ����ʾ�޳��˱��ʽ��exp@param �����ٲ���ת��</li>
         * </ul>
         * @param exp ���ϸ����ֶ����ı��ʽ not null
         * @return �µı��ʽ
         * @see ExpressionSqlBuilder#regExpSubstituter(String, ExpSubstituter)
         */
        FieldExp replace(FieldExp exp);
    }

    /**
     * ����SQL OrderbyƬ��
     */
    private static String getOrderbyFragment(String fieldName, int dir)
    {
        return "ORDER BY " + fieldName + ((dir == Order.ASC) ? " ASC " : " DESC ");
    }

    /**
     * ���캯��
     */
    public ExpressionSqlBuilder(List<FieldExp> exps)
    {
        this(exps, null);
    }

    /**
     * ���캯��
     * @param exps
     * @param fieldNameMap
     */
    public ExpressionSqlBuilder(List<FieldExp> exps, Map<String, String> fieldNameMap)
    {
        this.exps = exps;
        final Map<String, String> empty = New.hashMap();
        this.fieldNameMap = (fieldNameMap == null) ? empty : fieldNameMap;
        this.generatorMapByOp.put(Operator.STARTWITH, new StartWithExpSqlGenerator());
    }

    public String buildWhereExp()
    {
        Iterator<FieldExp> it = transedExps().iterator();
        if (!it.hasNext())
        {
            return "(1 < 2)";
        }

        final StringBuffer sb = new StringBuffer();
        appendFieldExpSql(it.next(), sb);
        while (it.hasNext())
        {
            sb.append(" AND ");
            appendFieldExpSql(it.next(), sb);
        }

        return sb.toString();
    }

    public String buildOrderBySql(Order order)
    {
        if (order == null)
        {
            return "";
        }
        String realFieldname = getRealFieldName(order.getFieldName());

        return ExpressionSqlBuilder.getOrderbyFragment(realFieldname, order.getDir());
    }

    /**
     * �����ʽ�б��еĲ�����ֵ��������PreparedStatement�У���stmt����ʹ��{@link #buildWhereExp(List)}
     * ������SQL�����ġ�
     * @param stmt PreparedStatement
     * @param startIndex
     * ������ֵʱ����ʼλ�ã���ͨ���ӦΪ1����stmt�е�SQL������ʹ�õ�"?"��startIndexΪexps��Ӧ��SQLƬ��ǰ"?"�ĸ�����1
     * @see #getParamCount(List)
     */
    public void assignQueryParameter(PreparedStatement stmt, int startIndex)
    {
        try
        {
            int index = startIndex;
            for (Iterator<FieldExp> it = transedExps().iterator(); it.hasNext();)
            {
                final FieldExp fieldExp = it.next();
                final ExpSqlGenerator expGen = getSqlGenerator(fieldExp);
                index = expGen.assignParameter(fieldExp, stmt, index);
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * ��ȡ���ʽ���ɵ�SQL�еĲ����ĸ��� Between 2����in�����is nullû�С�����1��
     * @return "?"�ĸ���
     */
    public int getParamCount()
    {
        int n = 0;
        for (Iterator<FieldExp> it = transedExps().iterator(); it.hasNext();)
        {
            final FieldExp exp = it.next();
            final ExpSqlGenerator expGen = getSqlGenerator(exp);
            n += expGen.getParamCount(exp);
        }
        return n;
    }

    /**
     * ע��һ�����ʽ��SQl������������Ϊ�˵��ֶν�ʹ�ø�����������
     * @param fieldName �ֶ��� not null
     * @param generator ���ʽ������ not null
     */
    public void regExpSqlGenerator(String fieldName, ExpSqlGenerator generator)
    {
        this.generatorMapByFieldName.put(fieldName, generator);
    }

    /**
     * ���һ�����ʽ�滻��
     * @param fieldName �ֶ���
     * @param substituter �滻��
     * @return �󶨵����ֶε�ԭ�滻����������Ϊnull
     */
    public ExpSubstituter regExpSubstituter(String fieldName, ExpSubstituter substituter)
    {
        return substituterMap.put(fieldName, substituter);
    }

    /**
     * ���ʽ�б�
     * @return not null
     */
    public List<FieldExp> getExps()
    {
        return exps;
    }

    /**
     * �ֶ�����ӳ���
     * @return not null may empty
     */
    public Map<String, String> getFieldNameMap()
    {
        return fieldNameMap;
    }

    /**
     * ��ȡ��Ը������ʽ��SQL�������������Ƿ��л������ֵ�������Ȼ���ǻ��ڲ�������������Ĭ��������������˳��
     */
    private ExpSqlGenerator getSqlGenerator(FieldExp exp)
    {
        if (generatorMapByFieldName.containsKey(exp.getFieldName()))
        {
            return generatorMapByFieldName.get(exp.getFieldName());
        }
        if (generatorMapByOp.containsKey(exp.getOp()))
        {
            return generatorMapByOp.get(exp.getOp());
        }
        return defaultGenerator;
    }

    /**
     * ����ĳ���ֶε��������ʽ
     */
    private void appendFieldExpSql(FieldExp exp, StringBuffer sb)
    {
        final ExpSqlGenerator expGen = getSqlGenerator(exp);
        sb.append(expGen.getExpressionSql(exp));
    }

    /**
     * ���ת����fieldNameӦ��ʹ�õ����ơ���δ�趨��Ӧ�����Ƽ�Ϊ
     */
    private String getRealFieldName(String fieldName)
    {
        if (fieldNameMap.containsKey(fieldName))
            return fieldNameMap.get(fieldName);
        return fieldName;
    }

    private List<FieldExp> transedExps()
    {
        if (this.transedExps == null)
        {
            this.transedExps = New.arrayList(this.exps.size());
            for (FieldExp exp : this.exps)
            {
                final ExpSubstituter subst = substituterMap.get(exp.getFieldName());
                final FieldExp transedExp = (subst != null) ? subst.replace(exp) : exp;
                if (transedExp != null)
                {
                    this.transedExps.add(transedExp);
                }
            }
        }
        return this.transedExps;
    }

    /** ��紫���exp�б� */
    private List<FieldExp> exps;
    /** ����Exp substitor ת������б� */
    private List<FieldExp> transedExps = null;

    private Map<String, String> fieldNameMap;
    private Map<String, ExpSqlGenerator> generatorMapByFieldName = New.hashMap();
    private Map<Operator, ExpSqlGenerator> generatorMapByOp = New.hashMap();
    private ExpSqlGenerator defaultGenerator = new DefaultExpSqlGenerator();
    private Map<String, ExpSubstituter> substituterMap = New.hashMap();

    /**
     * ���������ֶ�EQ,IN�����ı��ʽ�����ڣ���������: <br/>
     * ((field1 = ?) and (field2 = ?)) or ((field1 = ?) and (field2 = ?)) or
     * ((field1 = ?) and (field2 = ?)) ... <br/>
     * ������SQL���ʽ
     */
    public static class DualFieldExpGenerator implements ExpSqlGenerator
    {
        /** serialVersionUID */
        private static final long serialVersionUID = -7404660013849758659L;

        public DualFieldExpGenerator(String fieldName1, String fieldName2)
        {
            this.fieldName1 = fieldName1;
            this.fieldName2 = fieldName2;
        }

        @Override
        public String getExpressionSql(FieldExp exp)
        {
            StringBuilder sb = new StringBuilder("(");
            boolean notFirst = false;
            for (int i = 0; i < this.getOperand(exp).length; i++)
            {
                if (notFirst)
                {
                    sb.append(" OR ");
                }
                notFirst = true;
                sb.append("(");
                sb.append("(").append(fieldName1).append(" = ?)");
                sb.append(" AND ");
                sb.append("(").append(fieldName2).append(" = ?)");
                sb.append(")");
            }
            sb.append(")");
            return sb.toString();
        }

        @Override
        public int getParamCount(FieldExp exp)
        {
            return this.getOperand(exp).length * 2;
        }

        @Override
        public int assignParameter(FieldExp exp, PreparedStatement stmt, int startPos) throws SQLException
        {
            int index = startPos;
            for (Pair< ? , ? > p : this.getOperand(exp))
            {
                stmt.setObject(index++, p.fst);
                stmt.setObject(index++, p.snd);
            }
            return index;
        }

        private Pair< ? , ? >[] getOperand(FieldExp exp)
        {
            if (exp.getOp() == Operator.IN)
            {
                Object[] arr1 = ((Object[]) exp.getValue());
                Pair< ? , ? >[] arr2 = new Pair< ? , ? >[arr1.length];
                System.arraycopy(arr1, 0, arr2, 0, arr1.length);
                return arr2;
            }
            else if (exp.getOp() == Operator.EQ)
            {
                Pair< ? , ? >[] arr = new Pair< ? , ? >[] { (Pair< ? , ? >) exp.getValue() };
                return arr;
            }
            else
            {
                throw new RuntimeException("Unsupported op " + exp.getOp() + " for field " + exp.getFieldName());
            }
        }

        private String fieldName1;
        private String fieldName2;
    };

    /**
     * һ���ֶ��ö��ǰ׺���������ж���SQL�������� <br/>
     * �������� "(f1 like "xx%s") or (f1 like "yyy%s) ..."������SQL���ʽ
     * @version $Rev: 1262 $
     * @since 2011-11-23
     */
    public class StartWithExpSqlGenerator implements ExpSqlGenerator
    {
        /** SerialVersionUID */
        private static final long serialVersionUID = 4663336569777726766L;

        /***
         * @see com.zte.ums.u32.web.iface.common.ExpressionSqlBuilder.ExpSqlGenerator#getExpressionSql(com.zte.ums.u32.web.iface.common.FieldExp)
         */
        @Override
        public String getExpressionSql(FieldExp exp)
        {
            StringBuilder sb = new StringBuilder("(");
            boolean notFirst = false;

            for (int i = this.getParamCount(exp); i > 0; i--)
            {
                if (notFirst)
                {
                    sb.append(" OR ");
                }
                notFirst = true;
                final String realFieldname = ExpressionSqlBuilder.this.getRealFieldName(exp.getFieldName());
                sb.append("(").append(realFieldname).append(" like ? )");
            }
            sb.append(")");
            return sb.toString();
        }

        /***
         * @see com.zte.ums.u32.web.iface.common.ExpressionSqlBuilder.ExpSqlGenerator#getParamCount(com.zte.ums.u32.web.iface.common.FieldExp)
         */
        @Override
        public int getParamCount(FieldExp exp)
        {
            return operandAsStringArray(exp).length;
        }

        /***
         * @see com.zte.ums.u32.web.iface.common.ExpressionSqlBuilder.ExpSqlGenerator#assignParameter(com.zte.ums.u32.web.iface.common.FieldExp,
         * java.sql.PreparedStatement, int)
         */
        @Override
        public int assignParameter(FieldExp exp, PreparedStatement stmt, int startPos) throws SQLException
        {
            int index = startPos;
            for (String s : operandAsStringArray(exp))
            {
                stmt.setObject(index++, s + "%");
            }
            return index;
        }

        private String[] operandAsStringArray(FieldExp exp)
        {
            Constraint.assureNotNull(exp.getValue(), "value of " + exp.getFieldName());
            if (!exp.getValue().getClass().isArray())
            {
                throw new IllegalArgumentException("Error when process \"" + exp.getFieldName() +
                                                   "\". value field of STARTWITH should be String array");
            }
            Object[] arr1 = ((Object[]) exp.getValue());
            String[] arr2 = new String[arr1.length];
            System.arraycopy(arr1, 0, arr2, 0, arr1.length);
            return arr2;
        }
    }

    /**
     * Ĭ�ϵĵ����ֶεı��ʽ������
     * @author gaoyuan
     * @version $Rev: 1262 $
     * @since 2011-8-11
     */
    private class DefaultExpSqlGenerator implements ExpSqlGenerator
    {
        /** SerialVersionUID */
        private static final long serialVersionUID = 2544987310390940817L;

        /***
         * @see com.zte.ums.u32.web.iface.common.ExpressionSqlBuilder.ExpSqlGenerator#getExpressionSql(com.zte.ums.u32.web.iface.common.FieldExp)
         */
        @Override
        public String getExpressionSql(FieldExp exp)
        {
            StringBuilder sb = new StringBuilder();
            //�Ƿ�ת
            if (exp.isInvert())
            {
                sb.append("(NOT ");
            }
            //ǰһ�� "(field "
            sb.append("(").append(ExpressionSqlBuilder.this.getRealFieldName(exp.getFieldName()));
            //������
            sb.append(" ").append(getOpSymbol(exp.getOp())).append(" ");
            //������
            // ������� IN
            if (exp.getOp() == Operator.IN)// "(?, ?, ?, ?)"
            {
                sb.append("(");
                Object[] params = (Object[]) exp.getValue();
                for (int i = 0; i < params.length; i++)
                {
                    if (i > 0)
                        sb.append(", ");
                    sb.append("?");
                }
                sb.append(")");
            }
            else if (exp.getOp() != Operator.ISNULL)
            // һ��ľ���һ��"?"(�ų�isnull)
            {
                sb.append("?");
                // ������� BETWEEN
                if (exp.getOp() == Operator.BETWEEN)
                {
                    sb.append(" AND ?");
                }
                else if (exp.getOp() == Operator.ISEMPTY)
                {
                    //������� isEmpty
                    sb.append(" OR " + ExpressionSqlBuilder.this.getRealFieldName(exp.getFieldName()) + " " +
                              getOpSymbol(Operator.ISNULL));
                }
            }
            sb.append(")");

            //�Ƿ�ת        
            if (exp.isInvert())
            {
                sb.append(" )");
            }
            return sb.toString();
        }

        /***
         * @see com.zte.ums.u32.web.iface.common.ExpressionSqlBuilder.ExpSqlGenerator#getParamCount(com.zte.ums.u32.web.iface.common.FieldExp)
         */
        @Override
        public int getParamCount(FieldExp exp)
        {
            switch (exp.getOp())
            {
                case BETWEEN:
                    return 2;
                case IN:
                    return ((Object[]) exp.getValue()).length;
                case ISNULL:
                    return 0;
                default:
                    return 1;
            }
        }

        /***
         * @see com.zte.ums.u32.web.iface.common.ExpressionSqlBuilder.ExpSqlGenerator#assignParameter(com.zte.ums.u32.web.iface.common.FieldExp,
         * java.sql.PreparedStatement, int)
         */
        @Override
        public int assignParameter(FieldExp fieldExp, PreparedStatement stmt, int startPos) throws SQLException
        {
            int index = startPos;
            if (fieldExp.getOp() == Operator.BETWEEN)
            {
                @SuppressWarnings("unchecked")
                Pair<Object, Object> range = (Pair<Object, Object>) fieldExp.getValue();
                stmt.setObject(index, range.fst);
                stmt.setObject(index + 1, range.snd);
                index += 2;
            }
            else if (fieldExp.getOp() == Operator.IN)
            {
                final Object[] values = (Object[]) fieldExp.getValue();
                for (int i = 0; i < values.length; i++)
                {
                    stmt.setObject(index + i, values[i]);
                }
                index += values.length;
            }
            else if (fieldExp.getOp() == Operator.CONTAINS)
            {
                stmt.setObject(index, "%" + fieldExp.getValue() + "%");
                index += 1;
            }
            else if (fieldExp.getOp() == Operator.STARTWITH)
            {
                stmt.setObject(index, fieldExp.getValue() + "%");
                index += 1;
            }
            else if (fieldExp.getOp() == Operator.ISEMPTY)
            {
                stmt.setObject(index, "");//Ϊ��
                index += 1;
            }
            else if (fieldExp.getOp() != Operator.ISNULL)
            {
                stmt.setObject(index, fieldExp.getValue());
                index += 1;
            }
            return index;
        }

        /**
         * op��Ӧ��SQL������
         */
        private String getOpSymbol(Operator op)
        {
            switch (op)
            {
                case BETWEEN:
                    return "BETWEEN";
                case LE:
                    return "<=";
                case LT:
                    return "<";
                case GE:
                    return ">=";
                case GT:
                    return ">";
                case IN:
                    return "IN";
                case NE:
                    return "<>";
                case EQ:
                    return "=";
                case LIKE:
                    return "LIKE";
                case ISNULL:
                    return "IS NULL";
                case ISEMPTY:
                    return "=";//
                case CONTAINS:
                    return "LIKE";
                case STARTWITH:
                    return "LIKE";
                case TIMELATEST:
                    return ">=";
                default:
                    throw new RuntimeException("unknown operator " + op.symbol());
            }
        }
    }

    /**
     * 
     */
    private static final long serialVersionUID = -5025280419347864080L;

}
