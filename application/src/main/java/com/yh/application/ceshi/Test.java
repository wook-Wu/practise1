package com.yh.application.ceshi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {

    //耗时，请求任务
    public String request() throws InterruptedException {
//        long time = (long) (Math.random()*99);
        long time = 100;
        Thread.sleep(time);
        return String.format("耗时：%s", time);
    }

    //数据大小
    public List<String> getTestData(int num) {
        int size = (int)(Math.random()*num);
        ArrayList<String> list = new ArrayList<>(size);
        for (int index = 0; index < size; index++) {
            list.add(String.format("数据%s", index));
        }
        return list;
    }

    //第一种任务处理，将数据按一定条件分割，进行多任务处理
    public void taskOne(List<String> data, int divisionSize) throws InterruptedException {
        //数据大小
        int dataSize = data.size();
        //分割数量
        int num = (dataSize + divisionSize - 1) / divisionSize;
        //用来装分割后的 List
        List<List<String>> newData = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            int formIndex = i * divisionSize;
            int toIndex = Math.min(((i + 1) * divisionSize), dataSize);
            newData.add(data.subList(formIndex, toIndex));
        }
        //根据分割份数开启线程
        int listSplitSize = newData.size();
        CountDownLatch latch = new CountDownLatch(listSplitSize);
        List<String>[] listResult = new List[listSplitSize];
        System.out.println(String.format("size为%s, list为%s", listSplitSize, listResult.length));
        for (int i = 0; i < listSplitSize; i++) {
            taskOneRequest(latch, i, newData.get(i), listResult);
        }
        //等待全部结果完成
        latch.await();
        List<String> result = new ArrayList<>();
        for (List<String> list : listResult) {
            result.addAll(list);
        }
        System.out.println(result);
    }
    public void taskOneRequest(CountDownLatch latch, int index, List<String> data, List<String>[] totalResult) {
        new Thread(() -> {
            List<String> result = new ArrayList<>();
            for (String dataIndex : data) {
                try {
                    result.add(request());
                } catch (InterruptedException e) {
                    result.add("Null");
                }
            }
            //存放批量获取的结果
            totalResult[index] = result;
            latch.countDown();
        }).start();
    }

    //第二种任务处理，输入想要开的线程数量，多任务处理
    public void taskTwo(List<String> data, int taskSize) throws InterruptedException {
        int dataSize = data.size();
        String[] result = new String[dataSize];
        CountDownLatch latch = new CountDownLatch(taskSize);
        //进度
        AtomicInteger index = new AtomicInteger(0);
        AtomicInteger num = new AtomicInteger(); //请求次数
        for (int i = 0; i < taskSize; i++) {
            new Thread(() -> {
                int i1 = index.getAndIncrement();
                while (i1 < dataSize) {
                    try {
                        result[i1] = request();
//                        System.out.println(i1);
                        System.out.println(Thread.currentThread().getName());
                        //计算请求了多少次
                        num.getAndIncrement();
                    } catch (Exception e) {
                        result[i1] = "Null";
                    } finally {
                        //返回新的数据序号
                        i1 = index.getAndIncrement();
                    }
                }
                latch.countDown();
            }).start();
        }
        latch.await();
        System.out.println(String.format(
                "数据总大小：%s，\n" +
                "请求次数：%s，\n" +
                "所有结果：%s",
                dataSize, num, Arrays.toString(result)
        ));
    }

    public static void main(String[] args) throws InterruptedException {
        Test tt = new Test();
        List<String> data = tt.getTestData(20);
//        System.out.println(String.format("数据大小：%s", data.size()));
//        tt.taskOne(data, 1024);
        tt.taskTwo(data, 5);
    }
}
