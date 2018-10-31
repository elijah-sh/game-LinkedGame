package com.llk;


import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.sun.javafx.fxml.expression.Expression.add;

/**
 * @Auther: shuaihu.shen@hand-china.com
 * @Date: 2018/10/22 14:30
 * @Description:
 *     1． 新建一个组件（如JButton）。
 * 　　2． 将该组件添加到相应的面板（如JPanel）。
 * 　　3． 注册监听器以监听事件源产生的事件（如通过ActionListener来响应用户点击按钮）。
 * 　　4． 定义处理事件的方法（如在ActionListener中的actionPerformed中定义相应方法）。
 */
public class LLK implements ActionListener {

    static   int MAX_X = 6;     // 高度
    static    int MAX_Y = 6;   // 宽度
    static    int CELLCATEGORY = 15;   //  传入不同的元素类型个人

     JFrame mainFrame;  // 主面板

    static JButton gameButton[][] = new JButton[MAX_X+ 2][MAX_Y+ 2]; // 游戏存储按钮数组
    JButton exitButton = new JButton("退出游戏");;  // 两个菜单按钮
    JButton startButton = new JButton("现在开始");;  // 两个菜单按钮
    JButton resetButton = new JButton("重新开始");;  // 两个菜单按钮
    JButton nextButton = new JButton("下一关");;  // 两个菜单按钮
    JButton beforeButton = new JButton("上一关");;  // 两个菜单按钮
    JButton suggestButton = new JButton("提示");;  // 两个菜单按钮


 //    static int[][] grid = new int[MAX_X+2][MAX_Y+2];  // 游戏按钮位置 含边框 6 = 4+1+1
   // static int[][] icon = new int[MAX_X+2][MAX_Y+2];  // 游戏按钮位置 含边框 6 = 4+1+1

    static   boolean pressFlag = false ; //  按钮是否被选中

      JPanel centerJPanel, menuJPanel, downJPanel; // 子面板


    JLabel timeJLabel = new JLabel();   // 时间组件
    JLabel pointJLabel = new JLabel();   // 分数组件


     EvenHandler evenHandler = new EvenHandler()  ;
      private int[][] iconDate;

