import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Auther: shuaihu.shen@hand-china.com
 * @Date: 2018/10/27 13:07
 * @Description:
 */

public class ConfigTest extends GameApplicationTests {
    @Value("$(gameconfig.MAX_X")
    private int MAX_X ;     // 高度

    @Value("$(gameconfig.MAX_Y")
    private  int MAX_Y ;   // 宽度

    @Value("$(gameconfig.CELLCATEGORY")
    private  int CELLCATEGORY ;   //  传入不同的元素类型个数

    @Test
    public  void point (){
        System.out.println(MAX_X);
    }
}
