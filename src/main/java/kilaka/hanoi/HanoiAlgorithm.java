package kilaka.hanoi;

public class HanoiAlgorithm {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Board board = new Board(30);
        // 20: 1,048,575 times, <1 secs
        // 30: 1,073,741,823 times, 22 seconds
        // 40: 1,099,511,627,775 times, 10 hours
        board.draw();
        System.out.println("Number of moves: " + board.getMoves());
        System.out.println("Time in seconds : " + (System.currentTimeMillis() - start)/1000);
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                board.draw();
                System.out.println("Number of moves: " + board.getMoves());
                System.out.println("Time in seconds : " + (System.currentTimeMillis() - start)/1000);
            }
        }).start();

        // The actual solution
        solve(board);

        board.draw();

        System.out.println();
        System.out.println("Is board solved? " + board.isSolved());

        System.out.println("Time in seconds : " + (System.currentTimeMillis() - start)/1000);
        System.out.println("Number of moves: " + board.getMoves());
    }

    public static void solve(Board board) {
        solve(board, 0, 2, board.getNumberOfDisks());
    }

    private static void solve(Board board, int from, int to, int num) {
        if (num == 1) {
            board.move(from, to);
            return;
        }

        // from,to: help
        // 0,1: 2
        // 0,2: 1
        // 1,2: 0

        int help = 0;
        if (from != 1 && to != 1) {
            help = 1;
        } else if (from != 2 && to != 2) {
            help = 2;
        }

        solve(board, from, help, num-1);
        board.move(from, to);
        solve(board, help, to, num-1);
    }
}
