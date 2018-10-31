package com.test;

import com.llk.Cell;
import lombok.ToString;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Auther: shuaihu.shen@hand-china.com
 * @Date: 2018/10/29 16:59
 * @Description:   BFS 转换 需要考虑到拐点
 */
public class BFS {
    private int r = 4;//行
    private int c = 5;//列
    //数组
    private int[][] graph=new int[][]{
            {0, 0, 0, 0, 0},
            {0, 2, 0, 3, 0},
            {0, 1, 0, 2, 0},
            {0, 0, 0, 0, 0},
    };

    private int[][] gr=new int[r][c];   //  标记

    int[][] rc=new int[][]{{0,-1},{-1,0},{0,1},{1,0}};//左上右下，四个方向

    public static void main(String[] args) {
        Cell cell1 = new Cell((1),(1),null);
        Cell cell2 = new Cell((2),(3),null);
        new BFS().BFS(cell1,cell2);
    }
    //方法内部类，定义数据结构
    @ToString
    class Node{
        int r;//行
        int c;//列
        int k;//第几波被访问的
        int v;// 该点的值
        Node(int r,int c,int k){
            this.r=r;
            this.c=c;
            this.k=k;
        }

    }
    // Cell cell1, Cell cell2
    private boolean BFS(Cell cell1, Cell cell2) {
        Integer x1 = cell1.getX();
        Integer y1 = cell1.getY();
        Integer x2 = cell2.getX();
        Integer y2 = cell2.getY();

        Node node=new Node(x1,y1,0);//初始化，从0,0开始，
        gr[x1][y1]=1;//0,0默认已访问过
        Queue<Node> que=new LinkedList<Node>();//初始化队列
        que.offer(node);  //把初始化过的node传入队列

        while (!que.isEmpty()) {
            Node tem=que.poll();    //  获取并移除队列头
             System.out.println("head: "+tem.toString());
            for(int i=0;i<4;i++){   //  循环四次，四个方向
              //  System.out.println(rc[i][0]+","+rc[i][1]);
                int newr=tem.r+rc[i][0];    //  新的行
                int newc=tem.c+rc[i][1];    //  新的列

                //  0 左 1上 2 右 3下    根据与对首坐标的关系 对 拐点的增加
                // 比较棘手
                //  TODO

                if(newr < 0 || newc < 0 || newr >= r || newc >= c)continue;   //如果新的行和列超出范围就跳过这次循环

                if(gr[newr][newc]!=0){
                    continue;  //如果新的节点已被访问也跳过此次循环
                }

                if (graph[newr][newc] == graph[x2][y2]){
                    System.out.println("OK");
                    return true;
                }

                //  入队条件  相邻 或者在一条直线上  有障碍元素存在
                if (graph[newr][newc] != 0){   //  空值才能入队

                    continue;
                }


                gr[newr][newc]=1;   // 相邻元素 标记当前节点已被访问
                que.offer(new Node(newr,newc,tem.k+1));     // 相邻元素 入队
                //输出遍历到数组的值

                System.out.print (graph[newr][newc]+" ( "+newr+"  , "+newc+" ) ");

                int  l = que.size();
                System.out.println( );
                System.out.print ("que.size: "+  l );

                for (int j = 0 ;j<l;j++){
                    System.out.print ("   "+   ((LinkedList<Node>) que).get(j).toString() );
                }

                System.out.println();
            }
            System.out.println("========================" );

        }
        return false;
    }

}
