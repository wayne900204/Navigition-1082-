package com.example.fffffff.MESSAGEBOARD;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fffffff.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Message_fragment extends Fragment {
    private static final String TAG = "MainActivity";//測試用的LogT;
    EditText EtSystemIn, EtTitleIn;
    Button BtAddArticle;
    MyAdapter Adapter;
    Context context;

    ArrayList<HashMap<String, String>> List = new ArrayList<>();
    //對應資料庫我們適用ArrayList<HashMap<String,String>>型態
    RecyclerView Rc;

    //使用 ArticleSQL
   public Message_fragment(Context context){
     this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_fragment,container,false);
        final articleSQL ArticleSQL = new articleSQL(context);

        EtSystemIn = view.findViewById(R.id.editText);
        EtTitleIn = view.findViewById(R.id.ETtitle);
        BtAddArticle = view.findViewById(R.id.button12);
        Rc = view.findViewById(R.id.rc);
        List = ArticleSQL.getDATA();

        BtAddArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!EtSystemIn.getText().toString().equals("") && !EtTitleIn.getText().toString().equals("")) {
                    Adapter.addItem(EtTitleIn.getText().toString(), EtSystemIn.getText().toString());
                    //使用addItem這個方法＋並把輸入的值轉成字串帶入
                    ArticleSQL.insertSETDATE(EtTitleIn.getText().toString(), EtSystemIn.getText().toString());
                    //使用ArticleSQL這個class裡面的自訂的insert方法轉成字串給他，使資料庫可以儲存起來
                    EtTitleIn.setText(null);
                    EtSystemIn.setText(null);
                } else {
                    Toast toast = Toast.makeText(context, "此處不能為空白", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER | Gravity.TOP, 0, 0);
                    toast.show();
                }
            }
        });
        Rc.setLayoutManager(new LinearLayoutManager(context));
        Adapter = new MyAdapter(List,context,ArticleSQL);
        Rc.setAdapter(Adapter);

        return  view;
    }
}
