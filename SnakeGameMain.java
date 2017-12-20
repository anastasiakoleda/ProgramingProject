package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;

public class SnakeGameMain extends JPanel implements ActionListener {

    public static JFrame jFrame;
    public static final int SCALE = 32; //Size of square
    public static final int WIDTH = 20; //Number of squares in width
    public static final int HEIGHT = 20; //Number of squares in height
    public static int speed = 10;

    Snake s = new Snake(5,6,5,5);
    Apple apple = new Apple(Math.abs ((int) (Math.random()*SnakeGameMain.WIDTH - 1)),Math.abs ((int) (Math.random()*SnakeGameMain.HEIGHT - 1)));
    Timer timer = new Timer(1000/speed,this); //speed of the snake

    public SnakeGameMain() {
        timer.start();

        addKeyListener(new KeyBoard()); //Adding keyboard listener
        setFocusable(true);

    }

    public void paint(Graphics g) {
        //Drawing the field
       g.setColor(Color.pink);
       g.fillRect(0,0,WIDTH*SCALE,HEIGHT*SCALE);

       //Drawing vertical lines
        for (int x = 0; x < WIDTH*SCALE; x+=SCALE){
         g.setColor(Color.pink);
         g.drawLine(x,0,x,HEIGHT*SCALE);
       }

       //Drawing horizontal lines
        for (int y = 0; y < HEIGHT*SCALE; y+=SCALE){
            g.setColor(Color.pink);
            g.drawLine(0,y,WIDTH*SCALE,y);
        }

        //Drawing an apple
        g.setColor(Color.red);
        g.fillOval(apple.posX*SCALE+4, apple.posY*SCALE+4, SCALE-8,SCALE-8);

        //Drawing snake
        for (int l = 1; l < s.length; l++){
            g.setColor(Color.magenta);
            g.fillRect(s.sX[l]*SCALE+3, s.sY[l]*SCALE+3, SCALE-6, SCALE-6);
            g.setColor(Color.white);
            g.fillRect(s.sX[0]*SCALE+3, s.sY[0]*SCALE+3, SCALE-6, SCALE-6);
        }

    }

    public static void main(String[] args) {

        jFrame = new JFrame("Snake game Anastasia Koleda IVSB11"); //Title of the window
        jFrame.setSize(WIDTH*SCALE+6,HEIGHT*SCALE+28); //Window size
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);// Exit if window closed
        jFrame.setResizable(false); //Can't resize the window
        jFrame.setLocationRelativeTo(null); //Window location
        jFrame.add(new SnakeGameMain());
        jFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        s.move();
        if ((s.sX[0] == apple.posX)&&(s.sY[0] == apple.posY)) {
            apple.setRandomPosition(); //Generate new position for apple
            s.length++; //Increase snakes length
        }
        for (int l = 1; l < s.length; l++){
           if ((s.sX[l] == apple.posX)&&(s.sY[l] == apple.posY)){
               apple.setRandomPosition();  // If apple appear inside the snake then change apple's posit
            }
            if ((s.sX[0] == s.sX[l])&&(s.sY[0] == s.sY[l])){ //If the snake eats itself game will restart
               timer.stop();
               JOptionPane.showMessageDialog(null,"You lose! Start again?");
               jFrame.setVisible(false);
               s.length = 2;
               jFrame.setVisible(true);
               timer.start();
            }
        }

        repaint();

    }

    public class KeyBoard extends KeyAdapter{
        public void keyPressed (KeyEvent event){
            int key = event.getKeyCode();

            if((key == KeyEvent.VK_UP) && (s.direction != 2)) s.direction = 0;
            if((key == KeyEvent.VK_DOWN) && (s.direction != 0)) s.direction = 2;
            if((key == KeyEvent.VK_RIGHT) && (s.direction != 3)) s.direction = 1;
            if((key == KeyEvent.VK_LEFT) && (s.direction != 1)) s.direction = 3;

        }
    }
}
