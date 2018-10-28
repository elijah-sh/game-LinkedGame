package com.llk;

import java.util.*;

/**
 * @Auther: shuaihu.shen@hand-china.com
 * @Date: 2018/10/23 09:46
 * @Description:  核心算法    gameButton[cols][rows]    [y] [x]   y 是横坐标cols 向左 ， x 是纵坐标rows  向下
 * 棋盘初始化算法	25
 * 搜索最优路径算法	25
 * 时间限定功能/实现下一局功能	15
 * 界面开发 Swing/Web	20
 * 根据最优路径绘制路线	15
 */
public class LinkCore {




    //   水平检测  X 相等， Y不等  且中间不障碍
    // 水平检测用来判断两个点的纵坐标是否相等，同时判断两点间有没有障碍物。
    public   List<Cell> horizon(Cell cell1,Cell cell2 )
    {
        List<Cell> cellList = new ArrayList<>();

     /*   System.out.println(" horizon cell1 ： ( "+cell1.getX()+" , "+cell1.getY()+" )" );
        System.out.println(" horizon cell2 ： ( "+cell2.getX()+" , "+cell2.getY()+" )" );*/
        Integer x1 = cell1.getX();
        Integer y1 = cell1.getY();

        Integer x2 = cell2.getX();
        Integer y2 = cell2.getY();

        if (x1 == x2 && y1 == y2)
        {
            return null;
        }

        if (x1 != x2)
        {
            return null;
        }

        int start_y = (y1-y2)<0?y1:y2;   // 取坐标小的
        int end_y = (y1-y2)>0?y1:y2;

        if ( end_y - start_y == 1  ) { // 判断是否相邻
            cellList.add(cell1);
            cellList.add(cell2);
             return cellList;
        }

        cellList.add(cell1);

        for (int j = start_y+1; j < end_y; j++)
        {
            Cell cell = new Cell(x1,j,null);
          //  System.out.println(cell.getX()+" "+cell.getY());
            if (isBlocked(cell))     //  判断是否存在
            {
                return null;
            }
            cellList.add(cell);

        }
         cellList.add(cell2);
         return cellList;
    }


    /***
     * 垂直检测  Y 相等  X 不等
     * 垂直检测用来判断两个点的横坐标是否相等，同时判断两点间有没有障碍物。
     * @return
     */
    public  List<Cell> vertical(Cell cell1,Cell cell2 )
    {
        List<Cell> cellList = new ArrayList<>();

        Integer x1 = cell1.getX();
        Integer x2 = cell2.getX();
        Integer y1 = cell1.getY();
        Integer y2 = cell2.getY();
     /*   System.out.println(" vertical cell1 ： ( "+cell1.getX()+" , "+cell1.getY()+" )" );
        System.out.println(" vertical cell2 ： ( "+cell2.getX()+" , "+cell2.getY()+" )" );*/
        if (x1 == x2 && y1 == y2)
        {
            return null;
        }

        if (y1 != y2)
        {
            return null;
        }

        int start_x = (x1-x2)<0?x1:x2;   // 取坐标小的
        int end_x = (x1-x2)>0?x1:x2;

        if ( end_x - start_x == 1  ) { // 判断是否相邻
            cellList.add(cell1);
            cellList.add(cell2);
             return cellList;
        }

        cellList.add(cell1);

        for (int i = start_x+1; i < end_x; i++)
        {
            Cell cell = new Cell(i,y1,null);
           // System.out.println(cell.getX()+" "+cell.getY());
            if (isBlocked(cell))  //  有障碍  结束返回 false
            {
                return null;
            }
            cellList.add(cell);

        }
        cellList.add(cell2);

         return cellList;
    }

    /**
     * 一个拐角检测
     * 一个拐角检测可分解为水平检测和垂直检测，当两个同时满足时，便两点可通过一个拐角相连。即：
     * 一个拐角检测 = 水平检测 && 垂直检测
     *   C 0 0 B
     *   0 0 0 0
     *   A 0 0 D
     *   A 点至 B 点能否连接可转化为满足任意一点：
     *          A 点至 C 点的垂直检测，以及 C 点至 B 点的水平检测；
     *          A 点至 D 点的水平检测，以及 D 点至 B 点的垂直检测。
     * @return
     */
    Cell tmpCell = new Cell();

