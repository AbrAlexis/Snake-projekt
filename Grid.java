public class Grid {
    public static void main(String[] args) {
        System.out.println("Welcome to Snake");
        for (int i = 0; i <= 10; i++) {
            System.out.print(i + "poop ");
        }
        String[][] b0n3r = new String[10][10];
        for (int j = 0; j < b0n3r.length; j++) {
            System.out.println();
            for (int k = 0; k < b0n3r[j].length; k++) {
                System.out.print("b0n3r! ");
            }
        }
    }
}