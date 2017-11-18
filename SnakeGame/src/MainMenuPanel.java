
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainMenuPanel extends JPanel {

    private JLabel title;
    private JButton goGame;
    private JButton leaderboard;
    private JButton exitGame;
    private Font font;

    public MainMenuPanel() {
        setLayout(null);
        font = new Font(Font.SERIF, Font.BOLD, 24);
        //畫面上的元件設定
        title = new JLabel("貪吃蛇遊戲", SwingConstants.CENTER);
        goGame = new JButton("進入遊戲");
        leaderboard = new JButton("排行榜");
        exitGame = new JButton("結束遊戲");
        //以下設定字體 大小 顏色等等
        title.setFont(new Font(Font.SERIF, Font.BOLD, 48));
        title.setBounds(0, 0, 480, 100);
        title.setForeground(Color.BLUE);
        goGame.setFont(font);
        goGame.setBounds(165, 120, 150, 50);
        leaderboard.setFont(font);
        leaderboard.setBounds(165, 220, 150, 50);
        exitGame.setFont(font);
        exitGame.setBounds(165, 320, 150, 50);
        //加入到JPanel
        add(title);
        add(goGame);
        add(leaderboard);
        add(exitGame);
    }
    
    //按鈕對應事件處理
    public void setButtonListener(ActionListener actionListener) {
        goGame.addActionListener(actionListener);
        leaderboard.addActionListener(actionListener);
        exitGame.addActionListener(actionListener);
    }
}
