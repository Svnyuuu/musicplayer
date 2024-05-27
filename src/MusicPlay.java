import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import java.io.*;
import java.util.ArrayList;

public class MusicPlay extends PlaybackListener {
    // 用于更同步地更新isPaused的对象
    private static final Object playSignal = new Object();

    // 需要引用以便在此类中更新GUI
    private GUI musicPlayerGUI;

    // 我们需要一种方法来存储歌曲的详细信息，因此我们将创建一个歌曲类
    private Song currentSong;
    public Song getCurrentSong(){
        return currentSong;
    }

    private ArrayList<Song> playlist;

    // 我们需要跟踪播放列表中的索引
    private int currentPlaylistIndex;

    // 使用JLayer库创建一个AdvancedPlayer对象，该对象将处理音乐播放
    private AdvancedPlayer advancedPlayer;

    // 暂停的布尔标志，用于指示播放器是否已暂停
    private boolean isPaused;

    // 用于指示歌曲是否已完成的布尔标志
    private boolean songFinished;

    private boolean pressedNext, pressedPrev;


    // 存储播放完成时的最后一帧（用于暂停和恢复）
    private int currentFrame;
    public void setCurrentFrame(int frame){
        currentFrame = frame;
    }

    // 跟踪自播放歌曲以来经过的毫秒数（用于更新滑块）
    private int currentTimeInMilli;
    private static int curtime;
    public void setCurrentTimeInMilli(int timeInMilli){
        currentTimeInMilli = timeInMilli;
        curtime = timeInMilli;
    }
   

    public static int getCurrentTimeInMilli(){
        return curtime;
    }
    // 构造函数
    public MusicPlay(GUI musicPlayerGUI){
        this.musicPlayerGUI = musicPlayerGUI;
    }

    public void loadSong(Song song){
        currentSong = song;
        playlist = null;

        // 如果可能，停止歌曲
        if(!songFinished)
            stopSong();

        // 如果当前歌曲不为空，则播放当前歌曲
        if(currentSong != null){
            // 重置帧
            currentFrame = 0;

            // 重置当前毫秒时间
            currentTimeInMilli = 0;

            // 更新GUI
            musicPlayerGUI.setPlaybackSliderValue(0);

            playCurrentSong();
        }
    }

