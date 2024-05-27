import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class MusicPlaylistDialog extends JDialog {
    private GUI musicPlayerGUI;

    // 存储所有要写入txt文件的路径（当我们加载播放列表时）
    private ArrayList<String> songPaths;

    public MusicPlaylistDialog(GUI musicPlayerGUI){
        this.musicPlayerGUI = musicPlayerGUI;
        songPaths = new ArrayList<>();

        // 配置对话框
        setTitle("创建播放列表");
        setSize(400, 400);
        setResizable(false);
        getContentPane().setBackground(GUI.FRAME_COLOR);
        setLayout(null);
        setModal(true); // 此属性使对话框必须关闭以获得焦点
        setLocationRelativeTo(musicPlayerGUI);

        addDialogComponents();
    }

    private void addDialogComponents(){
        // 容器用于保存每个歌曲路径
        JPanel songContainer = new JPanel();
        songContainer.setLayout(new BoxLayout(songContainer, BoxLayout.Y_AXIS));
        songContainer.setBounds((int)(getWidth() * 0.025), 10, (int)(getWidth() * 0.90), (int) (getHeight() * 0.75));
        add(songContainer);

        // 添加歌曲按钮
        JButton addSongButton = new JButton("添加");
        addSongButton.setBounds(60, (int) (getHeight() * 0.80), 100, 25);
        addSongButton.setFont(new Font("Dialog", Font.BOLD, 14));
        addSongButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 打开文件浏览器
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileFilter(new FileNameExtensionFilter("MP3", "mp3"));
                jFileChooser.setCurrentDirectory(new File("src/assets"));
                int result = jFileChooser.showOpenDialog(MusicPlaylistDialog.this);

                File selectedFile = jFileChooser.getSelectedFile();
                if(result == JFileChooser.APPROVE_OPTION && selectedFile != null){
                    JLabel filePathLabel = new JLabel(selectedFile.getPath());
                    filePathLabel.setFont(new Font("Dialog", Font.BOLD, 12));
                    filePathLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                    // 添加到列表
                    songPaths.add(filePathLabel.getText());

                    // 添加到容器
                    songContainer.add(filePathLabel);

                    // 刷新对话框以显示新添加的JLabel
                    songContainer.revalidate();
                }
            }
        });
        add(addSongButton);

        // 保存播放列表按钮
        JButton savePlaylistButton = new JButton("保存");
        savePlaylistButton.setBounds(215, (int) (getHeight() * 0.80), 100, 25);
        savePlaylistButton.setFont(new Font("Dialog", Font.BOLD, 14));
        savePlaylistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    JFileChooser jFileChooser = new JFileChooser();
                    jFileChooser.setCurrentDirectory(new File("src/assets"));
                    int result = jFileChooser.showSaveDialog(MusicPlaylistDialog.this);

                    if(result == JFileChooser.APPROVE_OPTION){
                        // 使用getSelectedFile()获取要保存的文件的引用
                        File selectedFile = jFileChooser.getSelectedFile();

                        // 如果文件没有".txt"文件扩展名，则转换为.txt文件
                        // 这将检查文件是否没有".txt"文件扩展名
                        if(!selectedFile.getName().substring(selectedFile.getName().length() - 4).equalsIgnoreCase(".txt")){
                            selectedFile = new File(selectedFile.getAbsoluteFile() + ".txt");
                        }

                        // 在指定目录创建新文件
                        selectedFile.createNewFile();

                        // 现在我们将所有的歌曲路径写入这个文件
                        FileWriter fileWriter = new FileWriter(selectedFile);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                        // 遍历我们的歌曲路径列表，并将每个字符串写入文件
                        // 每首歌曲将在自己的行中写入
                        for(String songPath : songPaths){
                            bufferedWriter.write(songPath + "\n");
                        }
                        bufferedWriter.close();

                        // 显示成功对话框
                        JOptionPane.showMessageDialog(MusicPlaylistDialog.this, "播放列表创建成功！");

                        // 关闭此对话框
                        MusicPlaylistDialog.this.dispose();
                    }
                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }
        });
        add(savePlaylistButton);
    }
}
