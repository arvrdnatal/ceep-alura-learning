package com.example.ceep.ui.recyclerview.helper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ceep.dao.NoteDAO;
import com.example.ceep.ui.recyclerview.adapter.NotesListAdapter;

public class NoteItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final NotesListAdapter adapter;

    public NoteItemTouchHelperCallback(NotesListAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int dragFlags = ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        NoteDAO dao = new NoteDAO();
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        dao.swap(fromPosition, toPosition);
        adapter.swap(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        NoteDAO dao = new NoteDAO();
        dao.remove(position);
        adapter.remove(position);
    }
}
