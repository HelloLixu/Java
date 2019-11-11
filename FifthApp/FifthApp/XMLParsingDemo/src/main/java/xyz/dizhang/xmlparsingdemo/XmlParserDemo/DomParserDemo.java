package xyz.dizhang.xmlparsingdemo.XmlParserDemo;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.dizhang.xmlparsingdemo.R;
import xyz.dizhang.xmlparsingdemo.XMLHelper.DomParserHelper;
import xyz.dizhang.xmlparsingdemo.entity.Channel;

public class DomParserDemo extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SimpleAdapter adapter = new SimpleAdapter(this, getData(),
                R.layout.list, new String[]{"id", "name"}, new int[]{
                R.id.textId, R.id.textName});
        setListAdapter(adapter);
    }

    private List<Map<String, String>> getData() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        InputStream stream = getResources().openRawResource(R.raw.channels);
        List<Channel> channlist = DomParserHelper.getChannelList(stream);

        for (int i = 0; i < channlist.size(); i++) {
            Map<String, String> map = new HashMap<String, String>();
            Channel chann = channlist.get(i);
            map.put("id", chann.getId());
            map.put("url", chann.getUrl());
            map.put("name", chann.getName());
            list.add(map);
        }

        return list;
    }
}
