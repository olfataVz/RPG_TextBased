package src;

import java.awt.*;
import javax.swing.*;

public class Wizard {
    private int hp, atk, level, damage;
    protected int x, y, width, height;
    protected JLayeredPane layeredPane;
    protected JPanel wizardPanel;
    protected JLabel wizardLabel;
    protected Timer animationTimer;
    protected int frameIndex = 0;

    protected ImageIcon[] idleFrames, runLeftFrames, runRightFrames, attackFrames, skillFrames, 
                          skillAnimationFrames, ultimateFrames, hurtFrames, deathFrames;

    Game game;

    public Wizard(int startX, int startY, int width, int height, JLayeredPane layeredPane, Game game, int hp, int atk, int level) {
        this.x = startX;
        this.y = startY;
        this.width = width;
        this.height = height;
        this.layeredPane = layeredPane;
        this.game = game;
        this.hp = hp;
        this.atk = atk;
        this.level = level;

        wizardPanel = new JPanel();
        wizardPanel.setBounds(x, y, width, height);
        wizardPanel.setOpaque(false);
        layeredPane.add(wizardPanel, Integer.valueOf(1));

        wizardLabel = new JLabel();
        wizardPanel.add(wizardLabel);

        loadAnimations();
    }

    protected void loadAnimations() {
        idleFrames = loadAnimationFrames("Assets/wizard/idle", "idle", 7);
        runLeftFrames = loadAnimationFrames("Assets/wizard/run_left", "run_left", 8);
        runRightFrames = loadAnimationFrames("Assets/wizard/run_right", "run_right", 8);
        attackFrames = loadAnimationFrames("Assets/wizard/attack", "attack", 8);
        skillFrames = loadAnimationFrames("Assets/wizard/skill", "skill", 8);
        skillAnimationFrames = loadAnimationFrames("Assets/wizard/skill_animation", "skill_animation", 12);
        ultimateFrames = loadAnimationFrames("Assets/wizard/ultimate", "ultimate", 14);
        hurtFrames = loadAnimationFrames("Assets/wizard/hurt", "hurt", 3);
        deathFrames = loadAnimationFrames("Assets/wizard/death", "death", 6);
    }

    private ImageIcon[] loadAnimationFrames(String folderPath, String filePrefix, int frameCount) {
        ImageIcon[] frames = new ImageIcon[frameCount];
        for (int i = 0; i < frameCount; i++) {
            ImageIcon frame = new ImageIcon(folderPath + "/" + filePrefix + (i + 1) + ".png");
            Image scaledFrame = frame.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            frames[i] = new ImageIcon(scaledFrame);
        }
        return frames;
    }

    public void playIdleAnimation() {
        stopCurrentAnimation();
        frameIndex = 0;
        animationTimer = new Timer(150, e -> {
            wizardLabel.setIcon(idleFrames[frameIndex]);
            frameIndex = (frameIndex + 1) % idleFrames.length;
        });
        x = 90;
        wizardPanel.setBounds(x, y, width, height);
        animationTimer.start();
    }

    public void playRunLeftAnimation() {
        hideTextComponents();
        stopCurrentAnimation();
        frameIndex = 0;

        animationTimer = new Timer(100, e -> {
            if (frameIndex < runLeftFrames.length) {
                wizardLabel.setIcon(runLeftFrames[frameIndex]);
                x -= 90; // Kecepatan bergerak ke kiri
                wizardPanel.setBounds(x, y, width, height);
                frameIndex++;
            } else {
                animationTimer.stop();
                playIdleAnimation();
            }
        });
        animationTimer.start();
    }

    public void playRunRightAnimation() {
        hideTextComponents();
        stopCurrentAnimation();
        frameIndex = 0;

        animationTimer = new Timer(100, e -> {
            if (frameIndex < runRightFrames.length) {
                wizardLabel.setIcon(runRightFrames[frameIndex]);
                x += 90; // Kecepatan bergerak ke kanan
                wizardPanel.setBounds(x, y, width, height);
                frameIndex++;
            } else {
                animationTimer.stop();
                playAttackAnimation();
            }
        });
        animationTimer.start();
    }

    public void playAttackAnimation() {
        hideTextComponents();
        stopCurrentAnimation();
        damage = 1 * atk;
        frameIndex = 0;
        animationTimer = new Timer(150, e -> {
            if (frameIndex < attackFrames.length) {
                wizardLabel.setIcon(attackFrames[frameIndex]);
                frameIndex++;
            } else {
                animationTimer.stop();
                playRunLeftAnimation();
            }
        });
        animationTimer.start();
    }

