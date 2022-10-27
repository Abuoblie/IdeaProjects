package puissance4;

public class P4Game {
    private int[] colHight = new int[]{5, 5, 5, 5, 5, 5, 5};
    private int[] entrePerCol = new int[]{0, 0, 0, 0, 0, 0, 0};
    private String[][] board = new String[6][7];

    public P4Game() {
        System.out.println("welcome to my puissance 4");

        for(int i = 0; i < 6; ++i) {
            for(int j = 0; j < 7; ++j) {
                this.board[i][j] = "o";
                System.out.print("|o|");
            }

            System.out.println();
        }

    }

    public void showBoad() {
        for(String[] line: board){
            for (String cel:line) {

                System.out.print("|"+cel);
            }
            System.out.println();
        }
    }

}
