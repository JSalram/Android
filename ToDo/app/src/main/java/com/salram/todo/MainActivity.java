package com.salram.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.number.NumberFormatter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    EditText editText;
    Button addButton;
    Button delButton;
    RelativeLayout relativeLayout;
    List<TextView> items;
    List<CheckBox> boxes;
    int top;
    float maxWidth = 530;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        addButton = findViewById(R.id.addButton);
        delButton = findViewById(R.id.delButton);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        items = new ArrayList<>();
        boxes = new ArrayList<>();
        top = 0;

//        deleteItems(items, boxes);

        addButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!editText.getText().toString().equals(""))
                {
                    // Create a TextView programmatically.
                    TextView textView = new TextView(getApplicationContext());
                    CheckBox checkBox = new CheckBox(getApplicationContext());

                    // Create a LayoutParams for TextView
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT, // Width of TextView
                            RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView

                    lp.setMargins(75, top + 4, 0, 0);

                    RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT, // Width of TextView
                            RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView

                    lp2.setMargins(0, top, 0, 0);

                    // Apply the layout parameters to TextView widget
                    textView.setLayoutParams(lp);
                    checkBox.setLayoutParams(lp2);

                    // Set text to display in TextView
                    textView.setText(editText.getText().toString());
                    textView.setTextSize(20);

                    // Add newly created TextView to parent view group (RelativeLayout)
                    relativeLayout.addView(textView);
                    relativeLayout.addView(checkBox);

                    // Add textView and Checkbox to their lists
                    items.add(textView);
                    boxes.add(checkBox);

                    textView.measure(0, 0);
                    float width = textView.getMeasuredWidth();
                    while (width > maxWidth)
                    {
                        top += 50;
                        width -= maxWidth;
                    }

                    editText.setText("");
                    top += 50;
                }
            }
        });

        delButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!items.isEmpty())
                {
                    TextView tv = items.get(items.size() - 1);
                    items.get(items.size() - 1).setVisibility(View.GONE);
                    items.remove(items.size() - 1);
                    boxes.get(boxes.size() - 1).setVisibility(View.GONE);
                    boxes.remove(boxes.size() - 1);
                    top -= 50;

                    tv.measure(0, 0);
                    float width = tv.getMeasuredWidth();
                    while (width > maxWidth)
                    {
                        top -= 50;
                        width -= maxWidth;
                    }
                }
            }
        });
    }

//    public void deleteItems (List<TextView> items, List<CheckBox> boxes)
//    {
//        int i;
//        for (i = 0; i < items.size(); i++)
//        {
//            items.get(i).setOnLongClickListener(new View.OnLongClickListener()
//            {
//                @Override
//                public boolean onLongClick(View view)
//                {
//                    view.setVisibility(View.GONE);
//                    return false;
//                }
//            });
//        }
//    }
}