    public List<Cell> turnOnce(Cell cell1,Cell cell2)
    {
        List<Cell> cellList = new ArrayList<>();
         Integer x1 = cell1.getX();
        Integer x2 = cell2.getX();
        Integer y1 = cell1.getY();
        Integer y2 = cell2.getY();

        if (x1 == x2 && y1 == y2)
        {
            return null;
        }

        Cell cellC = new Cell(x1,y2,null);  //  C 与 1  X相同  Y 不同  只能水平比较H, C与2 Y相同，需要垂直比较V
        Cell cellD = new Cell(x2,y1,null);  //  D 与 2  X相同  Y 不同  只能水平比较
        boolean ret = false;
        if (!isBlocked(cellC))
        {
            //a|=b的意思就是把a和b按位或然后赋值给a 按位或的意思就是先把a和b都换成2进制，
            // 然后用或操作，相当于 a = a|b
          /*  System.out.println("cell1： "+cell1.getX()+" , "+cell1.getY());
            System.out.println("cell2： "+cell2.getX()+" , "+cell2.getY());
            System.out.println("cellC： "+cellC.getX()+" , "+cellC.getY());*/

            //  System.out.println("水平 "+ horizon(cell1, cellC));   //  比较Y 是否有障碍  y2  与 y1 之间
            // System.out.println("垂直 "+ vertical(cellC,cell2));
            List<Cell> tmp1 = horizon(cell1 , cellC);
            List<Cell> tmp2 = vertical(cellC , cell2);
            ret  |= (tmp1!=null) && (tmp2!=null);
          //  System.out.println("ret C "+ret);
            if (ret){
                cellList.addAll(tmp1);
                cellList.addAll(tmp2);
               tmpCell = cellC ;    //  中间点赋值全局
                return cellList;
            }
        }
      //  System.out.println(ret);
        if (!isBlocked(cellD))  //  无障碍  继续判断
        {
           /* System.out.println("cellD");
            System.out.println("cell1： "+cell1.getX()+" , "+cell1.getY());
            System.out.println("cell2： "+cell2.getX()+" , "+cell2.getY());
            System.out.println("cellD： "+cellD.getX()+" , "+cellD.getY());

            System.out.println("水平 比较Y不同的 "+ horizon(cell1, cellD));
            System.out.println("垂直 比较X不同的 "+ vertical(cellD,cell2));*/
            List<Cell> tmp1 = vertical(cell1 , cellD);
            List<Cell> tmp2 = horizon(cellD , cell2);
            ret  |= (tmp1!=null) && (tmp2!=null);
            if (ret){
                cellList.addAll(tmp1);
                cellList.addAll(tmp2);
                tmpCell = cellD ;    //  中间点赋值全局

                return cellList;
            }
          //   ret  |= horizon(cell2, cellD) && vertical(cellD,cell1);
           // System.out.println("ret D "+ret);
        }

        return null;
    }


