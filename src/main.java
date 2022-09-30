import Sison.UI.Input;
import java.io.IOException;

public class main
{
    public static void main(String[] args) throws InterruptedException
    {
        World world = null;
        world = getWorld(world);

        World check = new World();
        while (!check.equals(world))
        {
            printGrid(world);
            check = world;
            world = world.getNextGen();

            System.out.printf("\u001B[%dA", world.getHeight()); //
            Thread.sleep(75);
        }
        System.out.printf("\u001B[%dB",world.getHeight());
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