package src;

import javax.swing.*;

public class Golem extends Mob {
    public Golem(int startX, int startY, int width, int height, int hp, int atk, int walk, JLayeredPane layeredPane, Game game) {
        super("golem", startX, startY, width, height, hp, atk, walk, layeredPane, game);
    }

    @Override
    protected void loadAnimations() {
        idleFrames = loadAnimationFrames("Assets/golem/Idle", "idle", 18);
        walkLeftFrames = loadAnimationFrames("Assets/golem/Walk_Left", "walk-L", 12);
        walkRightFrames = loadAnimationFrames("Assets/golem/Walk_Right", "walk-R", 12);
        attackFrames = loadAnimationFrames("Assets/golem/Attack", "attack", 12);
        hurtFrames = loadAnimationFrames("Assets/golem/Hurt", "hurt", 12);
        deathFrames = loadAnimationFrames("Assets/golem/Death", "death", 15);
    }
}
