package com.appsbl.dmarion.model;

import java.util.List;

/**
 * Created by HP on 06-11-2016.
 */

public class LeftPanel {


    /**
     * status : success
     * message :
     * setting_info : {"about_us":"http://datelinemarion.com/mdetail.asp?app=Y&hn=datelinemarion&l=about-dateline-marion-p630-120.htm","about_us_text":"About US","about_app_description":"Dateline Marion 1.0.0","about_app_text":"About Application","terms_of_service":"http://datelinemarion.com/target_form.asp?app=Y&pform=ContactUs","terms_of_service_text":"Terms of service","subscribe_url":"","subscribe_url_text":"Subscribe Today","contact_us":"http://datelinemarion.com/target_form.asp?app=Y&pform=ContactUs","contact_us_text":"Contact Us","settings_text":"Settings","rate_application_text":"Rate the Application","share_application_text":"Share Application","provision_one_text":"Provision1","provision_one_url":"","provision_two_text":"Provision1","provision_two_url":"","no_article_message":"No data available for selected category.","no_internet_message":"No Internet Connection Available.","left_header_background_colour":"#000000","left_header_text_colour":"#ffffff","left_header_selection_color":"#ff0000","left_header_ruler_colour":"#a3a3a3","left_subHeader_background_colour":"#303030","left_subHeader_text_colour":"#ffffff","left_subheader_selection_color":"#00ffff","left_subHeader_ruler_colour":"#000000","navigation_bar_background_colour":"#ffffff","navigation_bar_text_colour":"#000000","navigation_bar_icon_colour":"#000000","navigation_bar_logo_url":"http://datelinemarion.com/clients/datelinemarion/applogo1.jpg","powered_by_screen_URL":"http://bulletlink.com/powered_by_bulletlink.html","powered_by_screen_load_time":3,"city_name_for_weather":"Marion, IL","article_background_colour":"#ffffff","article_text_colour":"#000000","article_ruler_colour":"#C5BFBF","article_section_font_colour":"#AEAFB0","article_date_font_colour":"#AEAFB0","article_date_ago_limit":"30","first_article_background_colour":"#80000000","first_article_text_colour":"#ffffff","first_article_section_font_colour":"#AEAFB0","first_article_ruler_colour":"#000000","first_article_date_colour":"#AEAFB0","advertisement_URL":"http://datelinemarion.com/clients/datelinemarion/bottom-ad.htm","advertisement_click_URL":"http://yahoo.com","height_advertisement_view_in_percentage":0,"main_image_width_height_ratio":"20:30","image_background_colour":"#AEAFB0","left_panel_width_in_percentage":70,"weather_section_background_colour":"#dcdcdc","weather_city_colour":"#ff0000","weather_sub_category_colour":"#ff0000","weather_temperature_colour":"#ff0000","weather_ruler_colour":"#C5BFBF","weather_woeid":"12521918","weather_format":"F","application_share_text":"Check out 'Dateline Marion' mobile application..","application_share_url_android":"https://play.google.com/store/apps/details?id=com.appstra.datelinemarion (Android)","application_share_url_ios":"https://itunes.apple.com/us/app/id1155776660 (iOS) \n","android_app_version":"1.0.0","iOS_app_version":"1.1","article_share_text":""}
     * Category : [{"key":"Home","is_general_category":true,"data":[]},{"key":"News","is_general_category":false,"data":["Local news","Obituaries","Police and Courts"]},{"key":"Sports","is_general_category":false,"data":["Local Sports","Prep Scoreboard","SIU Saluki Sports"]},{"key":"Submit","is_general_category":false,"data":["Submit News"]}]
     */

