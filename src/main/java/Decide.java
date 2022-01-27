public class Decide {
    public static void main(String[] args) {
        String output = outputString();

        System.out.println(output);
    }

    static String outputString() {
        return "Hello world!";
    }

    private boolean launch(boolean[] FUV) {
        for (i=0; i<FUV.lengh; i++) {
            if (!FUV[i]) {
                System.out.println("NO");
                return false;
            }
        }
        System.out.println("YES");
        return true;
    }
}
