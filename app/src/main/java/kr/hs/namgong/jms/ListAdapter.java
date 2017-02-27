package kr.hs.namgong.jms;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;



public class ListAdapter extends BaseAdapter{

    public String[] names ={"item1","item2","item3","item4","item5",};
    Context nContext;

    public ListAdapter (Context context) {
        nContext = context;

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
