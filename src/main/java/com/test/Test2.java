package com.test;

import lombok.ToString;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Auther: shuaihu.shen@hand-china.com
 * @Date: 2018/10/27 13:24
 * @Description:
 */

public class Test2 {

    /**
     * 产生排列组合的递归写法
     * @param t     数组
     * @param k     起始排列值
     * @param n     数组长度
     */
    static void pai(int[] t, int k, int n) {
        if (k == n-1){
        //输出这个排列
            for (int i = 0; i < n; i++)
            {
                System.out.print(t[i] + " ");
            }
            System.out.println();
        } else  {
            for (int i = k; i < n; i++)   {
                int tmp = t[i]; t[i] = t[k]; t[k] = tmp;
                 //一次挑选n个字母中的一个,和前位置替换
                pai(t, k+1, n);
                  //再对其余的n-1个字母一次挑选
            tmp = t[i]; t[i] = t[k]; t[k] = tmp;
                 //再换回来
            }
            }
    }

    public static void main(String[] args) {
        int[] t = {1,2,3,4};
        pai(t,1,4);
    }
}

