import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BattleHandler {
    Pokemon[] friends;
    Pokemon[] foes;
    int youInd = 0;
    int foeInd = 0;
    String foeName;
    String foeClass;
    String greeting;

    public BattleHandler(PChar player, String battle)
    {
        readBattle(battle);
        friends = player.getPokemons();
    }

    private void readBattle(String battle)
    {
        try {
            Scanner info = new Scanner(new File("Data/Battling/Battles" + battle));
            foeClass = info.nextLine();
            foeName = info.nextLine();
            greeting = info.nextLine();
            int numpoke = Integer.parseInt(info.nextLine());
            foes = new Pokemon[numpoke];
            for (int i = 0; i < numpoke; i++) {
                String pokeName = info.nextLine();
                String stats = info.nextLine();
                int numMoves = Integer.parseInt(info.nextLine());
                Move[] moves = new Move[numMoves];
                for (int j = 0; j < numMoves; j++) {
                    String movName = info.nextLine();
                    moves[j] = new Move(movName);
                }
                foes[i] = new Pokemon(stats,pokeName, moves);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
