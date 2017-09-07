package model;



import java.util.Random;
import java.util.ArrayList;

/**
 * Created by Slava on 28.06.2017.
 */

public class Algorithm {

    public class Group {
        private ArrayList<Integer> columns = new ArrayList<>();
        private ArrayList<Integer> rows = new ArrayList<>();
        private int value;

        public Group(Board board,int column, int row){
            int flags = 0;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (((row + j) >= 0) && ((column + i) >= 0) && ((row + j) < board.getHeight()) && ((column + i) < board.getWidth())) {
                        if (!board.getCell(column + i, row + j).isClicked() && !board.getCell(column + i, row + j).isFlagged()) {
                            columns.add(column + i);
                            rows.add(row + j);
                        }
                        if (board.getCell(column + i,row + j).isFlagged()) flags++;
                    }
                }
            }
            value = board.getValue(column, row) - flags;
        }

        public boolean finish(Board board) {
            boolean change = false;
            if (columns.size() == value) {
                for (int i = 0; i < columns.size(); i++) {
                    board.getCell(columns.get(i), rows.get(i)).addFlag();
                    change = true;
                }
            }
            if (value == 0) {
                for (int i = 0; i < columns.size(); i++) {
                    board.getCell(columns.get(i), rows.get(i)).cellClicked();
                    change = true;
                    if (board.getValue(columns.get(i), rows.get(i)) == 0) {
                        board.findZeroes(columns.get(i), rows.get(i));
                    }
                }
            }
            return change;
        }

    }

    private ArrayList<Group> groups = new ArrayList<>();
    

    public void findGroups(Board board) {
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                if (board.getCell(i, j).isClicked() && board.getValue(i, j) != 0) {
                    groups.add(new Group(board, i, j));
                }
            }
        }
    }


    public void step(Board board) {
        if (board.checkEnd() == 0) {
            boolean change = false;
            Random random = new Random();
            groups.clear();
            findGroups(board);
            for (int i = 0; i < groups.size(); i++) {
                if (groups.get(i).finish(board)) change = true;

            }
            if (!change) {
                ArrayList<Integer> x = new ArrayList<>();
                ArrayList<Integer> y = new ArrayList<>();
                for (int i = 0; i < board.getWidth(); i++) {
                    for (int j = 0; j < board.getHeight(); j++) {
                        if (!board.getCell(i, j).isClicked() && !board.getCell(i, j).isFlagged()) {
                            x.add(i);
                            y.add(j);
                        }
                    }
                }
                int k = random.nextInt(x.size());
                board.getCell(x.get(k), y.get(k)).cellClicked();
                board.findZeroes(x.get(k), y.get(k));
            }
        }

    }

    public void fullAlg(Board board) {
        while (board.checkEnd() == 0) {
            step(board);
        }
    }

}
