package puissance4;

import java.util.Random;
import java.util.Scanner;

public class P4Game {
    private int[] colHight = new int[]{5, 5, 5, 5, 5, 5, 5};
    private int[] entrePerCol = new int[]{0, 0, 0, 0, 0, 0, 0};
    private int totalslots;


    private String[][] board = new String[6][7];

    public P4Game() {

        for(int i = 0; i < 6; ++i) {
            System.out.print("########################");
            for(int j = 0; j < 7; ++j) {
                this.board[i][j] = "0";
                System.out.print("|0|");
            }

            System.out.println("#############################");
        }
        System.out.println("##########################################################################");
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
            System.out.print("########################");
            for (String cel:line) {

                System.out.print("|"+cel+"|");
            }
            System.out.println("#############################");
        }
        System.out.println("##########################################################################");
    }

    public boolean  horizontalWinningSlot(int col,String input){
        int i =this.colHight[col];

        //start counting  from three cells back
        int k=col-3<0? 0 : col-3;
        int lim = col+3>6? 6 : col+3;
        //check for four consecutive matches
        for (int stCol = k; stCol<=col; stCol++ ){
            int j = stCol,counter = 0;

            while (this.board[i][j].equals(input) && counter<4 && j<lim){
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
        int lim = i+3>5? 5 : i+3;
        int counter = 0;
        while (this.board[i][col].equals(input) && counter<4){
            counter++;

            if (i<lim)i++;
        }
        if (counter == 4){

            return true;
        }
        return false;
    }
    public boolean  rightDiagonalWinningSlot(int col,String input){
        if (col == 6){
            int startRowIndex =colHight[col];
            int i = startRowIndex, j =col, counter=0;
            while (board[i][j].equals(input) && counter<4){
                counter++;
                if(i<5)i++;
                if(j>0)j--;
            }
            if (counter == 4) {
                return true;
            }
        } else if (col>2 && colHight[col]<3) {
            int row =colHight[col];
            int colStart =col+row>6? 6 : col+row;
            int startRowIndex = row+col-6<0? 0 : row+col-6;

            for (int i = startRowIndex, j = colStart; i <= row && j >=col; i++, j--){
                int counter = 0;

                while (board[i][j].equals(input) && counter<4){
                    counter++;
                    if(i<5)j++;
                    if(j>0)j--;

                }
                if (counter == 4) {
                    return true;
                }

            }
        }
        return false;
    }
    public boolean  leftDiagonalWinningSlot(int col, String input){
        if (col == 0){
            int startRowIndex =colHight[col];
            int i = startRowIndex, j =col, counter=0;
            while (board[i][j].equals(input) && counter<4){
                counter++;
                if(i<5)i++;
                if(j<6)j++;
            }
            if (counter == 4) {
                return true;
            }
        } else if (col<4 && colHight[col]<3) {
            int row =colHight[col];
            int colStart =col-row<0? 0 : col-row;
            int startRowIndex = col-row<0? (col-row)+row: 0;
            for (int i = startRowIndex, j = colStart; i<= row && j <=col; i++, j++){
                int counter = 0;

                while (board[i][j].equals(input) && counter<4){
                    counter++;
                    if(i<5)i++;
                    if(j<6)j++;
                }
                if (counter == 4) {
                    return true;
                }

            }
        }

        return false;
    }
    public boolean play(Scanner kb,String x, int mode , int slot) {

        int col = this.col(kb,mode,slot);
        int row = this.colHight[col];
        this.board[row][col] = x;
        this.totalslots += 1;
        boolean playerXv = false,playerYv=false,playerXh=false,playerYh=false,playerXDl=false,playerXDR=false,playerYDl=false,playerYDR=false;

        if (this.totalslots >= 7 && row <= 2) {
            //check for vertical alignment
            playerXv = this.verticalWinningSlot(col, "X");
            playerYv = this.verticalWinningSlot(col, "Y");
        }
        if (this.totalslots >= 7 && !(playerXv||playerYv)) {
            //check for horizontal
            playerXh = this.horizontalWinningSlot(col, "X");
            playerYh = this.horizontalWinningSlot(col, "Y");
        }
        if (this.totalslots >= 10 && !(playerXh||playerYh) ){
            //check for diagonal alignment
            playerXDl = this.leftDiagonalWinningSlot(col, "X");
            playerXDR = this.rightDiagonalWinningSlot(col, "X");

            playerYDl = this.leftDiagonalWinningSlot(col, "Y");
            playerYDR = this.rightDiagonalWinningSlot(col, "Y");
        }
        this.showBoad();

        if (playerXh || playerXv || playerXDl ||playerXDR ){
                System.out.println("playerX a gagner");
                return true;

        }
        else if (playerYh ||playerYv|| playerYDl ||playerYDR ){
                System.out.println("playerY a gagner");
                return true;
        }


        this.colHight[col]-=1;
        this.entrePerCol[col]+=1;

        return false;
    }
    public int col(Scanner kb,int mode, int slot){
        int col=-1;
        if(mode == 1){
            Random random = new Random();
            if(slot%2==0){
                System.out.println("merci d'encoder le numéro(0 à 6) de la colonne où vous souhaitez déposer vos jeton ");
                do {
                    col = this.parseInput(kb);
                    if (this.colHight[col]<0 ){
                        System.out.println("la colonne choisie est pleine veuillez en choisir une autre");
                    } else if (col<0 || col>6) {
                        System.out.println("numero invalid: merci d'encoder un numéro de 0 à 6");
                    }
                }while (this.colHight[col]<0 || (col<0 || col>6));

            }else if (slot%2==1){

                col = random.nextInt(7);
                while (this.colHight[col]<0){
                    col = random.nextInt(7);
                }
            }

        }
        else {
            System.out.println("merci d'encoder le numéro(0 à 6) de la colonne où vous souhaitez déposer vos jeton ");
            do {
                col = this.parseInput(kb);
                if (this.colHight[col]<0 ){
                    System.out.println("la colonne choisie est pleine veuillez en choisir une autre");
                } else if (col<0 || col>6) {
                    System.out.println("numero invalid: merci d'encoder un numéro de 0 à 6");
                }
            }while (this.colHight[col]<0 || (col<0 || col>6));

        }


        return  col;
    }

}
