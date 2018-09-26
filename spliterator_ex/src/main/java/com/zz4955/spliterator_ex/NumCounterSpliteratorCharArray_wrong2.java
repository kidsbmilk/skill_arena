package com.zz4955.spliterator_ex;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

// JAVA8 stream 中Spliterator的使用(一)：http://ifeve.com/java8-stream-%E4%B8%ADspliterator%E7%9A%84%E4%BD%BF%E7%94%A8%E4%B8%80/
public class NumCounterSpliteratorCharArray_wrong2 implements Spliterator<Character> {

    private char[] str;
    private int currentIndex = 0;
    private int end = Integer.MAX_VALUE;
    private boolean canSplit = true;

    public NumCounterSpliteratorCharArray_wrong2(int currentIndex, int end, char[] str, boolean canSplit) {
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
                int splitBeforeEnd = end;
                end = i;
                canSplit = false; // 因为是此str从左到右找第一个可分割的点，所以此str分割后，已经不可再分割了，所以设置为false。
                if(i + 1 < splitBeforeEnd) {
                    return new NumCounterSpliteratorCharArray_wrong2(i + 1, splitBeforeEnd, str, true); // 注意：这个新str的currentIndex这0，且是可分割的。
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

        Spliterator<Character> spliterator = new NumCounterSpliteratorCharArray_wrong2(0, arr.length(), arr.toCharArray(), true);
        Stream<Character> parallelStream = StreamSupport.stream(spliterator, true);
//        System.out.println("parallel total: " + countNum(parallelStream));
        parallelStream.sorted().forEach(System.out::print); // 可以看到出错信息。
    }

    private static int countNum(Stream<Character> stream) {
        NumCounter numCounter = stream.reduce(new NumCounter(0, 0, false), NumCounter::accumulate, NumCounter::combine);
        return numCounter.getSum();
    }
}
