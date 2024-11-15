import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private class Tile
    {
        int x;
        int y;

        Tile(int x, int y){
            this.x = x;
            this.y = y;


        }
    }
    int boardwidth;
    int boardheight;
    int titleSize =5;

    //Snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    //Food
    Tile food;
    Random random;

    //game Logic
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;


    SnakeGame (int boardwidth, int boardheight) {
        this.boardwidth = boardwidth;
        this.boardheight = boardheight;
        setPreferredSize(new Dimension(this.boardwidth,  this.boardheight));
        setBackground(Color.black); //set the background of game 
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile (5, 5);
        snakeBody = new ArrayList<Tile>();


        food = new  Tile(10, 10);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;

        gameLoop = new Timer(100, this);
        gameLoop.start();

        }

    public void paintComponent (Graphics g) {
       super.paintComponent(g);
       draw(g);
    }
     public void draw (Graphics g) {
        //Grid
       // for (int i=0; i < boardwidth/titleSize; i++) {
            //(x1, y1, x2, y2)
        //    g.drawLine(i*titleSize, 0, i*titleSize, boardheight);
         //   g.drawLine(0, i*titleSize, boardwidth, i*titleSize);
       // }

        //Food
        g.setColor(Color.red); //food color
      //  g.fillRect(food.x*titleSize, food.y*titleSize, titleSize,titleSize);
        g.fill3DRect(food.x*titleSize, food.y*titleSize, titleSize,titleSize, true);

        //snake Head
        g.setColor (Color.green); //Snake color
      //  g.fillRect(snakeHead.x * titleSize, snakeHead.y * titleSize, titleSize, titleSize);
        g.fill3DRect(snakeHead.x * titleSize, snakeHead.y * titleSize, titleSize, titleSize, true);

        //snake Body
        for (int i=0; i<snakeBody.size(); i++) {
         Tile snakePart =snakeBody.get(i);
        // g.fillRect(snakePart.x * titleSize, snakePart.y * titleSize, titleSize, titleSize);
         g.fill3DRect(snakePart.x * titleSize, snakePart.y * titleSize, titleSize, titleSize, true);
        }

        //score
        g.setFont(new Font("Arial",Font.PLAIN, 16));
        if(gameOver)  {
         g.setColor(Color.red);
         g.drawString("Game Over:(" + String.valueOf(snakeBody.size()), titleSize - 16, titleSize);
        }
        else {
         g.drawString("Score:" + String.valueOf(snakeBody.size()), titleSize - 16, titleSize);
        }

     }

     public void placeFood() {
        food.x = random.nextInt(boardwidth/titleSize); //600/25 = 24
        food.y = random.nextInt(boardheight/titleSize);
     }
     
     public boolean collision (Tile tile1, Tile tile2) {
      return tile1.x == tile2.x && tile1.y == tile2.y;
     }


     public void move () {
      //eat food
      if(collision(snakeHead, food)) {
         snakeBody.add(new Tile(food.x, food.y));
         placeFood();
       }

       //Snake Body
       for (int i= snakeBody.size()-1; i>=0; i--) {
         Tile snakePart = snakeBody.get(i);
         if(i == 0) {
            snakePart.x = snakeHead.x;
            snakePart.y = snakeHead.y;
         }

         else {
            Tile prevSnakePart = snakeBody.get(i-1);
            snakePart.x = prevSnakePart.x;
            snakePart.y = prevSnakePart.y;
         }
       }

        //SnakeHead
        snakeHead.x += velocityX;
        snakeHead.y +=velocityY;

        //game over conditions 
        for (int i=0; i < snakeBody.size(); i++) {
         Tile snakePart = snakeBody.get(i);
         //collide with the snake
         if (collision(snakeHead, snakePart)) {
            gameOver = true;
         }
        }

        if (snakeHead.x*titleSize < 0 || snakeHead.x*titleSize > boardwidth ||
        snakeHead.y*titleSize < 0 || snakeHead.y *titleSize > boardheight) {
         gameOver = true;
        }

     }

     public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver) {
         gameLoop.stop();
        }

     }


     @Override
     public void keyPressed(KeyEvent e) {
         if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
         }
         else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
         }
         else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
         }
         else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
         }

         }

         

//do not need 
    @Override
    public void keyTyped(KeyEvent e) { }


    @Override
    public void keyReleased(KeyEvent e) { }
}
