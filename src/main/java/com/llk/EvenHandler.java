package com.llk;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.llk.LLK.MAX_X;
import static com.llk.LLK.MAX_Y;
import static com.llk.LLK.gameButton;

/**
 * @Auther: shuaihu.shen@hand-china.com
 * @Date: 2018/10/26 10:14
 * @Description:
 */
public class EvenHandler {


    Cell cell1 = new Cell();
    Cell cell2 = new Cell();

    /***
     *  点击按钮执行时间
     * @param cell
     * @param centerJPanel
     * @param gameButton
     * @param pointJLabel
     * @return
     */
    public int coreEven(Cell cell ,JPanel centerJPanel , JButton gameButton[][] ,
                         JLabel pointJLabel  ){

        System.out.println("选中坐标：   "+cell.toString()+" Icon "+ cell.getJButton().getIcon());


        if ( LLK.pressFlag == false){  // 未被选中
            cell2 = cell;  // 只被点击一次

        //    cell2.getJButton().setContentAreaFilled(true);//设置图片填满按钮所在的区域
            cell2.getJButton().setBorder(BorderFactory.createLineBorder(Color.RED));
            LLK.pressFlag = true;
        }else {
            // 已有选中的, 即不是第一次点击
            cell1 = cell2;
            cell2 = cell;

          //  cell1.getJButton().setContentAreaFilled(false);//设置图片填满按钮所在的区域
            cell1.getJButton().setBorder(null);

          //  cell2.getJButton().setContentAreaFilled(true);//设置图片填满按钮所在的区域
            cell2.getJButton().setBorder(BorderFactory.createLineBorder(Color.RED));

           // cell.getJButton().setBackground(new Color(127, 174, 252));
          //  cell.getJButton().setContentAreaFilled(true);//设置图片填满按钮所在的区域

            String icon1 = String.valueOf(cell1.getJButton().getIcon());
            String icon2 = String.valueOf(cell2.getJButton().getIcon());

            centerJPanel.repaint();
            if (cell2.getJButton() != cell1.getJButton()&&icon1.equals(icon2)){
                //  开始进行判断消除


                  int result = linked(cell1,cell2,centerJPanel,gameButton );

                  if (result != -2 ){   //  消除成功
                      pointUpdate(pointJLabel);   // 分数更新
                      return result;   //   -1  成功  0  1 清空了
                  }
            }
        }  //  if  结束
        return -1;

    }

    /**
     *   两个相同的元素消除
     * @param cell1
     * @param cell2
     * @param centerJPanel
     * @param gameButton
     * @return  -2 失败  其他返回值成功（-1）未消除玩
     */
    public int linked(Cell cell1,Cell cell2 ,JPanel centerJPanel ,
                          JButton gameButton[][]){

        //  0转角连通（直线连通）：两个图片的纵坐标或横坐标相等，且两者连线间没有其他图案阻隔。
        //  1 、相邻 同X 或者Y

        LinkCore linkCore = new LinkCore();
        EvenHandler evenHandler  = new EvenHandler();

        //   使用BFS 求  可用
      /*  List<Cell> bfsLsit = new ArrayList<>();
        boolean bfsResult =   LinkBFS.BFS(cell1,cell2);
        if (bfsResult){
            bfsLsit.add(cell1);
            bfsLsit.add(cell2);
            evenHandler.drawLine(bfsLsit, centerJPanel);
            int result =  clean(bfsLsit,gameButton );
            return result;
        }*/



        List<Cell> horizon = linkCore.horizon(cell1,cell2);
        if (horizon != null){
            evenHandler.drawLine(horizon, centerJPanel);
          int result =  clean(horizon,gameButton );
            return result;
        }
        List<Cell> vertical = linkCore.vertical(cell1,cell2);
        if (vertical != null){
            evenHandler.drawLine(vertical, centerJPanel);
            int result =  clean(vertical,gameButton );
            return result;
        }
        List<Cell> turnOnce = linkCore.turnOnce(cell1,cell2);
        if (turnOnce != null){
            evenHandler.drawLine(turnOnce, centerJPanel);
            int result =  clean(turnOnce,gameButton );
            return result;
        }
        List<Cell> turnTwice = linkCore.turnTwice(cell1,cell2);
        if (turnTwice != null){
            // 判断最优路线
           // if ()
            evenHandler.drawLine(turnTwice, centerJPanel);
            int result =   clean(turnTwice,gameButton );
            return result;
        }

        return -2;   // error
    }




    /**************************************
     *  消除cell
     * @param cellList
     */

