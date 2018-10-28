package com.test;

import org.springframework.beans.factory.annotation.Value;
import lombok.ToString;

import java.util.LinkedList;
import java.util.Queue;
/**
 * @Auther: shuaihu.shen@hand-china.com
 * @Date: 2018/10/27 13:24
 * @Description:
 */

public class Test {

    private int r=4;//行
    private int c=4;//列
    //数组
    private int[][] graph=new int[][]{
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10,11,12},
            {13,14,15,16}};
    private int[][] gr=new int[r][c];//标记

    int[][] rc=new int[][]{{0,-1},{-1,0},{0,1},{1,0}};//左上右下，四个方向

    public static void main(String[] args) {

        new Test().BFS();
    }
    //方法内部类，定义数据结构
    @ToString
    class Node{
        int r;//行
        int c;//列
        int k;//第几波被访问的
        Node(int r,int c,int k){
            this.r=r;
            this.c=c;
            this.k=k;
        }

    }
    private void BFS() {

        Node node=new Node(0,0,0);//初始化，从0,0开始，
        gr[0][0]=1;//0,0默认已访问过
        Queue<Node> que=new LinkedList<Node>();//初始化队列
        que.offer(node);  //把初始化过的node传入队列

        while (!que.isEmpty()) {
            Node tem=que.poll();    //  获取并移除队列头
             for(int i=0;i<4;i++){   //  循环四次，四个方向
                int newr=tem.r+rc[i][0];    //新的行
                int newc=tem.c+rc[i][1];    //新的列

                if(newr<0||newc<0||newr>=r||newc>=c)continue;   //如果新的行和列超出范围就跳过这次循环

                if(gr[newr][newc]!=0){
                    continue;  //如果新的节点已被访问也跳过此次循环
                }

                gr[newr][newc]=1;   // 临近结点 标记当前节点已被访问
                que.offer(new Node(newr,newc,tem.k+1)); //加入队列
                //输出遍历到数组的值

            }

        }
    }

}
