package minhna.submission.todocoderschool;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import minhna.submission.todocoderschool.dao.SQLiteDatasource;

public class MainActivity extends AppCompatActivity implements EditToDoDialogFragment.EditToDoDialogFragmentListener {
    private SQLiteDatasource datasource;
    private List<String> toDoList;
    private List<Long> toDoListId;
    private ArrayAdapter<String> adapter;

    private EditText toDoEdt;
    private ListView toDoLv;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toDoEdt = (EditText) findViewById(R.id.input);
        toDoLv = (ListView) findViewById(R.id.to_do_list);

        datasource = new SQLiteDatasource(this);
        toDoList = datasource.getAll();
        toDoListId = datasource.getAllId();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, toDoList);
        toDoLv.setAdapter(adapter);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toDo = toDoEdt.getText().toString();
                if (!toDo.trim().isEmpty()) {
                    datasource.addToDo(toDo);
                    toDoList.add(toDo);
                    toDoListId = datasource.getAllId();
                    adapter.notifyDataSetChanged();
                    toDoEdt.setText("");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        toDoLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showEditDialog(toDoListId.get(position), toDoList.get(position), position);
            }
        });
    }

    private void showEditDialog(long id, String toDo, int position) {
        FragmentManager fm = getSupportFragmentManager();
        EditToDoDialogFragment fragment = EditToDoDialogFragment.newInstance(id, toDo, position);
        fragment.show(fm, "fragment_edit");
    }

    @Override
    public void onFinishEditDialog(long id, String inputText) {
        if (!inputText.trim().isEmpty()) {
            datasource.updateToDo(id, inputText);
            toDoList = datasource.getAll();
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, toDoList);
            toDoLv.setAdapter(adapter);
        }
    }

    @Override
    public void onFinishDeleteDialog(long id, int position) {
        if (datasource.deleteItem(id)==1) {
            toDoList.remove(position);
            toDoListId.remove(position);
            adapter.notifyDataSetChanged();
        }
    }
}
