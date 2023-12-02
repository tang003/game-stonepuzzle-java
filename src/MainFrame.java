import sun.applet.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class MainFrame  extends JFrame  implements KeyListener {
    int[][] data={
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,0}
    };
    int[][] win ={
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,0}
    };
    int count=0;
//    0号坐标位置
    int row;
    int colum;
    public MainFrame(){
//        this:1-窗体对象  2-KeyListener实现类对象
        this.addKeyListener(this);


//        初始化界面
        initFrame();
        initDate();
//          绘制界面
        paintView();

        //        设置窗体可见
        setVisible(true);
    }

//    初始化数据(打乱二维数组)
    public void initDate(){
        Random r = new Random();
        for(int i=0;i<data.length;i++){
            for (int j = 0; j < data[i].length; j++) {
                int randomX = r.nextInt(4);
                int randomY = r.nextInt(4);
                int temp = data[i][j];
                data[i][j] = data[randomX][randomY];
                data[randomX][randomY] =temp;
            }
        }
        for(int i=0;i<data.length;i++){
            for (int j = 0; j < data[i].length; j++) {
                if(data[i][j]==0){
                    row =i;
                    colum = j;
                }

            }
        }
//        System.out.println(row);
//        System.out.println(colum);
    }


//    窗体初始化
    public void initFrame(){
        setSize(514,595);
//        设置窗体关闭模式
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("石头迷阵单机版V1.0");
//        设置窗体永远在最上层
        setAlwaysOnTop(true);
//        窗体居中
        setLocationRelativeTo(null);
//        取消默认布局
        setLayout(null);



    }

    //    绘制界面
    public void paintView(){

        super.getContentPane().removeAll();
        if(victory()){
            JLabel winLabel = new JLabel(new ImageIcon("src\\image\\win.png"));
            winLabel.setBounds(124,230,266,88);
            getContentPane().add(winLabel);
        }

        JLabel scoreLabel = new JLabel("步数为"+count);
        scoreLabel.setBounds(50,20,100,20);
        getContentPane().add(scoreLabel);

        JButton btn = new JButton("重新加载游戏");
        btn.setBounds(350,20,100,20);
        getContentPane().add(btn);
        btn.setFocusable(false);
        btn.addActionListener(e -> {
            count=0;
            initDate();
            paintView();
        });

        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                JLabel  imageLabel= new JLabel(new ImageIcon("src\\image\\"+data[i][j]+".png"));
                imageLabel.setBounds(50+100*j,90+100*i,100,100);
               getContentPane().add(imageLabel);
            }
        }


        JLabel background = new JLabel(new ImageIcon("src\\image\\background.png"));
        background.setBounds(26,30,450,484);
        getContentPane().add(background);


        super.getContentPane().repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
//处理移动业务
    private void move(int keyCode){
        if(victory()){
            return;
        }
        if(keyCode == 37 ){
            if(colum==3) return;
//            空白和右侧数据交换  <-
//            data[row][colum] data[row][colum+1]
            int temp =  data[row][colum];
            data[row][colum]=data[row][colum+1];
            data[row][colum+1] = temp;
            colum ++;
            count++;

        }else if(keyCode==38){
            if(row ==3) return;
            //    空白和下侧数据交换  ↑
//            data[row][colum] data[row+1][colum]
            int temp =  data[row][colum];
            data[row][colum]=data[row+1][colum];
            data[row+1][colum] = temp;
            row++;
            count++;

        }else if(keyCode == 39){
            if(colum ==0) return;
            //    空白和左侧数据交换    ->
//            data[row][colum] data[row][colum-1]
            int temp =  data[row][colum];
            data[row][colum]=data[row][colum-1];
            data[row][colum-1] = temp;
            colum--;
            count++;

        }else if(keyCode==40){
            if(row==0) return;
            //    空白和上侧数据交换  ↓
//            data[row][colum] data[row-1][colum]
            int temp =  data[row][colum];
            data[row][colum]=data[row-1][colum];
            data[row-1][colum] = temp;
            row--;
            count++;

        } else if (keyCode == 90) {
//            触发作弊器
             data=new int[][]{
                    {1,2,3,4},
                    {5,6,7,8},
                    {9,10,11,12},
                    {13,14,15,0}
            };
        }

    }

//    判断胜利
    public boolean victory(){


        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if(data[i][j] != win[i][j]){
                    return false;
                }
            }
        }
        return true;

    }



    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        move(keyCode);
        paintView();

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
