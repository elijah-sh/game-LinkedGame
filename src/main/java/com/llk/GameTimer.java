package com.llk;

import lombok.Data;

import javax.swing.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer
 * 　　TimerTask是一个实现了Runnable接口的抽象类，代表一个可以被Timer执行的任务。
 */
@Data
public class GameTimer {
	 Timer timer;
	 Integer  timeNum = 150;
	public GameTimer (JLabel timeJLabel,JPanel centerJPanel){
		timer = new Timer();
	   /**
		* 第一个参数是要操作的方法，
		* 第二个参数是要设定延迟的时间， delay
		*	第三个参数是周期的设定，每隔多长时间执行该操作 延迟1s
	    */
		timer.schedule(new GameTimerTask(timeJLabel,centerJPanel),1000,1000);
		//tiemr.schedule(执行的方法,延迟时间,多久执行一次)
	}

	class GameTimerTask  extends TimerTask{
		JLabel timeJLabel;
		JPanel centerJPanel;
		GameTimerTask(JLabel timeJLabel ,JPanel centerJPanel ){
			this.timeJLabel = timeJLabel;
			this.centerJPanel = centerJPanel;
		}
		@Override
		public void run() {

		//	System.out.println("=========="+ new Date());
			int time ;
			time = Integer.parseInt(timeJLabel.getText());

			timeJLabel.setText(String.valueOf(time-1));
			if (time == 1){
				timer.cancel();  // 关闭任务 游戏结束

			/*	Object[] options ={ "重玩", "结束" };  //自定义按钮上的文字
				int result = JOptionPane.showOptionDialog(null,
						"很遗憾，时间到了，闯关结束，请选择？",
						"失败",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.ERROR_MESSAGE,
						null, options, options[0]);*/
				JOptionPane.showMessageDialog(null,
						"很遗憾，时间到了，闯关结束.", "失败",JOptionPane.PLAIN_MESSAGE);

			/*	if (result == 0){
					//  重玩
				 return;
				}
				if (result == 1){
					//  结束

				}*/
				centerJPanel.removeAll();
				centerJPanel.repaint();

			}

		}
	}

}