    private String status;
    private String message;
    /**
     * about_us : http://datelinemarion.com/mdetail.asp?app=Y&hn=datelinemarion&l=about-dateline-marion-p630-120.htm
     * about_us_text : About US
     * about_app_description : Dateline Marion 1.0.0
     * about_app_text : About Application
     * terms_of_service : http://datelinemarion.com/target_form.asp?app=Y&pform=ContactUs
     * terms_of_service_text : Terms of service
     * subscribe_url :
     * subscribe_url_text : Subscribe Today
     * contact_us : http://datelinemarion.com/target_form.asp?app=Y&pform=ContactUs
     * contact_us_text : Contact Us
     * settings_text : Settings
     * rate_application_text : Rate the Application
     * share_application_text : Share Application
     * provision_one_text : Provision1
     * provision_one_url :
     * provision_two_text : Provision1
     * provision_two_url :
     * no_article_message : No data available for selected category.
     * no_internet_message : No Internet Connection Available.
     * left_header_background_colour : #000000
     * left_header_text_colour : #ffffff
     * left_header_selection_color : #ff0000
     * left_header_ruler_colour : #a3a3a3
     * left_subHeader_background_colour : #303030
     * left_subHeader_text_colour : #ffffff
     * left_subheader_selection_color : #00ffff
     * left_subHeader_ruler_colour : #000000
     * navigation_bar_background_colour : #ffffff
     * navigation_bar_text_colour : #000000
     * navigation_bar_icon_colour : #000000
     * navigation_bar_logo_url : http://datelinemarion.com/clients/datelinemarion/applogo1.jpg
     * powered_by_screen_URL : http://bulletlink.com/powered_by_bulletlink.html
     * powered_by_screen_load_time : 3
     * city_name_for_weather : Marion, IL
     * article_background_colour : #ffffff
     * article_text_colour : #000000
     * article_ruler_colour : #C5BFBF
     * article_section_font_colour : #AEAFB0
     * article_date_font_colour : #AEAFB0
     * article_date_ago_limit : 30
     * first_article_background_colour : #80000000
     * first_article_text_colour : #ffffff
     * first_article_section_font_colour : #AEAFB0
     * first_article_ruler_colour : #000000
     * first_article_date_colour : #AEAFB0
     * advertisement_URL : http://datelinemarion.com/clients/datelinemarion/bottom-ad.htm
     * advertisement_click_URL : http://yahoo.com
     * height_advertisement_view_in_percentage : 0
     * main_image_width_height_ratio : 20:30
     * image_background_colour : #AEAFB0
     * left_panel_width_in_percentage : 70
     * weather_section_background_colour : #dcdcdc
     * weather_city_colour : #ff0000
     * weather_sub_category_colour : #ff0000
     * weather_temperature_colour : #ff0000
     * weather_ruler_colour : #C5BFBF
     * weather_woeid : 12521918
     * weather_format : F
     * application_share_text : Check out 'Dateline Marion' mobile application..
     * application_share_url_android : https://play.google.com/store/apps/details?id=com.appstra.datelinemarion (Android)
     * application_share_url_ios : https://itunes.apple.com/us/app/id1155776660 (iOS)

     * android_app_version : 1.0.0
     * iOS_app_version : 1.1
     * article_share_text :
     */

    private SettingInfoBean setting_info;
    /**
     * key : Home
     * is_general_category : true
     * data : []
     */

    private List<CategoryBean> Category;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SettingInfoBean getSetting_info() {
        return setting_info;
    }

    public void setSetting_info(SettingInfoBean setting_info) {
        this.setting_info = setting_info;
    }

    public List<CategoryBean> getCategory() {
        return Category;
    }

    public void setCategory(List<CategoryBean> Category) {
        this.Category = Category;
    }

    public static class SettingInfoBean {
        private String about_us;
        private String about_us_text;
        private String about_app_description;
        private String about_app_text;
        private String terms_of_service;
        private String terms_of_service_text;
        private String subscribe_url;
        private String subscribe_url_text;
        private String contact_us;
        private String contact_us_text;
        private String settings_text;
        private String rate_application_text;
        private String share_application_text;
        private String provision_one_text;
        private String provision_one_url;
        private String provision_two_text;
        private String provision_two_url;
        private String no_article_message;
        private String no_internet_message;
        private String left_header_background_colour;
        private String left_header_text_colour;
        private String left_header_selection_color;
        private String left_header_ruler_colour;
        private String left_subHeader_background_colour;
        private String left_subHeader_text_colour;
        private String left_subheader_selection_color;
        private String left_subHeader_ruler_colour;
        private String navigation_bar_background_colour;
        private String navigation_bar_text_colour;
        private String navigation_bar_icon_colour;
        private String navigation_bar_logo_url;
        private String powered_by_screen_URL;
        private int powered_by_screen_load_time;
        private String city_name_for_weather;
        private String article_background_colour;
        private String article_text_colour;
        private String article_ruler_colour;
        private String article_section_font_colour;
        private String article_date_font_colour;
        private String article_date_ago_limit;
        private String first_article_background_colour;
        private String first_article_text_colour;
        private String first_article_section_font_colour;
        private String first_article_ruler_colour;
        private String first_article_date_colour;
        private String advertisement_URL;
        private String advertisement_click_URL;
        private int height_advertisement_view_in_percentage;
        private String main_image_width_height_ratio;
        private String image_background_colour;
        private int left_panel_width_in_percentage;
        private String weather_section_background_colour;
        private String weather_city_colour;
        private String weather_sub_category_colour;
        private String weather_temperature_colour;
        private String weather_ruler_colour;
        private String weather_woeid;
        private String weather_format;
        private String application_share_text;
        private String application_share_url_android;
        private String application_share_url_ios;
        private String android_app_version;
        private String iOS_app_version;
        private String article_share_text;

