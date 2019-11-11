package xyz.dizhang.xmlparsingdemo.XmlParserDemo;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import xyz.dizhang.xmlparsingdemo.R;
import xyz.dizhang.xmlparsingdemo.XMLHelper.SAXParserHelper;
import xyz.dizhang.xmlparsingdemo.entity.Channel;

public class SAXParserDemo extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        SimpleAdapter adapter = null;
        try {
            adapter = new SimpleAdapter(this, getData(), R.layout.list, new String[]{"name", "id"}, new int[]{
                    R.id.textName, R.id.textId
            });
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setListAdapter(adapter);
    }

    private List<Map<String, String>> getData() throws ParserConfigurationException, SAXException, IOException {
        List<Channel> list;
        list = getChannelList();
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", list.get(i).getName());
            map.put("id", list.get(i).getId());
            mapList.add(map);
        }
        return mapList;
    }

    private List<Channel> getChannelList() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser;
        parser = factory.newSAXParser();
        XMLReader xmlReader = parser.getXMLReader();
        SAXParserHelper helperHandler = new SAXParserHelper();
        xmlReader.setContentHandler(helperHandler);
        InputStream stream = getResources().openRawResource(R.raw.channels);
        InputSource is = new InputSource(stream);
        xmlReader.parse(is);
        return helperHandler.getList();
    }


}