    public int clean(List<Cell> cellList, JButton gameButton[][] ) {

        for (int i = 0; i < cellList.size() ; i++) {

            int x1 = cellList.get(i).getX();
            int y1 = cellList.get(i).getY();
            gameButton[x1][y1].setVisible(true);
            gameButton[x1][y1].setVisible(false);
            gameButton[x1][y1].setIcon(null);
        }

        LLK.pressFlag = false; // 使该静态变量还原为false

        // 判断是否消除完
        EvenHandler evenHandler = new EvenHandler();
        Boolean result =  evenHandler.isEmpty(gameButton);

        if (result){

            System.out.println("you win!");

            Object[] options ={ "重玩", "下一局" };  //自定义按钮上的文字

             int m = JOptionPane.showOptionDialog(null,
                    "恭喜你，闯关成功，请选择？",
                    "胜利",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.PLAIN_MESSAGE,   //  普通对话框
                    null, options, options[0]);

            return m ;
        }
        return -1;   //  游戏未结束

    }


    /****************************************
     * 分数更新
     */
    public void pointUpdate(JLabel pointJLabel) {
        int point = Integer.parseInt(pointJLabel.getText());
        pointJLabel.setText(String.valueOf(point + 5));// 在原有数字上加5分

    }


    /****************************************
     *  判断 游戏图标是否为空
     * @return
     */
    public boolean isEmpty(JButton[][] gameButton) {
        for (int i = 0; i < gameButton.length; i++) {
            for (int j = 0; j < gameButton[i].length; j++) {
                 if (gameButton[i][j].getIcon() != null ){
                     return false;
                }
            }
         }
        return true;
    }


