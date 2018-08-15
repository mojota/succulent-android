package com.mojota.succulent;

import com.google.gson.Gson;

/**
 * Created by mojota on 18-8-1.
 */
public class TestUtil {
    public static String getDiaryList() {
        return "{\"code\":\"200\",\"msg\":\"\",\"list\":[{\"noteTitle\":\"仙人球\"," +
                "\"updateTime\":\"2018-08-02 10:00\",\"permission\":\"0\"," +
                "\"likeCount\":\"999\",\"hasLike\":\"0\",\"picUrls\":[\"https://ss0.baidu" +
                ".com/6ONWsjip0QIZ8tyhnq/it/u=2875812309,3386562784&fm=58\"]}," +
                "{\"noteTitle\":\"虹之玉\",\"updateTime\":\"2018-08-02 10:05\"," +
                "\"permission\":\"1\",\"likeCount\":\"1\",\"hasLike\":\"1\"," +
                "\"picUrls\":[\"https://ss2.bdstatic" +
                ".com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=807985943," +
                "1351995441&fm=200&gp=0.jpg\"]},{\"noteTitle\":\"婴儿手指\"," +
                "\"updateTime\":\"2018-08-02 09:06\",\"permission\":\"0\"," +
                "\"likeCount\":\"99\",\"hasLike\":\"1\",\"picUrls\":[\"https://ss2.baidu" +
                ".com/6ONYsjip0QIZ8tyhnq/it/u=3497831567,3244393986&fm=58\"]}," +
                "{\"noteTitle\":\"雪莲\",\"updateTime\":\"2018-08-01 10:05\",\"permission\":\"1\"," +
                "\"likeCount\":\"0\",\"hasLike\":\"0\",\"picUrls\":[\"https://ss1.bdstatic" +
                ".com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3638332329," +
                "1145224074&fm=27&gp=0.jpg\"]},{\"noteTitle\":\"子持莲华\"," +
                "\"updateTime\":\"2018-08-01 08:05\",\"permission\":\"0\"," +
                "\"likeCount\":\"100\",\"hasLike\":\"1\",\"picUrls\":[\"https://ss2.bdstatic" +
                ".com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1099988064," +
                "3976727172&fm=27&gp=0.jpg\"]}]}";
    }

