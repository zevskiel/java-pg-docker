import java.util.LinkedList;

public class bfs2 {
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

    // BFS
    private static int shortestPath(int[][] array, int sourceRow, int sourceCol, int destRow, int destCol) {
        // add starting node to queue
        LinkedList<QItem> queue = new LinkedList<>();
        queue.add(new QItem(sourceRow, sourceCol, 0));

        //array to track visited node
        boolean[][] visited = new boolean[array.length][array[0].length];
        visited[sourceRow][sourceCol] = true;

        while (queue.isEmpty() == false) {
            QItem item = queue.remove();

            if(item.col == destCol && item.row == destRow) {
                return item.dist;
            }

            //search up
            if(isValid(item.row-1, item.col, array.length, array, visited)) {
                queue.add(new QItem(item.row-1, item.col, item.dist+1));
                visited[item.row-1][item.col] = true;
            }

            //search down
            if(isValid(item.row+1, item.col, array.length, array, visited)) {
                queue.add(new QItem(item.row+1, item.col, item.dist+1));
                visited[item.row+1][item.col] = true;
            }

            //search left
            if(isValid(item.row, item.col-1, array.length, array, visited)) {
                queue.add(new QItem(item.row, item.col-1, item.dist+1));
                visited[item.row][item.col-1] = true;
            }

            //search right
            if(isValid(item.row, item.col+1, array.length, array, visited)) {
                queue.add(new QItem(item.row, item.col+1, item.dist+1));
                visited[item.row][item.col+1] = true;
            }

        }

        //not found
        return -1;
    }

    // checking where it's valid or not
    private static boolean isValid(int x, int y,
                                   int size,
                                   int[][] array,
                                   boolean[][] visited) {
        //boundary & not visited yet & traversable path
        if(x>=0 && x<size && y>=0 && y<size && array[x][y] == 1 && !visited[x][y]) {
            return true;
        }
        return false;
    }
}