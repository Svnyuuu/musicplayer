// import java.applet.Applet;  
// import java.applet.AudioClip; 
// import java.net.MalformedURLException;  
// import java.net.URL;  
// import java.io.*;
// import javax.swing.text.*;
// import javax.swing.text.StyleContext.NamedStyle;
// import javax.swing.*;
// import static javax.swing.JFrame.*; //����JFrame�ľ�̬����
// import java.awt.event.*;
// import java.awt.*;
// import java.net.*;
// import java.util.*;
// import java.util.Timer;

// import javax.sound.sampled.*;
// import javax.swing.*;

// class audio {
//     private String musicPath; // ��Ƶ�ļ�·��
//     private AudioInputStream AudioStream;
//     private AudioFormat AuFormat;
//     private Clip clip;

//     public void SetPlayAudioPath(String path) {
//            this.musicPath = path;
//            prefetch();
//         }    

//      private void prefetch() {//������Ƶ�ļ���
//         try {
//             // ��File�л�ȡ��Ƶ������,File����ָ����Ч����Ƶ�ļ�,�����׳� javax.sound.sampled.UnsupportedAudioFileException, java.io.IOException�쳣
//             AudioStream = AudioSystem.getAudioInputStream(new File(musicPath));
//             // ��ȡ��Ƶ�ı������
//             AuFormat = AudioStream.getFormat();
//             // ��װ��Ƶ��Ϣ,AudioSystem.NOT_SPECIFIED���Ա�ʾ�ļ���С
//             DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, AuFormat, AudioSystem.NOT_SPECIFIED);
//             // ʹ�ð�װ��Ƶ��Ϣ���Info�ഴ��Դ�����У��䵱��Ƶ����Դ,ע��DataLine.Info���ܵ���SourceDataLine,������AudioSystem.getLine(dataLineInfo)ǿ��ת��ΪSourceDataLine����
//             //�ɽ���Ƶ����д�뻺����
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
//     // �ⲿ���ÿ��Ʒ���:������Ƶ���̣߳�
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

// class MyExtendsJFrame extends JFrame { //������
// 	 JLabel background;//�����ؼ�
// 	 JButton buttonPlay;//���Ű�ť
// 	 JTextPane textLyrics;//��ʿؼ�
// 	 JLabel playTime;//���Ž������ؼ�
// 	 JList listPlayFile;//�����б�ؼ�
// 	 Timer nTimer;//��ʱ������
	 
// 	public MyExtendsJFrame(){
// 		setTitle("������");//�����
// 		setBounds(160,100,710,430);	//���ô��ڴ�С		
// 		setLayout(null);//�ղ���			
// 		init();   //��ӿؼ��Ĳ�����װ��һ������         
// 		setVisible(true);//��������������ִ��
// 		setDefaultCloseOperation(EXIT_ON_CLOSE);
// 	}
// 	void init(){//��ӵĿؼ�	  		
// 		 Icon img=new ImageIcon(".//background.jpg");     //����ͼ�����			
// 		 background = new JLabel(img);//���ñ���ͼƬ
// 		 background.setBounds(0,0,700,400);//���ñ����ؼ���С
// 	     getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));//����ͼƬ�ؼ�������ײ�
// 		((JPanel)getContentPane()).setOpaque(false); //�ؼ�͸��
						
// 		  buttonPlay=new JButton();//��Ӳ��Ű�ť
// 	      buttonPlay.setBounds(322,335,40,40); //���ò��Ű�ť��С
// 	      Icon icon=new ImageIcon(".//play.jpg");//��������ͼ�����
// 	      buttonPlay.setIcon(icon);	      //���ò���ͼ��
// 	      add(buttonPlay);//��Ӳ��Ű�ť��������
		
// 	      listPlayFile=new JList();	  //���������б� 
// 	      listPlayFile.setBounds(500,100,150,150); //���ò����б��С 
// 	      listPlayFile.setOpaque(false);//���ò����б�͸��
// 	      listPlayFile.setBackground(new Color(0, 0, 0, 0));//���ò����б�����ɫ
// 	      listPlayFile.setForeground(Color.white);//���ò����б�������ɫ
// 	      add(listPlayFile);       //��Ӳ����б���������
	      
// 	      textLyrics=new JTextPane();   //������ʿؼ�    
// 	      textLyrics.setBounds(20,100,200,100);//���ø�ʿؼ���С
// 	      textLyrics.setForeground(Color.white);//��ʿؼ�������ɫ
// 	      textLyrics.setOpaque(false);//��ʿؼ�͸��
// 	      add(textLyrics);    //��Ӹ�ʿؼ���������
// 	      textLyrics.setText("ܽ�ػ���������֦ͷ \n"+"�κε�����\n"+
//              	 		"Ư���罭ˮ����\n"+"������ǰ���������� \n");//��ʿؼ��������
	      
// 	      Icon img2=new ImageIcon(".//time.jpg");     //����ͼ�����
// 	      playTime = new JLabel(img2);	  		//�������Ž���������
// 	      playTime.setBounds(0,324,0,3);	  	//���ò��Ž����������С	      
// 	      add(playTime); //��Ӳ��Ž�������������
// 	}		
	
// 	public  void timerFun(){//��ʱ������
// 		if(nTimer!=null){nTimer.cancel();}//�Ѿ��ж�ʱ����ر�
//         nTimer = new Timer();//������ʱ��
//         nTimer.schedule(new TimerTask(){  //������
//         	int nPlayTime=0;  
//             public void run() { //��ʱ��������
//             	playTime.setBounds(0, 324, nPlayTime+=5, 3);      

      	
//             }
//         },0,1000);
//     }
// 	}
// public class MusicPlay{
// @SuppressWarnings("unchecked")//���Ծ���
//     public static void main(String[] args) { 
//     	 audio audioPlay=new audio ();  //�������Ŷ���
//     	 audioPlay.SetPlayAudioPath("ɽ��С¥ҹ����.wav");//���ò����ļ�
//                //  audioPlay.SetPlayAudioPath("Ʈ�����.wav");//���ò����ļ�
// 		audioPlay.play(0);//��ʼ����
// audioPlay.playAfterPause(10000000);//��ʼ����
//   System.out.println(audioPlay.GetTotaltime()/1000000);
//         MyExtendsJFrame frame=new MyExtendsJFrame();//����������򴰿�  
        
//         frame.timerFun();//�򿪼�ʱ��
        
//         Vector  vt=new Vector ();	//����Vector����ͨ��add������Ӷ���	
//          vt.add("ɽ��С¥ҹ����.wav");
//          vt.add("1");
//          vt.add("2");
//          frame.listPlayFile.setListData(vt);//��Vector������ӵ������б�ؼ���
//     }
// }
