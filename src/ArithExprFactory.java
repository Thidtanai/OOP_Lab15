public class ArithExprFactory {
    private static ArithExprFactory instance;
    private ArithExprFactory(){}

    public static ArithExprFactory Instanced(){
        if(instance == null) instance = new ArithExprFactory();
        return instance;
    }
    public BinaryArithExpr TExpr(Expr left, String operator, Expr right) throws SyntaxError{
        return new BinaryArithExpr(left, operator, right);
    }
}
