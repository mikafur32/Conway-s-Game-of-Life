import Sison.UI.Input;
import java.io.IOException;
import java.io.*;

public class main
{
    private static void clearScreen() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }
    public static void main(String[] args) throws InterruptedException, IOException
    {
        World world = null;
        world = getWorld(world);

        World check = new World();
        while (!check.equals(world))
        {
            printGrid(world);
            check = world;
            world = world.getNextGen();

            clearScreen();
            //Thread.sleep();
        }
        clearScreen();
    }

    private static World getWorld(World world)
    {
        while (world == null)
        {
            String path = Input.get("Enter file path");
            try
            {
                world = World.fromFile(path);
            }
            catch (IOException e)
            {
                System.out.println("An I/O error occurred!");
            }
        }
        return world;
    }

    //C:\\Users\\perso\\Desktop\\Code\\test.txt
    private static void printGrid(World world)
    {
        for (int row = 0; row < world.getHeight(); row++)
        {
            for (int column = 0; column < world.getWidth(); column++)
            {
                System.out.print((world.getCell(row, column))
                    ? ("\u2588" + "\u2588")
                    : "  ");
            }
            System.out.println();
        }
    }
}