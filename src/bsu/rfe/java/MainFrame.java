package bsu.rfe.java;

mport javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {
    // Константы, задающие размер окна приложения, если оно
    // не распахнуто на весь экран
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private JMenuItem startMenuItem;
    private JMenuItem resetMenuItem;
    private JMenuItem pauseMenuItem;
    private JMenuItem resumeMenuItem;

    // Поле, по которому прыгают мячи
    private Field field = new Field();

    // Конструктор главного окна приложения
    public MainFrame() {
        super("Программирование и синхронизация потоков");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        // Отцентрировать окно приложения на экране
        setLocation((kit.getScreenSize().width - WIDTH)/2, (kit.getScreenSize().height - HEIGHT)/2);
        // Установить начальное состояние окна развѐрнутым на весь экран
        setExtendedState(MAXIMIZED_BOTH);
        // Создать меню
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu ballMenu = new JMenu("Игра");
        Action startAction = new AbstractAction("Начать/Добавить мяч") {
            public void actionPerformed(ActionEvent event) {
                field.addBall();
                field.resume();
                if(!pauseMenuItem.isEnabled() && !resumeMenuItem.isEnabled()) {
                    // Ни один из пунктов меню не являются
                    // доступными - сделать доступным "Паузу"
                    pauseMenuItem.setEnabled(true);
                }
                resetMenuItem.setEnabled(true);
            }
        };

        Action add10BallAction = new AbstractAction("Добавить 10 мячей") {
            public void actionPerformed(ActionEvent event) {
                for(int i = 1; i <= 10; i++)
                    field.addBall();
            }
        };