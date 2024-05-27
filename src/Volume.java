import javax.sound.sampled.*;
import java.io.File;

public class Volume {
    private Clip clip;

    public Volume(String audioFilePath) throws Exception {
        // 加载音频文件
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(audioFilePath));
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
    }

    public void muteVolume() throws Exception {
        // 获取音量控制
        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        // 设置音量为最小值以实现静音
        volumeControl.setValue(volumeControl.getMinimum());
    }
}