    /*******************************************
     * 初始化布局
     */
    public void init(){

       mainFrame = new JFrame("LLKD");
        Container container = mainFrame.getContentPane(); // 初始化mainframe
        container.setLayout(new BorderLayout());  // 布置容器的边框布局

         centerJPanel = new JPanel();   // 游戏界面
         downJPanel = new JPanel();
         menuJPanel = new JPanel();  // 菜单栏

        pointJLabel = new JLabel("0"); // 定义分数标签，并初始化为0.
        pointJLabel.setText(String.valueOf(Integer.parseInt(pointJLabel.getText())));  // 分数

        menuJPanel.add(pointJLabel); // 将“分数”标签加入northPanel

        container.add(centerJPanel,"Center");
        container.add(downJPanel,"South");
        container.add(menuJPanel,"East");

        centerJPanel.setLayout(new GridLayout(MAX_X+2,MAX_Y+2)); // MAX_X * MAX_Y 网格布局


        JPanel controlJPanel = new JPanel();  //  控制组件
        controlJPanel.setBackground(new Color(127, 174, 252));
        controlJPanel.setBorder(new EtchedBorder());
        BoxLayout controlLayout = new BoxLayout(controlJPanel, BoxLayout.Y_AXIS);  // Y_AXIS 表示垂直排列
        controlJPanel.setLayout(controlLayout);

        add(controlJPanel, BorderLayout.EAST);

        JPanel pointTextJPanel = new JPanel(); //  得分 文本
        pointTextJPanel.setBackground(new Color(169, 210, 254));
        pointTextJPanel.setBorder(new EtchedBorder());
        JLabel pointTextJLabel = new JLabel();
        pointTextJLabel.setText("得分");
        pointTextJPanel.add(pointTextJLabel);
        controlJPanel.add(pointTextJPanel);

        JPanel pointJPanel = new JPanel();  // 得分内容

        pointJPanel.setBorder(new EtchedBorder());
        pointJPanel.setBackground(new Color(208, 223, 255));

        pointJLabel.setText("0");
        pointJPanel.add(pointJLabel);
        controlJPanel.add(pointJPanel);

        JPanel timeTextPanel = new JPanel();  //  时间 文本
        timeTextPanel.setBackground(new Color(169, 210, 254));
        timeTextPanel.setBorder(new EtchedBorder());
        JLabel timeTextLabel = new JLabel();
        timeTextLabel.setText("剩余时间 (s)");
        timeTextPanel.add(timeTextLabel);
        controlJPanel.add(timeTextPanel);

        // count time
        JPanel timePanel = new JPanel();  //  时间内容
        timePanel.setBorder(new EtchedBorder());
        timePanel.setBackground(new Color(208, 223, 255));

        timeJLabel.setText("0");
        timePanel.add(timeJLabel);
        controlJPanel.add(timePanel);

        JPanel startJPanel = new JPanel();  // 按钮
        startJPanel.setBorder(new EtchedBorder());
        startJPanel.add(startButton);
        startButton.addActionListener(this); // 向“重列”按钮添加事件监听
        controlJPanel.add(startJPanel);

        JPanel resetJPanel = new JPanel();  // 按钮
        resetJPanel.setBorder(new EtchedBorder());
        resetJPanel.setBackground(new Color(208, 223, 255));
        resetJPanel.add(resetButton);
        resetButton.addActionListener(this); // 向“重列”按钮添加事件监听
        controlJPanel.add(resetJPanel);

        JPanel nextJPanel = new JPanel();  // 下一局
        nextJPanel.setBorder(new EtchedBorder());
        nextJPanel.add(nextButton);
        ImageIcon  nextIcon = new ImageIcon("image/next.png");
        nextButton.setIcon(nextIcon);
        nextButton.addActionListener(this); // 向“重列”按钮添加事件监听
        controlJPanel.add(nextJPanel);


        JPanel beforeJPanel = new JPanel();  // 上一局
        beforeJPanel.setBorder(new EtchedBorder());
        beforeJPanel.setBackground(new Color(208, 223, 255));
        beforeJPanel.add(beforeButton);
        ImageIcon  beforeIcon = new ImageIcon("image/left.png");
        beforeButton.setIcon(beforeIcon);
        beforeButton.addActionListener(this); // 向“重列”按钮添加事件监听
        controlJPanel.add(beforeJPanel);

        JPanel suggestJPanel = new JPanel();  // 提示
        suggestJPanel.setBorder(new EtchedBorder());
        suggestJPanel.setBackground(new Color(208, 223, 255));
        suggestJPanel.add(suggestButton);
        suggestButton.addActionListener(this); // 向“重列”按钮添加事件监听
        controlJPanel.add(suggestJPanel);


        JPanel exitJPanel = new JPanel();  // 退出
        exitJPanel.setBorder(new EtchedBorder());
        exitJPanel.add(exitButton);
        // 设置按钮背景图像
        ImageIcon  exitIcon = new ImageIcon("image/wrong.png");
        exitButton.setIcon(exitIcon);
        exitButton.addActionListener(this); // 向“重列”按钮添加事件监听
        controlJPanel.add(exitJPanel);


        JPanel initJPanel = new JPanel();
        JLabel initJLabel = new JLabel();
        initJLabel.setText("欢迎来到连连看世界");
        Icon icon=new ImageIcon("image/user7-128x128.jpg");//实例化Icon对象
        initJLabel.setIcon(icon);//为标签设置图片
        initJLabel.setHorizontalAlignment(SwingConstants.CENTER);  //设置文字放置在标签中间
        initJLabel.setOpaque(false);//设置标签为不透明状态
        initJLabel.setSize(128,128);
        initJPanel.add(initJLabel);
        centerJPanel.add(initJPanel);

        menuJPanel.add(controlJPanel);

            int height = 650;
            int width = 800;
        mainFrame.setBounds((1366-width)/2,(768-height)/2,width,height);     // 显示在屏幕中间
        mainFrame.setVisible(true);  // 可见

    }