    public static String getLandscapingList(){
        return "{\"code\":\"200\",\"msg\":\"\",\"list\":[{\"noteTitle\":\"造个景\"," +
                "\"updateTime\":\"2018-08-02 10:00\",\"permission\":\"0\"," +
                "\"likeCount\":\"999\",\"hasLike\":\"0\",\"picUrls\":[\"https://ss0.baidu" +
                ".com/6ONWsjip0QIZ8tyhnq/it/u=2875812309,3386562784&fm=58\"," +
                "\"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=807985943," +
                "1351995441&fm=200&gp=0.jpg\"]},{\"noteTitle\":\"拼个盆\"," +
                "\"updateTime\":\"2018-08-02 10:05\",\"permission\":\"1\"," +
                "\"likeCount\":\"1\",\"hasLike\":\"1\",\"picUrls\":[\"https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1533718815649&di" +
                "=e0c909615935f966e33f370f7455198f&imgtype=0&src=http%3A%2F%2Fimgq.duitang" +
                ".com%2Fuploads%2Fitem%2F201505%2F10%2F20150510225505_VarSj.jpeg\"]}," +
                "{\"noteTitle\":\"摆盆\",\"updateTime\":\"2018-08-02 09:06\",\"permission\":\"0\"," +
                "\"likeCount\":\"99\",\"hasLike\":\"1\",\"picUrls\":[\"https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1533718815649&di" +
                "=e0c909615935f966e33f370f7455198f&imgtype=0&src=http%3A%2F%2Fimgq.duitang" +
                ".com%2Fuploads%2Fitem%2F201505%2F10%2F20150510225505_VarSj.jpeg\"," +
                "\"https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1533718844496&di" +
                "=ecd2d03ba3096182e9527425acd8981c&imgtype=0&src=http%3A%2F%2Fimages.jhrx" +
                ".cn%2Fattachment%2Fforum%2F201603%2F24%2F113022t2oza7xo3bzzhkbz.jpg\"," +
                "\"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1099988064," +
                "3976727172&fm=27&gp=0.jpg\"]},{\"noteTitle\":\"拼盘\",\"updateTime\":\"2018-08-01" +
                " 10:05\",\"permission\":\"1\",\"likeCount\":\"0\",\"hasLike\":\"0\"," +
                "\"picUrls\":[\"https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1533718844496&di" +
                "=ecd2d03ba3096182e9527425acd8981c&imgtype=0&src=http%3A%2F%2Fimages.jhrx" +
                ".cn%2Fattachment%2Fforum%2F201603%2F24%2F113022t2oza7xo3bzzhkbz.jpg\"," +
                "\"https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1533718877525&di" +
                "=ee003770244efbdd71303032f92f20e6&imgtype=0&src=http%3A%2F%2Fs10.sinaimg" +
                ".cn%2Fmw690%2F0024bWVagy6QsYZD5cl29%26690\",\"https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1533719912190&di" +
                "=c6cd336973aa16ec3954a742237a371d&imgtype=0&src=http%3A%2F%2Fs3.sinaimg" +
                ".cn%2Fmw690%2F003eiiLTgy70hQK8OnUf2%26690\",\"https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1533718877525&di" +
                "=ee003770244efbdd71303032f92f20e6&imgtype=0&src=http%3A%2F%2Fs10.sinaimg" +
                ".cn%2Fmw690%2F0024bWVagy6QsYZD5cl29%26690\",\"https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1533718877525&di" +
                "=ee003770244efbdd71303032f92f20e6&imgtype=0&src=http%3A%2F%2Fs10.sinaimg" +
                ".cn%2Fmw690%2F0024bWVagy6QsYZD5cl29%26690\",\"https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1533718877525&di" +
                "=ee003770244efbdd71303032f92f20e6&imgtype=0&src=http%3A%2F%2Fs10.sinaimg" +
                ".cn%2Fmw690%2F0024bWVagy6QsYZD5cl29%26690\"]},{\"noteTitle\":\"子持莲华\"," +
                "\"updateTime\":\"2018-08-01 08:05\",\"permission\":\"0\"," +
                "\"likeCount\":\"100\",\"hasLike\":\"1\",\"picUrls\":[\"https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1533718877525&di" +
                "=ee003770244efbdd71303032f92f20e6&imgtype=0&src=http%3A%2F%2Fs10.sinaimg" +
                ".cn%2Fmw690%2F0024bWVagy6QsYZD5cl29%26690\",\"https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1534313664&di" +
                "=b905eb785671c948864121131edf66f3&imgtype=jpg&er=1&src=http%3A%2F%2Fimg4q" +
                ".duitang.com%2Fuploads%2Fitem%2F201505%2F03%2F20150503185151_GZQyT.jpeg\"," +
                "\"https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1533718985781&di" +
                "=55ef50e4144ed2406457d30d346c0b06&imgtype=0&src=http%3A%2F%2Fimg.zcool" +
                ".cn%2Fcommunity%2F01594b5541bad5000001e78c9c37d3.jpg\",\"https://timgsa" +
                ".baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534314743&di" +
                "=ed0cd3abe36faa4dc824472907afc359&imgtype=jpg&er=1&src=http%3A%2F" +
                "%2Fimg5.duitang.com%2Fuploads%2Fblog%2F201308%2F17%2F20130817161051_Hxz5F" +
                ".thumb.700_0.jpeg\"]},{\"noteTitle\":\"摆个盘盘\",\"updateTime\":\"2018-08-01 " +
                "08:05\",\"permission\":\"0\",\"likeCount\":\"100\",\"hasLike\":\"1\"," +
                "\"picUrls\":[\"https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1533718877525&di" +
                "=ee003770244efbdd71303032f92f20e6&imgtype=0&src=http%3A%2F%2Fs10.sinaimg" +
                ".cn%2Fmw690%2F0024bWVagy6QsYZD5cl29%26690\",\"https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1534313664&di" +
                "=b905eb785671c948864121131edf66f3&imgtype=jpg&er=1&src=http%3A%2F%2Fimg4q" +
                ".duitang.com%2Fuploads%2Fitem%2F201505%2F03%2F20150503185151_GZQyT.jpeg\"," +
                "\"\",\"https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1534314743&di" +
                "=ed0cd3abe36faa4dc824472907afc359&imgtype=jpg&er=1&src=http%3A%2F" +
                "%2Fimg5.duitang.com%2Fuploads%2Fblog%2F201308%2F17%2F20130817161051_Hxz5F" +
                ".thumb.700_0.jpeg\"]}]}";
    }

    public static String getDiryDetails(){
        return "{\"code\":\"200\",\"msg\":\"\",\"noteInfo\":{\"noteId\":\"001\"," +
                "\"noteTitle\":\"虹之玉\",\"updateTime\":\"2018-08-02 10:05\"," +
                "\"permission\":\"1\"},\"diarys\":[{\"diaryId\":\"00001\"," +
                "\"content\":\"上盆\",\"createTime\":\"2018-08-02 10:00\"," +
                "\"picUrls\":[\"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2875812309," +
                "3386562784&fm=58\"]},{\"diaryId\":\"00002\",\"content\":\"一周后\"," +
                "\"createTime\":\"2018-08-02 10:00\",\"picUrls\":[\"https://ss0.baidu" +
                ".com/6ONWsjip0QIZ8tyhnq/it/u=2875812309,3386562784&fm=58\",\"https://timgsa" +
                ".baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533718877525&di" +
                "=ee003770244efbdd71303032f92f20e6&imgtype=0&src=http%3A%2F%2Fs10.sinaimg" +
                ".cn%2Fmw690%2F0024bWVagy6QsYZD5cl29%26690\"]},{\"diaryId\":\"00002\"," +
                "\"content\":\"一周后\",\"createTime\":\"2018-08-02 10:00\"," +
                "\"picUrls\":[\"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2875812309," +
                "3386562784&fm=58\",\"https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1533718815649&di" +
                "=e0c909615935f966e33f370f7455198f&imgtype=0&src=http%3A%2F%2Fimgq.duitang" +
                ".com%2Fuploads%2Fitem%2F201505%2F10%2F20150510225505_VarSj.jpeg\"]}," +
                "{\"diaryId\":\"00002\",\"content\":\"一周后\",\"createTime\":\"2018-08-02 " +
                "10:00\",\"picUrls\":[\"https://ss1.bdstatic" +
                ".com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3638332329," +
                "1145224074&fm=27&gp=0.jpg\"]}]}";
    }
}