    public void playSkill() {
        stopCurrentAnimation();
        damage = 2 * atk;
        frameIndex = 0;
        animationTimer = new Timer(150, e -> {
            if (frameIndex < skillFrames.length) {
                wizardLabel.setIcon(skillFrames[frameIndex]);
                frameIndex++;
            } else {
                animationTimer.stop();
                playSkillAnimation();
            }
        });
        animationTimer.start();
    }

    public void playSkillAnimation() {
        frameIndex = 0;
        animationTimer = new Timer(100, e -> {
            if (frameIndex < skillAnimationFrames.length) {
                wizardLabel.setIcon(skillAnimationFrames[frameIndex]);
                x += 95; // Pergeseran kecil per frame
                wizardPanel.setBounds(x, y, width, height);
                frameIndex++;
            } else {
                x = 90;
                wizardPanel.setBounds(x, y, width, height);
                animationTimer.stop();
                playIdleAnimation();
            }
        });
        animationTimer.start();
    }

    public void playUltimateLeft () {
        hideTextComponents();
        stopCurrentAnimation();
        damage = 4 * atk;
        frameIndex = 0;
        animationTimer = new Timer(100, e -> {
            if (frameIndex < runLeftFrames.length) {
                wizardLabel.setIcon(runLeftFrames[frameIndex]);
                x -= 80; // Kecepatan bergerak ke kiri
                wizardPanel.setBounds(x, y, width, height);
                frameIndex++;
            } else {
                animationTimer.stop();
                playIdleAnimation();
            }
        });
        animationTimer.start();
    }

    public void playUltimateAnimation() {
        stopCurrentAnimation();
        frameIndex = 0;

        animationTimer = new Timer(100, e -> {
            if (frameIndex < ultimateFrames.length) {
                wizardLabel.setIcon(ultimateFrames[frameIndex]);
                x += 20; // You can adjust the movement for ultimate animation here
                wizardPanel.setBounds(x, y, width, height);
                frameIndex++;
            } else {
                animationTimer.stop();
                playRunLeftAnimation(); // After ultimate animation, return to idle
            }
        });
        animationTimer.start();
    }

    public void playUltimateRight () {
        hideTextComponents();
        stopCurrentAnimation();
        frameIndex = 0;

        animationTimer = new Timer(100, e -> {
            if (frameIndex < runRightFrames.length) {
                wizardLabel.setIcon(runRightFrames[frameIndex]);
                x += 65; // Kecepatan bergerak ke kanan
                wizardPanel.setBounds(x, y, width, height);
                frameIndex++;
            } else {
                animationTimer.stop();
                playUltimateAnimation();
            }
        });
        animationTimer.start();
    }

    public void playHurtAnimation() {
        stopCurrentAnimation();
        frameIndex = 0;

        animationTimer = new Timer(300, e -> {
            if (frameIndex < hurtFrames.length) {
                x = 90; 
                wizardPanel.setBounds(x, y, width, height);
                wizardLabel.setIcon(hurtFrames[frameIndex]);
                frameIndex++;
            } else {
                animationTimer.stop();
                playIdleAnimation();
            }
        });
        animationTimer.start();
    }

    public void playDeathAnimation() {
        stopCurrentAnimation();
        frameIndex = 0;

        animationTimer = new Timer(200, e -> {
            if (frameIndex < deathFrames.length) {
                wizardLabel.setIcon(deathFrames[frameIndex]);
                frameIndex++;
            } else {
                animationTimer.stop();
                wizardPanel.setVisible(false);
                game.synopsisTextArea.setText("Kamu telah mati");
                game.synopsisTextArea.setVisible(true);
            }
        });
        animationTimer.start();
    }

    private void stopCurrentAnimation() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
    }    

    private void hideTextComponents() {
        game.battleTextArea.setVisible(true);
        game.backgroundBattleText.setVisible(true);
        game.synopsisTextArea.setVisible(false);
        game.backgroundText.setVisible(false);
    }

    public void heal() {
        if (hp > 0) {
            int healing = 30;
            this.hp += healing;
            // Ensure HP does not exceed maximum value
            if (hp > 100) {  // Assuming 100 is the max HP
                hp = 100;
            }
            game.battleTextArea.setText("Wizard healed. Current HP: " + getHP());
        }
    }

    public int getAtk() {
        return atk;
    }

    public int getHP() {
        return hp;
    }

    public int getLevel() {
        return level;
    }

    public void reduceHP(int damage) {
        this.hp -= damage;
        game.battleTextArea.setText("Wizard menerima damage: " + damage + ". HP sekarang: " + getHP());
        if (hp <= 0) {
            playDeathAnimation();
        } else {
            playHurtAnimation();
        }
    }
}
