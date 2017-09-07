
import model.Algorithm;
import model.Board;
import org.junit.Test;

import static org.junit.Assert.*;


public class AlgTest {

    @Test
    public void test() {
        int count = 0;
        for (int i = 0; i < 100; i++) {
            Board board = new Board(15,15);
            board.createGame(30);
            Algorithm algorithm = new Algorithm();
            algorithm.fullAlg(board);
            if (board.checkEnd() == 1) count++;
        }
        System.out.printf(" " + count);
        assertTrue(count >= 20);
    }
}
