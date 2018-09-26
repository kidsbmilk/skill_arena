package com.zz4955.spliterator_ex;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

// JAVA8 stream 中Spliterator的使用(二)：http://ifeve.com/java8-stream-%E4%B8%ADspliterator%E7%9A%84%E4%BD%BF%E7%94%A8%E4%BA%8C/
public class NumCounterSpliteratorCharArray_right implements Spliterator<Character> {

    private char[] str;
    private int currentIndex = 0;
    private int end = Integer.MAX_VALUE;
    private boolean canSplit = true;

    public NumCounterSpliteratorCharArray_right(int currentIndex, int end, char[] str, boolean canSplit) {
        this.str = str;
        this.currentIndex = currentIndex;
        this.end = end;
        this.canSplit = canSplit;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Character> action) { // 该方法会处理每个元素，如果没有元素处理，则应该返回false，否则返回true。
        action.accept(str[currentIndex ++]); // 最终将调用到reduce 操作的ccumulate方法
        return currentIndex < end;
    }

    @Override
    public Spliterator<Character> trySplit() {
        int i = currentIndex;
        for(; canSplit && i < end; i ++) {
            // 第一个不是数字的pos，进行分割
            if(!Character.isDigit(str[i])) {
                // 因为是此str从左到右找第一个可分割的点，所以此str分割后，已经不可再分割了，所以设置为false。
                canSplit = false;
                if(i + 1 < end) {
                    int currentIndexOld = currentIndex;
                    currentIndex = i + 1;
                    return new NumCounterSpliteratorCharArray_right(currentIndexOld, i, str, true); // 前面一些版本出错：最关键的是这一行代码，前面的版本在这行代码上的思路都有问题，
                    // 正确的思路是这样的：stream本质上底层是f/j代码，而f/j分割时候，是基于trySplit进行分割的。查看了java.util.stream.Streams.RangeIntSpliterator源码后发现，trySplit的分割是需要从[begin,end]返回一个以begin 为开始的Spliterator，例如分割为[begin,end1]，将当前Spliterator的begin修改为end1+1,即分割为[end1+1,end].
                    // 更具体的说明见：http://ifeve.com/java8-stream-%E4%B8%ADspliterator%E7%9A%84%E4%BD%BF%E7%94%A8%E4%BA%8C/
                    // 感觉作者太专业了，佩服，又学到东西了。
                } else {
                    return null;
                }
            }
        }
        canSplit = false; // for循环过完了，此str已经分割完了，所以设置为不可分割了。
        return null;
    }

    @Override
    public long estimateSize() {
        return end - currentIndex;
    }

    @Override
    public int characteristics() {
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }

    public static void main(String[] args) {
        String arr = "12%3 21sdas s34d dfsdz45   R3 jo34 sjkf8 3$1P 213ikflsd fdg55 kfd";
        Stream<Character> stream = IntStream.range(0, arr.length()).mapToObj(arr::charAt);
        System.out.println("ordered total: " + countNum(stream));

        Spliterator<Character> spliterator = new NumCounterSpliteratorCharArray_right(0, arr.length(), arr.toCharArray(), true);
        Stream<Character> parallelStream = StreamSupport.stream(spliterator, true);
        System.out.println("parallel total: " + countNum(parallelStream));
//        parallelStream.distinct().forEach(System.out::print);

    }

    private static int countNum(Stream<Character> stream) {
        NumCounter numCounter = stream.reduce(new NumCounter(0, 0, false), NumCounter::accumulate, NumCounter::combine);
        return numCounter.getSum();
    }
}
