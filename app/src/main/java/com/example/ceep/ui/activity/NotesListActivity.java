package com.example.ceep.ui.activity;

import static com.example.ceep.ui.activity.ActivitiesConstants.NOTES_LIST_ACTIVITY_TITLE;
import static com.example.ceep.ui.activity.ActivitiesConstants.NOTE_KEY;
import static com.example.ceep.ui.activity.ActivitiesConstants.NOTE_POSITION_KEY;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ceep.R;
import com.example.ceep.dao.NoteDAO;
import com.example.ceep.model.NoteModel;
import com.example.ceep.ui.recyclerview.adapter.NotesListAdapter;
import com.example.ceep.ui.recyclerview.helper.NoteItemTouchHelperCallback;

public class NotesListActivity extends AppCompatActivity {

    private final NoteDAO dao = new NoteDAO();
    private ActivityResultLauncher<Intent> mGoToFormInsertNote;
    private ActivityResultLauncher<Intent> mGoToFormEditNote;
    private NotesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        setTitle(NOTES_LIST_ACTIVITY_TITLE);

        setIntents();
        configRecyclerView();
        configButtonInsertNote();
    }

    private void configButtonInsertNote() {
        TextView buttonInsertNote = findViewById(R.id.insert_new_note);
        buttonInsertNote.setOnClickListener(view -> mGoToFormInsertNote.launch(new Intent(this, NoteFormActivity.class)));
    }

    private void setIntents() {
        mGoToFormInsertNote = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent intentResult = result.getData();
                if (intentResult != null && intentResult.hasExtra(NOTE_KEY)) {
                    NoteModel note = (NoteModel) intentResult.getSerializableExtra(NOTE_KEY);
                    dao.insert(note);
                    adapter.insert(note);
                }
            }
        });

        mGoToFormEditNote = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent intentResult = result.getData();
                if (intentResult != null && intentResult.hasExtra(NOTE_KEY) && intentResult.hasExtra(NOTE_POSITION_KEY)) {
                    NoteModel note = (NoteModel) intentResult.getSerializableExtra(NOTE_KEY);
                    int position = (int) intentResult.getSerializableExtra(NOTE_POSITION_KEY);
                    dao.update(position, note);
                    adapter.update(position, note);
                }
            }
        });
    }

    private void configRecyclerView() {
        adapter = new NotesListAdapter(this, dao.getAll());
        RecyclerView notesList = findViewById(R.id.notes_list);
        notesList.setAdapter(adapter);
        adapter.setItemClickListener(position -> {
            NoteModel note = adapter.getItem(position);
            Intent goToForm = new Intent(this, NoteFormActivity.class);
            goToForm.putExtra(NOTE_KEY, note);
            goToForm.putExtra(NOTE_POSITION_KEY, position);
            mGoToFormEditNote.launch(goToForm);
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new NoteItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(notesList);
    }
}