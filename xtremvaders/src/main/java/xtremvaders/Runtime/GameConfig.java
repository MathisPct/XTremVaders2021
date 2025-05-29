package xtremvaders.Runtime;

public class GameConfig {
    public static String kBuildVersion = "2.0.0";
    public static boolean kDebugPauseMode = false;
    public static boolean kDebugGameControls = false;
    public static boolean kAutoLaunchGame = true;
    public static boolean kLargeMode = false;
    public static boolean kHitBoxDisplay = false;
    public static boolean kGameCursor = true;
    public static boolean kDisableMusic = false;
    public static boolean kDisableSfx = false;

    public static void loadFrom(ConfigManager config) {
        kBuildVersion = config.getString("buildVersion", kBuildVersion);
        kDebugPauseMode = config.getBoolean("debugPauseMode", kDebugPauseMode);
        kDebugGameControls = config.getBoolean("debugGameControls", kDebugGameControls);
        kAutoLaunchGame = config.getBoolean("autoLaunchGame", kAutoLaunchGame);
        kLargeMode = config.getBoolean("largeMode", kLargeMode);
        kHitBoxDisplay = config.getBoolean("hitBoxDisplay", kHitBoxDisplay);
        kGameCursor = config.getBoolean("gameCursor", kGameCursor);
        kDisableMusic = config.getBoolean("disableMusic", kDisableMusic);
        kDisableSfx = config.getBoolean("disableSfx", kDisableSfx);
    }

    public static void saveTo(ConfigManager config) {
        config.set("buildVersion", kBuildVersion);
        config.set("debugPauseMode", kDebugPauseMode);
        config.set("debugGameControls", kDebugGameControls);
        config.set("autoLaunchGame", kAutoLaunchGame);
        config.set("largeMode", kLargeMode);
        config.set("hitBoxDisplay", kHitBoxDisplay);
        config.set("gameCursor", kGameCursor);
        config.set("disableMusic", kDisableMusic);
        config.set("disableSfx", kDisableSfx);
        config.save();
    }
}
