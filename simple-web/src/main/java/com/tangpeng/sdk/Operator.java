
package com.tangpeng.sdk;

/**
 * ��ʾ�ֶι�ϵ�������ö��
 * @author tangpeng
 */
public enum Operator
{
    /** Equal */
    EQ(1, "="), //
    /** Less Equal */
    LE(2, "<="), //
    /** Great Equal */
    GE(3, ">="), //
    /** Unequal */
    NE(4, "<>"), //
    /** Less Than */
    LT(5, "<"), //
    /** Great Than */
    GT(6, ">"), //
    /** IN */
    IN(7, "IN"), //
    /** Between */
    BETWEEN(8, "Between"),
    /** buildʱ������% */
    LIKE(9, "Like"),
    ISNULL(10,"IsNull"),
    /** ��buildʱ�Զ������߼���% */
    CONTAINS(11, "Contains"),

    ISEMPTY(12, "IsEmpty"),
    /** �ں������%*/
    STARTWITH(13, "StartWith"),
    /** TimeLatest */
    TIMELATEST(21,"TimeLatest");

    Operator(int id, String symbol)
    {
        this.id = id;
        this.symbol = symbol;
    }

    public int id()
    {
        return id;
    }

    public String symbol()
    {
        return symbol;
    }

    @Override
    public String toString()
    {
        return symbol;
    }

    /**
     * ���ݴ���Id��ȡOP
     * @param i
     * @return
     */
    public static Operator getOpById(int i){
        switch(i){
            case 1: return EQ;
            case 2: return LE;
            case 3: return GE;
            case 4: return NE;
            case 5: return LT;
            case 6: return GT;
            case 7: return IN;
            case 8: return BETWEEN;
            case 9: return LIKE;
            case 10: return ISNULL;
            case 11:return CONTAINS;
            case 12:return ISEMPTY;
            case 13:return STARTWITH;
            case 21: return TIMELATEST;
            default:return null;
        }
    }
    private int id = 0;
    private String symbol = "";
    
    
}
