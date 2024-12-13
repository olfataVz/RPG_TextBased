package src;

import javax.swing.*;

public class Golem extends Mob {
    public Golem(int startX, int startY, int width, int height, int hp, int atk, int walk, JLayeredPane layeredPane, Game game) {
        super("golem", startX, startY, width, height, hp, atk, walk, layeredPane, game);
    }

    @Override
    protected void loadAnimations() {
        idleFrames = loadAnimationFrames("golem", "Idle", 18);
        walkLeftFrames = loadAnimationFrames("golem", "Walk_Left", 12);
        walkRightFrames = loadAnimationFrames("golem", "Walk_Right", 12);
        attackFrames = loadAnimationFrames("golem", "Attack", 12);
        hurtFrames = loadAnimationFrames("golem", "Hurt", 12);
        deathFrames = loadAnimationFrames("golem", "Death", 15);
    }
}