        public String getAbout_us() {
            return about_us;
        }

        public void setAbout_us(String about_us) {
            this.about_us = about_us;
        }

        public String getAbout_us_text() {
            return about_us_text;
        }

        public void setAbout_us_text(String about_us_text) {
            this.about_us_text = about_us_text;
        }

        public String getAbout_app_description() {
            return about_app_description;
        }

        public void setAbout_app_description(String about_app_description) {
            this.about_app_description = about_app_description;
        }

        public String getAbout_app_text() {
            return about_app_text;
        }

        public void setAbout_app_text(String about_app_text) {
            this.about_app_text = about_app_text;
        }

        public String getTerms_of_service() {
            return terms_of_service;
        }

        public void setTerms_of_service(String terms_of_service) {
            this.terms_of_service = terms_of_service;
        }

        public String getTerms_of_service_text() {
            return terms_of_service_text;
        }

        public void setTerms_of_service_text(String terms_of_service_text) {
            this.terms_of_service_text = terms_of_service_text;
        }

        public String getSubscribe_url() {
            return subscribe_url;
        }

        public void setSubscribe_url(String subscribe_url) {
            this.subscribe_url = subscribe_url;
        }

        public String getSubscribe_url_text() {
            return subscribe_url_text;
        }

        public void setSubscribe_url_text(String subscribe_url_text) {
            this.subscribe_url_text = subscribe_url_text;
        }

        public String getContact_us() {
            return contact_us;
        }

        public void setContact_us(String contact_us) {
            this.contact_us = contact_us;
        }

        public String getContact_us_text() {
            return contact_us_text;
        }

        public void setContact_us_text(String contact_us_text) {
            this.contact_us_text = contact_us_text;
        }

        public String getSettings_text() {
            return settings_text;
        }

        public void setSettings_text(String settings_text) {
            this.settings_text = settings_text;
        }

        public String getRate_application_text() {
            return rate_application_text;
        }

        public void setRate_application_text(String rate_application_text) {
            this.rate_application_text = rate_application_text;
        }

        public String getShare_application_text() {
            return share_application_text;
        }

        public void setShare_application_text(String share_application_text) {
            this.share_application_text = share_application_text;
        }

        public String getProvision_one_text() {
            return provision_one_text;
        }

        public void setProvision_one_text(String provision_one_text) {
            this.provision_one_text = provision_one_text;
        }

        public String getProvision_one_url() {
            return provision_one_url;
        }

        public void setProvision_one_url(String provision_one_url) {
            this.provision_one_url = provision_one_url;
        }

        public String getProvision_two_text() {
            return provision_two_text;
        }

        public void setProvision_two_text(String provision_two_text) {
            this.provision_two_text = provision_two_text;
        }

        public String getProvision_two_url() {
            return provision_two_url;
        }

        public void setProvision_two_url(String provision_two_url) {
            this.provision_two_url = provision_two_url;
        }

        public String getNo_article_message() {
            return no_article_message;
        }

        public void setNo_article_message(String no_article_message) {
            this.no_article_message = no_article_message;
        }

        public String getNo_internet_message() {
            return no_internet_message;
        }

        public void setNo_internet_message(String no_internet_message) {
            this.no_internet_message = no_internet_message;
        }

        public String getLeft_header_background_colour() {
            return left_header_background_colour;
        }

        public void setLeft_header_background_colour(String left_header_background_colour) {
            this.left_header_background_colour = left_header_background_colour;
        }

        public String getLeft_header_text_colour() {
            return left_header_text_colour;
        }

        public void setLeft_header_text_colour(String left_header_text_colour) {
            this.left_header_text_colour = left_header_text_colour;
        }

        public String getLeft_header_selection_color() {
            return left_header_selection_color;
        }

        public void setLeft_header_selection_color(String left_header_selection_color) {
            this.left_header_selection_color = left_header_selection_color;
        }

        public String getLeft_header_ruler_colour() {
            return left_header_ruler_colour;
        }

        public void setLeft_header_ruler_colour(String left_header_ruler_colour) {
            this.left_header_ruler_colour = left_header_ruler_colour;
        }

        public String getLeft_subHeader_background_colour() {
            return left_subHeader_background_colour;
        }

        public void setLeft_subHeader_background_colour(String left_subHeader_background_colour) {
            this.left_subHeader_background_colour = left_subHeader_background_colour;
        }

