import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;
import java.io.IOException;
// import org.apache.commons.io.FilenameUtils;
// import com.example.MySliderUI;
// import java.net.URL;

public class GUI extends JFrame {
    // 颜色配置 全局FRAME_COLOR黑 TEXT_COLOR白
    // 要修改直接在这修改
    public static final Color FRAME_COLOR = Color.BLACK;
    public static final Color TEXT_COLOR = Color.WHITE;

    private MusicPlay musicPlayer;
    JLabel playTime;//播放进度条控件
    // private Volume volume;

    // 允许在应用程序中使用文件浏览
    private JFileChooser jFileChooser;

    private JLabel songTitle, songArtist;
    private JPanel playbackBtns;
    private JSlider playbackSlider;


    private JTextArea lyricsArea;
    private JScrollPane lyricsScrollPane;

    JTextPane textLyrics;

    public GUI() {

        // 调用JFrame构造函数来配置GUI并设置标题
        // comic sans ms
        setTitle("Pito's Music Player");// 软件名
        // super("Pito's Music Player"); //等效

        // 设置标题图标
        try {
            setIconImage(ImageIO.read(new File("src\\assets\\icon\\pito.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 设置宽度和高度
        // 1100,700
        // setSize(400, 600);
        setSize(700, 600);
        // 设置为全屏
        // setExtendedState(JFrame.MAXIMIZED_BOTH);

        // 关闭应用程序时结束进程
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // 在屏幕中央启动应用程序
        setLocationRelativeTo(null);

        // 防止应用程序调整大小
        setResizable(false);

        // 将布局设置为null，允许我们控制组件的(x, y)坐标，并设置高度和宽度
        setLayout(null);

        // 更改框架颜色
        getContentPane().setBackground(FRAME_COLOR);

        musicPlayer = new MusicPlay(this);
        jFileChooser = new JFileChooser();

        // 设置文件浏览的默认路径
        jFileChooser.setCurrentDirectory(new File("src/assets"));

        // 过滤文件浏览，只显示.mp3文件
        jFileChooser.setFileFilter(new FileNameExtensionFilter("MP3 Files", "mp3"));
        
        

        textLyrics=new JTextPane();   //创建歌词控件    
        textLyrics.setBounds(400,200,200,200);//设置歌词控件大小
        //设置字体大小
        textLyrics.setFont(new Font("华文行楷", Font.PLAIN, 20));
        //设为只读
        textLyrics.setEditable(false);
        textLyrics.setForeground(Color.white);//歌词控件字体颜色
        textLyrics.setOpaque(false);//歌词控件透明
        add(textLyrics);    //添加歌词控件至窗口中
        textLyrics.setText("芙蓉花又栖满了枝头 \n"+"奈何蝶难留\n"+
                        "漂泊如江水向东流\n"+"望断门前隔岸的杨柳 \n");//歌词控件添加文字
        
        Icon img2=new ImageIcon(".//time.jpg");     //创建图标对象
        playTime = new JLabel(img2);	  		//创建播放进度条对象
        playTime.setBounds(0,324,0,3);	  	//设置播放进度条对象大小	      
        add(playTime); //添加播放进度条至窗口中

        // textLyrics=new JTextPane();  
        // textLyrics.setBounds(20,100,200,100);
        // textLyrics.setForeground(Color.white);
        // textLyrics.setOpaque(false);
        // add(textLyrics);    
        // textLyrics.setText("haloooooooo\nnohalo");
        
        addGuiComponents();

        // addlyrics();
    }

    // private void addlyrics() {
    //     // // 歌词
    //     // JLabel lyrics = new JLabel("歌词");
    //     // lyrics.setBounds(0, 500, getWidth() - 10, 30);
    //     // lyrics.setFont(new Font("华文行楷", Font.BOLD, 24));
    //     // lyrics.setForeground(TEXT_COLOR);
    //     // lyrics.setHorizontalAlignment(SwingConstants.CENTER);
    //     // add(lyrics);
    //     lyricsArea = new JTextArea(5, 20);
    //     // lyricsArea.setBounds(300,20, 300, 500);
    //     lyricsArea.setLineWrap(true);
    //     lyricsArea.setWrapStyleWord(true);
    //     lyricsArea.setText("这里是歌词");
    //     // 创建一个滚动面板，并将文本区域添加到滚动面板中
    //     lyricsScrollPane = new JScrollPane(lyricsArea);

    //     // 将滚动面板添加到GUI中
    //     this.add(lyricsScrollPane, BorderLayout.EAST);
    // }

    private void addGuiComponents() {
        // 添加工具栏
        addToolbar();

        // // // 加载唱片图像
        // JLabel songImage = new JLabel(loadImage("src/assets/record.png"));
        // songImage.setBounds(0, 50, getWidth() - 20, 225);
        // add(songImage);
        // // revalidate();
        // // repaint();

        // 加载唱片动态图像 src\assets\playgif7.gif
        // 加载 GIF 图像
        // ImageIcon gifIcon = loadImage("src/assets/icon/Supernova.mv");
        ImageIcon gifIcon = loadImage("src/assets/icon/playgif7.gif");
        // ImageIcon gifIcon = loadImage("src/assets/icon/g1.gif");
        //随机刷新gif
        // String[] gifPath = {"src/assets/icon/g1.gif", "src/assets/icon/g2.gif", "src/assets/icon/g3.gif"};
        // int index = (int) (Math.random() * 3);
        // ImageIcon gifIcon = loadImage(gifPath[index]);
        JLabel label = new JLabel(gifIcon);
        label.setBounds(30, 134, 300, 300);
        add(label);

        // 歌曲标题
        songTitle = new JLabel("歌曲标题");
        // songTitle = new JLabel("Supernova");
        // songTitle.setBounds(0, 285, getWidth() - 10, 30);
        // songTitle.setBounds(0, 350, getWidth() - 10, 30);
        // 置于滑轨之上
        songTitle.setBounds(0, 40, getWidth(), 38);
        songTitle.setFont(new Font("华文行楷", Font.BOLD, 48));
        songTitle.setForeground(TEXT_COLOR);
        songTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(songTitle);

        // 歌手
        songArtist = new JLabel("歌手");
        // songArtist = new JLabel("Aespa");
        // songArtist.setBounds(0, 315, getWidth() - 10, 30);
        // 置于标题之下
        songArtist.setBounds(0, 90, getWidth() - 10, 30);
        // Serif
        songArtist.setFont(new Font("华文行楷", Font.PLAIN, 32));
        songArtist.setForeground(TEXT_COLOR);
        songArtist.setHorizontalAlignment(SwingConstants.CENTER);
        add(songArtist);

        // 播放进度滑块
        // 滑块图标怎么添加

        // playbackSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        // playbackSlider.setBounds(getWidth() / 2 - 300 / 2, 365, 300, 40);
        // 置于底部按钮之上
        playbackSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        playbackSlider.setBounds(0, 450, getWidth() - 15, 40);
        playbackSlider.setBackground(null);

        // playbackSlider.setForeground(TEXT_COLOR);
        // 设置滑块颜色
        // playbackSlider.setUI(new MySliderUI(playbackSlider));

        playbackSlider.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // 当用户按住滑块时，暂停歌曲
                musicPlayer.pauseSong();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // 当用户释放滑块时
                JSlider source = (JSlider) e.getSource();

                // 获取用户希望回放到的帧值
                int frame = source.getValue();

                // 将音乐播放器的当前帧更新为此帧
                musicPlayer.setCurrentFrame(frame);

                // 同时更新以毫秒为单位的当前时间
                musicPlayer.setCurrentTimeInMilli(
                        (int) (frame / (2.08 * musicPlayer.getCurrentSong().getFrameRatePerMilliseconds())));

                // 恢复歌曲播放
                musicPlayer.playCurrentSong();

                // 切换为暂停按钮，切换关闭播放按钮
                enablePauseButtonDisablePlayButton();
            }
        });
        add(playbackSlider);

        // 播放按钮（上一首、播放、下一首）
        addPlaybackBtns();
    }

    private void addToolbar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setBounds(0, 0, getWidth(), 20);

        // 防止工具栏被移动
        toolBar.setFloatable(false);

        // 添加下拉菜单
        JMenuBar menuBar = new JMenuBar();
        toolBar.add(menuBar);

        // // 添加歌曲菜单，将加载歌曲选项放置在其中
        // JMenu songMenu = new JMenu("歌曲");
        // menuBar.add(songMenu);

        // // 添加"加载歌曲"项到歌曲菜单
        // JMenuItem loadSong = new JMenuItem("加载歌曲");
        // loadSong.addActionListener(new ActionListener() {
        // @Override
        // public void actionPerformed(ActionEvent e) {
        // // 返回一个整数，告诉我们用户做了什么
        // int result = jFileChooser.showOpenDialog(GUI.this);
        // File selectedFile = jFileChooser.getSelectedFile();

        // // 这意味着我们还要检查用户是否按下了"打开"按钮
        // if (result == JFileChooser.APPROVE_OPTION && selectedFile != null) {
        // // 根据所选文件创建一个歌曲对象
        // Song song = new Song(selectedFile.getPath());

        // // 在音乐播放器中加载歌曲
        // musicPlayer.loadSong(song);

        // // 更新歌曲标题和歌手
        // updateSongTitleAndArtist(song);

        // // 更新播放进度滑块
        // updatePlaybackSlider(song);

        // // 切换为暂停按钮，切换关闭播放按钮
        // enablePauseButtonDisablePlayButton();
        // }
        // }
        // });
        // songMenu.add(loadSong);

        // 添加播放列表菜单
        JMenu playlistMenu = new JMenu("播放列表");
        menuBar.add(playlistMenu);

        // 然后将项目添加到播放列表菜单
        JMenuItem createPlaylist = new JMenuItem("创建播放列表");
        createPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 加载音乐播放列表对话框
                new MusicPlaylistDialog(GUI.this).setVisible(true);
            }
        });
        playlistMenu.add(createPlaylist);

        JMenuItem loadPlaylist = new JMenuItem("加载播放列表");
        loadPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileFilter(new FileNameExtensionFilter("Playlist", "txt"));
                jFileChooser.setCurrentDirectory(new File("src/assets/playlist"));

                int result = jFileChooser.showOpenDialog(GUI.this);
                File selectedFile = jFileChooser.getSelectedFile();

                if (result == JFileChooser.APPROVE_OPTION && selectedFile != null) {
                    // 停止音乐
                    musicPlayer.stopSong();

                    // 加载播放列表
                    musicPlayer.loadPlaylist(selectedFile);
                }
            }
        });
        playlistMenu.add(loadPlaylist);

        add(toolBar);
    }

    private void addPlaybackBtns() {
        playbackBtns = new JPanel();
        // playbackBtns.setBounds(0, 435, getWidth() - 10, 80);
        // 置底
        playbackBtns.setBounds(0, 480, getWidth() - 10, 80);
        playbackBtns.setBackground(null);

        // 上一首按钮
        JButton previousButton = new JButton(loadImage("src/assets/icon/previous.png"));
        previousButton.setBorderPainted(false);
        previousButton.setBackground(null);
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 转到上一首歌曲
                musicPlayer.prevSong();
            }
        });
        playbackBtns.add(previousButton);

        // 播放按钮
        JButton playButton = new JButton(loadImage("src/assets/icon/play.png"));
        playButton.setBorderPainted(false);
        playButton.setBackground(null);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 切换关闭播放按钮，切换为暂停按钮
                enablePauseButtonDisablePlayButton();

                // 播放或恢复歌曲
                musicPlayer.playCurrentSong();
            }
        });
        playbackBtns.add(playButton);

        // 暂停按钮
        JButton pauseButton = new JButton(loadImage("src/assets/icon/pause.png"));
        pauseButton.setBorderPainted(false);
        pauseButton.setBackground(null);
        pauseButton.setVisible(false);
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 切换关闭暂停按钮，切换为播放按钮
                enablePlayButtonDisablePauseButton();

                // 暂停歌曲
                musicPlayer.pauseSong();
            }
        });
        playbackBtns.add(pauseButton);

        // 下一首按钮
        JButton nextButton = new JButton(loadImage("src/assets/icon/next.png"));
        nextButton.setBorderPainted(false);
        nextButton.setBackground(null);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 转到下一首歌曲
                musicPlayer.nextSong();
            }
        });
        playbackBtns.add(nextButton);

        add(playbackBtns);

        // // 添加"加载歌曲"项到歌曲菜单
        // JButton loadSong = new JButton("Load");
        // //修改字体
        // loadSong.setFont(new Font("华文行楷", Font.PLAIN, 24));

        // 加载按钮
        JButton loadSong = new JButton(loadImage("src/assets/icon/load.jpg"));
        // 设置背景透明
        loadSong.setBorderPainted(false);
        loadSong.setBackground(null);
        loadSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 返回一个整数，告诉我们用户做了什么
                int result = jFileChooser.showOpenDialog(GUI.this);
                File selectedFile = jFileChooser.getSelectedFile();

                // 这意味着我们还要检查用户是否按下了"打开"按钮
                if (result == JFileChooser.APPROVE_OPTION && selectedFile != null) {
                    // 根据所选文件创建一个歌曲对象
                    Song song = new Song(selectedFile.getPath());

                    // 在音乐播放器中加载歌曲
                    musicPlayer.loadSong(song);

                    // 更新歌曲标题和歌手
                    updateSongTitleAndArtist(song);

                    // 更新播放进度滑块
                    updatePlaybackSlider(song);

                    // 切换为暂停按钮，切换关闭播放按钮
                    enablePauseButtonDisablePlayButton();
                }
            }
        });
        playbackBtns.add(loadSong);

                // JButton linkButton = new JButton("MV");
                // linkButton.addActionListener(new ActionListener() {
                //     @Override
                //     public void actionPerformed(ActionEvent e) {
                //         // String songTitle = songTitle;
                //         // String mvFilePath = "path/to/mv/" + songTitle + ".mp4";
                //         String mvFilePath = "src/assets/icon/Supernova.mv";
                //         try {
                //             Desktop.getDesktop().open(new File(mvFilePath));
                //         } catch (IOException ex) {
                //             ex.printStackTrace();
                //         }
                //     }
                // });
                // playbackBtns.add(linkButton);
        //mp4按钮 打开电脑上的Media Player软件播放mv
        JButton linkButton = new JButton("mv");
        linkButton.setFont(new Font("comic sans ms", Font.BOLD, 20));
        linkButton.setBorderPainted(false);
        linkButton.setBackground(null);
        linkButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // String songTitle = songTitle;
                    // String mvFilePath = "path/to/mv/" + songTitle + ".mp4";
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("D:\\Code\\java\\musicplayer\\src\\assets\\mv"));
                    fileChooser.setDialogTitle("Select MV File");
                    int result = fileChooser.showOpenDialog(GUI.this);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        String mvFilePath = selectedFile.getAbsolutePath();
                        try {
                            Desktop.getDesktop().open(new File(mvFilePath));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                } // Add this closing brace
            });
        playbackBtns.add(linkButton);

    }

    // // 静音按键
    // JButton muteButton = new JButton(loadImage("src/assets/icon/mute.png"));
    // nextButton.setBorderPainted(false);
    // nextButton.setBackground(null);
    // nextButton.addActionListener(new ActionListener() {
    // @Override
    // public void actionPerformed(ActionEvent e) {
    // try {
    // volume.muteVolume();
    // } catch (Exception ex) {
    // // Handle the exception here
    // ex.printStackTrace();
    // }
    // }
    // });
    // playbackBtns.add(muteButton);
    // add(playbackBtns);

    // }

    // //静音按键
    // JButton muteButton = new JButton(loadImage("src/assets/icon/mute.png"));
    // nextButton.setBorderPainted(false);
    // nextButton.setBackground(null);
    // nextButton.addActionListener(new ActionListener() {
    // @Override
    // public void actionPerformed(ActionEvent e) {
    // volume.muteVolume();
    // }
    // });
    // playbackBtns.add(muteButton);
    // add(playbackBtns);

    // //mv链接按钮
    // JButton linkButton = new JButton("MV");
    // linkButton.addActionListener(new ActionListener() {
    // @Override
    // public void actionPerformed(ActionEvent e) {
    // String songTitle = songTitle.getText();
    // String mvFilePath = "path/to/mv/" + songTitle + ".mp4";
    // try {
    // Desktop.getDesktop().open(new File(mvFilePath));
    // } catch (IOException ex) {
    // ex.printStackTrace();
    // }
    // }
    // });

    // 用于从音乐播放器类更新滑块
    public void setPlaybackSliderValue(int frame) {
        playbackSlider.setValue(frame);
    }

    public void updateSongTitleAndArtist(Song song) {
        songTitle.setText(song.getSongTitle());
        songArtist.setText(song.getSongArtist());
    }

    public void updatePlaybackSlider(Song song) {
        // 更新滑块的最大值
        playbackSlider.setMaximum(song.getMp3File().getFrameCount());

        // 创建歌曲长度标签
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();


        //获取当前播放的时间
        
        // 开始为00:00
        JLabel labelBeginning = new JLabel("00:00");
        labelBeginning.setFont(new Font("Dialog", Font.BOLD, 18));
        labelBeginning.setForeground(TEXT_COLOR);
        
        // // 获取当前播放的时间并将00：00更新
        // int currentFrame = MusicPlay.getCurrentTimeInMilli();
        // int seconds = ;
        // int minutes = seconds / 60;
        // seconds = seconds % 60;
        // String currentTime = String.format("%02d:%02d", minutes, seconds);
        // labelBeginning.setText(currentTime);
        // 滑轨标记也跟着变
        // 滑轨标记按钮随播放时间同步变化
        // 00：00也改为滑轨相应时间

        // 结束时间根据歌曲而变化
        JLabel labelEnd = new JLabel(song.getSongLength());
        labelEnd.setFont(new Font("Dialog", Font.BOLD, 18));
        labelEnd.setForeground(TEXT_COLOR);

        labelTable.put(0, labelBeginning);
        labelTable.put(song.getMp3File().getFrameCount(), labelEnd);

        playbackSlider.setLabelTable(labelTable);
        playbackSlider.setPaintLabels(true);
    }

    public void enablePauseButtonDisablePlayButton() {
        // 从playbackBtns面板中获取对播放按钮的引用
        JButton playButton = (JButton) playbackBtns.getComponent(1);
        JButton pauseButton = (JButton) playbackBtns.getComponent(2);

        // 关闭播放按钮
        playButton.setVisible(false);
        playButton.setEnabled(false);

        // 打开暂停按钮
        pauseButton.setVisible(true);
        pauseButton.setEnabled(true);
    }

    public void enablePlayButtonDisablePauseButton() {
        // 从playbackBtns面板中获取对播放按钮的引用
        JButton playButton = (JButton) playbackBtns.getComponent(1);
        JButton pauseButton = (JButton) playbackBtns.getComponent(2);

        // 打开播放按钮
        playButton.setVisible(true);
        playButton.setEnabled(true);

        // 关闭暂停按钮
        pauseButton.setVisible(false);
        pauseButton.setEnabled(false);
    }

    // private ImageIcon loadImage(String imagePath) {
    // try {
    // // 从给定路径读取图像文件
    // BufferedImage image = ImageIO.read(new File(imagePath));

    // // 返回一个图像图标，以便我们的组件可以渲染图像
    // return new ImageIcon(image);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return null;
    // }

    private ImageIcon loadImage(String imagePath) {
        try {
            // 直接返回 ImageIcon 实例
            return new ImageIcon(imagePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 找不到资源
        return null;
    }

}
