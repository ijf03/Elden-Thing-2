package game;

import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
import game.actors.*;
import game.items.Seed;
import game.terrain.Bloodrose;
import game.terrain.Inheritree;
import game.items.Talisman;
import game.terrain.Blight;
import game.terrain.Floor;
import game.terrain.Soil;
import game.terrain.Wall;

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
        Seed InheritreeSeed = new Seed("Inheritree Seed", new Inheritree("Inheritree"));
        Seed BloodroseSeed = new Seed("Bloodrose Seed", new Bloodrose("Bloodrose"));
        player.addItemToInventory(InheritreeSeed);
        player.addItemToInventory(BloodroseSeed);
        player.addItemToInventory(new Talisman());
        player.addAttribute(STAMINA ,new BaseActorAttribute(100));
        world.addPlayer(player, gameMap.at(25, 13));

        // game setup
        gameMap.at(24, 11).addItem(new Talisman());
        gameMap.at(23, 12).addActor(new SpiritGoat());
        gameMap.at(20, 13).addActor(new OmenSheep());
        gameMap.at(20,12).addActor(new Sellen());
        gameMap.at(14,12).addActor(new Kale());
        gameMap.at(24,12).addActor(new Guts());

        world.run();
    }
}
