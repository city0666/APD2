package lazylist;

import java.util.ArrayList;
import java.util.HashMap;

import com.justshan.pinelope.PinsPage;
import com.justshan.pinelope.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    //private String[] data;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    
//    public LazyAdapter(Activity a, ArrayList<HashMap<String,String>> data, int textViewResourceId, String[] info, int[] views){
//    //public LazyAdapter(Activity a, int resource, int textViewResourceId, String[] objects) {
//    //public LazyAdapter(Activity a, String[] d) {
//        activity = a;
//        //data = objects;
//        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        imageLoader=new ImageLoader(activity.getApplicationContext());
//    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public Object getItemEx(int position) {
        return data.get(position);
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.lazyitem, null);

        //TextView text=(TextView)vi.findViewById(R.id.textgrid);;
        ImageView image=(ImageView)vi.findViewById(R.id.imagegrid);
        
        HashMap<String, String> smart = new HashMap<String, String>();
        smart = data.get(position);
        
        //text.setText("item "+position);
        imageLoader.DisplayImage(smart.get(PinsPage.KEY_IMG), image);
        return vi;
    }
}