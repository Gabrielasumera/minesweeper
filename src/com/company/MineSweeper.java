package com.company;

import java.util.concurrent.ThreadLocalRandom;

public class MineSweeper {
    public enum CellState {EXPOSED, UNEXPOSED, SEALED}

    public enum GameStatus{INPROGRESS, LOSS, WIN}
    public boolean[][] mined = new boolean[10][10];
    public CellState[][] cells = new CellState[10][10];


    public MineSweeper(){
        for (int i = 0; i<10; i++){
            for (int j =0; j <10; j++){
                cells[i][j] = CellState.UNEXPOSED;
                mined[i][j] = false;
            }
        }
    }

    public void exposeCell(int row, int column) {
        if (cells[row][column] == CellState.UNEXPOSED) {
            cells[row][column] = CellState.EXPOSED;
            if (countNumberOfAdjacentCells(row, column) == 0)
                exposeNeighborsOf(row, column);
        }
    }

    public void exposeNeighborsOf(int row, int column){
        if (!mined[row][column]) {
            for (int i = -1; i < 2; i++)
                for (int j = -1; j < 2; j++)
                    if ((0 <= i+row && i+row < 10) && (0 <= j+column && j+column < 10) && (i != 0 || j != 0))
                        if (cells[row + i][column + j] == CellState.UNEXPOSED)
                            exposeCell(row + i, column + j);
        }
    }

    public void toggleSeal(int row, int column) {
        if(cells[row][column] == CellState.UNEXPOSED) {
            cells[row][column] = CellState.SEALED;
        }
        else if(cells[row][column] == CellState.SEALED) {
            cells[row][column] = CellState.UNEXPOSED;
        }
    }

    public CellState getCellState(int row, int column){
        return cells[row][column];
    }

    public void setMines() {
        for (int i = 0; i < 10;) {
            int randomRow = ThreadLocalRandom.current().nextInt(0, 10);
            int randomColumn = ThreadLocalRandom.current().nextInt(0, 10);
            if (!mined[randomRow][randomColumn]){
                mined[randomRow][randomColumn] = true;
                i++;
            }
        }
    }

    public int countNumberOfAdjacentCells(int row, int column){
        int count = 0;
        for (int i = -1; i < 2; i++)
            for (int j = -1; j < 2; j++)
                if ((0 <= i+row && i+row < 10) && (0 <= j+column && j+column < 10) && (i != 0 || j != 0))
                    if(mined[row+i][column+j])
                        count++;
        return count;
    }

    public GameStatus gameStatus() {
        int count = 0;
        boolean allCellsExpose = true;

        for (int i = 0; i< 10; i++){
            for (int j = 0; j < 10; j++){
                if (cells[i][j] == CellState.EXPOSED){
                    if (mined[i][j])
                        return GameStatus.LOSS;
                }
                else if (cells[i][j] == CellState.UNEXPOSED){
                    allCellsExpose = false;
                }
                else {
                    if (mined[i][j])
                        count++;
                }
            }
        }

        if(count==10 && allCellsExpose)
            return GameStatus.WIN;

        return GameStatus.INPROGRESS;
    }
}