// import java.applet.Applet;  
// import java.applet.AudioClip; 
// import java.net.MalformedURLException;  
// import java.net.URL;  
// import java.io.*;
// import javax.swing.text.*;
// import javax.swing.text.StyleContext.NamedStyle;
// import javax.swing.*;
// import static javax.swing.JFrame.*; //引入JFrame的静态常量
// import java.awt.event.*;
// import java.awt.*;
// import java.net.*;
// import java.util.*;
// import java.util.Timer;

// import javax.sound.sampled.*;
// import javax.swing.*;

// class audio {
//     private String musicPath; // 音频文件路径
//     private AudioInputStream AudioStream;
//     private AudioFormat AuFormat;
//     private Clip clip;

//     public void SetPlayAudioPath(String path) {
//            this.musicPath = path;
//            prefetch();
//         }    

//      private void prefetch() {//读入音频文件流
//         try {
//             // 从File中获取音频输入流,File必须指向有效的音频文件,可能抛出 javax.sound.sampled.UnsupportedAudioFileException, java.io.IOException异常
//             AudioStream = AudioSystem.getAudioInputStream(new File(musicPath));
//             // 获取音频的编码对象
//             AuFormat = AudioStream.getFormat();
//             // 包装音频信息,AudioSystem.NOT_SPECIFIED可以表示文件大小
//             DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, AuFormat, AudioSystem.NOT_SPECIFIED);
//             // 使用包装音频信息后的Info类创建源数据行，充当混频器的源,注意DataLine.Info接受的是SourceDataLine,可以让AudioSystem.getLine(dataLineInfo)强制转化为SourceDataLine类型
//             //可将音频数据写入缓冲区
//             clip=AudioSystem.getClip();
//             clip.open(AudioStream);

//         } catch (UnsupportedAudioFileException e ) {
//             e.printStackTrace();
//         }
//         catch ( LineUnavailableException  e) {
//             e.printStackTrace();
//         }
//         catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

//     private void playClip( int framePos)
//     {
//         if(clip == null)
//             return;
//         stop();
//         clip.setFramePosition(framePos);
//     }
//     // 外部调用控制方法:生成音频主线程；
//     public void play(int framePos) {
//         playClip(framePos);
//         clip.start();
//     }

//     public void stop()
//     {
//         if(clip.isRunning()) {
//             clip.stop();
//         }
//     }

//     public void playAfterPause(long time)
//     {
//         clip.setMicrosecondPosition(time);
//         clip.start();
//     }

//     public long getFramePosition()
//     {
//         return clip.getMicrosecondPosition();
//     }

//     public  void SetTime(long time)
//     {
//         clip.setMicrosecondPosition(time);
//     }

//     public  long GetTotaltime()
//     {
//         return clip.getMicrosecondLength();
//     }

//     public boolean getIsRunning()
//     {
//         return clip.isRunning();
//     }
// }

// class MyExtendsJFrame extends JFrame { //窗口类
// 	 JLabel background;//背景控件
// 	 JButton buttonPlay;//播放按钮
// 	 JTextPane textLyrics;//歌词控件
// 	 JLabel playTime;//播放进度条控件
// 	 JList listPlayFile;//播放列表控件
// 	 Timer nTimer;//定时器对象
	 
// 	public MyExtendsJFrame(){
// 		setTitle("播放器");//软件名
// 		setBounds(160,100,710,430);	//设置窗口大小		
// 		setLayout(null);//空布局			
// 		init();   //添加控件的操作封装成一个函数         
// 		setVisible(true);//放在添加组件后面执行
// 		setDefaultCloseOperation(EXIT_ON_CLOSE);
// 	}
// 	void init(){//添加的控件	  		
// 		 Icon img=new ImageIcon(".//background.jpg");     //创建图标对象			
// 		 background = new JLabel(img);//设置背景图片
// 		 background.setBounds(0,0,700,400);//设置背景控件大小
// 	     getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));//背景图片控件置于最底层
// 		((JPanel)getContentPane()).setOpaque(false); //控件透明
						
// 		  buttonPlay=new JButton();//添加播放按钮
// 	      buttonPlay.setBounds(322,335,40,40); //设置播放按钮大小
// 	      Icon icon=new ImageIcon(".//play.jpg");//创建播放图标对象
// 	      buttonPlay.setIcon(icon);	      //设置播放图标
// 	      add(buttonPlay);//添加播放按钮至窗口中
		
// 	      listPlayFile=new JList();	  //创建播放列表 
// 	      listPlayFile.setBounds(500,100,150,150); //设置播放列表大小 
// 	      listPlayFile.setOpaque(false);//设置播放列表透明
// 	      listPlayFile.setBackground(new Color(0, 0, 0, 0));//设置播放列表背景颜色
// 	      listPlayFile.setForeground(Color.white);//设置播放列表字体颜色
// 	      add(listPlayFile);       //添加播放列表至窗口中
	      
// 	      textLyrics=new JTextPane();   //创建歌词控件    
// 	      textLyrics.setBounds(20,100,200,100);//设置歌词控件大小
// 	      textLyrics.setForeground(Color.white);//歌词控件字体颜色
// 	      textLyrics.setOpaque(false);//歌词控件透明
// 	      add(textLyrics);    //添加歌词控件至窗口中
// 	      textLyrics.setText("芙蓉花又栖满了枝头 \n"+"奈何蝶难留\n"+
//              	 		"漂泊如江水向东流\n"+"望断门前隔岸的杨柳 \n");//歌词控件添加文字
	      
// 	      Icon img2=new ImageIcon(".//time.jpg");     //创建图标对象
// 	      playTime = new JLabel(img2);	  		//创建播放进度条对象
// 	      playTime.setBounds(0,324,0,3);	  	//设置播放进度条对象大小	      
// 	      add(playTime); //添加播放进度条至窗口中
// 	}		
	
// 	public  void timerFun(){//定时器函数
// 		if(nTimer!=null){nTimer.cancel();}//已经有定时器则关闭
//         nTimer = new Timer();//创建定时器
//         nTimer.schedule(new TimerTask(){  //匿名类
//         	int nPlayTime=0;  
//             public void run() { //定时器函数体
//             	playTime.setBounds(0, 324, nPlayTime+=5, 3);      

      	
//             }
//         },0,1000);
//     }
// 	}
// public class MusicPlay{
// @SuppressWarnings("unchecked")//忽略警告
//     public static void main(String[] args) { 
//     	 audio audioPlay=new audio ();  //创建播放对象
//     	 audioPlay.SetPlayAudioPath("山外小楼夜听雨.wav");//设置播放文件
//                //  audioPlay.SetPlayAudioPath("飘洋过海.wav");//设置播放文件
// 		audioPlay.play(0);//开始播放
// audioPlay.playAfterPause(10000000);//开始播放
//   System.out.println(audioPlay.GetTotaltime()/1000000);
//         MyExtendsJFrame frame=new MyExtendsJFrame();//创建聊天程序窗口  
        
//         frame.timerFun();//打开计时器
        
//         Vector  vt=new Vector ();	//创建Vector对象，通过add方法添加多行	
//          vt.add("山外小楼夜听雨.wav");
//          vt.add("1");
//          vt.add("2");
//          frame.listPlayFile.setListData(vt);//把Vector对象添加到播放列表控件中
//     }
// }