    /***
     * 两个拐角检测
     * 两个拐角检测可分解为一个拐角检测和水平检测或垂直检测。即：
     * 两个拐角检测 = 一个拐角检测 && (水平检测 || 垂直检测)
     *  需要考虑最短路径
     * @param cell1
     * @param cell2
     * @return
     */
   public List<Cell>  turnTwice(Cell cell1,Cell cell2)
    {

        List<Integer> disShort = new ArrayList<>();
        Map<Integer, List<Cell>> map = new HashMap<>();
        Integer x1 = cell1.getX();
        Integer x2 = cell2.getX();
        Integer y1 = cell1.getY();
        Integer y2 = cell2.getY();

        if (x1 == x2 && y1 == y2)
        {
            return null;
        }
        for (int i = 0; i <  LLK.MAX_X+2; i++)  // X
        {
            for (int j = 0; j <  LLK.MAX_Y+2; j++)  // Y  00 01  02 03 04  05
            {

                if (i != x1 && i != x2 && j != y1 && j != y2) // 该店不能和 x1 x2 y1 y2 没有一点关系， 不然就不能两折了  跳出本次循环
                {
                    continue;
                }

                if ((i == x1 && j == y1) || (i == x2 && j == y2))  // 该点不能是自己
                {
                    continue;
                }
                Cell cell = new Cell(i,j,null);

                if (isBlocked(cell))  //  有元素 淘汰
                {
                    continue;
                }

                //  cell1 到  cell 一折   cell到cell2 是一条线

                List<Cell> tmp1 = turnOnce(cell1 , cell);
                List<Cell> tmp2 = vertical(cell , cell2);
                List<Cell> tmp3 = horizon(cell , cell2);

                //if (turnOnce(cell1, cell) && (horizon(cell,cell2) || vertical(cell1, cell2)))
                if (tmp1 != null && ((tmp2!=null) || (tmp3!=null)))
                {
                    List<Cell> cellList = new ArrayList<>();

                    cellList.addAll(tmp1);
                    int  dis = distance2(cell1 , cell) +  distance1(cell , cell2);   // distance2 2折   distance1 1折

                    if (tmp2!=null){
                        cellList.addAll(tmp2);
                    }else {
                        cellList.addAll(tmp3);
                     }
                  //  System.out.println("最短路径"+dis);
                //    System.out.println("最短路径元素"+cellList.size());          disShort.add(dis);
                    map.put(dis,cellList);
                }
                //  cell2 到  cell 一折   cell到cell1是一条线

                List<Cell> tmpList1 = vertical(cell1 , cell);
                List<Cell> tmpList2 = horizon(cell1 , cell);
                List<Cell> tmpList3 = turnOnce(cell , cell2);

              //  if (turnOnce(cell, cell2) && (horizon(cell1,cell) || vertical(cell1,cell)))
                if (tmpList3 != null && ((tmpList2!=null) || (tmpList1!=null)))
                {
                    List<Cell> cellList = new ArrayList<>();
                    if (tmpList1!=null){
                        cellList.addAll(tmpList1);
                    }else {
                        cellList.addAll(tmpList2);
                    }
                    cellList.addAll(tmpList3);
                    int  dis =   distance1(cell1 , cell)+ distance2(cell , cell2);;  // 1折    2折
                 //   System.out.println("最短路径"+dis);
                  //  System.out.println("最短路径元素"+cellList.size());
                    disShort.add(dis);
                    map.put(dis,cellList);
                }

            } //  内侧循环  MAX_Y

        }  //  外侧循环  MAX_X

      //  System.out.println( "最短路径集合： "+disShort.toString());
      //  System.out.println( "最短路径集合： "+map.toString());


        if (map.size() > 0){
            // 1.8使用lambda表达式
            List<Map.Entry<Integer, List<Cell>>> list  = new ArrayList<>();
             list.addAll(map.entrySet());
            Collections.sort(list, (o1, o2) -> o1.getKey()-o2.getKey());   //  最短路径排序
         //   System.out.println( "最短路径集合排序之后： "+map.toString());

            Collections.sort(disShort, (a, b) -> a.compareTo(b));   //  最短路径距离排序
         //   System.out.println( "最短路径集合排序之后： "+disShort.toString());
           // list.forEach(entry -> {
           //          System.out.println("key:" + entry.getKey() + ",value:" + entry.getValue());
           //  });
         //   System.out.println( "最短路径数据： "+disShort.get(0));
         //   System.out.println( "最短路径数据： "+map.get(disShort.get(0)).toString());

           return map.get(disShort.get(0));
        }
        return null;
    }

    /**
     *  判断这个元素是否存在  既数组内容是否为0
     * @param cell
     * @return
     */
    public boolean isBlocked(Cell cell){

        if (cell.getX()==3&&cell.getY()==8){
          //  System.out.println("BUG");
        }

         if (cell.getY()<1||cell.getY()>LLK.MAX_Y||cell.getX()<1||cell.getX()>LLK.MAX_X){
             return false;  // 边界  无障碍
         }

        // System.out.println( "isBlocked  ("+cell.getX()+ " , "+ cell.getY()+" )");
        // System.out.println( "grid  "+LLK.grid[cell.getX()][cell.getY()]);

        if(LLK.grid[cell.getX()][cell.getY()] ==0){
            return false;   //  无障碍
        }
        return true;
    }

    /*****************************************
     * 计算两点的距离
     * @param cell1
     * @param cell2
     * @return
     */

    public int distance1(Cell cell1,Cell cell2)  {
        Integer x1 = cell1.getX();
        Integer x2 = cell2.getX();
        Integer y1 = cell1.getY();
        Integer y2 = cell2.getY();

        if(x1 != x2 && y1 != y2) {
            return -1;
        }
        int dis = 0;
        int disY = (y1-y2)>0?(y1-y2):(y2-y1) ;   // 取坐标小的
        int disX = (x1-x2)>0?(x1-x2):(x2-x1);

        if(x1 == x2){
            dis = disY;  //两点同横向
        } else {
            dis = disX;  //两点同竖向
        }
        return dis;
    }


    /*****************************************
     * 计算两点折线距离
     * @param cell1
     * @param cell2
     * @return
     */
    public int distance2(Cell cell1,Cell cell2) {

        turnOnce(cell1,cell2);   //turnOnce，通过 tmpCell 来调用distance1
        return distance1(cell1, tmpCell) + distance1(tmpCell, cell2);  //通过 tmpCell 做链接，两次调用distance1
    }



}
