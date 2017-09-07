package controller;

import model.Board;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MinesweeperAdapter extends MouseAdapter {

    private final int cellSize = 50;

    private boolean locked;
    private Board board;
    private Container container;

    public MinesweeperAdapter(Board board, Container container) {
        locked = false;
        this.board = board;
        this.container = container;
    }

    public void setLocked() {locked = true;}

    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if (!locked) {
            if (e.getX() < board.getWidth() * cellSize && e.getY() < board.getHeight() * cellSize) {

                    int column = e.getX() / cellSize;
                    int row = e.getY() / cellSize;
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        if (board.getCell(column, row).isFlagged()) {
                            board.getCell(column, row).cellFlagged();
                        } else {
                            board.getCell(column, row).cellClicked();
                            board.findZeroes(column, row);
                        }
                    }
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        if (!board.getCell(column, row).isClicked()) {
                            board.getCell(column, row).cellFlagged();
                        }
                    }

                    container.repaint();
            }
        }
    }
}
