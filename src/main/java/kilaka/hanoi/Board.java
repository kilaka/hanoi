package kilaka.hanoi;

import com.google.common.collect.Iterators;
import lombok.SneakyThrows;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Board {

    private final int numberOfDisks;
    private Stack<Integer>[] towers = new Stack[]{new StackImpl<>(), new StackImpl<>(), new StackImpl<>()};
    private volatile long moves = 0;

    public Board(int numberOfDisks) {
        this.numberOfDisks = numberOfDisks;
        IntStream.rangeClosed(1, numberOfDisks).boxed().sorted(Collections.reverseOrder()).forEach(a()::push);
    }

    public int getNumberOfDisks() {
        return numberOfDisks;
    }

    private Stack<Integer> a() {
        return towers[0];
    }

    private Stack<Integer> b() {
        return towers[1];
    }

    private Stack<Integer> c() {
        return towers[2];
    }

    public boolean isSolved() {
        return Iterators.elementsEqual(IntStream.rangeClosed(1, numberOfDisks).boxed().sorted(Collections.reverseOrder()).iterator(),
                c().stream().iterator());
    }

    public synchronized void draw() {
        System.out.println();
        System.out.println("----------");
        for (int towerNum = 0 ; towerNum < towers.length ; towerNum++) {
            System.out.print(towerNum + " |");
            towers[towerNum].stream().forEach(i -> System.out.print(i + "-"));
            System.out.println();
        }
        System.out.println("----------");
    }

    @SneakyThrows
    public synchronized void move(int from, int to) {
        if (from < 0 || from > 3 || to < 0 || to > 3) {
            throw new IllegalArgumentException("Illegal from or to: " + from + "," + to);
        }

        int popped = towers[from].pop();
        towers[to].push(popped);
        moves++;

//        draw();
//        Thread.sleep(100);
    }

    public long getMoves() {
        return moves;
    }

    public interface Stack<T> {
        T pop();

        void push(T t);

        Stream<T> stream();
    }

    public static class StackImpl<T> implements Stack<T> {

        private final List<T> list = new LinkedList<>();

        @Override
        public T pop() {
            return list.remove(list.size() - 1);
        }

        @Override
        public void push(T t) {
            list.add(t);
        }

        @Override
        public Stream<T> stream() {
            return list.stream();
        }
    }
}
