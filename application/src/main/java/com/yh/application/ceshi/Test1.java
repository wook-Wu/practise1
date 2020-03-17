package com.yh.application.ceshi;

import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Test1 {

    public static void main(String[] args) {
        System.out.println("还好");
        ArrayList<Integer>  list =new ArrayList<>();
        for (int i =0; i<313;i++){
            list.add(i);
        }

        CountDownLatch begin = new CountDownLatch(1);  //这里最好设置1 ,方便初次开启
        //有余,说明次数要+1
        int  ifAdd =list.size()%100 ==0 ? 0 : 1;
        CountDownLatch end = new CountDownLatch(list.size()/100 +ifAdd);  //此处一般为线程数

        int j;
        int toIndex=100;
        for( j = 0;j<list.size();j+=100) {
            if (j + 100 > list.size()) {        //作用为toIndex最后没有100条数据则剩余几条newList中就装几条
                toIndex = list.size() - j;
            }
            List newList = list.subList(j, j + toIndex);
            //baseMapper .selectList ().in("order_id",newList.toArray())
            System.out.println(newList);
        }

            for(int i=0; i<4; i++){
            new Thread(()->{
                try {
                    begin.await();//主线程初次倒计后 因为此时已经为0了，所以不阻塞
                    System.out.println(Thread.currentThread().getName() + " 抵达 !");
                    //每调用一次 , end 计数 -1 , 到0时 end不在阻塞
                    end.countDown();//countDown() 并不是直接唤醒线程,当end.getCount()为0时线程会自动唤醒
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        try{
            System.out.println("裁判: 比赛开始");
            //倒计,每调用一次该方法,begin 的次数减1 , 到0 时 不在阻塞
            begin.countDown();
            end.await();//await() 方法具有阻塞作用，也就是说主线程在这里暂停
            System.out.println("裁判: 比赛结束");
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}