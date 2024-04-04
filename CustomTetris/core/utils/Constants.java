package core.utils;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static class WindowConstants {
        public static final int INIT_WINDOW_WIDTH = 1200;
        public static final int INIT_WINDOW_HEIGHT = 750;
        public static final float SCALE = 1.5f;
        public static final int GAME_ZONE_WIDTH = 300;
        public static final int GAME_ZONE_HEIGHT = 690;
        public static final int RIGHT_LEFT_PADDING = (INIT_WINDOW_WIDTH -
                GAME_ZONE_WIDTH) / 2;
        public static final int TOP_BOTTOM_PADDING = (INIT_WINDOW_HEIGHT -
                GAME_ZONE_HEIGHT) / 2;
        public static final int GEN_BLOCK_POS_X = RIGHT_LEFT_PADDING +
                GAME_ZONE_WIDTH / 2;
        public static final int GEN_BLOCK_POS_Y = TOP_BOTTOM_PADDING + 30;
    }

    public static class MainConstants {
        public static final int FPS_SET = 120;
        public static final int UPS_SET = 200;
    }

    public static class GameConstants {
        public static final int[][][][] ROTATIONS = new int[][][][]{
                {
                        {{0, 1, 1, 0}, {1, 1, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
                        {{0, 1, 0, 0}, {0, 1, 1, 0}, {0, 0, 1, 0}, {0, 0, 0, 0}},
                        {{0, 1, 1, 0}, {1, 1, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
                        {{1, 0, 0, 0}, {1, 1, 0, 0}, {0, 1, 0, 0}, {0, 0, 0, 0}}

                },
                {
                        {{1, 1, 0, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
                        {{0, 0, 1, 0}, {0, 1, 1, 0}, {0, 1, 0, 0}, {0, 0, 0, 0}},
                        {{1, 1, 0, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
                        {{0, 1, 0, 0}, {1, 1, 0, 0}, {1, 0, 0, 0}, {0, 0, 0, 0}}
                },
                {
                        {{0, 0, 1, 0}, {1, 1, 1, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
                        {{0, 1, 0, 0}, {0, 1, 0, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}},
                        {{1, 1, 1, 0}, {1, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
                        {{1, 1, 0, 0}, {0, 1, 0, 0}, {0, 1, 0, 0}, {0, 0, 0, 0}}
                },
                {
                        {{1, 0, 0, 0}, {1, 1, 1, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
                        {{0, 1, 1, 0}, {0, 1, 0, 0}, {0, 1, 0, 0}, {0, 0, 0, 0}},
                        {{1, 1, 1, 0}, {0, 0, 1, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
                        {{0, 1, 0, 0}, {0, 1, 0, 0}, {1, 1, 0, 0}, {0, 0, 0, 0}}
                },
                {
                        {{1, 1, 1, 1}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
                        {{0, 0, 1, 0}, {0, 0, 1, 0}, {0, 0, 1, 0}, {0, 0, 1, 0}},
                        {{0, 0, 0, 0}, {1, 1, 1, 1}, {0, 0, 0, 0}, {0, 0, 0, 0}},
                        {{0, 1, 0, 0}, {0, 1, 0, 0}, {0, 1, 0, 0}, {0, 1, 0, 0}}
                },
                {
                        {{0, 1, 0, 0}, {1, 1, 1, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
                        {{0, 1, 0, 0}, {0, 1, 1, 0}, {0, 1, 0, 0}, {0, 0, 0, 0}},
                        {{0, 0, 0, 0}, {1, 1, 1, 0}, {0, 1, 0, 0}, {0, 0, 0, 0}},
                        {{0, 1, 0, 0}, {1, 1, 0, 0}, {0, 1, 0, 0}, {0, 0, 0, 0}}
                },
                {
                        {{1, 1, 0, 0}, {1, 1, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
                        {{1, 1, 0, 0}, {1, 1, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
                        {{1, 1, 0, 0}, {1, 1, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
                        {{1, 1, 0, 0}, {1, 1, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}}
                }
        };

        public static final Map<Integer, Integer> colorScheme = new HashMap<>() {{
            put(0, 2);
            put(1, 5);
            put(2, 4);
            put(3, 0);
            put(4, 1);
            put(5, 3);
            put(6, 6);
        }};

        public static final int BLOCK_SIZE = 30;
    }

    public static class MenuConstants {
        public static final int MENU_THIRD_HEIGHT = WindowConstants.INIT_WINDOW_HEIGHT / 3;
        public static final int MENUBAR_INIT_X = 260;
        public static final int MENUBAR_INIT_Y = MENU_THIRD_HEIGHT;
        public static final int MENUBAR_HEIGHT = MENU_THIRD_HEIGHT * 2 - 100;
        public static final int MENUBAR_WIDTH = 700;
        public static final int TITLE_WIDTH = MENUBAR_WIDTH;
    }
}
