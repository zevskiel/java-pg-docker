/*
https://leetcode.com/problems/longest-increasing-path-in-a-matrix/description/
[
[1,1,1,1],
[1,1,1,1],
[2,3,4,1],
[1,1,5,6]
]
*/

import java.util.Stack;

public class dfs_memoization {
    public int longestIncreasingPath(int[][] matrix) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int max = 1;
        int[][] cache = new int[matrix.length][matrix[0].length];

        for(int row=0 ; row<matrix.length ; row++) {
            for(int col=0 ; col<matrix[0].length ; col++) {
                if(cache[row][col] == 0) {
                    max = Math.max(max, dfs(matrix, row, col, cache));
                }
            }
        }

        return max;
    }

    private class Node {
        int row;
        int col;
        int dist;

        public Node(int row, int col, int dist) {
            this.row = row;
            this.col = col;
            this.dist = dist;
        }
    }

    private int dfs(int[][] matrix, int row, int col, int[][] cache) {
        Stack<Node> stack = new Stack<Node>();
        stack.push(new Node(row, col, 1));

        int currMax = 0;
        int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!stack.isEmpty()) {
            Node currNode = stack.pop();

            if (cache[currNode.row][currNode.col] > currNode.dist) {
                continue;
            }

            //check all direction
            for (int x = 0; x < dir.length; x++) {
                int[] coor = dir[x];
                int nextRow = currNode.row + coor[0];
                int nextCol = currNode.col + coor[1];

                if ((nextRow >= 0 && nextRow < matrix.length)
                        && (nextCol >= 0 && nextCol < matrix[0].length)
                        && matrix[currNode.row][currNode.col] <= matrix[nextRow][nextCol]) {
                    stack.push(new Node(nextRow, nextCol, currNode.dist + 1));
                    cache[nextRow][nextCol] = currNode.dist + 1;
                }
            }

            currMax = Math.max(currMax, currNode.dist);
        }

        return currMax;
    }
}


//better time, dfs with recursion
 class Solution {
     public int longestIncreasingPath(int[][] matrix) {
         int row = matrix.length;
         int col = matrix[0].length;
         if (row == 1 && col == 1) {
             return 1;
         }

         int max = 0;
         int[][] memo = new int[row][col];

         for (int i = 0; i < row; i++) {
             for (int j = 0; j < col; j++) {
                 if (memo[i][j] == 0) {
                     max = Math.max(max, dfs(matrix, memo, i, j));
                 }
             }
         }

         return max;
     }

     public int dfs(int[][] matrix, int[][] memo, int x, int y) {
         if (memo[x][y] > 0) {
             return memo[x][y];
         }

         int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

         int ans = 1;

         int row = matrix.length;
         int col = matrix[0].length;

         for (int[] dir : dirs) {
             int nextX = x + dir[0];
             int nextY = y + dir[1];

             if (nextX < 0 || nextX >= row || nextY < 0 || nextY >= col) {
                 continue;
             }

             if (matrix[nextX][nextY] <= matrix[x][y]) {
                 continue;
             }

             ans = Math.max(ans, dfs(matrix, memo, nextX, nextY) + 1);
         }

         memo[x][y] = ans;
         return ans;
     }
 }
