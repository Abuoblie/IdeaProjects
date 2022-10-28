package puissance4;

import java.util.Scanner;

public class P4Game {
    private int[] colHight = new int[]{5, 5, 5, 5, 5, 5, 5};
    private int[] entrePerCol = new int[]{0, 0, 0, 0, 0, 0, 0};
    private int totalslots;


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
    public static int parseInput(Scanner kb)
    {

        int userInput=0;
        while (!kb.hasNextInt()){
            System.out.println("le numero code invalide: veuillez reéssayer");
            var input = kb.nextLine();
        }

        userInput = kb.nextInt();
        return  userInput;
    }

    public void showBoad() {
        for(String[] line: board){
            for (String cel:line) {

                System.out.print("|"+cel+"|");
            }
            System.out.println();
        }
    }
    public boolean  horizontalWinningSlot(int col,String input){
        int i =this.colHight[col];

        //start counting  from three cells back
        int k=col-3<0? 0 : col-3;
        int counter = 0;

        //check for four consecutive matches
        for (int stCol = k;stCol<=col; stCol++ ){
            int j = stCol;
            while (this.board[i][j].equals(input) && counter<4 && j <6){
                counter++;
                j++;
            }
            if (counter == 4){
                return true;
            }
        }
        return false;
    }
    public boolean verticalWinningSlot(int col, String input){
        int i =this.colHight[col];
        int counter = 0;
        while (this.board[i][col].equals(input) && counter<4){
            counter++;
            if (i<5){i++;}
        }
        if (counter == 4){
            return true;
        }
        return false;
    }
    public boolean  rightDiagonalWinningSlot(int col,String input){
        //obtain the row index from colHight
        int a =this.colHight[col];
        //start counting  from three cells back
        int n = a+3>5? 5 : a+3;
        int k=col-3<0? 0 : col-3;
        int counter = 0;

        for (int b = n,c=k; a>=a && c<=col ; c++, b--){
            int i = b, j =c;

            while (this.board[i][j].equals(input)  && counter<4){
                counter++;
                // System.out.println(" i = "+i+"j = "+j);
                i--;
                if (j<6){j++;}

            }
            if (counter == 4){
                return true;
            }

        }
        return false;
    }
    public boolean  leftDiagonalWinningSlot(int col, String input){
        //obtain the row index from colHight
        int a =this.colHight[col];
        //start counting  from three cells back
        int n = a-3<0? 0 : a-3;
        int k=col-3<0? 0 : col-3;
        System.out.println(" n = "+n+"k = "+k);
        int counter = 0;
        for (int b = n, c=k; c<=col && b<=a; c++, b++){
            int i = b, j =c;

            while (this.board[i][j].equals(input)  && counter<4){

                counter++;
                if (i<5){i++;}
                if (j<6){j++;}
            }
            if (counter == 4){
                return true;
            }

        }
        return false;
    }
    public boolean play(Scanner kb,String x){
        System.out.println("merci d'encoder le numéro(0 à 6) de la colonne où vous souhaitez déposer vos jeton");
        int col = this.parseInput(kb);
        this.board[this.colHight[col]][col]=x;
        this.totalslots+=1;

        if (this.totalslots>=7){

            //check for horizontal  and vertical alignment
            boolean playerXh = this.horizontalWinningSlot(col,"X");
            boolean playerXv = this.verticalWinningSlot(col,"X");


            boolean playerYh = this.horizontalWinningSlot(col,"Y");
            boolean playerYv = this.verticalWinningSlot(col,"Y");


                //check for diagonal alignment
            boolean playerXDl = this.leftDiagonalWinningSlot(col,"X");
            boolean playerXDR = this.rightDiagonalWinningSlot(col,"X");

            boolean playerYDl = this.leftDiagonalWinningSlot(col,"Y");
            boolean playerYDR = this.rightDiagonalWinningSlot(col,"Y");

            if (playerXh || playerXv || playerXDl ||playerXDR ){
                System.out.println("playerX a gagner");
                return  true;
            }
            else if (playerYh ||playerYv|| playerYDl ||playerYDR ){
                System.out.println("playerY a gagner");
                return true;
            }
        }

        this.colHight[col]-=1;
        this.entrePerCol[col]+=1;
        this.showBoad();
        return false;
    }

}
