import java.io.*;
import java.util.*;
import java.nio.file.*;

public class World
{
    public static World fromFile(String filePath) throws IOException
    {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        World world = lines.isEmpty() ? new World(lines.size(), 0) : new World(lines.size(), lines.get(0).length());

        for(int row = 0; row < lines.size(); row++)
        {
            for(int column = 0; column < lines.get(row).length(); column++)
            {
                world.grid[row][column] = lines.get(row).charAt(column) == 'X';
            }
        }

        return world;
    }

    private boolean[][] grid;
    private int height;
    private int width;

    public World(int height, int width)
    {
        grid = new boolean[height][width];
        this.height = height;
        this.width = width;
    }
    public World()
    {
        grid = null;
    }

    public int getHeight()
    {
        return height;
    }
    public int getWidth()
    {
        return width;
    }

    public boolean getCell(int row, int column)
    {
       return grid[row][column];
    }

    //RULES
    // 1. live cell with LESS THAN 2 live neighbors DIES
    // 2. live cell with 2 OR 3 live neighbors SURVIVES
    // 3. live cell with MORE THAN 3 live neighbors DIES
    // 4. dead cell with EXACTLY 3 live neighbors becomes ALIVE

    public World getNextGen()
    {
        World genTwo = new World(getHeight(), getWidth());

        for(int row = 0; row < grid.length; row++)
        {
            for (int column = 0; column < grid[0].length; column++)
            {
                int neighbors = liveConditions(row, column);

                if(grid[row][column]) //LIVE cell
                {
                    if(neighbors < 2)                   //Rule 1. live cell with LESS THAN 2 live neighbors DIES
                    {
                        genTwo.grid[row][column] = false;
                    }
                                                        //Rule 2. live cell with 2 OR 3 live neighbors SURVIVES
                    if(neighbors == 3 || neighbors == 2)
                    {
                        genTwo.grid[row][column] = true;
                    }
                    if(neighbors > 3)                   //Rule 3. live cell with MORE THAN 3 live neighbors DIES
                    {
                        genTwo.grid[row][column] = false;
                    }
                }
                else if(neighbors == 3) //dead
                {
                    genTwo.grid[row][column] = true;   //Rule 4. dead cell with EXACTLY 3 live neighbors becomes ALIVE
                }
            }
        }

        return genTwo;
    }

    public int liveConditions(int row, int column)
    {
        return (streakLive(row, column, 0, 1) // vertical
            + streakLive(row, column, 0, -1) // anti vert
            + streakLive(row, column, 1, 1) // \ diagonal down
            + streakLive(row, column, 1, 0) // horizontal
            + streakLive(row, column, -1, 0) // anti horizontal
            + streakLive(row, column, 1, -1) //   / up
            + streakLive(row, column, -1, -1) //   \ up
            + streakLive(row, column, -1, 1)); //   \ down

    }
    private int streakLive(int row, int column, int changeX, int changeY)
    {
        int streak = 0;
        row = row + changeY;
        column = column + changeX;
        if(row < 0 || column < 0 || row >= grid.length || column >= grid[0].length)
        {
            return 0;
        }

        if(grid[row][column])
        {
            streak++;
        }
        return streak;
    }
    public boolean equals(World worldTwo)
    {
        if(grid == null)
        {
            return false;
        }
        for(int row = 0; row < grid.length; row++)
        {
            for (int column = 0; column < grid[0].length; column++)
            {
                if(grid[row][column] != worldTwo.getCell(row,column))
                {
                    return false;
                }

            }
        }
        return true;
    }
}










/*
    ........................X...........
	......................X.X...........
	............XX......XX............XX
	...........X...X....XX............XX
	XX........X.....X...XX..............
	XX........X...X.XX....X.X...........
	..........X.....X.......X...........
	...........X...X....................
	............XX......................
 */