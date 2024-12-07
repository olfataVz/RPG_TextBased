package src;

import javax.swing.*;

public class Slime extends Mob {
    public Slime(int startX, int startY, int width, int height, int hp, int atk, int walk,JLayeredPane layeredPane, Game game) {
        super("slime", startX, startY, width, height, hp, atk, walk, layeredPane, game);
    }

    @Override
    protected void loadAnimations() {
        idleFrames = loadAnimationFrames("Assets/slime/Idle", "idle", 6);
        walkLeftFrames = loadAnimationFrames("Assets/slime/Walk_Left", "walk-L", 16);
        walkRightFrames = loadAnimationFrames("Assets/slime/Walk_Right", "walk-R", 16);
        attackFrames = loadAnimationFrames("Assets/slime/Attack", "attack", 10);
        hurtFrames = loadAnimationFrames("Assets/slime/Hurt", "hurt", 5);
        deathFrames = loadAnimationFrames("Assets/slime/Death", "death", 10);
    }
}
