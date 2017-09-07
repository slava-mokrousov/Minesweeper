package model;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Board {

    private int height;
    private int width;

    private List<List<Cell>> array = new ArrayList<>();

    final Random random = new Random();

    public Board(int width, int height) {
        this.height = height;
        this.width = width;
        for (int i = 0; i < width; i++) {
            array.add(new ArrayList<>());
            for (int j = 0; j < height; j++) {
                array.get(i).add(new Cell());
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getValue(int column, int row) { return array.get(column).get(row).getValue(); }

    public void setValue(int column, int row) {
        if (!array.get(column).get(row).isBomb()) {
            for (int i = -1; i <= 1 ; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (((row + j) >= 0) && ((column + i) >= 0) && ((row + j) < height) && ((column + i) < width)) {
                        if (array.get(column + i).get(row + j).isBomb()) {
                            array.get(column).get(row).incValue();
                        }
                    }
                }
            }
        }
    }

    public void setValues() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                array.get(i).get(j).clearValue();
                setValue(i,j);
            }
        }
    }

    public void setBombs(int amount) {
        int i = 0;
        while (i < amount) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            if (!array.get(x).get(y).isBomb()) {
                array.get(x).get(y).setBomb();
                array.get(x).get(y).setValue(9);
            }
            else i--;
            i++;
        }
    }


    public Cell getCell(int column, int row) {
        return array.get(column).get(row);
    }


    public void findZeroes(int column, int row) {
        if (array.get(column).get(row).getValue() == 0) {
            ArrayList<Integer> columns = new ArrayList<>();
            ArrayList<Integer> rows = new ArrayList<>();
            columns.add(column);
            rows.add(row);
            int iter = 0;
            while (iter < columns.size()) {
                column = columns.get(iter);
                row = rows.get(iter);
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if ((column + i) >= 0 && (column + i) < width && (row + j) >= 0 && (row + j) < height) {
                            if (!array.get(column + i).get(row + j).isClicked()) {
                                array.get(column + i).get(row + j).flagClear();
                                array.get(column + i).get(row + j).cellClicked();
                                if (array.get(column + i).get(row + j).getValue() == 0) {
                                    columns.add(column + i);
                                    rows.add(row + j);
                                }
                            }
                        }
                    }
                }
                iter++;
            }
        }
    }


    public void createGame(int bombs) {
        setBombs(bombs);
        setValues();
    }

    public int checkEnd() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (array.get(i).get(j).isClicked() && array.get(i).get(j).isBomb()) {
                    return -1;//lose
                }
            }
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (!array.get(i).get(j).isClicked() && !array.get(i).get(j).isBomb()) {
                    return 0; //cont
                }
            }
        }
        return 1; //win
    }
}

