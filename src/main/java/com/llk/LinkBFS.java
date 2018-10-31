package com.llk;

import javax.swing.*;

import static com.llk.LLK.MAX_X;
import static com.llk.LLK.gameButton;

/**
 * @Auther: shuaihu.shen@hand-china.com
 * @Date: 2018/10/29 16:59
 * @Description:
 */
public class LinkBFS {

    static int  x2, y2;
    static boolean map[][] = new boolean[1005][1005];
    static boolean flag;

    /**
     *
     * @param x
     * @param y
     * @param z  上1 下2 左3 右4  四个方向
     * @param k  转弯数
     */
    static void dfs(int x,int y,int z,int k,JButton gameButton[][])//坐标(x,y)
    {

        if(flag)return;    // 成功 则返回

        if(x < 0||y < 0||x > MAX_X+1 || y > MAX_X+1)return;//若超界，返回
        if(k>=3)return; //  若转弯数已经超过3次，返回
        if(x == x2 && y == y2)
        {
            flag=true;
            return;
        }
        if(k==2)//超强剪枝：若已经转两次弯，则目标坐标必须要在前进方向的前面，否则直接返回
        {
            if(!(z == 1 && x>x2 && y == y2||z == 2 && x < x2 &&  y==y2
                    || z==3 && y>y2 && x==x2 || z==4 && y < y2 && x==x2 ))
                return;
        }

        if(gameButton[x][y].getIcon() != null)return;//如果该点不是0，则不能走，返回
        if(map[x][y])return;//如果该点已经走过，返回


        map[x][y]=true;//标记该点为走过

        // 向不同的方向 拐点+1
        if(z==1)//上
        {
            dfs(x-1,y,1, k, gameButton);
            dfs(x+1,y,2,k+1, gameButton);
            dfs(x,y-1,3,k+1, gameButton);
            dfs(x,y+1,4,k+1, gameButton);
        }

        if(z==2)//下
        {
            dfs(x-1,y,1,k+1, gameButton);
            dfs(x+1,y,2,k, gameButton);
            dfs(x,y-1,3,k+1, gameButton);
            dfs(x,y+1,4,k+1, gameButton);
        }

        if(z==3)//左
        {
            dfs(x-1,y,1,k+1, gameButton);
            dfs(x+1,y,2,k+1, gameButton);
            dfs(x,y-1,3,k, gameButton);
            dfs(x,y+1,4,k+1, gameButton);
        }

        if(z==4)//右
        {
            dfs(x-1,y,1,k+1, gameButton);
            dfs(x+1,y,2,k+1, gameButton);
            dfs(x,y-1,3,k+1, gameButton);
            dfs(x,y+1,4,k, gameButton);
        }
        map[x][y]=false;//若深搜不成功，标记该点为未走过
    }

    public static boolean BFS(Cell cell1, Cell cell2) {

        Integer x1 = cell1.getX();
        Integer y1 = cell1.getY();

        x2 = cell2.getX();
        y2 = cell2.getY();


        flag = false;

        dfs(x1 - 1, y1, 1, 0, gameButton);
        dfs(x1 + 1, y1, 2, 0, gameButton);
        dfs(x1, y1 - 1, 3, 0, gameButton);
        dfs(x1, y1 + 1, 4, 0, gameButton);

        for (int i = 0; i < MAX_X+2; i++) {
            for (int j = 0; j < MAX_X+2; j++) {
                String icon  = String.valueOf(gameButton[i][j].getIcon());
                if (icon.equals("null")){
                    icon = "00";
                }else {
                    String a= "image/icon/default6.png";
                    if (icon.length() == a.length()){
                        icon = "0"+icon.substring(icon.length()-5,icon.length()-4);
                    }else {
                        icon = icon.substring(icon.length()-6,icon.length()-4);
                    }

                }
                System.out.print(icon + " ");
            }
            System.out.println();
        }

        return flag;

    }
}