    public static void main(String[] args) {
        LLK llk = new LLK(); // 初始化
         llk.init(); // 调用init

     }



    /**
     *  按钮的监控
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == exitButton){    // 退出
            System.exit(0);
        }

        if (e.getSource() == startButton) {
            int[][] icon =    nextGame(0,0);  // 现在开始
            test( icon);
            pointJLabel.setText("0");
        }

        if (e.getSource() == nextButton){
            int[][] icon =  nextGame(2,5); //  下一局
             test(icon );
            pointJLabel.setText("0");
        }

        if (e.getSource() == resetButton){
            int[][] icon =   nextGame(0,0); //  重玩
            test(icon );
            pointJLabel.setText("0");
        }

        if (e.getSource() == suggestButton){  // 建议


            try {
                evenHandler.suggest(iconDate,centerJPanel);
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "请先开始游戏!", "提示",JOptionPane.PLAIN_MESSAGE);
            }
        }

        if (e.getSource() == beforeButton){  //  上一局
            if (MAX_X < 4 || MAX_Y <4 ){
                JOptionPane.showMessageDialog(null,
                        "当前为第一关，无法选择上一关.", "提示",JOptionPane.PLAIN_MESSAGE);
            }else {
                int[][] icon =  nextGame(-2,-5);
               test(icon );
            }

        }


        //  鼠标点击按钮
        for (int x = 0; x < MAX_X+2; x++){
            for (int y= 0 ; y < MAX_Y+2; y++){
                if (e.getSource() == gameButton[x][y]){
                    //  调用 开始判断 进入核心算法
                    Cell cell = new Cell((x),(y),gameButton[x][y]);

                    int result = evenHandler.coreEven(cell,centerJPanel,gameButton,
                             pointJLabel );
                    if (result == 0){
                        //  重玩
                     int[][] icon = nextGame(0,0);
                        test(icon);   //  检验死局
                    }
                    if (result == 1){    //  下一关
                        int[][] icon = nextGame(2,8);
                       test(icon);   //  检验死局
                    }
                }
            }
        }

        mainFrame.setVisible(true);  // 可见
    }


    /**********************************
     *   验证是否出现死局
     */
    public void test(int[][]  icon ){


        int[]  iconCopy = new int[(MAX_X+2)*(MAX_Y+2)];

        int n = 0 ;
         for (int i = 0; i < MAX_X+2; i++) {
            for (int j = 0; j < MAX_Y+2; j++) {
                iconCopy[n] = icon[i][j]; // 将现在任然存在的数字存入save中
                n++;
            }
        }

        boolean result = validation(icon);

        for (int i=0; i <MAX_X/2; i++){
            if (!result){
                result = validation(icon);
            }
        }

        if (result){
            // 没有问题 需要赋值上去
            int m = 0 ;
            for (int i = 0; i < MAX_X+2; i++) {
                for (int j = 0; j < MAX_Y+2; j++) {
                     icon[i][j] = iconCopy[m] ;
                    m++;
                }
            }

             nextGameButton(icon);

         }else {
           // new Exception(" 死局 ");
            System.out.println("死局");
            icon = nextGame(0,0);
            test(icon );  // 验证是否死局
        }

    }


    /**********************
     * 验证时 选取第一个Cell
     * @param icon
     * @return
     */
    public boolean validation(  int[][]  icon ) {

        for (int x = 1; x < icon.length -1; x++) {

            for (int y = 1; y < icon[x].length -1; y++) {

                if (gameButton[x][y].getIcon() == null){
                    continue;
                }
                if (x == icon.length -2 && y == icon[x].length -2 ){  //  最后一个不用便利
                    continue;
                }
                Cell cell1   = new Cell(x, y, gameButton[x][y]);

                boolean   result  =   validation2(x,y,cell1 ,icon);

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
    public boolean validation2(int x, int y ,Cell cell1, int[][]  icon ) {

        int result = -1;
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
                  //   System.out.println (cell1.toString()+"  "+ cell2.toString());
                      result =  evenHandler.linkedTest(cell1,cell2,centerJPanel,gameButton);

                      //  尽使用提示功能
                }

            }
         }

        if (result == 0){   //  结果为0 时  棋盘为空
            System.out.println ("Test 完全消除完毕,没有问题了！！");
            return true;
        }
        return false;
    }


