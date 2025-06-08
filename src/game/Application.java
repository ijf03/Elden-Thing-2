package game;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
import game.actors.*;
import game.actors.boss.BedOfChaos;
import game.items.Seed;
import game.terrain.Bloodrose;
import game.terrain.Inheritree;
import game.items.Talisman;
import game.terrain.Blight;
import game.terrain.Floor;
import game.terrain.Soil;
import game.terrain.Wall;
import game.weapons.magic.FlamingStrike;
import game.weapons.magic.FrozenArmament;
import game.weapons.magic.LifestealFist;
import game.weapons.Broadsword;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
        world.addPlayer(player, gameMap.at(21, 7));

        // game setup
        gameMap.at(24, 11).addItem(new Talisman());
        gameMap.at(19, 12).addActor(new SpiritGoat());
        gameMap.at(20, 13).addActor(new OmenSheep());
        gameMap.at(25,12).addActor(new Sellen());
        gameMap.at(26,14).addActor(new Kale());
        gameMap.at(24,12).addActor(new Guts());
        gameMap.at(23,14).addActor(new GoldenBeetle());
        gameMap.at(19,7).addActor(new BedOfChaos());

        Random rand = new Random();
        if (rand.nextDouble() < 0.3) {

            int x1 = rand.nextInt(gameMap.getXRange().max());
            int y1 = rand.nextInt(gameMap.getYRange().max());
            gameMap.at(x1, y1).addItem(new LifestealFist());

            int x2 = rand.nextInt(gameMap.getXRange().max());
            int y2 = rand.nextInt(gameMap.getYRange().max());
            gameMap.at(x2, y2).addItem(new FrozenArmament());

            int x3 = rand.nextInt(gameMap.getXRange().max());
            int y3 = rand.nextInt(gameMap.getYRange().max());
            gameMap.at(x3, y3).addItem(new FlamingStrike());}

        player.addItemToInventory(new LifestealFist());
        player.addItemToInventory(new FrozenArmament());
        player.addItemToInventory(new FlamingStrike());
        player.addItemToInventory(new Broadsword());

        world.run();
    }
}
