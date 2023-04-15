public class StringBuilderExtended {
    StringBuilder sb;
    int count;
    
    StringBuilderExtended() {
        this.sb = new StringBuilder();
        count = 0;
    }
    
    int insert(String s) {
        sb.append(s);
        sb.append(" ");
        count++; 
        return count;
    }
    
    String removeFirstWord() {
        if (sb.length() == 0) {
            return null;
        }
        int index = sb.indexOf(" ");
        String result = sb.substring(0, index);
        sb.delete(0, index);
        sb.deleteCharAt(0);
        count--;
        return result.strip();
    }
    
    @Override
    public String toString() {
        return sb.toString().strip();
    }
}
