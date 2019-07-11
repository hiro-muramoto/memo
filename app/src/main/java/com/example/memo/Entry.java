package com.example.memo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Entry {
    public final String title;
    public final String link;
    public final String summary;
    public final String published;

    public static String ns;

    public Entry(String title, String summary, String link, String published) {
        this.title = title;
        this.summary = summary;
        this.link = link;
        this.published = published;
    }

    // entryの内容を解析。
    // title、summary、またはlinkに遭遇した場合は、それぞれの readメソッドに渡す。
    // そうでなければ、タグをスキップ。
    public static Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {

        parser.require(XmlPullParser.START_TAG, ns, "entry");
        String title = null;
        String summary = null;
        String link = null;
        String published = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if (name.equals("title")) {
                title = readTitle(parser);
            } else if (name.equals("summary")) {
                summary = readSummary(parser);
            } else if (name.equals("link") && parser.getAttributeValue(null, "rel").equals("alternate")) {
                link = readLink(parser);
            } else if (name.equals("published")) {
                published = readPublished(parser);
            } else {
                skip(parser);
            }

        }

        return new Entry(title, summary, link, published);

    }

    // 文章内のtitleの中身の処理
    public static String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }

    // 文章内のlinkの中身の処理
    public static String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
        String link = "";
        parser.require(XmlPullParser.START_TAG, ns, "link");
        String tag = parser.getName();
        String relType = parser.getAttributeValue(null, "rel");

        if (tag.equals("link")) {
            if (relType.equals("alternate")){
                link = parser.getAttributeValue(null, "href");
                parser.nextTag();
            }
        }

        parser.require(XmlPullParser.END_TAG, ns, "link");

        return link;
    }

    // 文章内のsummaryの中身の処理
    public static String readSummary(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "summary");
        String summary = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "summary");
        return summary;
    }

    // 文章内のpublishedの中身の処理
    public static String readPublished(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "published");
        String date = readText(parser);
        String published = formatJST(date);
        parser.require(XmlPullParser.END_TAG, ns, "published");
        return published;
    }

    // テキストの時間を変換
    public static String formatJST(String date) {

        String result = "";

        try {

            // UTC時間 2019-07-04T14:59:57+09:00
            SimpleDateFormat utcdatestr = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            utcdatestr.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));

            Date utcdate = utcdatestr.parse(date);
            result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(utcdate);

        } catch(ParseException e) {
            e.printStackTrace();
        }

        return result;

    }


    // テキストの中身の抽出(タグがtitleとsummaryとpublishedの場合)
    public static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    // 不要なタグをスキップ
    public static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}