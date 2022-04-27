package com.example.ceep.dao;

import com.example.ceep.model.NoteModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NoteDAO {

    private static final List<NoteModel> notes = new ArrayList<>();

    public List<NoteModel> getAll() {
        return new ArrayList<>(notes);
    }

    public void insert(NoteModel... notesReceived) {
        notes.addAll(Arrays.asList(notesReceived));
    }

    public void update(int position, NoteModel note) {
        notes.set(position, note);
    }

    public void remove(int position) {
        notes.remove(position);
    }

    public void swap(int fromPosition, int toPosition) {
        Collections.swap(notes, fromPosition, toPosition);
    }

}