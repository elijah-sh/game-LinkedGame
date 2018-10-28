package com.llk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;

/**
 * @Auther: shuaihu.shen@hand-china.com
 * @Date: 2018/10/22 18:52
 * @Description:  元素的信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cell {
    private Integer x;
    private Integer y;
  //  private Integer value;
    private JButton jButton;

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
           //     ", value=" + value +
           //      ", jButton=" + jButton.getText() +
                '}';
    }
}