    public void loadPlaylist(File playlistFile){
        playlist = new ArrayList<>();

        // 将文本文件中的路径存储到播放列表数组列表中
        try{
            FileReader fileReader = new FileReader(playlistFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // 从文本文件中读取每一行，并将文本存储到songPath变量中
            String songPath;
            while((songPath = bufferedReader.readLine()) != null){
                // 基于歌曲路径创建歌曲对象
                Song song = new Song(songPath);

                // 添加到播放列表数组列表中
                playlist.add(song);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        if(playlist.size() > 0){
            // 重置播放滑块
            musicPlayerGUI.setPlaybackSliderValue(0);
            currentTimeInMilli = 0;

            // 更新当前歌曲为播放列表中的第一首歌曲
            currentSong = playlist.get(0);

            // 从开始帧开始
            currentFrame = 0;

            // 更新GUI
            musicPlayerGUI.enablePauseButtonDisablePlayButton();
            musicPlayerGUI.updateSongTitleAndArtist(currentSong);
            musicPlayerGUI.updatePlaybackSlider(currentSong);

            // 播放歌曲
            playCurrentSong();
        }
    }

    public void pauseSong(){
        if(advancedPlayer != null){
            // 更新isPaused标志
            isPaused = true;

            // 然后停止播放器
            stopSong();
        }
    }

    public void stopSong(){
        if(advancedPlayer != null){
            advancedPlayer.stop();
            advancedPlayer.close();
            advancedPlayer = null;
        }
    }

    public void nextSong(){
        // 如果没有播放列表，则无需切换到下一首歌曲
        if(playlist == null) return;

        // 检查是否已达到播放列表的末尾，如果是，则不执行任何操作
        if(currentPlaylistIndex + 1 > playlist.size() - 1) return;

        pressedNext = true;

        // 如果可能，停止歌曲
        if(!songFinished)
            stopSong();

        // 增加当前播放列表索引
        currentPlaylistIndex++;

        // 更新当前歌曲
        currentSong = playlist.get(currentPlaylistIndex);

        // 重置帧
        currentFrame = 0;

        // 重置当前毫秒时间
        currentTimeInMilli = 0;

        // 更新GUI
        musicPlayerGUI.enablePauseButtonDisablePlayButton();
        musicPlayerGUI.updateSongTitleAndArtist(currentSong);
        musicPlayerGUI.updatePlaybackSlider(currentSong);

        // 播放歌曲
        playCurrentSong();
    }

    public void prevSong(){
        // 如果没有播放列表，则无需切换到上一首歌曲
        if(playlist == null) return;

        // 检查是否可以切换到上一首歌曲
        if(currentPlaylistIndex - 1 < 0) return;

        pressedPrev = true;

        // 如果可能，停止歌曲
        if(!songFinished)
            stopSong();

        // 减少当前播放列表索引
        currentPlaylistIndex--;

        // 更新当前歌曲
        currentSong = playlist.get(currentPlaylistIndex);

        // 重置帧
        currentFrame = 0;

        // 重置当前毫秒时间
        currentTimeInMilli = 0;

        // 更新GUI
        musicPlayerGUI.enablePauseButtonDisablePlayButton();
        musicPlayerGUI.updateSongTitleAndArtist(currentSong);
        musicPlayerGUI.updatePlaybackSlider(currentSong);

        // 播放歌曲
        playCurrentSong();
    }

    public void playCurrentSong(){
        if(currentSong == null) return;

        try{
            // 读取mp3音频数据
            FileInputStream fileInputStream = new FileInputStream(currentSong.getFilePath());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

            // 创建一个新的AdvancedPlayer对象
            advancedPlayer = new AdvancedPlayer(bufferedInputStream);
            advancedPlayer.setPlayBackListener(this);

            // 开始播放音乐
            startMusicThread();

            // 开始播放滑块线程
            startPlaybackSliderThread();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // 创建一个处理播放音乐的线程
    private void startMusicThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    if(isPaused){
                        synchronized(playSignal){
                            // 更新标志
                            isPaused = false;

                            // 通知其他线程继续（确保isPaused正确更新为false）
                            playSignal.notify();
                        }

                        // 从上次帧继续播放音乐
                        advancedPlayer.play(currentFrame, Integer.MAX_VALUE);
                    }else{
                        // 从头开始播放音乐
                        advancedPlayer.play();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // 创建一个处理更新滑块的线程
    private void startPlaybackSliderThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(isPaused){
                    try{
                        // 等待其他线程通知继续
                        // 确保isPaused布尔标志在继续之前更新为false
                        synchronized(playSignal){
                            playSignal.wait();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }

                while(!isPaused && !songFinished && !pressedNext && !pressedPrev){
                    try{
                        // 增加当前毫秒时间
                        currentTimeInMilli++;

                        // 计算帧值
                        int calculatedFrame = (int) ((double) currentTimeInMilli * 2.08 * currentSong.getFrameRatePerMilliseconds());

                        // 更新GUI
                        musicPlayerGUI.setPlaybackSliderValue(calculatedFrame);

                        // 使用Thread.sleep模拟1毫秒
                        Thread.sleep(1);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void playbackStarted(PlaybackEvent evt) {
        // 歌曲开始播放时调用此方法
        System.out.println("播放开始");
        songFinished = false;
        pressedNext = false;
        pressedPrev = false;
    }

    @Override
    public void playbackFinished(PlaybackEvent evt) {
        // 歌曲完成播放或播放器关闭时调用此方法
        System.out.println("播放完成");
        if(isPaused){
            currentFrame += (int) ((double) evt.getFrame() * currentSong.getFrameRatePerMilliseconds());
        }else{
            // 如果用户按下了下一首或上一首，我们不需要执行其余的代码
            if(pressedNext || pressedPrev) return;

            // 当歌曲结束时
            songFinished = true;

            if(playlist == null){
                // 更新GUI
                musicPlayerGUI.enablePlayButtonDisablePauseButton();
            }else{
                // 播放列表中的最后一首歌曲
                if(currentPlaylistIndex == playlist.size() - 1){
                    // 更新GUI
                    musicPlayerGUI.enablePlayButtonDisablePauseButton();
                }else{
                    // 切换到播放列表中的下一首歌曲
                    nextSong();
                }
            }
        }
    }
}
