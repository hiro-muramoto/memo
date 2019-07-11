package com.example.memo;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RssXmlParser {

    // We don't use namespaces
    private static final String ns = null;

    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();

            // XMLの名前空間に関する処理を使用するをfalse
            // http://outofmem.hatenablog.com/entry/2014/09/09/055113
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);

            // 第二引数 文字コード
            parser.setInput(in, "utf-8");

            // START_TAGまたはEND_TAGの場合はeventを返し、それ以外の場合は例外をスローする。
            parser.nextTag();

            return readFeed(parser);

        } finally {
            in.close();
        }
    }

    // <entry>～</entry>までを返す
    public List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "feed");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            // entryを探す
            if (name.equals("entry")) {
                entries.add(Entry.readEntry(parser));
            } else {
                Entry.skip(parser);
            }
        }
        return entries;
    }

}

