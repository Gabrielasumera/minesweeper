package com.company;
import javax.swing.*;

public class MineSweeperCell extends JButton {
    public final int row;
    public final int column;

    public MineSweeperCell(int Row, int Column){
        row = Row;
        column = Column;
        setSize(50,50);
    }
}