        public String getLeft_subHeader_text_colour() {
            return left_subHeader_text_colour;
        }

        public void setLeft_subHeader_text_colour(String left_subHeader_text_colour) {
            this.left_subHeader_text_colour = left_subHeader_text_colour;
        }

        public String getLeft_subheader_selection_color() {
            return left_subheader_selection_color;
        }

        public void setLeft_subheader_selection_color(String left_subheader_selection_color) {
            this.left_subheader_selection_color = left_subheader_selection_color;
        }

        public String getLeft_subHeader_ruler_colour() {
            return left_subHeader_ruler_colour;
        }

        public void setLeft_subHeader_ruler_colour(String left_subHeader_ruler_colour) {
            this.left_subHeader_ruler_colour = left_subHeader_ruler_colour;
        }

        public String getNavigation_bar_background_colour() {
            return navigation_bar_background_colour;
        }

        public void setNavigation_bar_background_colour(String navigation_bar_background_colour) {
            this.navigation_bar_background_colour = navigation_bar_background_colour;
        }

        public String getNavigation_bar_text_colour() {
            return navigation_bar_text_colour;
        }

        public void setNavigation_bar_text_colour(String navigation_bar_text_colour) {
            this.navigation_bar_text_colour = navigation_bar_text_colour;
        }

        public String getNavigation_bar_icon_colour() {
            return navigation_bar_icon_colour;
        }

        public void setNavigation_bar_icon_colour(String navigation_bar_icon_colour) {
            this.navigation_bar_icon_colour = navigation_bar_icon_colour;
        }

        public String getNavigation_bar_logo_url() {
            return navigation_bar_logo_url;
        }

        public void setNavigation_bar_logo_url(String navigation_bar_logo_url) {
            this.navigation_bar_logo_url = navigation_bar_logo_url;
        }

        public String getPowered_by_screen_URL() {
            return powered_by_screen_URL;
        }

        public void setPowered_by_screen_URL(String powered_by_screen_URL) {
            this.powered_by_screen_URL = powered_by_screen_URL;
        }

        public int getPowered_by_screen_load_time() {
            return powered_by_screen_load_time;
        }

        public void setPowered_by_screen_load_time(int powered_by_screen_load_time) {
            this.powered_by_screen_load_time = powered_by_screen_load_time;
        }

        public String getCity_name_for_weather() {
            return city_name_for_weather;
        }

        public void setCity_name_for_weather(String city_name_for_weather) {
            this.city_name_for_weather = city_name_for_weather;
        }

        public String getArticle_background_colour() {
            return article_background_colour;
        }

        public void setArticle_background_colour(String article_background_colour) {
            this.article_background_colour = article_background_colour;
        }

        public String getArticle_text_colour() {
            return article_text_colour;
        }

        public void setArticle_text_colour(String article_text_colour) {
            this.article_text_colour = article_text_colour;
        }

        public String getArticle_ruler_colour() {
            return article_ruler_colour;
        }

        public void setArticle_ruler_colour(String article_ruler_colour) {
            this.article_ruler_colour = article_ruler_colour;
        }

        public String getArticle_section_font_colour() {
            return article_section_font_colour;
        }

        public void setArticle_section_font_colour(String article_section_font_colour) {
            this.article_section_font_colour = article_section_font_colour;
        }

        public String getArticle_date_font_colour() {
            return article_date_font_colour;
        }

        public void setArticle_date_font_colour(String article_date_font_colour) {
            this.article_date_font_colour = article_date_font_colour;
        }

        public String getArticle_date_ago_limit() {
            return article_date_ago_limit;
        }

        public void setArticle_date_ago_limit(String article_date_ago_limit) {
            this.article_date_ago_limit = article_date_ago_limit;
        }

        public String getFirst_article_background_colour() {
            return first_article_background_colour;
        }

        public void setFirst_article_background_colour(String first_article_background_colour) {
            this.first_article_background_colour = first_article_background_colour;
        }

        public String getFirst_article_text_colour() {
            return first_article_text_colour;
        }

        public void setFirst_article_text_colour(String first_article_text_colour) {
            this.first_article_text_colour = first_article_text_colour;
        }

        public String getFirst_article_section_font_colour() {
            return first_article_section_font_colour;
        }

        public void setFirst_article_section_font_colour(String first_article_section_font_colour) {
            this.first_article_section_font_colour = first_article_section_font_colour;
        }

        public String getFirst_article_ruler_colour() {
            return first_article_ruler_colour;
        }

        public void setFirst_article_ruler_colour(String first_article_ruler_colour) {
            this.first_article_ruler_colour = first_article_ruler_colour;
        }

