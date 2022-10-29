package puissance4;

import java.util.Random;
import java.util.Scanner;

import static java.lang.Math.round;

public class P4Game {
    private static final String ANSI_RESE ="\u001B[0m" ;
    private int Gi,Gj;
    private static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    private int[] colHight = new int[]{5, 5, 5, 5, 5, 5, 5};
    private int[] entrePerCol = new int[]{0, 0, 0, 0, 0, 0, 0};
    private int totalslots;
    private int previousRand=0;


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
            int j = stCol,counth = 0;

            while (this.board[i][j].equals(input) && counth<4){
                counth++;
                if(counth==4){Gi=i; Gj=j;}
                if (j+1>6){ break;}else{j++;}

            }
            if (counth == 4){
                return true;
            }
        }
        return false;
    }
    public boolean verticalWinningSlot(int col, String input){
        int i =this.colHight[col];
        int countv = 0;
        while (this.board[i][col].equals(input) && countv<4 ){
            countv++;
            if(countv==4){Gi=i; Gj=col;}
            if (i>=5){break;}else {i++;}

        }
        if (countv == 4){

            return true;
        }
        return false;
    }
    public boolean  rightDiagonalWinningSlot(int col,String input){
        if (col == 6){
            int startRowIndex =colHight[col];
            int i = startRowIndex, j =col, countDr=0;

            while (board[i][j].equals(input) && countDr<4){
                countDr++;
                if(countDr==4){Gi=i; Gj=j;}
                if(i==5||j==0){break;} else {i++;j--;}

            }
            if (countDr == 4) {
                return true;
            }
        } else {
            int row =colHight[col];
            int colStart =col+row>6? 6 : col+row;
            int startRowIndex = row+col-6<0? 0 : row+col-6;

            for (int i = startRowIndex, j = colStart; i <= row && j >=col; i++, j--){
                int counter = 0;

                while (board[i][j].equals(input) && counter<4){
                    counter++;
                    if(counter==4){Gi=i; Gj=j;}
                    if(i==5||j==0){break;} else {i++;j--;}

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
                if(counter==4){Gi=i; Gj=j;}
                if(i==5||j==6){break;} else {i++;j++;}
            }
            if (counter == 4) {
                return true;
            }
        } else {
            int row =colHight[col];
            int colStart =col-row<0? 0 : col-row;
            int startRowIndex = row-col<0? 0: row-col;

            for (int i = startRowIndex, j = colStart; i<= row && j <=col; i++, j++){
                int counter = 0;

                while (board[i][j].equals(input) && counter<4){
                    counter++;
                    if(counter==4){Gi=i; Gj=j;}
                    if(i==5||j==6){break;} else {i++;j++;}
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

        if (playerXh||playerYh ){
            for(int i = 0 ; i<6;i++){
                System.out.print("########################");
                for (int j = 0 ; j<7;j++) {

                        if(i==Gi && (j==Gj||j==Gj-1||j==Gj-2||j==Gj-3)){
                            System.out.print(ANSI_RED_BACKGROUND+"|"+board[i][j]+"|"+ ANSI_RESE);
                        }else {
                            System.out.print("|"+board[i][j]+"|");
                        }


                }
                System.out.println("#############################");
            }
            System.out.println("##########################################################################");

            System.out.println("player "+x+" a gagner sur l'horizontal");
            return true;

        }
        else if (playerXv||playerYv ){
            for(int i = 0 ; i<6;i++){
                System.out.print("########################");
                for (int j = 0 ; j<7;j++) {

                    if(j==Gj && (i==Gi||i==Gi-1||i==Gi-2||i==Gi-3)){
                        System.out.print(ANSI_RED_BACKGROUND+"|"+board[i][j]+"|"+ ANSI_RESE);
                    }else {
                        System.out.print("|"+board[i][j]+"|");
                    }


                }
                System.out.println("#############################");
            }
            System.out.println("##########################################################################");

            System.out.println("player "+x+" a gagner sur l'vertical");
            return true;
        } else if (playerXDl|| playerYDl) {
            for(int i = 0 ; i<6;i++){
                System.out.print("########################");
                for (int j = 0 ; j<7;j++) {

                    if((i==Gi && j==Gj)||(i==Gi-1 && j==Gj-1)||(i==Gi-2 && j==Gj-2)||(i==Gi-3 && j==Gj-3) ){
                        System.out.print(ANSI_RED_BACKGROUND+"|"+board[i][j]+"|"+ ANSI_RESE);
                    }else {
                        System.out.print("|"+board[i][j]+"|");
                    }


                }
                System.out.println("#############################");
            }
            System.out.println("##########################################################################");

            System.out.println("player "+x+" a gagner sur le DL");
            return true;
        } else if (playerXDR ||playerYDR) {
            for(int i = 0 ; i<6;i++){
                System.out.print("########################");
                for (int j = 0 ; j<7;j++) {

                    if((i==Gi && j==Gj)||(i==Gi-1 && j==Gj+1)||(i==Gi-2 && j==Gj+2)||(i==Gi-3 && j==Gj+3) ){
                        System.out.print(ANSI_RED_BACKGROUND+"|"+board[i][j]+"|"+ ANSI_RESE);
                    }else {
                        System.out.print("|"+board[i][j]+"|");
                    }


                }
                System.out.println("#############################");
            }
            System.out.println("##########################################################################");
            System.out.println("player "+x+" a gagner sur le DR");
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
                int min =0;
                int max=6;
                if (slot>1){
                    min = this.previousRand-2 < 0? 0: this.previousRand-2;
                    max = this.previousRand+2>6 ? 6 :this.previousRand+2;
                }



                col = (int) round(random.nextDouble(max-min)+min);
                while (this.colHight[col]<0){
                    col = (int) round(random.nextDouble(max+1-min)+min);
                }
                this.previousRand=col;
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
