package com.example.ceep.model;

import java.io.Serializable;

public class NoteModel implements Serializable {

    private final String title;
    private final String description;

    public NoteModel(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
