package com.example.ceep.ui.activity;

import static com.example.ceep.ui.activity.ActivitiesConstants.EDIT_NOTE_FORM_ACTIVITY_TITLE;
import static com.example.ceep.ui.activity.ActivitiesConstants.NEW_NOTE_FORM_ACTIVITY_TITLE;
import static com.example.ceep.ui.activity.ActivitiesConstants.NOTE_KEY;
import static com.example.ceep.ui.activity.ActivitiesConstants.NOTE_POSITION_KEY;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ceep.R;
import com.example.ceep.model.NoteModel;

public class NoteFormActivity extends AppCompatActivity {

    private EditText title;
    private EditText description;
    private final Intent result = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_form);
        setTitle(NEW_NOTE_FORM_ACTIVITY_TITLE);

        configPrevIntent();
    }

    private void configPrevIntent() {
        Intent prevIntent = getIntent();
        title = findViewById(R.id.title_input);
        description = findViewById(R.id.description_input);

        if (prevIntent != null && prevIntent.hasExtra(NOTE_KEY)) {
            setTitle(EDIT_NOTE_FORM_ACTIVITY_TITLE);
            NoteModel note = (NoteModel) prevIntent.getSerializableExtra(NOTE_KEY);
            title.setText(note.getTitle());
            description.setText(note.getDescription());
            int position = (int) prevIntent.getSerializableExtra(NOTE_POSITION_KEY);
            result.putExtra(NOTE_POSITION_KEY, position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form_new_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.button_menu_save_note) {
            NoteModel note = new NoteModel(title.getText().toString(), description.getText().toString());
            result.putExtra(NOTE_KEY, note);
            setResult(Activity.RESULT_OK, result);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}