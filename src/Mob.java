package src;

import java.awt.*;
import javax.swing.*;

public abstract class Mob {
    protected String mobType;
    protected int x, y, width, height, hp, atk, damage, walk;
    protected JLayeredPane layeredPane;
    protected JPanel mobPanel;
    protected JLabel mobLabel;
    protected Timer animationTimer;
    Game game;
    protected int frameIndex = 0;
    protected ImageIcon[] idleFrames, walkLeftFrames, walkRightFrames, attackFrames, hurtFrames, deathFrames;
    
    public Mob(String mobType, int startX, int startY, int width, int height, int hp, int atk, int walk, JLayeredPane layeredPane, Game game) {
        if (layeredPane == null) {
            throw new IllegalArgumentException("layeredPane cannot be null");
        }
        this.mobType = mobType;
        this.x = startX;
        this.y = startY;
        this.width = width;
        this.height = height;
        this.hp = hp;
        this.atk = atk;
        this.walk = walk;
        this.layeredPane = layeredPane;
        this.game = game;
    
        mobPanel = new JPanel();
        mobPanel.setBounds(x, y, width, height);
        mobPanel.setOpaque(false);
        layeredPane.add(mobPanel, Integer.valueOf(1));
    
        mobLabel = new JLabel();
        mobPanel.add(mobLabel);
    
        loadAnimations();
    }    

    // New method to get the mob's name
    public String getName() {
        return mobType;
    }

    protected ImageIcon[] loadAnimationFrames(String folderPath, String filePrefix, int frameCount) {
        ImageIcon[] frames = new ImageIcon[frameCount];
        for (int i = 0; i < frameCount; i++) {
            ImageIcon frame = new ImageIcon(folderPath + "/" + filePrefix + (i + 1) + ".png");
            Image scaledFrame = frame.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            frames[i] = new ImageIcon(scaledFrame);
        }
        return frames;
    }
    protected abstract void loadAnimations();

    private void hideTextComponents() {
        game.battleTextArea.setVisible(true);
        game.backgroundBattleText.setVisible(true);
        // game.synopsisTextArea.setVisible(false);
        game.backgroundText.setVisible(false);
    }

    public void playIdleAnimation() {
        hideTextComponents();
        stopCurrentAnimation();
        frameIndex = 0;
        animationTimer = new Timer(150, e -> {
            mobLabel.setIcon(idleFrames[frameIndex]);
            frameIndex = (frameIndex + 1) % idleFrames.length;
        });
        x = 900;
        mobPanel.setBounds(x, y, width, height);
        animationTimer.start();
    }

    public void playWalkLeftAnimation() {
        hideTextComponents();
        stopCurrentAnimation();
        frameIndex = 0;
        animationTimer = new Timer(100, e -> {
            if (frameIndex < walkLeftFrames.length){
                mobLabel.setIcon(walkLeftFrames[frameIndex]);
                x -= walk;
                mobPanel.setBounds(x, y, width, height);
                frameIndex++;
            } else {
                animationTimer.stop();
                playAttackAnimation();
            }
        });
        animationTimer.start();
    }

    public void playWalkRightAnimation() {
        hideTextComponents();
        stopCurrentAnimation();
        frameIndex = 0;
        animationTimer = new Timer(100, e -> {
            if (frameIndex < walkRightFrames.length){
                mobLabel.setIcon(walkRightFrames[frameIndex]);
                x += walk;
                mobPanel.setBounds(x, y, width, height);
                frameIndex++;
            } else {
                animationTimer.stop();
                playIdleAnimation();
            }
        });
        animationTimer.start();
    }

    public void playAttackAnimation() {
        hideTextComponents();
        stopCurrentAnimation();
        damage = 1 * atk;
        frameIndex = 0;
        animationTimer = new Timer(100, e -> {
            if(frameIndex < attackFrames.length){   
                mobLabel.setIcon(attackFrames[frameIndex]);
                frameIndex++;
            } else {
                animationTimer.stop();
                playWalkRightAnimation();
            }
        });
        animationTimer.start();
    }

    public void playHurtAnimation() {
        hideTextComponents();
        stopCurrentAnimation();
        frameIndex = 0;
        animationTimer = new Timer(700, e -> {
            mobLabel.setIcon(hurtFrames[frameIndex]);
            frameIndex++;
            if (frameIndex < hurtFrames.length) {
                mobLabel.setIcon(hurtFrames[frameIndex]);
                frameIndex++;
            } else{
                animationTimer.stop();
                playIdleAnimation();
            }
        });
        animationTimer.start();
    }

    public void playDeathAnimation() {
        hideTextComponents();
        stopCurrentAnimation();
        frameIndex = 0;
        animationTimer = new Timer(200, e -> {
            if(frameIndex < deathFrames.length){
                mobLabel.setIcon(deathFrames[frameIndex]);
                frameIndex++;
            } else {
                animationTimer.stop();
                mobPanel.setVisible(false);
            }
        });
        animationTimer.start();
    }

    private void stopCurrentAnimation() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
    }    

    public void reduceHP(int damage) {
        this.hp -= damage;
        if(hp <= 0) {
            hp = 0;
        }

        game.battleTextArea.setText("HP " + mobType+ " berkurang: " + damage + ". HP sekarang: " + hp);
        
        if (hp <= 0) {
            playDeathAnimation();
        } else {
            playHurtAnimation();
        }
    }

    public int getHP() {
        return hp;
    }

    public int getAtk() {
        return atk;
    }
    
}
