package com.infamous.jjbot;

import javafx.scene.control.Toggle;



public class UserSettings {
    // Defining  all of the settings buttons
    public Toggle camelToggl;
    public Toggle upperToggl;
    public Toggle normalCaseToggl;
    public Toggle lowerToggl;
    public Toggle nonePuncToggl;
    public Toggle exclamationToggl;
    public Toggle fullStopToggl;
    public Toggle normalToggl;
    public Toggle hellToggl;
    public Toggle deathToggl;
    public Toggle jumpToggl;
    public Toggle cheerToggl;

    public UserSettings(Toggle camelToggl, Toggle upperToggl, Toggle normalCaseToggl, Toggle lowerToggl,
                        Toggle nonePuncToggl, Toggle exclamationToggl, Toggle fullStopToggl, Toggle normalToggl,
                        Toggle hellToggl, Toggle deathToggl, Toggle jumpToggl, Toggle cheerToggl) {
        this.camelToggl = camelToggl;
        this.upperToggl = upperToggl;
        this.normalCaseToggl = normalCaseToggl;
        this.lowerToggl = lowerToggl;
        this.nonePuncToggl = nonePuncToggl;
        this.exclamationToggl = exclamationToggl;
        this.fullStopToggl = fullStopToggl;
        this.normalToggl = normalToggl;
        this.hellToggl = hellToggl;
        this.deathToggl = deathToggl;
        this.jumpToggl = jumpToggl;
        this.cheerToggl = cheerToggl;
    }

    public String getAction() {
        if (jumpToggl.isSelected()) {
            return "jump";
        } else
            return "cheer";
    }

    public String getMode() {
        if (normalToggl.isSelected()) {
            return "normal";
        } else if (hellToggl.isSelected()) {
            return "hell";
        } else
            return "death";
    }

    public String applyCase(String text) {
        if (camelToggl.isSelected()) {
            // Apply camel casing to the beginning of every word in the string
            String[] words = text.split(" ");
            StringBuilder sb = new StringBuilder();
            for (String word : words) {
                sb.append(word.substring(0, 1).toUpperCase()).append(word.substring(1).toLowerCase()).append(" ");
            }
            // Remove the final space from the string builder sb
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        } else if (upperToggl.isSelected()) {
            return text.toUpperCase();
        } else if (lowerToggl.isSelected()) {
            return text.toLowerCase();
        } else
            return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }

    public String applyPunctuation(String text) {
        if (exclamationToggl.isSelected()) {
            return text + "!";
        } else if (fullStopToggl.isSelected()) {
            return text + ".";
        } else
            return text;
    }
}
