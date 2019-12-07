package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;

import Module.TestPattern;
import entity.MonHoc;

public class MainActivity extends AppCompatActivity {
    EditText et_Ma,et_Ten,et_SoChi;
    Button bt_Them,bt_Tim,bt_Xoa;
    TextView tv_TestMa,tv_TestSoChi;
    ListView listView;
    ArrayList<MonHoc>dsmh;
    public void loaddata()
    {
     dsmh=new ArrayList<MonHoc>();
     Cursor cursor=getContentResolver().query(MonHocProvider.CONTENT_URI,null,null,null,null);
     if(cursor.moveToFirst())
     {
         do {
             int ma=cursor.getInt(cursor.getColumnIndex(MonHocProvider.MAMONHOC));
             String ten=cursor.getString(cursor.getColumnIndex(MonHocProvider.TENMONHOC));
             int sochi=cursor.getInt(cursor.getColumnIndex(MonHocProvider.SOCHI));
             MonHoc monHoc=new MonHoc(ma,ten,sochi);
             dsmh.add(monHoc);
         }while (cursor.moveToNext());
         ArrayAdapter<MonHoc>adapter=new ArrayAdapter<MonHoc>(MainActivity.this,android.R.layout.simple_list_item_1,dsmh);
         listView.setAdapter(adapter);
     }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_Ma=(EditText)findViewById(R.id.editTextMaMonHoc);
        et_Ten=(EditText)findViewById(R.id.editTextTenMonHoc);
        et_SoChi=(EditText)findViewById(R.id.editTextSoChi);
        bt_Them=(Button)findViewById(R.id.buttonThem);
        bt_Tim=(Button)findViewById(R.id.buttonTim);
        bt_Xoa=(Button)findViewById(R.id.buttonXoa);
        tv_TestMa=(TextView)findViewById(R.id.textViewTestMa);
        tv_TestSoChi=(TextView)findViewById(R.id.textViewTestSoChi);
        listView=(ListView)findViewById(R.id.listview);
        loaddata();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MonHoc mh=dsmh.get(position);
                et_Ma.setText(String.valueOf(mh.getMamonhoc()));
                et_Ten.setText(String.valueOf(mh.getTenmonhoc()));
                et_SoChi.setText(String.valueOf(mh.getSochi()));
            }
        });
        bt_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestPattern testPattern=new TestPattern();
                boolean testma=testPattern.TestMa(et_Ma.getText().toString());
                boolean testsochi=testPattern.TestSoChi(et_SoChi.getText().toString());
                switch (testma+"-"+testsochi)
                {
                    case "true-true":
                    {
                        tv_TestMa.setText("");
                        tv_TestSoChi.setText("");
                        if(et_Ten.getText().toString().equalsIgnoreCase(""))
                        {
                            Toast.makeText(MainActivity.this,"Ban phai nhap ten",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            ContentValues values = new ContentValues();
                            values.put(MonHocProvider.MAMONHOC, Integer.parseInt(et_Ma.getText().toString()));
                            values.put(MonHocProvider.TENMONHOC, et_Ten.getText().toString());
                            values.put(MonHocProvider.SOCHI, et_SoChi.getText().toString());
                            getContentResolver().insert(MonHocProvider.CONTENT_URI, values);
                            loaddata();
                        }
                        break;
                    }
                    case "true-false":
                    {
                        tv_TestMa.setText("");
                        tv_TestSoChi.setText("So Chi Phai La So");
                        break;
                    }
                    case "false-true":
                    {
                        tv_TestMa.setText("Ma Phai La So");
                        tv_TestSoChi.setText("");
                        break;
                    }
                    case "false-false":
                    {
                        tv_TestMa.setText("Ma Phai La So");
                        tv_TestSoChi.setText("So Chi Phai La So");
                        break;
                    }
                }

            }
        });
        bt_Tim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_Ma.getText().toString().equalsIgnoreCase(""))
                {
                    loaddata();
                }
                else
                {
                    Uri uri=Uri.parse(MonHocProvider.CONTENT_URI+"/"+et_Ma.getText().toString());
                  Cursor cursor=  getContentResolver().query(uri,null,null,null,null);
                    if(cursor.moveToFirst())
                    {
                        dsmh=new ArrayList<MonHoc>();
                        do {
                            int ma=cursor.getInt(cursor.getColumnIndex(MonHocProvider.MAMONHOC));
                            String ten=cursor.getString(cursor.getColumnIndex(MonHocProvider.TENMONHOC));
                            int sochi=cursor.getInt(cursor.getColumnIndex(MonHocProvider.SOCHI));
                            MonHoc monHoc=new MonHoc(ma,ten,sochi);
                            dsmh.add(monHoc);
                        }while (cursor.moveToNext());
                        ArrayAdapter<MonHoc>adapter=new ArrayAdapter<MonHoc>(MainActivity.this,android.R.layout.simple_list_item_1,dsmh);
                        listView.setAdapter(adapter);
                    }
                }
            }
        });
        bt_Xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_Ma.getText().toString().equalsIgnoreCase(""))
                {
                    getContentResolver().delete(MonHocProvider.CONTENT_URI,null,null);
                    loaddata();
                }
                else
                {
                    Uri uri=Uri.parse(MonHocProvider.CONTENT_URI+"/"+et_Ma.getText().toString());
                    getContentResolver().delete(uri,null,null);
                    loaddata();
                }
            }
        });

    }
}
