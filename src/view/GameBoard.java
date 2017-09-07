package view;

import controller.MinesweeperAdapter;
import model.Board;

import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameBoard extends JPanel{

    private final int cellSize = 50;

    private Board board;

    private MinesweeperAdapter adapter;



    public void newGame(int width, int height, int bombs) {
        board = new Board(width,height);
        board.createGame(bombs);
        adapter = new MinesweeperAdapter(board, this);
        this.addMouseListener(adapter);
        this.repaint();
    }

    public Board getBoard() {
        return board;
    }


    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Consolas", Font.PLAIN, 40));
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1));
        for (int x = 0; x <= board.getWidth()*cellSize; x += cellSize) {
            g2d.drawLine(x, 0, x, board.getHeight()*cellSize);
        }
        for (int y = 0; y <= board.getHeight()*cellSize; y += cellSize) {
            g2d.drawLine(0, y, board.getWidth() * cellSize, y);
        }
        for (int column = 0; column < board.getWidth(); column++) {
            for (int row = 0; row < board.getHeight(); row++) {
                if (board.getCell(column, row).isClicked()) {
                    int number = board.getValue(column, row);
                    if (number > 0) {
                        if (number == 9) {
                            try {
                                BufferedImage image = ImageIO.read(new File("res/mine.png"));
                                g2d.drawImage(image, column * cellSize + 1, row * cellSize + 1, cellSize - 1, cellSize - 1, null);
                            } catch (java.io.IOException ex) {
                            }
                            adapter.setLocked();
                        }
                        else {
                            g2d.setColor(Color.RED);
                            g2d.drawString("" + number, column * cellSize + cellSize / 3, row * cellSize + cellSize * 2 / 3);
                            }
                        }
                    else {
                        g2d.setColor(Color.GRAY);
                        g2d.fillRoundRect(column * cellSize , row * cellSize ,  cellSize  , cellSize  , 40, 40);
                    }
                }
                if (board.getCell(column, row).isFlagged()) {
                    try {
                        BufferedImage image = ImageIO.read(new File("res/flag.png"));
                        g2d.drawImage(image, column * cellSize + 1, row * cellSize + 1, cellSize - 1, cellSize - 1, null);
                    }
                    catch(java.io.IOException ex){
                    }
                }
            }
        }
        this.setPreferredSize(new Dimension(board.getWidth()*cellSize, board.getHeight()*cellSize));

    }
}


