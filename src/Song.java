import com.mpatric.mp3agic.Mp3File;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import java.io.File;

// 描述歌曲的类
public class Song {
    private String songTitle; // 歌曲标题
    private String songArtist; // 歌曲艺术家
    private String songLength; // 歌曲长度
    private String filePath; // 文件路径
    private Mp3File mp3File; // Mp3文件对象
    private double frameRatePerMilliseconds; // 每毫秒的帧率

    public Song(String filePath){
        this.filePath = filePath;
        try{
            mp3File = new Mp3File(filePath);
            frameRatePerMilliseconds = (double) mp3File.getFrameCount() / mp3File.getLengthInMilliseconds();
            songLength = convertToSongLengthFormat();

            // 使用jaudiotagger库创建AudioFile对象以读取mp3文件的信息
            AudioFile audioFile = AudioFileIO.read(new File(filePath));

            // 读取音频文件的元数据
            Tag tag =  audioFile.getTag();
            if(tag != null){
                songTitle = tag.getFirst(FieldKey.TITLE);
                songArtist = tag.getFirst(FieldKey.ARTIST);
            }else{
                // 无法读取mp3文件的元数据
                songTitle = "N/A";
                songArtist = "N/A";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private String convertToSongLengthFormat(){
        long minutes = mp3File.getLengthInSeconds() / 60;
        long seconds = mp3File.getLengthInSeconds() % 60;
        String formattedTime = String.format("%02d:%02d", minutes, seconds);

        return formattedTime;
    }

    // 获取器
    public String getSongTitle() {
        return songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public String getSongLength() {
        return songLength;
    }

    public String getFilePath() {
        return filePath;
    }

    public Mp3File getMp3File(){return mp3File;}
    public double getFrameRatePerMilliseconds(){return frameRatePerMilliseconds;}
}
