import puissance4.P4Game;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.println("Bievenu au puissance 4 consol!");
        P4Game game = new P4Game();


        for (int u=0; u<15; u++){
            String x=(u%2 == 0)?"X":"Y";
            game.play(kb,x);
        }


    }
}
