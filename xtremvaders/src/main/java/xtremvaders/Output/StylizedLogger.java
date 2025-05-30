package xtremvaders.Output;

import xtremvaders.Runtime.GameConfig;

public class StylizedLogger {
    // ANSI styles
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BOLD = "\u001B[1m";

    private static final int BOX_WIDTH = 40;

    // --- Public Log Methods ---

    public static void printWaveAnnouncement(int waveNumber) {
        String title = "VAGUE " + waveNumber;
        printBoxed(title, CYAN, YELLOW);
    }

    public static void printGameLaunch(String buildVersion, String buildChanges) {
        printBoxed(new String[]{
            "XTREM VADERS",
            "Build " + buildVersion,
            buildChanges
        }, GREEN, GREEN);
    }

    public static void printPropertiesConfig() {
        printBoxed(new String[]{
            "CONFIGURATION",
            "Version: " + GameConfig.kBuildVersion,
            "kDebugPauseMode: " + GameConfig.kDebugPauseMode,
            "kAutoLaunchGame: " + GameConfig.kAutoLaunchGame,
            "kDebugGameControls: " + GameConfig.kDebugGameControls,
            "kLargeMode: " + GameConfig.kLargeMode,
            "kHitBoxDisplay: " + GameConfig.kHitBoxDisplay,
            "kGameCursor: " + GameConfig.kGameCursor,
            "DisableMusic: " + GameConfig.kDisableMusic,
            "DisableSfx: " + GameConfig.kDisableSfx
        }, CYAN, YELLOW);
    }

    // --- Private Unified Display Method ---

    private static void printBoxed(String title, String borderColor, String textColor) {
        printBoxed(new String[]{title}, borderColor, textColor);
    }

    private static void printBoxed(String[] lines, String borderColor, String textColor) {
        String border = "+" + "-".repeat(BOX_WIDTH) + "+";
        System.out.println(BOLD + borderColor + border + RESET);

        for (String line : lines) {
            String centered = centerText(line, BOX_WIDTH);
            System.out.println(BOLD + borderColor + "|" + textColor + centered + borderColor + "|" + RESET);
        }

        System.out.println(BOLD + borderColor + border + RESET);
        System.out.println();
    }

    private static String centerText(String text, int width) {
        if (text.length() >= width) return text;
        int leftPadding = (width - text.length()) / 2;
        int rightPadding = width - text.length() - leftPadding;
        return " ".repeat(leftPadding) + text + " ".repeat(rightPadding);
    }
}
