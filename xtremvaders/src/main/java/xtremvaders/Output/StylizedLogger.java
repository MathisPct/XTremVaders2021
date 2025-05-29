package xtremvaders.Output;

public class StylizedLogger {
    // Codes ANSI pour les couleurs et styles
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BOLD = "\u001B[1m";

    public static void printWaveAnnouncement(int waveNumber) {
        String line = "======================================";
        String waveText = String.format("~~~ VAGUE %d ~~~", waveNumber);
        int totalWidth = line.length();

        // Centre le texte vague dans le cadre
        int padding = (totalWidth - waveText.length()) / 2;
        String paddedWaveText = " ".repeat(padding) + waveText + " ".repeat(totalWidth - waveText.length() - padding);

        System.out.println(BOLD + CYAN + line + RESET);
        System.out.println(BOLD + YELLOW + paddedWaveText + RESET);
        System.out.println(BOLD + CYAN + line + RESET);
        System.out.println();
    }

    public static void printGameLaunch(String buildVersion, String buildChanges) {
        String title = " XTREM VADERS ";
        String buildInfo = "Build " + buildVersion;
        int width = 40;

        String border = "+" + "-".repeat(width) + "+";
        System.out.println(GREEN + BOLD + border + RESET);

        // Centre le titre
        System.out.println(GREEN + BOLD + "|" + centerText(title, width) + "|" + RESET);
        System.out.println(GREEN + BOLD + "|" + centerText(buildInfo, width) + "|" + RESET);
        System.out.println(GREEN + BOLD + border + RESET);
        System.out.println();
    }

    private static String centerText(String text, int width) {
        if (text.length() >= width) return text;
        int leftPadding = (width - text.length()) / 2;
        int rightPadding = width - text.length() - leftPadding;
        return " ".repeat(leftPadding) + text + " ".repeat(rightPadding);
    }
}
