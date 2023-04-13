package com.company;
//import MineSweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MineSweeperFrame extends JFrame {
    MineSweeper mineSweeper;
    com.company.MineSweeperCell cell;


    com.company.MineSweeperCell[][] cellButtons;

    @Override
    protected void frameInit(){
        super.frameInit();
        mineSweeper = new MineSweeper();
        mineSweeper.setMines();
        setLayout(new GridLayout(10,10));
        cellButtons = new com.company.MineSweeperCell[10][10];
        for (int i = 0; i < 10; i++){
            for (int j = 0; j <10; j++){
                cell = new com.company.MineSweeperCell(i, j);
                getContentPane().add(cell);

                cell.addActionListener(new CellClickedHandler());
                cell.addMouseListener(new rightClick());

                cellButtons[i][j] = cell;
                cell.setFont(new Font("Arial", Font.PLAIN, 40));
            }
        }
    }

    public static void main(String[] args){
        JFrame frame = new MineSweeperFrame();
        frame.setSize(750, 750);
        frame.setVisible(true);
    }

    private  class rightClick implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) { }

        @Override
        public void mousePressed(MouseEvent e) { }

        @Override
        public void mouseReleased(MouseEvent e) {
            com.company.MineSweeperCell cell = (com.company.MineSweeperCell) e.getSource();

            if(SwingUtilities.isRightMouseButton(e))
            {

                if(mineSweeper.getCellState(cell.row,cell.column)== MineSweeper.CellState.UNEXPOSED)
                    cell.setText("S");
                else if(mineSweeper.getCellState(cell.row,cell.column)== MineSweeper.CellState.SEALED)
                    cell.setText("");

                mineSweeper.toggleSeal(cell.row,cell.column);

            }
            updateGame();
        }

        @Override
        public void mouseEntered(MouseEvent e) { }

        @Override
        public void mouseExited(MouseEvent e) { }
    }

    private class CellClickedHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent){
            com.company.MineSweeperCell cell = (com.company.MineSweeperCell) actionEvent.getSource();

            mineSweeper.exposeCell(cell.row, cell.column);

            MineSweeper.CellState placed = mineSweeper.getCellState(cell.row, cell.column);

            if(mineSweeper.mined[cell.row][cell.column]&&mineSweeper.cells[cell.row][cell.column]!= MineSweeper.CellState.SEALED) {
                gameOver();
            }
            else if (placed == MineSweeper.CellState.EXPOSED){
                cell.setBackground(Color.lightGray);

                updateGame();
            }
        }
    }

    public void gameOver(){
        for(int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                cell = cellButtons[i][j];

                if(mineSweeper.mined[i][j]==true){
                    mineSweeper.cells[i][j] = MineSweeper.CellState.EXPOSED;
                    cell.setBackground(Color.RED);
                    cell.setFont(new Font("Arial", Font.BOLD, 40));
                    cell.setText("M");
                }
                if(mineSweeper.cells[i][j] == MineSweeper.CellState.UNEXPOSED){
                    cell.setBackground(Color.lightGray);
                    int adjacentCount = mineSweeper.countNumberOfAdjacentCells(i,j);
                    if (adjacentCount > 0){
                        cell.setText(String.valueOf(adjacentCount));
                    }
                }
            }
        }
        JOptionPane.showMessageDialog(cell, "przegrana");
    }

    public void updateGame(){
        for(int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                if (mineSweeper.cells[i][j] == MineSweeper.CellState.EXPOSED && !mineSweeper.mined[i][j]){
                    cell = cellButtons[i][j];
                    cell.setBackground(Color.lightGray);

                    int adjacentCount = mineSweeper.countNumberOfAdjacentCells(i, j);
                    if (adjacentCount > 0){
                        cell.setText(String.valueOf(adjacentCount));
                    }
                }
            }
        }

        MineSweeper.GameStatus getStatus = mineSweeper.gameStatus();

        if (getStatus == MineSweeper.GameStatus.WIN){
            JOptionPane.showMessageDialog(cell, "wygrana");

        }
    }
}
