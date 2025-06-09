package game;

import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.*;
import game.actors.*;
import game.actors.boss.BedOfChaos;
import game.capabilities.Status;
import game.items.Seed;
import game.items.TeleportationCircle;
import game.terrain.*;
import game.items.Talisman;

import java.util.Arrays;
import java.util.List;

import static edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes.STAMINA;

/**
 * The main class to setup and run the game.
 * @author Adrian Kristanto
 */
public class Application {

    public static void main(String[] args) {

        World world = new World(new Display());

        FancyGroundFactory groundFactory = new FancyGroundFactory(new Blight(),
                new Wall(), new Floor(), new Soil());

        List<String> valleyMap = Arrays.asList(
                "xxxx...xxxxxxxxxxxxxxxxxxxxxxx........xx",
                "xxx.....xxxxxxx..xxxxxxxxxxxxx.........x",
                "..........xxxx....xxxxxxxxxxxxxx.......x",
                "....xxx...........xxxxxxxxxxxxxxx.....xx",
                "...xxxxx...........xxxxxxxxxxxxxx.....xx",
                "...xxxxxxxxxx.......xxxxxxxx...xx......x",
                "....xxxxxxxxxx........xxxxxx...xxx......",
                "....xxxxxxxxxxx.........xxx....xxxx.....",
                "....xxxxxxxxxxx................xxxx.....",
                "...xxxx...xxxxxx.....#####.....xxx......",
                "...xxx....xxxxxxx....#___#.....xx.......",
                "..xxxx...xxxxxxxxx...#___#....xx........",
                "xxxxx...xxxxxxxxxx...##_##...xxx.......x",
                "xxxxx..xxxxxxxxxxx.........xxxxx......xx",
                "xxxxx..xxxxxxxxxxxx.......xxxxxx......xx");

        GameMap gameMap = new GameMap("Valley of the Inheritree", groundFactory, valleyMap);
        world.addGameMap(gameMap);

        // BEHOLD, ELDEN THING!
        for (String line : FancyMessage.TITLE.split("\n")) {
            new Display().println(line);
            try {
                Thread.sleep(200);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        Player player = new Player("Farmer", '@', 100);
        player.addBalance(3500);
        Seed InheritreeSeed = new Seed("Inheritree Seed", new Inheritree("Inheritree"));
        Seed BloodroseSeed = new Seed("Bloodrose Seed", new Bloodrose("Bloodrose"));
        player.addItemToInventory(InheritreeSeed);
        player.addItemToInventory(BloodroseSeed);
        player.addItemToInventory(new Talisman());
        player.addAttribute(STAMINA ,new BaseActorAttribute(200));
        world.addPlayer(player, gameMap.at(21, 7));

        // game setup
        gameMap.at(24, 11).addItem(new Talisman());
        gameMap.at(19, 12).addActor(new SpiritGoat());
        gameMap.at(20, 13).addActor(new OmenSheep(Status.RANDOM_BEHAVIOUR));
        gameMap.at(25,12).addActor(new Sellen());
        gameMap.at(26,14).addActor(new Kale());
        gameMap.at(24,12).addActor(new Guts());
        gameMap.at(12,14).addActor(new GoldenBeetle(Status.NORMAL_BEHAVIOUR));
        gameMap.at(20,7).addItem(new TeleportationCircle());

        world.run();
    }
}