    /****************************************
     * 两个cell上画线
     * @param cellList  画线的轨迹
     */
    public void  drawLine(List<Cell> cellList, JPanel centerJPanel) {

        int MAX_Y = LLK.MAX_Y;
        int MAX_X = LLK.MAX_X;
        int width = centerJPanel.getWidth() / (MAX_Y+2);  // 按钮宽度
        int height = centerJPanel.getHeight() / (MAX_X+2);  // 按钮长度

        for (int i = 0; i < cellList.size() - 1; i++) {

            int startY = cellList.get(i).getX() * height;   // 起始位置
            int startX = cellList.get(i).getY() * width;

            int endY = cellList.get(i+1).getX() * height;   // 结束位置
            int endX = cellList.get(i+1).getY() * width;


            Graphics2D graphics2D = (Graphics2D) centerJPanel.getGraphics();
            graphics2D.setStroke(new BasicStroke(2.0F));  //定义线条的特征
            graphics2D.setColor(Color.blue);
            graphics2D.drawLine(
                    startX+(width/2),
                    startY+(height/2),
                    endX+(width/2),
                    endY+(height/2)
            );

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     *   两个相同的元素消除  棋盘初始化测试
     * @param cell1
     * @param cell2
     * @param centerJPanel
     * @param gameButton
     * @return  -2 失败  其他返回值成功（-1）未消除玩
     */
    public int linkedTest(Cell cell1,Cell cell2 ,JPanel centerJPanel ,
                      JButton gameButton[][]){

        LinkCore linkCore = new LinkCore();

        List<Cell> horizon = linkCore.horizon(cell1,cell2);
        if (horizon != null){
            int result =  cleanTest(horizon,gameButton);
            return result;
        }
        List<Cell> vertical = linkCore.vertical(cell1,cell2);
        if (vertical != null){
            int result =  cleanTest(vertical,gameButton);
            return result;
        }
        List<Cell> turnOnce = linkCore.turnOnce(cell1,cell2);
        if (turnOnce != null){
            int result =  cleanTest(turnOnce,gameButton);
            return result;
        }
        List<Cell> turnTwice = linkCore.turnTwice(cell1,cell2);
        if (turnTwice != null){
            // 判断最优路线
            int result =   cleanTest(turnTwice,gameButton );
            return result;
        }

        return -2;   // error
    }


    /**
     *  消除cell
     * @param cellList
     * 返回0 时  消除完毕
     */

    public int cleanTest(List<Cell> cellList, JButton gameButton[][]) {


            int x1 = cellList.get(0).getX();
            int y1 = cellList.get(0).getY();
            int x2 = cellList.get(cellList.size()-1).getX();
            int y2 = cellList.get(cellList.size()-1).getY();
                gameButton[x1][y1].setIcon(null);
                gameButton[x2][y2].setIcon(null);

        // 判断是否消除完
        EvenHandler evenHandler = new EvenHandler();
        Boolean result =  evenHandler.isEmpty(gameButton);

        if (result){
            System.out.println(" Test 棋盘为空");
            return 0 ;
        }
        return -1;   //  游戏未结束

    }


    /**********************************
     *   提示功能
     */
    public void suggest(int[][]  icon , JPanel centerJPanel){
        LLK  llk = new LLK();
        boolean result =  suggestCell1(icon,centerJPanel);

        if (!result){
            System.out.println(" 没有可以消除的");
            JOptionPane.showMessageDialog(null,
                    "很遗憾，没有可以消除的", "提示",JOptionPane.PLAIN_MESSAGE);
        }

    }

    public boolean suggestCell1(  int[][]  icon , JPanel centerJPanel ) {

        for (int x = 1; x < icon.length -1; x++) {

            for (int y = 1; y < icon[x].length -1; y++) {

                if (gameButton[x][y].getIcon() == null){
                    continue;
                }
                if (x == icon.length -2 && y == icon[x].length -2 ){  //  最后一个不用便利
                    continue;
                }
                Cell cell1   = new Cell(x, y, gameButton[x][y]);
                boolean   result  =   suggestCell2(x,y,cell1 ,icon,centerJPanel);

                if (result){
                    return true;  // 成功
                }
            }
        }

        return false;
    }
    /**********************
     * 验证时 选取第二个Cell
     * @param x
     * @param y
     * @param cell1
     * @param icon
     * @return
     */
    public boolean suggestCell2(int x, int y ,Cell cell1, int[][]  icon , JPanel centerJPanel) {

        for (int i = 1; i < icon.length -1; i++) {

            for (int j = 1; j < icon[i].length -1; j++) {
                if (i <  x  ){
                    continue;
                }
                if (i <  x+1  && j <  y ){
                    continue;
                }
                if (i ==1 && j == 1){
                    continue;
                }
                if (gameButton[x][y].getIcon() == null){   // 不校验空值
                    continue;
                }
                Cell  cell2   = new Cell(i,j,gameButton[i][j]);
                // 先判断数值是否相等

                String icon1 = String.valueOf(cell1.getJButton().getIcon());
                String icon2 = String.valueOf(cell2.getJButton().getIcon());

                if (cell2.getJButton() != cell1.getJButton()&&icon1.equals(icon2)){
                  boolean  result = linkedSuggest(cell1,cell2 ,centerJPanel);

                    if (result){
                        return true;  // 成功
                    }
                }

            }
        }

        return false;
    }


    /**
     *   两个相同的元素消除  棋盘初始化测试
     * @param cell1
     * @param cell2
     * @return  -2 失败  其他返回值成功（-1）未消除玩
     */

    public boolean linkedSuggest(Cell cell1,Cell cell2 ,JPanel centerJPanel){

        //   使用BFS 求  可用
        boolean bfsResult =   LinkBFS.BFS(cell1,cell2);
         if (bfsResult){

            cell1.getJButton().setBackground(new Color(127, 174, 252));
            cell2.getJButton().setBackground(new Color(127, 174, 252));

             drawLineSimple(cell1,centerJPanel);
             drawLineSimple(cell2,centerJPanel);


        }


        return bfsResult ;
    }

    /****************************************
     * 单个cell上画线
     * @param cell  画线的轨迹
     */
    public void  drawLineSimple(Cell cell, JPanel centerJPanel) {

        int MAX_Y = LLK.MAX_Y;
        int MAX_X = LLK.MAX_X;
        int width = centerJPanel.getWidth() / (MAX_Y+2);  // 按钮宽度
        int height = centerJPanel.getHeight() / (MAX_X+2);  // 按钮长度

        int cellY = cell.getX() * height;   // 起始位置
        int cellX = cell.getY() * width;

        List<Integer> pontX = new ArrayList<>();
        List<Integer> pontY = new ArrayList<>();

        pontX.add(cellX);   //    横  右
        pontY.add(cellY);
        pontX.add(cellX+width);
        pontY.add(cellY);

        pontX.add(cellX+width); //  丨 下
        pontY.add(cellY);
        pontX.add(cellX+width);
        pontY.add(cellY+height);

        pontX.add(cellX+width);  //  横 左
        pontY.add(cellY+height);
        pontX.add(cellX);
        pontY.add(cellY+height);

        pontX.add(cellX);       //   丨 上
        pontY.add(cellY+height);
        pontX.add(cellX);
        pontY.add(cellY);

        for (int i = 0; i < 8; i=i+2) {

            int startX = pontX.get(i);
            int startY = pontY.get(i);   // 起始位置

            int endX = pontX.get(i+1);
            int endY = pontY.get(i+1);   // 结束位置


            Graphics2D graphics2D = (Graphics2D) centerJPanel.getGraphics();
            graphics2D.setStroke(new BasicStroke(3.0F));  //定义线条的特征
            graphics2D.setColor(Color.blue);
            graphics2D.drawLine(
                    startX, startY, endX, endY
            );

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}
