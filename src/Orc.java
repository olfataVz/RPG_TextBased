package src;

import javax.swing.*;

public class Orc extends Mob {
    public Orc(int startX, int startY, int width, int height, int hp, int atk, int walk,JLayeredPane layeredPane, Game game) {
        super("orc", startX, startY, width, height, hp, atk, walk, layeredPane, game);
    }
    @Override
    protected void loadAnimations() {
        idleFrames = loadAnimationFrames("orc", "Idle", 6);
        walkLeftFrames = loadAnimationFrames("orc", "Walk_Left", 8);
        walkRightFrames = loadAnimationFrames("orc", "Walk_Right", 8);
        attackFrames = loadAnimationFrames("orc", "Attack", 6);
        hurtFrames = loadAnimationFrames("orc", "Hurt", 4);
        deathFrames = loadAnimationFrames("orc", "Death", 4);
    }
}
