package mazerunner;

/**
 *
 * @author cagri
 */
public class Robot {

    // current
    public int x;
    public int y;
    public Stack path;
    public int dir = 1;

    // prev
    Robot(int x, int y, Stack path) {
        this.x = x;
        this.y = y;
        this.path = path;

    }

    public void GetPosition() {

        System.out.println("Robot at X:" + x + "Y:" + y);
    }

    void SetPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    boolean noRightWall(int[][] maze, int mode) {
        // 0 north, 1 east, 2 south, 3 west
        // check for direction
        if (dir == 3) {

            if (maze[x - 1][y] != mode) {

                return true;
            }
        }

        if (dir == 1) {

            if (maze[x + 1][y] != mode) {

                return true;
            }
        }
        if (dir == 0) {

            if (maze[x][y + 1] != mode) {

                return true;
            }
        }

        if (dir == 2) {

            if (maze[x][y - 1] != mode) {

                return true;
            }
        }
        System.out.println("(((right WALL!))) " + mode + "\n");
        return false;
    }

    public boolean RobotUndoStep(int[][] maze) {

        Pos pos = (Pos) path.pop();
        System.out.println(pos.x + " " + pos.y);
        if (maze[x][y] == maze[pos.x][pos.y]) {
            maze[pos.x][pos.y] = 0;
            
            
            return true;
        }

        System.out.println("UNDO ERROR");
        return false;
    }

    void RotateCWAndStep(int[][] maze, boolean cikmazSokak) {

        System.out.println("current DIR" + dir);
        System.out.println("ROTATE CW AND STEP");

        if (dir > 2) {
            dir = 0;
        } else {
            dir++;
        }
        System.out.println("new DIR" + dir);
        if (dir == 1) {
            System.out.println(maze[x + 1][y] + "go east");
            if (maze[x][y + 1] != 1) {
                y++;
                maze[x][y] = 2;

            }
        }

        if (dir == 3) {
            System.out.println(maze[x + 1][y] + "go west");
            if (maze[x][y - 1] != 1) {
                y--;
                maze[x][y] = 2;
            }
        }
        if (dir == 2) {
            System.out.println(maze[x + 1][y] + "go south");
            if (maze[x + 1][y] != 1) {
                x++;
                maze[x][y] = 2;
            }
        }

        if (dir == 0) {
            System.out.println(maze[x][y + 1] + "go north");
            if (maze[x - 1][y] != 1) {
                x--;
                maze[x][y] = 2;
            }
        }
        System.out.println("push" + x + " " + y);
        if (!cikmazSokak) {
            path.push(new Pos(x, y));
        }
    }

    void Rotate90CCW() {
        System.out.println("current DIR" + dir);
        System.out.println("ROTATE CCW");

        if (dir < 1) {
            dir = 3;
        } else {
            dir--;
        }
        System.out.println("new DIR" + dir);
    }

    boolean noFrontWall(int[][] maze, int mode) {

        // 0 north, 1 east, 2 south, 3 west
        // check for direction
        if (dir == 0) {

            if (maze[x - 1][y] != mode) {

                return true;
            }
        }

        if (dir == 2) {

            if (maze[x + 1][y] != mode) {

                return true;
            }
        }
        if (dir == 1) {

            if (maze[x][y + 1] != mode) {

                return true;
            }
        }

        if (dir == 3) {

            if (maze[x][y - 1] != mode) {

                return true;
            }
        }
        System.out.println("(((There is FRONT! " + mode + ")))\n");
        return false;
    }

    void StepForward(int[][] maze, boolean cikmazSokak) {
        if (dir == 0) {
            System.out.println(maze[x - 1][y] + "go north");
            if (maze[x - 1][y] != 1) {
                x--;

                maze[x][y] = 2;

            }
        }

        if (dir == 2) {
            System.out.println(maze[x + 1][y] + "go south");
            if (maze[x + 1][y] != 1) {
                x++;
                maze[x][y] = 2;
            }
        }
        if (dir == 1) {
            System.out.println(maze[x][y + 1] + "go east");
            if (maze[x][y + 1] != 1) {
                y++;
                maze[x][y] = 2;
            }
        }

        if (dir == 3) {
            System.out.println(maze[x][y - 1] + "go west");
            if (maze[x][y - 1] != 1) {
                y--;
                maze[x][y] = 2;
            }
        }
        System.out.println("push" + x + " " + y);
        if (!cikmazSokak) {
            path.push(new Pos(x, y));
        }
    }

    void RobotMovement(int[][] maze, int grid) {

        int failCount = 0;
        boolean cikmazSokak = false;
        // while not reached the end. :D
        while (x < 2*grid-1 || y < 2*grid-1) {
            System.out.println("ROBOT X Y :" + x + "," + y);
            if (failCount == 2) {
                cikmazSokak = true;
                failCount = 0;
            }
            System.out.println("cikmazSokak" + cikmazSokak);
            if (noRightWall(maze, 1)) {

                if (!noRightWall(maze, 2) && cikmazSokak) {

                    this.RobotUndoStep(maze);
                    
                }
                if(noRightWall(maze, 2) && cikmazSokak)
                {
                    cikmazSokak = false;
                }
                
               ;
                RotateCWAndStep(maze, cikmazSokak);
                failCount = 0;

            } else if (noFrontWall(maze, 1)) {

                if (!noFrontWall(maze, 2) && cikmazSokak) {
                    this.RobotUndoStep(maze);
                    
                }
                if(noFrontWall(maze, 2) && cikmazSokak)
                {
                    cikmazSokak = false;
                }
                
                StepForward(maze, cikmazSokak);
                failCount = 0;

            } else {
                failCount++;
                System.out.println("failCount" + failCount);

                if (failCount == 2) {
                    cikmazSokak = true;
                }
                Rotate90CCW();

            }

            try {
                System.in.read();
            } catch (Exception e) {
            }
            System.out.print("\033[H\033[2J");
            System.out.flush();

            MazeUtility.plotMaze(maze);

        }

        System.out.println("Path Stack");
        while (!path.isEmpty()) {

            Pos pos = (Pos) path.pop();
            System.out.println("| x:" + pos.x + "  y:" + pos.y + " |");
        }
        if (path.isEmpty()) {
            System.out.println("___________");
        }
    }

    void RobotCheckTailRight() {

    }

}
