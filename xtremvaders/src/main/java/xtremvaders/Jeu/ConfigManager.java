package xtremvaders.Jeu;

import java.io.*;
import java.util.Properties;

public class ConfigManager {
    private final Properties props = new Properties();
    private final String configPath;

    public ConfigManager(String configPath) {
        this.configPath = configPath;
        File file = new File(configPath);
        if (!file.exists()) {
            System.out.println("Fichier de configuration non trouvé. Création avec les valeurs par défaut.");
            save(); // crée un fichier vide avec les valeurs actuelles si besoin
        } else {
            load();
        }
    }

    // Chargement du fichier .properties
    public void load() {
        try (InputStream input = new FileInputStream(configPath)) {
            props.load(input);
        } catch (IOException e) {
            System.err.println("Erreur de chargement de la configuration : " + e.getMessage());
        }
    }

    // Sauvegarde dans le fichier
    public void save() {
        try (OutputStream output = new FileOutputStream(configPath)) {
            props.store(output, "Fichier de configuration du jeu");
        } catch (IOException e) {
            System.err.println("Erreur de sauvegarde de la configuration : " + e.getMessage());
        }
    }

    // Accesseurs de lecture
    public String getString(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.parseBoolean(props.getProperty(key, String.valueOf(defaultValue)));
    }

    public double getDouble(String key, double defaultValue) {
        try {
            return Double.parseDouble(props.getProperty(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    // Accesseurs d'écriture
    public void set(String key, String value) {
        props.setProperty(key, value);
    }

    public void set(String key, boolean value) {
        props.setProperty(key, Boolean.toString(value));
    }

    public void set(String key, double value) {
        props.setProperty(key, Double.toString(value));
    }
}
