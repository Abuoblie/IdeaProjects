import puissance4.P4Game;

import java.util.*;

public class Main {


    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.println("################### Bievenu au puissance 4 consol! #######################");
        P4Game game = new P4Game();
        int slot=1, mode;
        System.out.println("######################### Puissance 4 ####################################");
        System.out.println("################### Bien venu au puissance ###############################");
        System.out.println("######################## Mode de jeur ####################################");
        System.out.println("################# 1) jouer contre le consol ##############################");
        System.out.println("############ un autre chiffre pour jouer contre un ami ###################");
        mode = P4Game.parseInput(kb);
        boolean result = false;

        while (!result && slot < 42 ){
            String x=(slot%2 == 0)?"X":"Y";
            result = game.play(kb,x,mode,slot);
            slot++;
        }
        if (!result){
            System.out.println("le jeu s'est terminé sans vainqueur");
        }

    }
}

