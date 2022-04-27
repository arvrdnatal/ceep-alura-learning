package com.example.ceep.ui.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ceep.R;
import com.example.ceep.model.NoteModel;

import java.util.Collections;
import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NoteViewHolder> {

    private final Context context;
    private final List<NoteModel> notes;
    private ItemClickListener itemClickListener;

    public NotesListAdapter(Context context, List<NoteModel> notes) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public NotesListAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note_list, parent, false);

        return new NoteViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesListAdapter.NoteViewHolder holder, int position) {
        NoteModel note = notes.get(position);
        holder.link(note);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void insert(NoteModel note) {
        notes.add(note);
        notifyItemInserted(notes.indexOf(note));
    }

    public void update(int position, NoteModel note) {
        notes.set(position, note);
        notifyItemChanged(position);
    }

    public void remove(int position) {
        notes.remove(position);
        notifyItemRemoved(position);
    }

    public void swap(int fromPosition, int toPosition) {
        Collections.swap(notes, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    public NoteModel getItem(int position) {
        return notes.get(position);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView title;
        private final TextView description;
        private final ItemClickListener clickListener;

        public NoteViewHolder(@NonNull View itemView, ItemClickListener clickListener) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title_note_item);
            this.description = itemView.findViewById(R.id.description_note_item);
            this.clickListener = clickListener;
            itemView.setOnClickListener(this);
        }

        public void link(@NonNull NoteModel note) {
            title.setText(note.getTitle());
            description.setText(note.getDescription());
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}
