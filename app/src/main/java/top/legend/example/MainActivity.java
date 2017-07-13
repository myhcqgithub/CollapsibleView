package top.legend.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import top.legend.collapsible.CollapsibleLayout;
import top.legend.collapsible.adapter.DefaultAdapter;
import top.legend.collapsible.adapter.holder.DefaultHolder;
import top.legend.collapsible.listener.OnItemClickListener;
import top.legend.collapsible.listener.OnItemLongClickListener;
import top.legend.collapsible.listener.ToggleListener;

public class MainActivity extends AppCompatActivity {
    private CollapsibleLayout mRoot;
    List<String> strings = Arrays.asList("1111", "214124", "12242443",
            "1111", "214124", "12242443", "1111", "214124", "12242443",
            "1111", "214124", "12242443");
    private List<String> list = new ArrayList<>();
    private DefaultAdapter<String> mDefaultAdapter;

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
    }

    private void assignViews() {
        mRoot = (CollapsibleLayout) findViewById(R.id.main_root);
        list.addAll(strings);

        mDefaultAdapter = new DefaultAdapter<String>(list, R.layout.item) {
            @Override
            public void bind(DefaultHolder defaultHolder, int position, String item) {
                defaultHolder.setText(R.id.text, item);
            }
        };
        mRoot.setAdapter(mDefaultAdapter);

        mRoot.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(CollapsibleLayout view, int position, Object data) {
                Toast.makeText(MainActivity.this, data.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        mRoot.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(CollapsibleLayout view, int position, Object data) {
                Toast.makeText(MainActivity.this, data.toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mRoot.setToggleListener(new ToggleListener() {
            @Override
            public void toggle(CollapsibleLayout layout, boolean toggle) {
                Toast.makeText(MainActivity.this, toggle + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void refresh(View view) {
        i++;
        list.add(i, "new..." + i);
        mDefaultAdapter.notifyDataSetChanged();
    }

    public void newAdapter(View view) {
        mRoot.setAdapter(mDefaultAdapter);
    }
}
