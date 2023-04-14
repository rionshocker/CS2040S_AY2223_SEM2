class HelloWorld {
    public static String name = "Jaryl";
    public static String joke_Qn = "What is 4 + 7?";
    public static String joke_Ans = "11?";
    public static String joke_Callback = "Yes Papa?";
    public static String info = "I like gaming! I am also the youngest yet the tallest in my family.";
    public static int ans_1a = 3125;
    public static String ans_1b = "Mystery Function is calculating the value of argA to the power of argB";

    public static void main(String[] args) {
        System.out.println("Hello, I am " + HelloWorld.name + "!");
        System.out.println("My favourite joke is: " + joke_Qn + " " + joke_Ans + " " + joke_Callback);
        //Context: 11 is a character in Stranger Things and all she could say at first was yes and papa.
        System.out.println(info);
        System.out.println("The answer for problem 1a is " + ans_1a + " and for problem 1b is " + ans_1b + ".");
    }
}
