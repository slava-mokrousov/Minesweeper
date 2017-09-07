package view;

import model.Algorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showMessageDialog;

public class GameFrame {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Minesweeper");
        GameBoard board = new GameBoard();
        board.newGame(15, 15, 30);
        Algorithm algorithm = new Algorithm();
        board.setLayout(new BorderLayout());
        JScrollPane scroll = new JScrollPane(board);
        JMenuBar menu = new JMenuBar();
        JMenu game = new JMenu("game");
        JMenu help = new JMenu("help");
        JMenuItem newGame = new JMenuItem("New game");
        JMenuItem oneStep = new JMenuItem("One step");
        JMenuItem fullAlg = new JMenuItem("Full algorithm");
        game.add(newGame);
        help.add(oneStep);
        help.add(fullAlg);
        menu.add(game);
        menu.add(help);
        frame.setJMenuBar(menu);
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.newGame(15,15,30);
            }
        });
        oneStep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                algorithm.step(board.getBoard());
                board.repaint();
            }
        });
        fullAlg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                algorithm.fullAlg(board.getBoard());
                board.repaint();
                if (board.getBoard().checkEnd() == 1) {
                    showMessageDialog(board, "you won");
                }
                if (board.getBoard().checkEnd() == -1) {
                    showMessageDialog(board, "you lost");
                }
            }
        });
        frame.add(scroll);
        frame.setSize(1200, 1200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}