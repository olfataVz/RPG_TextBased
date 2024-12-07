package src;

import javax.swing.*;

public class Orc extends Mob {
    public Orc(int startX, int startY, int width, int height, int hp, int atk, JLayeredPane layeredPane, Game game) {
        super("orc", startX, startY, width, height, hp, atk, layeredPane, game);
    }

    @Override
    protected void loadAnimations() {
        idleFrames = loadAnimationFrames("Assets/orc/idle", "idle", 6);
        walkLeftFrames = loadAnimationFrames("Assets/orc/walk_Left", "walk_L", 8);
        walkRightFrames = loadAnimationFrames("Assets/orc/walk_Right", "walk_R", 8);
        attackFrames = loadAnimationFrames("Assets/orc/attack", "attack", 6);
        hurtFrames = loadAnimationFrames("Assets/orc/hurt", "hurt", 4);
        deathFrames = loadAnimationFrames("Assets/orc/death", "death", 4);
    }
}