        public String getFirst_article_date_colour() {
            return first_article_date_colour;
        }

        public void setFirst_article_date_colour(String first_article_date_colour) {
            this.first_article_date_colour = first_article_date_colour;
        }

        public String getAdvertisement_URL() {
            return advertisement_URL;
        }

        public void setAdvertisement_URL(String advertisement_URL) {
            this.advertisement_URL = advertisement_URL;
        }

        public String getAdvertisement_click_URL() {
            return advertisement_click_URL;
        }

        public void setAdvertisement_click_URL(String advertisement_click_URL) {
            this.advertisement_click_URL = advertisement_click_URL;
        }

        public int getHeight_advertisement_view_in_percentage() {
            return height_advertisement_view_in_percentage;
        }

        public void setHeight_advertisement_view_in_percentage(int height_advertisement_view_in_percentage) {
            this.height_advertisement_view_in_percentage = height_advertisement_view_in_percentage;
        }

        public String getMain_image_width_height_ratio() {
            return main_image_width_height_ratio;
        }

        public void setMain_image_width_height_ratio(String main_image_width_height_ratio) {
            this.main_image_width_height_ratio = main_image_width_height_ratio;
        }

        public String getImage_background_colour() {
            return image_background_colour;
        }

        public void setImage_background_colour(String image_background_colour) {
            this.image_background_colour = image_background_colour;
        }

        public int getLeft_panel_width_in_percentage() {
            return left_panel_width_in_percentage;
        }

        public void setLeft_panel_width_in_percentage(int left_panel_width_in_percentage) {
            this.left_panel_width_in_percentage = left_panel_width_in_percentage;
        }

        public String getWeather_section_background_colour() {
            return weather_section_background_colour;
        }

        public void setWeather_section_background_colour(String weather_section_background_colour) {
            this.weather_section_background_colour = weather_section_background_colour;
        }

        public String getWeather_city_colour() {
            return weather_city_colour;
        }

        public void setWeather_city_colour(String weather_city_colour) {
            this.weather_city_colour = weather_city_colour;
        }

        public String getWeather_sub_category_colour() {
            return weather_sub_category_colour;
        }

        public void setWeather_sub_category_colour(String weather_sub_category_colour) {
            this.weather_sub_category_colour = weather_sub_category_colour;
        }

        public String getWeather_temperature_colour() {
            return weather_temperature_colour;
        }

        public void setWeather_temperature_colour(String weather_temperature_colour) {
            this.weather_temperature_colour = weather_temperature_colour;
        }

        public String getWeather_ruler_colour() {
            return weather_ruler_colour;
        }

        public void setWeather_ruler_colour(String weather_ruler_colour) {
            this.weather_ruler_colour = weather_ruler_colour;
        }

        public String getWeather_woeid() {
            return weather_woeid;
        }

        public void setWeather_woeid(String weather_woeid) {
            this.weather_woeid = weather_woeid;
        }

        public String getWeather_format() {
            return weather_format;
        }

        public void setWeather_format(String weather_format) {
            this.weather_format = weather_format;
        }

        public String getApplication_share_text() {
            return application_share_text;
        }

        public void setApplication_share_text(String application_share_text) {
            this.application_share_text = application_share_text;
        }

        public String getApplication_share_url_android() {
            return application_share_url_android;
        }

        public void setApplication_share_url_android(String application_share_url_android) {
            this.application_share_url_android = application_share_url_android;
        }

        public String getApplication_share_url_ios() {
            return application_share_url_ios;
        }

        public void setApplication_share_url_ios(String application_share_url_ios) {
            this.application_share_url_ios = application_share_url_ios;
        }

        public String getAndroid_app_version() {
            return android_app_version;
        }

        public void setAndroid_app_version(String android_app_version) {
            this.android_app_version = android_app_version;
        }

        public String getIOS_app_version() {
            return iOS_app_version;
        }

        public void setIOS_app_version(String iOS_app_version) {
            this.iOS_app_version = iOS_app_version;
        }

        public String getArticle_share_text() {
            return article_share_text;
        }

        public void setArticle_share_text(String article_share_text) {
            this.article_share_text = article_share_text;
        }
    }

    public static class CategoryBean {
        private String key;
        private boolean is_general_category;
        private List<String> data;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public boolean isIs_general_category() {
            return is_general_category;
        }

        public void setIs_general_category(boolean is_general_category) {
            this.is_general_category = is_general_category;
        }

        public List<String> getData() {
            return data;
        }

        public void setData(List<String> data) {
            this.data = data;
        }
    }
}
