package bsu.rfe.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Field extends JPanel {
    //флаг приостановленности движения
    private boolean paused;
    //флаг приостановленности движения красных шаров
    private boolean stopRed;

    //динамический список скачущих мячей
    private ArrayList<BouncingBall> balls = new ArrayList<BouncingBall>(10);
    public ArrayList<BouncingBall> getBalls() { return balls; }
    public void setBalls(ArrayList<BouncingBall> balls) { this.balls = balls; }

    private long startPressedTime;

    private int mouseX;
    private int mouseY;

    // Класс таймер отвечает за регулярную генерацию событий ActionEvent
    // При создании его экземпляра используется анонимный класс,
    // реализующий интерфейс ActionListener
    private Timer repaintTimer = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            // Задача обработчика события ActionEvent - перерисовка окна
            repaint();
        }
    });

    //конструктор класса BouncingBall
    public Field() {
        // Установить цвет заднего фона белым
        setBackground(Color.WHITE);
        // Запустить таймер
        repaintTimer.start();

        //c1
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPressedTime = System.nanoTime();
                mouseX = e.getX();
                mouseY = e.getY();
                pause();

            }

            public void mouseReleased(MouseEvent e) {
                resume();
                for (BouncingBall ball : balls) {
                    int ballX = (int) ball.getX();
                    int ballY = (int) ball.getY();
                    double distance = Math.pow(Math.pow((ballX - mouseX), 2) + Math.pow((ballY - mouseY), 2), 0.5);
                    if (distance <= 1.5 * ball.getRadius()) {
                        ball.setDirection(e.getX(), e.getY(), (int) ((System.nanoTime() - startPressedTime) / Math.pow(10, 7)));
                    }
                }
            }
        });
    }

    // Унаследованный от JPanel метод перерисовки компонента
    public void paintComponent(Graphics g) {
        // Вызвать версию метода, унаследованную от предка
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
        // Последовательно запросить прорисовку от всех мячей из списка
        for (BouncingBall ball : balls) {
            ball.paint(canvas);
        }
        canvas.drawString(String.valueOf(balls.size()), WIDTH, HEIGHT);
    }

