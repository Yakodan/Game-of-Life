package net.yakodan;

import java.util.Arrays;
import java.util.Random;

public class GameEngine {

    public static boolean[][] generateMatrix(int n){
        boolean[][] matrix = new boolean[n][n];
        Random random = new Random();
        for(int i=0;i<10000;i++){
            int key = random.nextInt(n*n);
            matrix[key/n][key%n] = true;
        }

        return matrix;
    }


    public static boolean[][] getNextMatrix(boolean[][] oldMatrix){
        int n = oldMatrix.length;
        int[][] countMatrix = new int[n][n];

        for(int i = 0;i<n;i++){
            for(int j = 0; j<n;j++) {
                increaseSurroundingCount(oldMatrix[i][j],countMatrix,i,j);
            }
        }


        boolean[][] newMatrix = new boolean[n][n];

        for(int i = 0;i<n;i++){
            for(int j = 0; j<n;j++) {
                newMatrix[i][j] = countMatrix[i][j] == 3 || (countMatrix[i][j] == 2 && oldMatrix[i][j]);
            }
        }

        return newMatrix;
    }

    private static void increaseSurroundingCount(boolean cell, int[][] countMatrix,int i,int j){
        for(int k = i-1;k<=i+1;k++){
            for(int l = j-1;l<=j+1;l++){
                if(k==i && l==j){
                    continue;
                }
                if(cell){
                    try {
                        countMatrix[k][l]++;
                    } catch (ArrayIndexOutOfBoundsException e){
                    }
                }
            }
        }
    }
}
