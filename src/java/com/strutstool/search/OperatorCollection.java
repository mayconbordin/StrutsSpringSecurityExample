package com.strutstool.search;

import com.strutstool.collection.DataCollection;

/**
 *
 * @author maycon
 */
public class OperatorCollection {
    public static final DataCollection<String, Operator> operators = new DataCollection<String, Operator>();

    static {
        //Numeric
        operators.put(Operator.EQUAL, new Operator("equal", Operator.EQUAL, false));
        operators.put(Operator.NOT_EQUAL, new Operator("not equal", Operator.NOT_EQUAL, false));
        operators.put(Operator.GREATER_THAN_OR_EQUAL, new Operator("greater than or equal", Operator.GREATER_THAN_OR_EQUAL, false));
        operators.put(Operator.GREATER_THAN, new Operator("greater than", Operator.GREATER_THAN, false));
        operators.put(Operator.I_LIKE, new Operator("like", Operator.I_LIKE, true));
        operators.put(Operator.LIKE, new Operator("like (case sensitive)", Operator.LIKE, true));
        operators.put(Operator.LESS_THAN_OR_EQUAL, new Operator("less than or equal", Operator.LESS_THAN_OR_EQUAL, false));
        operators.put(Operator.LESS_THAN, new Operator("less than", Operator.LESS_THAN, false));
    }
}
