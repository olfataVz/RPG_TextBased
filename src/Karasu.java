package src;

import javax.swing.*;

public class Karasu extends Mob {
    public Karasu(int startX, int startY, int width, int height, int hp, int atk, int walk,JLayeredPane layeredPane, Game game) {
        super("karasu", startX, startY, width, height, hp, atk, walk, layeredPane, game);
    }
    @Override
    protected void loadAnimations() {
        idleFrames = loadAnimationFrames("karasu", "Idle", 5);
        walkLeftFrames = loadAnimationFrames("karasu", "Walk_Left", 8);
        walkRightFrames = loadAnimationFrames("karasu", "Walk_Right", 8);
        attackFrames = loadAnimationFrames("karasu", "Attack", 6);
        hurtFrames = loadAnimationFrames("karasu", "Hurt", 3);
        deathFrames = loadAnimationFrames("karasu", "Death", 3);
    }
}
