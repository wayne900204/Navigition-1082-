package com.example.fffffff.MESSAGEBOARD;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fffffff.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    public ArrayList<HashMap<String, String>> MapList;
    //這裡的List跟上面的list不一樣這裡是選告一個這個形態的東西
    Context context;
    articleSQL ArticleSQL;
    public MyAdapter(ArrayList<HashMap<String, String>> list, Context context,articleSQL ArticleSQL) {
        this.MapList = list;
        this.context = context;
        this.ArticleSQL = ArticleSQL;
    }//Article裡面的getDate



    public void addItem(String title, String str) {

        HashMap<String, String> map = new HashMap<>();
        //在宣告一個map 來put 進去
        map.put("Title", title);//注意 這裡要跟資料庫的insert 名稱一樣
        map.put("Article", str);
        MapList.add(map);//把這邊的map 傳給MapList
        notifyDataSetChanged();
        //需要告訴list你需要更新你的畫面//有點類似重整


    }

    public void removeItem(int position) {

        ArticleSQL.DELETEDATE(MapList.get(MapList.size() - position - 1).get("Title"));//資料庫刪除
        MapList.remove(MapList.size() - position - 1);//移除MapList
        notifyItemRemoved(position);//刪掉多的畫面更新 有點類似重整
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.TxItemSetArticle.setText(MapList.get(MapList.size() - i - 1).get("Article"));//取KEY值HasMap的用法
        viewHolder.TxItemSetTitle.setText(MapList.get(MapList.size() - i - 1).get("Title"));
    }

    @Override
    public int getItemCount() {//recyclerview要顯示幾筆資料

        return MapList.size();//要告訴總共有幾個長度
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView TxItemSetArticle;
        TextView TxItemShowTest;
        TextView TxItemSetTitle;
        TextView EtDialogTitle;
        ImageButton ImgBtEdit;
        TextView EtDialogArticle;


        ViewHolder(final View itemView) {
            super(itemView);
            TxItemSetArticle = itemView.findViewById(R.id.articletx);
            TxItemSetTitle = itemView.findViewById(R.id.title1);
            TxItemShowTest = itemView.findViewById(R.id.showtx);
            ImgBtEdit = itemView.findViewById(R.id.ImgBtEdit);


            ImgBtEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PopupMenu popup = new PopupMenu(ImgBtEdit.getContext(), v);
                    popup.getMenuInflater().inflate(R.menu.editmenu, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.MenuEdit:
                                    MapList = ArticleSQL.getDATA();//就算update正常，Maplist需要重抓資料
                                    final Dialog dialog = new Dialog(context);//建立一個dialog
                                    dialog.setContentView(R.layout.editdialog);
                                    EtDialogTitle = dialog.findViewById(R.id.EtDialogTitle);
                                    EtDialogArticle = dialog.findViewById(R.id.EtDialogArticle);
                                    Button BtDialogOK = dialog.findViewById(R.id.BtDialogOK);
                                    WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
                                    dialog.getWindow().setAttributes(lp);
                                    EtDialogTitle.setText(MapList.get(MapList.size() - getAdapterPosition() - 1).get("Title"));//getAdapterPosition()你地幾個item的位置
                                    EtDialogArticle.setText(MapList.get(MapList.size() - getAdapterPosition() - 1).get("Article"));//getAdapterPosition()知道你點的是第幾個item
                                    BtDialogOK.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {//在dialog裡面按確定之後要做什麼事情
                                            if (!EtDialogArticle.getText().toString().equals("") && !EtDialogTitle.getText().toString().equals("")) {
                                                ArticleSQL.upDate(EtDialogTitle.getText().toString(), EtDialogArticle.getText().toString(),MapList.size() - getAdapterPosition());
                                                TxItemSetTitle.setText(EtDialogTitle.getText().toString());
                                                TxItemSetArticle.setText(EtDialogArticle.getText().toString());
                                                dialog.dismiss();
                                            } else {
                                                Toast toast = Toast.makeText(context, "此欄位不能為空白", Toast.LENGTH_SHORT);
                                                toast.setGravity(Gravity.CENTER | Gravity.TOP, 0, 0);
                                                toast.show();
                                            }
                                        }
                                    });
                                    dialog.show();

                                    break;
                                case R.id.MenuDelete:
                                    Toast.makeText(context, "刪除",
                                            Toast.LENGTH_SHORT).show();
                                    removeItem(getAdapterPosition());
                                    break;
                            }
                            return true;
                        }

                    });
                    popup.show();

                }
            });
        }

    }
}