import java.util.*;
import java.io.*;

public class Maze{

    private char[][]maze;
    private int startx,starty;
    private boolean animate;

    /*Constructor loads a maze text file.
      1. The file contains a rectangular ascii maze, made with the following 4 characters:
      '#' - locations that cannot be moved onto
      ' ' - locations that can be moved onto
      'E' - the location of the goal (only 1 per file)
      'S' - the location of the start(only 1 per file)

      2. The maze has a border of '#' around the edges. So you don't have to check for out of bounds!

      3. When the file is not found, print an error and exit the program.
    */
    public Maze(String filename, boolean ani){
        //COMPLETE CONSTRUCTOR
	try{
	    Scanner in = new Scanner(new File(filename));
	    animate = ani;

	    int i,j;
	    i=j=0;
	    while(in.hasNextLine())
		{
		    
		    String line = in.nextLine();
		    j=line.length();
		    i++;
		}
	    	    System.out.println(i + " " + j);
	    BufferedReader real = new BufferedReader(new FileReader(filename));
	    maze = new char[i][j];
	    for(int x=0;x<i;x++)
		{
		    //	    System.out.println(x); 
		    String next = real.readLine();
		    System.out.println(next.length());
		    for(int y=0;y<next.length();y++)
			{
			    // System.out.println(y);
			    if(next.charAt(y)=='S')
				{
				    startx = x;
				    starty = y;
				    // System.out.println(startx+ " "+starty);
				}
			    maze[x][y]=next.charAt(y);
			    //	    System.out.print(maze[x][y]);
			}
		    //		    System.out.println();
		}
	}
	catch(IOException e)
	    {System.out.println(e);}
	
    }


    /*Main Solve Function

      Things to note:
       When no S is contained in maze, print an error and return false.
    */
    public boolean solve(){
	//	System.out.println(this);
        if(startx < 0){
            System.out.println("No starting point 'S' found in maze.");
            return false;
        }else{
	    //	    System.out.println(startx+" "+starty);
            maze[startx][starty] = ' ';

	    return solve(startx,starty);
        }
    }

    /*
      Recursive Solve function:

      A solved maze has a path marked with '@' from S to E.
      The S is replaced with '@' but the 'E' is not.

      Postcondition:
        Returns true when the maze is solved,
        Returns false when the maze has no solution.

        All visited spots that were not part of the solution are changed to '.'
        All visited spots that are part of the solution are changed to '@'

    */
    private boolean solve(int x, int y){
        if(animate){
            System.out.println(this);
            wait(20);
        }
	//	System.out.println(x + " " + y);
	int[] goX = {x+1,x-1};
	int[] goY = {y+1,y-1};
	//	maze[x][y]='@';
	if(maze[x][y]=='E')
	    {
		//		System.out.println("solved");
		return true;
	    }
	for(int i=0;i<2;i++)
	    {
		if(goX[i] >0 && goX[i] < maze.length-1 && (maze[goX[i]][y]=='E'||maze[goX[i]][y]==' '))
		    {
			if(maze[goX[i]][y]=='E')
			    {
				return true;
			    }
			maze[goX[i]][y]='@';
			if(solve(goX[i],y))
			    {
				return true;
			    }
			else
			    {
				maze[goX[i]][y]='.';
			    }
		    }
	    }
	for(int i=0;i<2;i++)                                                                                                                                                                                            {                                                                                                                                                                                                              if(goY[i] >0 && goY[i] < maze[0].length-1 && (maze[x][goY[i]]=='E'||maze[x][goY[i]]==' '))                                                                                                
		        {   
			    if(maze[x][goY[i]]=='E')
				{
				    return true;
				}
			    maze[x][goY[i]]='@';                                                                                                                                                                                        if(solve(x,goY[i])||maze[x][goY[i]]=='E')                                                                                                                                                                                             {                                                                                                                                                                                                              return true;                                                                                                                                                                                           }                                                                                                                                                                                                      else                                                                                                                                                                                                           {                                                                                                                                                                                                              maze[x][goY[i]]='.';                                                                                                                                                                                   }                                                                                                                                                                                                  }                                                                                                                                                                                                  } 

	for(int i=0;i<2;i++)
	    {
		if(maze[goX[i]][y]=='@')
		   {
		       maze[goX[i]][y]='.';
		       return solve(goX[i],y);
		   }
	    }
	for(int i=0;i<2;i++)                                                                                                                                                                               	   {                                                                                                                                                                                               
	    if(maze[x][goY[i]]=='@')                                                                                                                                                            			   {
		   maze[x][goY[i]]='.';                                                                                                                                                                          	      return solve(x,goY[i]); 
	        } 
	}       
	

	//     return maze[x][y]=='E';

        //COMPLETE SOLVE
          return false; //so it compiles
    }


    //FREE STUFF!!! *you should be aware of this*

    public void clearTerminal(){
              System.out.println(CLEAR_SCREEN);
    }

    public String toString(){
        int maxx = maze.length;
        int maxy = maze[0].length;
        String ans = "";
        if(animate){
            ans = "Solving a maze that is " + maxx + " by " + maxy + "\n";
        }
        for(int i = 0; i < maxx * maxy; i++){
            if(i % maxx == 0 && i != 0){
                ans += "\n";
            }
            char c =  maze[i % maxx][i / maxx];
            if(c == '#'){
                ans += color(38,47)+c;
            }else{
                ans += color(32,40)+c;
            }
        }
        return HIDE_CURSOR + go(0,0) + ans + "\n" + SHOW_CURSOR + color(37,40);
    }

    //MORE FREE STUFF!!! *you can ignore all of this*
    //Terminal keycodes to clear the terminal, or hide/show the cursor
    private static final String CLEAR_SCREEN =  "\033[2J";
    private static final String HIDE_CURSOR =  "\033[?25l";
    private static final String SHOW_CURSOR =  "\033[?25h";
    //terminal specific character to move the cursor
    private String go(int x,int y){
        return ("\033[" + x + ";" + y + "H");
    }

    private String color(int foreground,int background){
        return ("\033[0;" + foreground + ";" + background + "m");
    }

    private void wait(int millis){
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException e) {
        }
    }

    
    //END FREE STUFF



}
