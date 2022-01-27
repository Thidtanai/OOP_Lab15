public class ExprTokenizer implements Tokenizer{
    private String src;
    private String next;
    private int pos;

    public ExprTokenizer(String src) throws SyntaxError{
        this.src = src;
        pos = 0;
        computeNext();
    }

    private void computeNext() throws SyntaxError{
        StringBuilder s = new StringBuilder();
        while(pos < src.length()){
            char c = src.charAt(pos);
            s.append(c);
            if(Character.isDigit(c)){
                for(pos++; pos<src.length() && Character.isDigit(src.charAt(pos)); pos++){
                    s.append(src.charAt(pos));
                }
                break;
            }else if(Character.isLetter(c)){
                break;
            }else if(c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '(' || c == ')'){
                pos++;
                break;
            }else if(Character.isWhitespace(c)){//for ignore whitespace
                pos++;
                s.setLength(s.length()-1);
            }else {
                throw new SyntaxError("unknow character: " + c);
            }
        }
        next = s.toString();
    }

    @Override
    public String peek() {
        return next;
    }

    @Override
    public String consume() throws SyntaxError {
        String sum = next;
        computeNext();
        return sum;
    }

    public boolean peek(String input){
        return next.equals(input);
    }

    public void consume(String input) throws SyntaxError{
        if(peek(input)){
            consume();
        }else {
            throw new SyntaxError("error with " + peek());
        }
    }
    
}
