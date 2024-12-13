package src;

import javax.swing.*;

public class Slime extends Mob {
    public Slime(int startX, int startY, int width, int height, int hp, int atk, int walk,JLayeredPane layeredPane, Game game) {
        super("slime", startX, startY, width, height, hp, atk, walk, layeredPane, game);
    }

    @Override
    protected void loadAnimations() {
        idleFrames = loadAnimationFrames("slime", "Idle", 6);
        walkLeftFrames = loadAnimationFrames("slime", "Walk_Left", 16);
        walkRightFrames = loadAnimationFrames("slime", "Walk_Right", 16);
        attackFrames = loadAnimationFrames("slime", "Attack", 10);
        hurtFrames = loadAnimationFrames("slime", "Hurt", 5);
        deathFrames = loadAnimationFrames("slime", "Death", 10);
    }

    public void setVisible(boolean b) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setVisible'");
    }
}
