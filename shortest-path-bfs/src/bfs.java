import java.util.LinkedList;
import java.util.Queue;

public class bfs{
    static class QItem {
        int row;
        int col;
        int dist;
        public QItem(int row, int col, int dist)
        {
            this.row = row;
            this.col = col;
            this.dist = dist;
        }
    }

    public static void main(String[] args) {
        /*
                {1,1,0,0,0},
                {1,2,0,0,1},
                {1,1,0,0,1},
                {1,1,2,1,1},
                {1,1,1,1,1},
        * */
        // find where is the base located, base should be in 1 and should have minimal maximum distance to 2
        int[][] array = {
                {1,1,0,0,0},
                {1,1,0,0,1},
                {1,1,0,0,1},
                {1,1,1,1,1},
                {1,1,1,1,1},
        };
        //count of 2s
        int numberOfDest = 2;
        int[][] dest = {
                {1,1},
                {3,2}
        };

        int minimalDistance = Integer.MAX_VALUE;

        for(int x=0; x<array.length ; x++) {
            for(int y=0 ; y<array[0].length ; y++) {
                //skip
                if(array[x][y] == 0) {
                    continue;
                }

                int currMaxDistance = Integer.MIN_VALUE;
                for(int count=0 ; count<numberOfDest ; count++) {
                    currMaxDistance = Math.max(currMaxDistance, shortestPath(array, x, y, dest[count][0], dest[count][1]));
                }

                minimalDistance = Math.min(minimalDistance, currMaxDistance);
            }
        }

        System.out.println(minimalDistance);
    }

    private static int shortestPath(int[][] array, int row, int col, int destRow, int destCol) {
        // applying BFS on matrix cells starting from source
        Queue<QItem> queue = new LinkedList<>();
        queue.add(new QItem(row, col, 0));

        boolean[][] visited = new boolean[array.length][array[0].length];
        visited[row][col] = true;

        while (queue.isEmpty() == false) {
            QItem p = queue.remove();

            // Destination found;
            if (p.row == destRow && p.col == destCol)
                return p.dist;

            // moving up
            if (isValid(p.row - 1, p.col, array, visited)) {
                queue.add(new QItem(p.row - 1, p.col,
                        p.dist + 1));
                visited[p.row - 1][p.col] = true;
            }

            // moving down
            if (isValid(p.row + 1, p.col, array, visited)) {
                queue.add(new QItem(p.row + 1, p.col,
                        p.dist + 1));
                visited[p.row + 1][p.col] = true;
            }

            // moving left
            if (isValid(p.row, p.col - 1, array, visited)) {
                queue.add(new QItem(p.row, p.col - 1,
                        p.dist + 1));
                visited[p.row][p.col - 1] = true;
            }

            // moving right
            if (isValid(p.row, p.col + 1, array, visited)) {
                queue.add(new QItem(p.row, p.col + 1,
                        p.dist + 1));
                visited[p.row][p.col + 1] = true;
            }
        }

        //not found
        return -1;
    }

    // checking where it's valid or not
    private static boolean isValid(int x, int y,
                                   int[][] grid,
                                   boolean[][] visited)
    {
        if (x >= 0 && y >= 0 && x < grid.length
                && y < grid[0].length && grid[x][y] != 0
                && !visited[x][y]) {
            return true;
        }
        return false;
    }
}