    /**********************
     *  格子里面填数  重新装  重置
     */
    public int[][] nextGame(int addNum, int category) {

        MAX_X = MAX_X + addNum ;
        MAX_Y = MAX_Y + addNum;

        CELLCATEGORY = CELLCATEGORY  + category;
        int[][]  gridData =   new int[MAX_X+2][MAX_Y+2];
        gameButton  = new JButton[MAX_X+ 2][MAX_Y+ 2];

        centerJPanel.setLayout(new GridLayout(MAX_X+2,MAX_Y+2)); // MAX_X * MAX_Y 网格布局

        int randoms , cols, rows ;
         List<Integer> iconList = new ArrayList< >();
        Set<Integer> iconSet = new TreeSet<>();

        int[][] icon = new int[MAX_X+2][MAX_Y+2];

        for (int twins = 1; twins <= (MAX_X*MAX_Y/2); twins++){
            randoms = (int)(Math.random() *  CELLCATEGORY + 1);
            iconSet.add(randoms);
            for (int alike = 1; alike <= 2; alike++){  //  将生成的数字填充到格子里面
                cols = (int)(Math.random() *  MAX_X + 1 );
                rows = (int)(Math.random() *  MAX_Y + 1);

                while (gridData[cols][rows] != 0){
                    cols = (int)(Math.random() *  MAX_X + 1 );
                    rows = (int)(Math.random() *  MAX_Y + 1 );
                }

                gridData[cols][rows] = randoms;   // 填充
            }

        }

        iconList.addAll(iconSet);
        //按条件过滤

        for (int i = 0; i < iconSet.size(); i++){

            for (int x = 0; x < MAX_X + 2; x++){
                for (int y = 0; y < MAX_Y + 2 ; y++){

                    if (iconList.get(i) == gridData[x][y]){
                        icon[x][y] = i+1;
                    }
                }
            }

        }

        nextGameButton(icon);

        return icon ;

    }


    /**********************
     *  生成带图标的按钮
     * @param icon
     */
    public void nextGameButton( int[][]  icon) {

        mainFrame.setVisible(false);
        pressFlag = false; // 这里一定要将按钮点击信息归为初始
        centerJPanel.removeAll();
        for (int cols = 0; cols < MAX_X + 2; cols++){
            for (int rows = 0; rows < MAX_Y + 2 ; rows++){
                if (icon[cols][rows]>30){
                    icon[cols][rows] = 30 ;
                }
                 ImageIcon  nextIcon = new ImageIcon("image/icon/default"+icon[cols][rows]+".png");
                gameButton[cols][rows] = new JButton(nextIcon);   //  新建按钮
                gameButton[cols][rows].setMaximumSize(new Dimension(48,48));//设置按钮和图片的大小相同
                gameButton[cols][rows].setFocusPainted(false);
                gameButton[cols][rows].setBorder(null);
               // gameButton[cols][rows].setContentAreaFilled(false);//设置图片填满按钮所在的区域
              //  gameButton[cols][rows].setBorderPainted(false); //设置按钮边界不显示
               //  gameButton[cols][rows].setBackground(new Color(127, 174, 252));
                gameButton[cols][rows].addActionListener(this); // 添加监听事件
                centerJPanel.add(gameButton[cols][rows]);

                if (cols == 0 || rows ==0||cols == MAX_X+1 || rows == MAX_Y+1 ){
                    gameButton [cols][rows].setIcon(null);
                    gameButton[cols][rows].setVisible(false);
                }
            }
        }

            this.iconDate = icon;

        //   重置时间
       int time = Integer.parseInt(timeJLabel.getText());
        if (time  == 0){
            timeJLabel.setText(String.valueOf(180));
            new GameTimer(timeJLabel,centerJPanel);
        }else {
            timeJLabel.setText(String.valueOf(180));
        }

    }


}
