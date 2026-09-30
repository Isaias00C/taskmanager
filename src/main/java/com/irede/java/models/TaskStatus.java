package com.irede.java.models;

public enum TaskStatus {
    NAO_INICIADA("Não Iniciada"), EM_ANDAMENTO("Em Andamento"), CONCLUIDA("Concluída");

    private final String label;
    TaskStatus(String label) { this.label = label; }

    public String getLabel() { return label; }

    public static TaskStatus fromLabel(String label){
        for (TaskStatus s : values()) if (s.label.equals(label)) return s;
        return NAO_INICIADA;
    }
}