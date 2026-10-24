
public class Sample {
    private final String message = "Hello, Java!";

    public static void main(String[] args) {
        Sample sample = new Sample();
        System.out.println(sample.message);
    }

    public int add(int a, int b) {
        return a + b;
    }
}
