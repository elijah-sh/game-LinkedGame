package com.llk;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Auther: shuaihu.shen@hand-china.com
 * @Date: 2018/10/27 12:44
 * @Description:
 */
@Data
@AllArgsConstructor
public class GameConfig {
    private int MAX_X ;     // 高度
    private  int MAX_Y ;   // 宽度
    private  int CELLCATEGORY ;   //  传入不同的元素类型个数
}
