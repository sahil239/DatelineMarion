package com.appsbl.dmarion;

import com.appsbl.dmarion.model.NewsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 04-11-2016.
 */

public class Constants {

    public static String newsUrl = "http://www.020103050.com/clients/020103050/generalnews-a2.json";
    public static String leftPanelUrl = "http://www.020103050.com/clients/020103050/leftpanel-a2.json";

    public static String dataFilePath = "/Dateline marion/data/";
    public static String newsFile = "generalnews-a2.json";
    public static String leftPanelFile = "leftpanel-a2.json";


    public static String databaseName = "Dateline_Marion.db";
    public static String newsTable = "news";
    public static String bookmarksTable = "bookmarks";

    public static List<NewsModel.GeneralNewsBean> newsArrayList = new ArrayList<>();


}
