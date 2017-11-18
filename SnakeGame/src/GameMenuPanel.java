
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class GameMenuPanel extends JPanel {

    private GameWindow gameWindow;
    private JToggleButton startGame;
    private JToggleButton restartGame;
    private JToggleButton pauseGame;
    private JToggleButton continueGame;
    private JButton back;
    private JLabel scoreLabel;
    private final int size = 24;
    private int score;
    private int offset = 5;
    private int nodeCount;
    private int minX, minY, maxX, maxY; // 邊界xy
    private boolean gameIsRunning;
    private Font font;
    private List<SnakeNode> snakeNodes;
    private List<SnakeNode> foods;
    private Timer timer;
    private Direction direction;
    private Queue<Direction> directions;

    public GameMenuPanel() {
        setLayout(null);
        font = new Font(Font.SERIF, Font.BOLD, 24);
        //初始化畫面元件
        startGame = new JToggleButton("開始遊戲");
        restartGame = new JToggleButton("重新開始");
        pauseGame = new JToggleButton("暫停遊戲");
        continueGame = new JToggleButton("繼續遊戲");
        back = new JButton("返回");
        startGame.setFont(font);
        startGame.setBounds(70, 100, 150, 50);
        restartGame.setFont(font);
        restartGame.setBounds(70, 175, 150, 50);
        restartGame.setSelected(true);
        pauseGame.setFont(font);
        pauseGame.setBounds(70, 250, 150, 50);
        continueGame.setFont(font);
        continueGame.setBounds(70, 325, 150, 50);
        continueGame.setSelected(true);
        back.setFont(font);
        back.setBounds(70, 400, 150, 50);
        scoreLabel = new JLabel("Score : 0");
        scoreLabel.setFont(font);
        scoreLabel.setBounds(70, 500, 150, 50);
        snakeNodes = new LinkedList<>();  //貪吃蛇身體 
        foods = new LinkedList<>();   //食物
        directions = new LinkedList<>();  //方向Queue
        gameIsRunning = false;
        //貪吃蛇遊戲活動範圍視窗
        gameWindow = new GameWindow();
        gameWindow.setBounds(300, 60, 370, 490);
        //加入到JPanel
        add(gameWindow);
        ButtonGroup bg1 = new ButtonGroup();
        bg1.add(startGame);
        bg1.add(restartGame);
        ButtonGroup bg2 = new ButtonGroup();
        bg2.add(pauseGame);
        bg2.add(continueGame);
        add(startGame);
        add(restartGame);
        add(pauseGame);
        add(continueGame);
        add(back);
        add(scoreLabel);
        //加入遊戲相關按鈕事件
        ButtonAboutGameHandler buttonAboutGameHandler = new ButtonAboutGameHandler();
        startGame.addActionListener(buttonAboutGameHandler);
        restartGame.addActionListener(buttonAboutGameHandler);
        continueGame.addActionListener(buttonAboutGameHandler);
        pauseGame.addActionListener(buttonAboutGameHandler);
        //加入鍵盤事件
        addKeyListener(new KeyboardHandler());
        //加入遊戲執行程序
        timer = new Timer();
        timer.schedule(new GameTask(), 0, 200);
    }

    private class GameWindow extends JPanel {

        public GameWindow() {
            setLayout(null);
            directions.add(Direction.RIGHT);
            setCurrentDirection();      //設定初始前進方向
            setSize(370, 490);
            minX = offset;
            minY = offset;
            maxX = offset + size * 14;
            maxY = offset + size * 19;
            setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.ORANGE));
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int i = 0; i < snakeNodes.size(); i++) {
                if (i % 2 == 0) {
                    g.setColor(Color.red);
                } else {
                    g.setColor(Color.BLACK);
                }
                snakeNodes.get(i).draw(g);
            }
            for (int i = 0; i < foods.size(); i++) {
                g.setColor(Color.red);
                foods.get(i).draw(g);
            }
        }

    }

    //鍵盤事件類別
    private class KeyboardHandler extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    if (direction == Direction.DOWN||direction == Direction.UP) {
                        return;   //不能180度轉彎,及跟現在前進方向一樣時return
                    }
                    directions.add(Direction.UP);
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction == Direction.UP||direction == Direction.DOWN) {
                        return;     //不能180度轉彎,及跟現在前進方向一樣時return
                    }
                    directions.add(Direction.DOWN);
                    break;
                case KeyEvent.VK_LEFT:
                    if (direction == Direction.RIGHT||direction == Direction.LEFT) {
                        return;  //不能180度轉彎,及跟現在前進方向一樣時return
                    }
                    directions.add(Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction == Direction.LEFT||direction == Direction.RIGHT) {
                        return;   //不能180度轉彎,及跟現在前進方向一樣時return
                    }
                    directions.add(Direction.RIGHT);
                    break;
            }
        }
    }

    //遊戲執行程序
    private class GameTask extends TimerTask {

        @Override
        public void run() {
            if (gameIsRunning) {
                setCurrentDirection();
                switch (direction) {
                    case UP:
                        if (isOutOfBound(direction)) {
                            return;
                        }
                        snakeTransfer(direction);
                        break;
                    case DOWN:
                        if (isOutOfBound(direction)) {
                            return;
                        }
                        snakeTransfer(direction);
                        break;
                    case RIGHT:
                        if (isOutOfBound(direction)) {
                            return;
                        }
                        snakeTransfer(direction);
                        break;
                    case LEFT:
                        if (isOutOfBound(direction)) {
                            return;
                        }
                        snakeTransfer(direction);
                        break;
                }
                repaint();
            }
        }

    }

    //遊戲相關按鈕事件類別
    private class ButtonAboutGameHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "開始遊戲":
                    requestFocus();//讓JFrame獲得焦點
                    startGame.setEnabled(false);
                    restartGame.setEnabled(true);
                    snakeNodes.add(new SnakeNode(new Point(48 + offset, 48 + offset), size));
                    snakeNodes.add(new SnakeNode(new Point(24 + offset, 48 + offset), size));
                    snakeNodes.add(new SnakeNode(new Point(0 + offset, 48 + offset), size));
                    nodeCount = 3;
                    gameIsRunning = true;
                    generateFoods(3);
                    break;
                case "重新開始":
                    reset();
                    break;
                case "暫停遊戲":
                    gameIsRunning = false;
                    break;
                case "繼續遊戲":
                    requestFocus();//讓JFrame獲得焦點
                    gameIsRunning = true;
                    break;
            }
            repaint();
        }

    }

    //生成食物
    public void generateFoods(int count) {
        Random random = new Random();
        if (nodeCount < 295) {
            for (int c = 0; c < count; c++) {
                Point point = new Point(offset + random.nextInt(15) * size, offset + random.nextInt(20) * size);
                for (int i = 0; i < snakeNodes.size(); i++) {
                    if (snakeNodes.get(i).isSame(point)) {
                        point = new Point(offset + random.nextInt(15) * size, offset + random.nextInt(20) * size);
                        i = -1;
                    }
                    if (i == snakeNodes.size()) {
                        break;
                    }
                }
                foods.add(new SnakeNode(point, size));
            }

        } else {
            if (foods.size() == 0) {
                Point point = new Point(offset + (random.nextInt(15) + 1) * size, offset + (random.nextInt(20) + 1) * size);
                for (int i = 0; i < snakeNodes.size(); i++) {
                    if (snakeNodes.get(i).isSame(point)) {
                        point = new Point(offset + (random.nextInt(15) + 1) * size, offset + (random.nextInt(20) + 1) * size);
                        i = -1;
                    }
                    if (i == snakeNodes.size()) {
                        break;
                    }
                }
                foods.add(new SnakeNode(point, size));
            }
        }
    }

    //判斷是否出界
    public boolean isOutOfBound(Direction d) {
        boolean outOfBound = false;
        switch (d) {
            case UP:
                if (snakeNodes.get(0).getPoint().y == minY) {
                    outOfBound = true;
                }
                break;
            case DOWN:
                if (snakeNodes.get(0).getPoint().y == maxY) {
                    outOfBound = true;
                }
                break;
            case LEFT:
                if (snakeNodes.get(0).getPoint().x == minX) {
                    outOfBound = true;
                }
                break;
            case RIGHT:
                if (snakeNodes.get(0).getPoint().x == maxX) {
                    outOfBound = true;
                }
                break;
        }
        if (outOfBound) {
            JOptionPane.showMessageDialog(GameMenuPanel.this, "撞牆了!笨蛋!!", "超出邊界", JOptionPane.ERROR_MESSAGE);
            gameLeaderboardUpdate();
            restartGame.setSelected(true);
            reset();
            return true;
        }
        return false;
    }

    //遊戲運行程序,並在過程中判斷是否有自撞情況
    public void snakeTransfer(Direction d) {
        int transferX = 0, transferY = 0;
        switch (d) {
            case UP:
                transferY -= size;
                break;
            case DOWN:
                transferY += size;
                break;
            case LEFT:
                transferX -= size;
                break;
            case RIGHT:
                transferX += size;
                break;
        }
        //以下判斷是否自撞
        for (int i = 0; i < snakeNodes.size() - 1; i++) {
            if (snakeNodes.get(i).isSame(new Point(snakeNodes.get(0).getPoint().x + transferX,
                    snakeNodes.get(0).getPoint().y + transferY))) {
                JOptionPane.showMessageDialog(GameMenuPanel.this, "Game Over", "自撞事故", JOptionPane.ERROR_MESSAGE);
                gameLeaderboardUpdate();
                restartGame.setSelected(true);
                reset();
                return;
            }
        }
        //如果沒有自撞情況就執行正常移動,並判斷是否有吃到食物
        snakeNodes.add(0, new SnakeNode(new Point(snakeNodes.get(0).getPoint().x + transferX,
                snakeNodes.get(0).getPoint().y + transferY), size));
        checkGetFood();
        return;
    }

    //檢查是否吃到食物
    public boolean checkGetFood() {
        for (int i = 0; i < foods.size(); i++) {
            if (snakeNodes.get(0).isSame(foods.get(i).getPoint())) {
                foods.remove(i);
                generateFoods(1);
                nodeCount++;
                score++;
                scoreLabel.setText("Score : " + score);
                return true;
            }
        }
        snakeNodes.remove(nodeCount);
        return false;
    }

    //reset
    public void reset() {
        restartGame.setEnabled(false);
        startGame.setEnabled(true);
        snakeNodes.clear();
        foods.clear();
        directions.clear();
        directions.add(Direction.RIGHT);
        setCurrentDirection();
        nodeCount = 0;
        score = 0;
        scoreLabel.setText("Score : ");
        gameIsRunning = false;
        repaint();
    }

    //設定現在前進方向
    public void setCurrentDirection() {
        if (directions.peek() != null) {
            direction = directions.poll();
        }
    }

    //按鈕對應事件處理
    public void setButtonListener(ActionListener actionListener) {
        back.addActionListener(actionListener);
    }

    //將遊玩紀錄比對舊的排行榜,如果能上前五就更新
    public void gameLeaderboardUpdate() {
        String name = JOptionPane.showInputDialog(GameMenuPanel.this, "請輸入姓名", "玩家姓名", JOptionPane.INFORMATION_MESSAGE);
        Score scores[] = new Score[6];
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader((getClass().getResource("leaderboard.txt")).openStream(), "utf-8"));
            String line;
            int index = 0;
            //將leaderboard.txt分別存入scores物件陣列中
            while ((line = br.readLine()) != null) {
                scores[index++] = new Score(line);
                if (index == 5) {
                    break;
                }
            }
            //比較是否能上榜
            scores[index] = new Score("", name, score);
            Arrays.sort(scores, new Comparator<Score>() {
                @Override
                public int compare(Score s1, Score s2) {
                    return s2.compareTo(s1);
                }
            });
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getClass().getResource("leaderboard.txt").getPath()),"utf-8"));
            if (bw != null) {
                for(int i=0;i<index;i++){
                    if(i==0){
                        scores[i].setRank("第一名");
                    }else if(i==1){
                        scores[i].setRank("第二名");
                    }else if(i==2){
                        scores[i].setRank("第三名");
                    }else if(i==3){
                        scores[i].setRank("第四名");
                    }else if(i==4){
                        scores[i].setRank("第五名");
                    }
                    bw.write(scores[i].getRank()+"\t");
                    bw.write(scores[i].getName()+"\t");
                    bw.write(String.valueOf(scores[i].getScore()));
                    bw.newLine();
                    bw.flush();
                }               
            }
            br.close();
            bw.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}
