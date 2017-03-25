# PhotoPickDemo
个人学习：shared sdk；
gridview；
ble蓝牙；
通知栏操作；
数据库DBFlow；

``` java
        Document doc = Jsoup.parse(mHtmlString);
        Log.d(TAG,doc.body().select("img").first().attr("src"));
        for (Element x:doc.body().children()){
            if (x.tagName().equals("h3")) {
                Log.d(TAG,"H3"+x.text());
                Elements lis=x.nextElementSibling().children();
                for (Element li:lis){
                    Log.e(TAG,li.text());
                    Element a=li.getElementsByTag("a").first();
                    Log.d(TAG,a.attr("href"));
                    Elements imgs=li.getElementsByTag("img");
                    for (Element img:imgs){
                        Log.e(TAG,img.attr("src"));
                    }
                }
            }
        }
    }


```
