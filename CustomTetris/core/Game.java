package core;

import core.gamestage.ModeSelectionMenu;
import core.gamestage.Playing;
import core.gamestage.StateManager;
import core.update.Updater;
import core.windows.GamePanel;
import core.windows.MainFrame;
import core.render.Renderer;
import core.gamestage.Menu;

import javax.swing.*;

import java.awt.*;

import static core.utils.Constants.MainConstants.FPS_SET;
import static core.utils.Constants.MainConstants.UPS_SET;


public class Game implements Runnable {
    private JPanel gamePanel;
    private MainFrame frame;
    private Thread gameThread;
    private Renderer renderer;
    private Updater updater;
    private StateManager stateManager;

    public Game() {
        init();
        gamePanel = new GamePanel(this);
        frame = new MainFrame(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void init() {
        this.stateManager = new StateManager(this);
        this.renderer = new Renderer(this);
        this.updater = new Updater(this);
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double timePerFrame = 1_000_000_000.0 / FPS_SET;
        double timePerUpdate = 1_000_000_000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }
            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                System.err.println(frames);
                frames = 0;
                updates = 0;
                lastCheck = System.currentTimeMillis();
            }
        }
    }

    private void update() {
        updater.update();
    }

    public void render(Graphics g) {
        this.renderer.render(g);
    }


    public Renderer getRenderer() {
        return renderer;
    }

    public Updater getUpdater() {
        return updater;
    }

    public StateManager getStateManager() {
        return stateManager;
    }


}
