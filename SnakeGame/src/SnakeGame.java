
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SnakeGame extends JFrame{

    private MainMenuPanel mainMenuPanel;    //主目錄視窗
    private GameMenuPanel gameMenuPanel;    //遊戲目錄視窗

    public SnakeGame() {
        super("貪吃蛇遊戲");
        //新增主目錄視窗
        mainMenuPanel = new MainMenuPanel();
        add(mainMenuPanel);

        //初始化遊戲視窗
        gameMenuPanel = new GameMenuPanel();

        //宣告按鈕事件並給予每個JPanel
        ButtonHandler buttonHandler = new ButtonHandler();
        mainMenuPanel.setButtonListener(buttonHandler);
        gameMenuPanel.setButtonListener(buttonHandler);
        //JFrame顯示設定
        setVisible(true);
        setSize(480, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //主程式進入點
    public static void main(String[] args) {
        new SnakeGame();
    }

    //按鈕事件類別
    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "進入遊戲":
                    setSize(720, 600);
                    remove(mainMenuPanel);
                    add(gameMenuPanel);
                    gameMenuPanel.gameBgmPlayState(true);
                    break;
                case "排行榜":
                    try {
                        BufferedReader br = new BufferedReader(new InputStreamReader((getClass().getResource("leaderboard.txt")).openStream(), "utf-8"));
                        String line;
                        StringBuilder message = new StringBuilder();
                        while ((line = br.readLine()) != null) {
                            String scores[] = line.split("\t");
                            message.append(scores[0]);
                            message.append("               ");
                            message.append(scores[1]);
                            message.append("               ");
                            message.append(scores[2] + "分");
                            message.append("\n");
                        }
                        JOptionPane.showMessageDialog(SnakeGame.this, message, "排行榜", JOptionPane.INFORMATION_MESSAGE);
                        br.close();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    break;
                case "結束遊戲":
                    System.exit(0);
                    break;
                case "返回":
                    setSize(480, 600);
                    remove(gameMenuPanel);
                    add(mainMenuPanel);
                    gameMenuPanel.gameBgmPlayState(false);
                    break;
            }
        }
    }

}
