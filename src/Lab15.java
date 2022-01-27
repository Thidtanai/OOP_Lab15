import java.util.ArrayList;

public class Lab15 {
    public static ArrayList<String> sum;
    Lab15 lab13 = new Lab15();
    public static void main(String[] args) throws Exception {
        System.out.println(Grouping("5"));
        System.out.println(Grouping("1+2"));
        System.out.println(Grouping("3%3+2"));
        System.out.println(Grouping("1+2/3*5"));
        System.out.println(Grouping("30%10%2"));
        System.out.println(Grouping("23-23/3"));
    }

    public static String Grouping(String input) throws SyntaxError, EvalError {
        try {
            ExprParser exprParser = new ExprParser(input);
            Expr expr = exprParser.parseE();
            StringBuilder stringBuild = new StringBuilder();
            expr.prettyPrint(stringBuild);
            return String.valueOf(stringBuild);
        }catch (Exception e){
            e.printStackTrace();
            return "Failed";
        }
    }
}
