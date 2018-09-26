package com.zz4955.spliterator_ex;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

// Java8里面的java.util.Spliterator接口有什么用？：https://segmentfault.com/q/1010000007087438
// 这个错在：trySplit中for那里的初始化有问题，所以这里的并行其实还是串行。另外，还有其他错误，见_wrong2文件里的说明。
public class NumCounterSpliterator_wrong implements Spliterator<Character> {

    private String str;
    private int currentChar = 0;

    public NumCounterSpliterator_wrong(String str) {
        this.str = str;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        action.accept(str.charAt(currentChar++));
        return currentChar < str.length();
    }

    @Override
    public Spliterator<Character> trySplit() {

        int currentSize = str.length() - currentChar;
        if (currentSize < 10) {
            return null;
        }

        System.out.println("currentSize: " + currentSize);
        System.out.println("pos: " + (currentSize/2 + currentSize));
        System.out.println("str.length: " + str.length());
        for (int pos = currentSize/2 + currentSize; pos < str.length(); pos++){
            if (pos+1 < str.length()){
                // 当前Character是数字，且下一个Character不是数字，才需要划分一个新的Spliterator
                if (Character.isDigit(str.charAt(pos)) && !Character.isDigit(str.charAt(pos+1))){
                    Spliterator<Character> spliterator = new NumCounterSpliterator_wrong(str.substring(currentChar, pos));
                    currentChar = pos;
                    return spliterator;
                }
            }else {
                if (Character.isDigit(str.charAt(pos))){
                    Spliterator<Character> spliterator = new NumCounterSpliterator_wrong(str.substring(currentChar, pos));
                    currentChar = pos;
                    return spliterator;
                }
            }
        }
        return null;
    }

    @Override
    public long estimateSize() {
        return str.length() - currentChar;
    }

    @Override
    public int characteristics() {
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }

    public static void main(String[] args) {
        String arr = "12%3 21sdas s34d dfsdz45   R3 jo34 sjkf8 3$1P 213ikflsd fdg55 kfd";
        Stream<Character> stream = IntStream.range(0, arr.length()).mapToObj(arr::charAt);
        System.out.println("ordered total: " + countNum(stream));

        Spliterator<Character> spliterator = new NumCounterSpliterator_wrong(arr);
        // 传入true表示是并行流
        Stream<Character> parallelStream = StreamSupport.stream(spliterator, true);
        System.out.println("parallel total: " + countNum(parallelStream));
    }

    private static int countNum(Stream<Character> stream){
        NumCounter numCounter = stream.reduce(new NumCounter(0, 0, false), NumCounter::accumulate, NumCounter::combine);
        return numCounter.getSum();
    }
}