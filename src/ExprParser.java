import java.util.Map;

//Node----------------------------------
interface Node{
    void prettyPrint(StringBuilder s);
}
interface Expr extends Node{
    int eval(Map<String, Integer> bindings) throws EvalError, SyntaxError;
}
class IntLit implements Expr{
    private int val;
    
    public IntLit(int val){
        this.val = val;
    }
    @Override
    public int eval(Map<String, Integer> bindings){
        return val;
    }
    @Override
    public void prettyPrint(StringBuilder s){
        s.append(val);
    }
}
class BinaryArithExpr implements Expr{
    private Expr left, right;
    private String op;
    public BinaryArithExpr(Expr left, String op, Expr right){
        this.left = left;
        this.op = op;
        this.right = right;
    }
    @Override
    public void prettyPrint(StringBuilder s) {
        s.append("(");
        left.prettyPrint(s);
        s.append(op);
        right.prettyPrint(s);
        s.append(")");
        
    }
    @Override
    public int eval(Map<String, Integer> bindings) throws EvalError, SyntaxError {
        int lv = left.eval(bindings);
        int rv = right.eval(bindings);
        if(op.equals("+"))return lv + rv;
        else if(op.equals("-"))return lv - rv;
        else if(op.equals("*"))return lv * rv;
        else if(op.equals("/")){
            if(rv == 0)throw new SyntaxError("Can't divided by 0!");
            else return lv / rv;
        }
        else if(op.equals("%")){
            if(rv == 0)throw new SyntaxError("Can't mod by 0!");
            else return lv % rv;
        }
        throw new EvalError("unknow op: " + op);
    }
}
//---------------------------------------

public class ExprParser {
    private static ExprTokenizer tkz;
    static ArithExprFactory arithFac = ArithExprFactory.Instanced();

    public ExprParser(String src) throws SyntaxError{
        this.tkz = new ExprTokenizer(src);
    }

    //parse T E F
    public static Expr parseE() throws SyntaxError {
        Expr e = parseT();
        while(tkz.peek("+") || tkz.peek("-")){
            String op = tkz.consume();
            e = arithFac.TExpr(e, op, parseT());
        }
        return e;
    }

    public static Expr parseT() throws SyntaxError{
        Expr t = parseF();
        while(tkz.peek("*") || tkz.peek("/") || tkz.peek("%")){
            String op = tkz.consume();
            t = arithFac.TExpr(t, op, parseF());
        }
        return t;
    }

    public static Expr parseF() throws SyntaxError{
        String f = tkz.peek();
        if(isNum(f)){
            String x = tkz.consume();
            return new IntLit(Integer.parseInt(x));
        }else {
            tkz.consume("(");
            Expr e = parseE();
            tkz.consume(")");
            return e;
        }
    }

    private static boolean isNum(String input){
        try{
            Double.parseDouble(input);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    
}
