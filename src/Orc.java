package src;

import javax.swing.*;

public class Orc extends Mob {
    public Orc(int startX, int startY, int width, int height, int hp, int atk, JLayeredPane layeredPane, Game game) {
        super("orc", startX, startY, width, height, hp, atk, layeredPane, game);
    }

    @Override
    protected void loadAnimations() {
        idleFrames = loadAnimationFrames("Assets/orc/Idle", "idle", 6);
        walkLeftFrames = loadAnimationFrames("Assets/orc/Walk_Left", "walk-L", 16);
        walkRightFrames = loadAnimationFrames("Assets/orc/Walk_Right", "walk-R", 16);
        attackFrames = loadAnimationFrames("Assets/orc/Attack", "attack", 10);
        hurtFrames = loadAnimationFrames("Assets/orc/Hurt", "hurt", 5);
        deathFrames = loadAnimationFrames("Assets/orc/Death", "death", 10);
    }
}
