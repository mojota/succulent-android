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
                "{\"noteTitle\":\"虹之玉虹之玉虹之玉虹之玉虹之玉虹之玉虹之玉虹之玉虹之玉\",\"updateTime\":\"2018-08-02 10:05\"," +
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
                "\"likeCount\":\"999\",\"hasLike\":\"0\",\"picUrls\":[\"http://imgsrc.baidu" +
                ".com/baike/pic/item/b812c8fcc3cec3fd15f34ab1dc88d43f86942721.jpg\"," +
                "\"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=807985943," +
                "1351995441&fm=200&gp=0.jpg\"]}," +
                "{\"noteTitle\":\"拼个盆拼个盆拼个盆拼个盆拼个盆拼个盆拼个盆拼个盆拼个盆拼个盆拼个盆拼个盆拼个盆拼个盆\"," +
                "\"updateTime\":\"2018-08-02 10:05\",\"permission\":\"1\"," +
                "\"likeCount\":\"1\",\"hasLike\":\"1\",\"picUrls\":[\"https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1533718815649&di" +
                "=e0c909615935f966e33f370f7455198f&imgtype=0&src=http%3A%2F%2Fimgq.duitang" +
                ".com%2Fuploads%2Fitem%2F201505%2F10%2F20150510225505_VarSj.jpeg\"]}," +
                "{\"noteTitle\":\"摆盆\",\"updateTime\":\"2018-08-02 09:06\"," +
                "\"permission\":\"0\",\"likeCount\":\"99\",\"hasLike\":\"1\"," +
                "\"picUrls\":[\"https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1533718815649&di" +
                "=e0c909615935f966e33f370f7455198f&imgtype=0&src=http%3A%2F%2Fimgq.duitang" +
                ".com%2Fuploads%2Fitem%2F201505%2F10%2F20150510225505_VarSj.jpeg\"," +
                "\"https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1533718844496&di" +
                "=ecd2d03ba3096182e9527425acd8981c&imgtype=0&src=http%3A%2F%2Fimages.jhrx" +
                ".cn%2Fattachment%2Fforum%2F201603%2F24%2F113022t2oza7xo3bzzhkbz.jpg\"," +
                "\"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1099988064," +
                "3976727172&fm=27&gp=0.jpg\"]},{\"noteTitle\":\"拼盘\"," +
                "\"updateTime\":\"2018-08-01 10:05\",\"permission\":\"1\"," +
                "\"likeCount\":\"0\",\"hasLike\":\"0\",\"picUrls\":[\"https://timgsa.baidu" +
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
                ".thumb.700_0.jpeg\"]},{\"noteTitle\":\"摆盆\",\"updateTime\":\"2018-08-02 " +
                "09:06\",\"permission\":\"0\",\"likeCount\":\"99\",\"hasLike\":\"1\"," +
                "\"picUrls\":[\"https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1533718815649&di" +
                "=e0c909615935f966e33f370f7455198f&imgtype=0&src=http%3A%2F%2Fimgq.duitang" +
                ".com%2Fuploads%2Fitem%2F201505%2F10%2F20150510225505_VarSj.jpeg\"," +
                "\"https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1533718844496&di" +
                "=ecd2d03ba3096182e9527425acd8981c&imgtype=0&src=http%3A%2F%2Fimages.jhrx" +
                ".cn%2Fattachment%2Fforum%2F201603%2F24%2F113022t2oza7xo3bzzhkbz.jpg\"," +
                "\"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1099988064," +
                "3976727172&fm=27&gp=0.jpg\"]},{\"noteTitle\":\"拼盘\"," +
                "\"updateTime\":\"2018-08-01 10:05\",\"permission\":\"1\"," +
                "\"likeCount\":\"0\",\"hasLike\":\"0\",\"picUrls\":[\"https://timgsa.baidu" +
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
                ".thumb.700_0.jpeg\"]},{\"noteTitle\":\"摆盆\",\"updateTime\":\"2018-08-02 " +
                "09:06\",\"permission\":\"0\",\"likeCount\":\"99\",\"hasLike\":\"1\"," +
                "\"picUrls\":[\"https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1533718815649&di" +
                "=e0c909615935f966e33f370f7455198f&imgtype=0&src=http%3A%2F%2Fimgq.duitang" +
                ".com%2Fuploads%2Fitem%2F201505%2F10%2F20150510225505_VarSj.jpeg\"," +
                "\"https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1533718844496&di" +
                "=ecd2d03ba3096182e9527425acd8981c&imgtype=0&src=http%3A%2F%2Fimages.jhrx" +
                ".cn%2Fattachment%2Fforum%2F201603%2F24%2F113022t2oza7xo3bzzhkbz.jpg\"," +
                "\"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1099988064," +
                "3976727172&fm=27&gp=0.jpg\"]},{\"noteTitle\":\"拼盘\"," +
                "\"updateTime\":\"2018-08-01 10:05\",\"permission\":\"1\"," +
                "\"likeCount\":\"0\",\"hasLike\":\"0\",\"picUrls\":[\"https://timgsa.baidu" +
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

    public static String getFamilyList() {
        return "{\"code\":\"200\",\"msg\":\"\",\"list\":[{\"familyId\":\"1001\"," +
                "\"familyName\":\"景天科\",\"genusList\":[{\"genusId\":\"1001000\"," +
                "\"genusName\":\"全部\"},{\"genusId\":\"1001001\",\"genusName\":\"莲华掌属\"}," +
                "{\"genusId\":\"1001002\",\"genusName\":\"仙女杯属\"},{\"genusId\":\"1001003\"," +
                "\"genusName\":\"长生草属\"},{\"genusId\":\"1001004\",\"genusName\":\"拟石莲属\"}]}," +
                "{\"familyId\":\"1002\",\"familyName\":\"大㦸科\"," +
                "\"genusList\":[{\"genusId\":\"1002000\",\"genusName\":\"全部\"}," +
                "{\"genusId\":\"1002001\",\"genusName\":\"风车草属\"},{\"genusId\":\"1002002\"," +
                "\"genusName\":\"瓦松属\"},{\"genusId\":\"1002003\",\"genusName\":\"魔南景天属\"}," +
                "{\"genusId\":\"1002004\",\"genusName\":\"十二卷属\"}]},{\"familyId\":\"1003\"," +
                "\"familyName\":\"龙舌兰科\",\"genusList\":[{\"genusId\":\"1003000\"," +
                "\"genusName\":\"全部\"},{\"genusId\":\"1003001\",\"genusName\":\"圆筒仙人掌属\"}," +
                "{\"genusId\":\"1003002\",\"genusName\":\"仙女杯属\"},{\"genusId\":\"1003003\"," +
                "\"genusName\":\"长生草属\"},{\"genusId\":\"1003004\",\"genusName\":\"拟石莲属\"}]}," +
                "{\"familyId\":\"1004\",\"familyName\":\"番杏科\"," +
                "\"genusList\":[{\"genusId\":\"1001000\",\"genusName\":\"全部\"}," +
                "{\"genusId\":\"1001001\",\"genusName\":\"莲华掌属\"},{\"genusId\":\"1001002\"," +
                "\"genusName\":\"仙女杯属\"},{\"genusId\":\"1001003\",\"genusName\":\"长生草属\"}," +
                "{\"genusId\":\"1001004\",\"genusName\":\"拟石莲属\"}]},{\"familyId\":\"1005\"," +
                "\"familyName\":\"百合科\",\"genusList\":[{\"genusId\":\"1001000\"," +
                "\"genusName\":\"全部\"},{\"genusId\":\"1001001\",\"genusName\":\"莲华掌属\"}," +
                "{\"genusId\":\"1001002\",\"genusName\":\"仙女杯属\"},{\"genusId\":\"1001003\"," +
                "\"genusName\":\"长生草属\"},{\"genusId\":\"1001004\",\"genusName\":\"拟石莲属\"}]}," +
                "{\"familyId\":\"1001\",\"familyName\":\"景天科\"},{\"familyId\":\"1002\"," +
                "\"familyName\":\"大㦸科\"},{\"familyId\":\"1003\",\"familyName\":\"龙舌兰科\"}," +
                "{\"familyId\":\"1004\",\"familyName\":\"番杏科\"},{\"familyId\":\"1005\"," +
                "\"familyName\":\"百合科\"},{\"familyId\":\"1001\",\"familyName\":\"景天科\"}," +
                "{\"familyId\":\"1002\",\"familyName\":\"大㦸科\"},{\"familyId\":\"1003\"," +
                "\"familyName\":\"龙舌兰科\"},{\"familyId\":\"1004\",\"familyName\":\"番杏科\"}," +
                "{\"familyId\":\"1005\",\"familyName\":\"百合科\"},{\"familyId\":\"1004\"," +
                "\"familyName\":\"番杏科\",\"genusList\":[{\"genusId\":\"1001000\"," +
                "\"genusName\":\"全部\"},{\"genusId\":\"1001001\",\"genusName\":\"莲华掌属\"}," +
                "{\"genusId\":\"1001002\",\"genusName\":\"仙女杯属\"},{\"genusId\":\"1001003\"," +
                "\"genusName\":\"长生草属\"},{\"genusId\":\"1001004\",\"genusName\":\"拟石莲属\"}]}," +
                "{\"familyId\":\"1005\",\"familyName\":\"百合科\"," +
                "\"genusList\":[{\"genusId\":\"1001000\",\"genusName\":\"全部\"}," +
                "{\"genusId\":\"1001001\",\"genusName\":\"莲华掌属\"},{\"genusId\":\"1001002\"," +
                "\"genusName\":\"仙女杯属\"},{\"genusId\":\"1001003\",\"genusName\":\"长生草属\"}," +
                "{\"genusId\":\"1001004\",\"genusName\":\"拟石莲属\"}]},{\"familyId\":\"1001\"," +
                "\"familyName\":\"景天科\"},{\"familyId\":\"1002\",\"familyName\":\"大㦸科\"}," +
                "{\"familyId\":\"1003\",\"familyName\":\"龙舌兰科\"},{\"familyId\":\"1004\"," +
                "\"familyName\":\"番杏科\"},{\"familyId\":\"1005\",\"familyName\":\"百合科\"}," +
                "{\"familyId\":\"1001\",\"familyName\":\"景天科\"},{\"familyId\":\"1002\"," +
                "\"familyName\":\"大㦸科\"},{\"familyId\":\"1003\",\"familyName\":\"龙舌兰科\"}," +
                "{\"familyId\":\"1004\",\"familyName\":\"番杏科\"},{\"familyId\":\"1005\"," +
                "\"familyName\":\"百合科\"}]}";
    }

    public static String getSpeciesList() {
        return "{\"code\":\"200\",\"msg\":\"\",\"speciesList\":[{\"speciesId\":\"10010010001\"," +
                "\"speciesName\":\"黄丽\",\"speciesPicUrl\":\"http://imgsrc.baidu" +
                ".com/baike/pic/item/b812c8fcc3cec3fd15f34ab1dc88d43f86942721.jpg\"," +
                "\"speciesLinkUrl\":\"\"},{\"speciesId\":\"10010010002\"," +
                "\"speciesName\":\"吉娃莲\",\"speciesPicUrl\":\"https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1533718844496&di" +
                "=ecd2d03ba3096182e9527425acd8981c&imgtype=0&src=http%3A%2F%2Fimages.jhrx" +
                ".cn%2Fattachment%2Fforum%2F201603%2F24%2F113022t2oza7xo3bzzhkbz.jpg\"," +
                "\"speciesLinkUrl\":\"\"},{\"speciesId\":\"10010010003\"," +
                "\"speciesName\":\"阿尔巴佳人\",\"speciesPicUrl\":\"https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1533718877525&di" +
                "=ee003770244efbdd71303032f92f20e6&imgtype=0&src=http%3A%2F%2Fs10.sinaimg" +
                ".cn%2Fmw690%2F0024bWVagy6QsYZD5cl29%26690\",\"speciesLinkUrl\":\"\"}," +
                "{\"speciesId\":\"10010010004\",\"speciesName\":\"雪莲\"," +
                "\"speciesPicUrl\":\"http://imgsrc.baidu" +
                ".com/baike/pic/item/b812c8fcc3cec3fd15f34ab1dc88d43f86942721.jpg\"," +
                "\"speciesLinkUrl\":\"\"},{\"speciesId\":\"10010010005\"," +
                "\"speciesName\":\"玉碟\",\"speciesPicUrl\":\"https://ss2.bdstatic" +
                ".com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=807985943," +
                "1351995441&fm=200&gp=0.jpg\",\"speciesLinkUrl\":\"\"}," +
                "{\"speciesId\":\"10010010006\",\"speciesName\":\"子持莲华\"," +
                "\"speciesPicUrl\":\"http://imgsrc.baidu" +
                ".com/baike/pic/item/b812c8fcc3cec3fd15f34ab1dc88d43f86942721.jpg\"," +
                "\"speciesLinkUrl\":\"\"},{\"speciesId\":\"10010010007\"," +
                "\"speciesName\":\"新玉缀\",\"speciesPicUrl\":\"http://imgsrc.baidu" +
                ".com/baike/pic/item/b812c8fcc3cec3fd15f34ab1dc88d43f86942721.jpg\"," +
                "\"speciesLinkUrl\":\"\"}]}";
    }

    public static String getLinkList() {
        return "{\"code\":\"200\",\"msg\":\"\",\"list\":[{\"linkId\":\"0\"," +
                "\"linkTitle\":\"种植建议\",\"linkSummary\":\"\",\"linkTime\":\"2018-08-02 " +
                "10:00\",\"linkReadCount\":\"999\",\"linkUrl\":\"www.baidu.com\"," +
                "\"linkPicUrl\":\"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2875812309," +
                "3386562784&fm=58\"},{\"linkId\":\"1\",\"linkTitle\":\"多肉夏天怎么过\"," +
                "\"linkSummary\":\"\",\"linkTime\":\"2018-08-02 10:00\"," +
                "\"linkReadCount\":\"999\",\"linkUrl\":\"www.sohu.com\"," +
                "\"linkPicUrl\":\"\"},{\"linkId\":\"2\",\"linkTitle\":\"多肉怎么过冬多肉怎么过冬多肉怎么过冬多肉怎么过冬多肉怎么过冬多肉怎么过冬多肉怎么过冬多肉怎么过冬多肉怎么过冬多肉怎么过冬\"," +
                "\"linkSummary\":\"\",\"linkTime\":\"2018-08-02 10:00\"," +
                "\"linkReadCount\":\"999\",\"linkUrl\":\"www.baidu.com\"," +
                "\"linkPicUrl\":\"\"},{\"linkId\":\"3\"," +
                "\"linkTitle\":\"多肉注意事项多肉注意事项多肉注意事项多肉注意事项多肉注意事项多肉注意事项多肉注意事项多肉注意事项多肉注意事项多肉注意事项多肉注意事项\",\"linkSummary\":\"\",\"linkTime\":\"2018-08-02 10:00\",\"linkReadCount\":\"999\",\"linkUrl\":\"www.baidu.com\",\"linkPicUrl\":\"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2875812309,3386562784&fm=58\"},{\"linkId\":\"4\",\"linkTitle\":\"用什么土种\",\"linkSummary\":\"\",\"linkTime\":\"2018-08-02 10:00\",\"linkReadCount\":\"999\",\"linkUrl\":\"www.baidu.com\",\"linkPicUrl\":\"\"},{\"linkId\":\"5\",\"linkTitle\":\"怎样防虫\",\"linkSummary\":\"\",\"linkTime\":\"2018-08-02 10:00\",\"linkReadCount\":\"999\",\"linkUrl\":\"www.baidu.com\",\"linkPicUrl\":\"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2875812309,3386562784&fm=58\"},{\"linkId\":\"6\",\"linkTitle\":\"仙人球\",\"linkSummary\":\"\",\"linkTime\":\"2018-08-02 10:00\",\"linkReadCount\":\"999\",\"linkUrl\":\"www.baidu.com\",\"linkPicUrl\":\"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2875812309,3386562784&fm=58\"}]}";
    }

    public static String getQaList() {
        return "{\"code\":\"200\",\"msg\":\"\"," +
                "\"qaList\":[{\"userInfo\":{\"userId\":\"10001\",\"nickname\":\"小花儿\"," +
                "\"avatarUrl\":\"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2875812309," +
                "3386562784&fm=58\"},\"questionId\":\"0\"," +
                "\"questionTitle\":\"种植建议种植建议种植建议种植建议种植建议种植建议种植建议种植建议\"," +
                "\"questionTime\":\"2018-08-02 10:00\",\"answerCount\":\"919\"," +
                "\"questionPicUrl\":\"https://ss0.baidu" +
                ".com/6ONWsjip0QIZ8tyhnq/it/u=2875812309,3386562784&fm=58\"}," +
                "{\"userInfo\":{\"userId\":\"10001\",\"nickname\":\"小花儿\"," +
                "\"avatarUrl\":\"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2875812309," +
                "3386562784&fm=58\"},\"questionId\":\"0\",\"questionTitle\":\"种植建议\"," +
                "\"questionTime\":\"2018-08-02 10:00\",\"answerCount\":\"919\"," +
                "\"questionPicUrl\":\"https://ss0.baidu" +
                ".com/6ONWsjip0QIZ8tyhnq/it/u=2875812309,3386562784&fm=58\"}," +
                "{\"userInfo\":{\"userId\":\"10001\",\"nickname\":\"小花儿\"," +
                "\"avatarUrl\":\"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2875812309," +
                "3386562784&fm=58\"},\"questionId\":\"0\",\"questionTitle\":\"种植建议\"," +
                "\"questionTime\":\"2018-08-02 10:00\",\"answerCount\":\"919\"," +
                "\"questionPicUrl\":\"https://ss0.baidu" +
                ".com/6ONWsjip0QIZ8tyhnq/it/u=2875812309,3386562784&fm=58\"}," +
                "{\"userInfo\":{\"userId\":\"10001\",\"nickname\":\"小花儿\"," +
                "\"avatarUrl\":\"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2875812309," +
                "3386562784&fm=58\"},\"questionId\":\"0\",\"questionTitle\":\"种植建议\"," +
                "\"questionTime\":\"2018-08-02 10:00\",\"answerCount\":\"919\"," +
                "\"questionPicUrl\":\"\"},{\"userInfo\":{\"userId\":\"10001\"," +
                "\"nickname\":\"小花儿\",\"avatarUrl\":\"https://ss0.baidu" +
                ".com/6ONWsjip0QIZ8tyhnq/it/u=2875812309,3386562784&fm=58\"}," +
                "\"questionId\":\"0\",\"questionTitle\":\"种植建议\"," +
                "\"questionTime\":\"2018-08-02 10:00\",\"answerCount\":\"919\"," +
                "\"questionPicUrl\":\"https://ss0.baidu" +
                ".com/6ONWsjip0QIZ8tyhnq/it/u=2875812309,3386562784&fm=58\"}," +
                "{\"userInfo\":{\"userId\":\"10001\",\"nickname\":\"小花儿\"," +
                "\"avatarUrl\":\"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2875812309," +
                "3386562784&fm=58\"},\"questionId\":\"0\",\"questionTitle\":\"种植建议\"," +
                "\"questionTime\":\"2018-08-02 10:00\",\"answerCount\":\"919\"," +
                "\"questionPicUrl\":\"\"},{\"userInfo\":{\"userId\":\"10001\"," +
                "\"nickname\":\"小花儿\",\"avatarUrl\":\"https://ss0.baidu" +
                ".com/6ONWsjip0QIZ8tyhnq/it/u=2875812309,3386562784&fm=58\"}," +
                "\"questionId\":\"0\",\"questionTitle\":\"种植建议\"," +
                "\"questionTime\":\"2018-08-02 10:00\",\"answerCount\":\"919\"," +
                "\"questionPicUrl\":\"https://ss0.baidu" +
                ".com/6ONWsjip0QIZ8tyhnq/it/u=2875812309,3386562784&fm=58\"}]}";
    }

    public static String getQaAnswers() {
        return "{\"code\":\"200\",\"msg\":\"\"," +
                "\"answerList\":[{\"userInfo\":{\"userId\":\"10001\",\"nickname\":\"小花儿\"," +
                "\"avatarUrl\":\"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2875812309," +
                "3386562784&fm=58\"},\"answerId\":\"0\"," +
                "\"answerContent\":\"种植建议种植建议种植建议种植建议种植建议种植建议种植建议种植建议建议种植建议种植建建议种植建议种植建\"," +
                "\"answerTime\":\"2018-08-02 10:00\",\"answerUpCount\":\"919\"," +
                "\"answerUpState\":\"1\"},{\"userInfo\":{\"userId\":\"10001\"," +
                "\"nickname\":\"小花儿\",\"avatarUrl\":\"https://ss0.baidu" +
                ".com/6ONWsjip0QIZ8tyhnq/it/u=2875812309,3386562784&fm=58\"}," +
                "\"answerId\":\"0\",\"answerContent\":\"种植建议种植植建议种植建议\"," +
                "\"answerTime\":\"2018-08-02 10:00\",\"answerUpCount\":\"919\"," +
                "\"answerUpState\":\"0\"},{\"userInfo\":{\"userId\":\"10001\"," +
                "\"nickname\":\"小花儿\",\"avatarUrl\":\"https://ss0.baidu" +
                ".com/6ONWsjip0QIZ8tyhnq/it/u=2875812309,3386562784&fm=58\"}," +
                "\"answerId\":\"0\",\"answerContent\":\"我来回答\",\"answerTime\":\"2018-08-02 " +
                "10:00\",\"answerUpCount\":\"919\",\"answerUpState\":\"1\"}," +
                "{\"userInfo\":{\"userId\":\"10001\",\"nickname\":\"小花儿\"," +
                "\"avatarUrl\":\"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2875812309," +
                "3386562784&fm=58\"},\"answerId\":\"0\"," +
                "\"answerContent\":\"种植建议种植建议种植建议种植建议种植建议种植建议种植建议种植建议\"," +
                "\"answerTime\":\"2018-08-02 10:00\",\"answerUpCount\":\"919\"," +
                "\"answerUpState\":\"1\"},{\"userInfo\":{\"userId\":\"10001\"," +
                "\"nickname\":\"小花儿\",\"avatarUrl\":\"https://ss0.baidu" +
                ".com/6ONWsjip0QIZ8tyhnq/it/u=2875812309,3386562784&fm=58\"}," +
                "\"answerId\":\"0\",\"answerContent\":\"种植建议种植建议种植建议种植建议种植建议种植建议种植建议种植建议\"," +
                "\"answerTime\":\"2018-08-02 10:00\",\"answerUpCount\":\"919\"," +
                "\"answerUpState\":\"1\"},{\"userInfo\":{\"userId\":\"10001\"," +
                "\"nickname\":\"小花儿\",\"avatarUrl\":\"https://ss0.baidu" +
                ".com/6ONWsjip0QIZ8tyhnq/it/u=2875812309,3386562784&fm=58\"}," +
                "\"answerId\":\"0\",\"answerContent\":\"种植建议种植建植建议\"," +
                "\"answerTime\":\"2018-08-02 10:00\",\"answerUpCount\":\"919\"," +
                "\"answerUpState\":\"1\"},{\"userInfo\":{\"userId\":\"10001\"," +
                "\"nickname\":\"小小儿\",\"avatarUrl\":\"\"},\"answerId\":\"0\"," +
                "\"answerContent\":\"种植建议\",\"answerTime\":\"2018-08-02 10:00\"," +
                "\"answerUpCount\":\"919\",\"answerUpState\":\"1\"}]}";
    }

    public static String getMomentlist() {
        return "{\"code\":\"200\",\"msg\":\"\"," +
                "\"list\":[{\"userInfo\":{\"userId\":\"10001\",\"nickname\":\"小花儿1\"," +
                "\"avatarUrl\":\"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2875812309," +
                "3386562784&fm=58\",\"region\":\"北京\"},\"noteId\":\"0001\"," +
                "\"noteTitle\":\"仙人球\",\"updateTime\":\"2018-08-02 10:00\"," +
                "\"permission\":\"0\",\"likeCount\":\"999\",\"hasLike\":\"0\"," +
                "\"picUrls\":[\"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2875812309," +
                "3386562784&fm=58\"],\"noteType\":\"1\"}," +
                "{\"userInfo\":{\"userId\":\"10001\",\"nickname\":\"小花儿2\"," +
                "\"avatarUrl\":\"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2875812309," +
                "3386562784&fm=58\",\"region\":\"北京\"},\"noteId\":\"0001\"," +
                "\"noteTitle\":\"造个景\",\"updateTime\":\"2018-08-02 10:00\"," +
                "\"permission\":\"0\",\"likeCount\":\"999\",\"hasLike\":\"0\"," +
                "\"picUrls\":[\"http://imgsrc.baidu" +
                ".com/baike/pic/item/b812c8fcc3cec3fd15f34ab1dc88d43f86942721.jpg\"," +
                "\"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=807985943," +
                "1351995441&fm=200&gp=0.jpg\"],\"noteType\":\"2\"}," +
                "{\"userInfo\":{\"userId\":\"10001\",\"nickname\":\"小花儿6\"," +
                "\"avatarUrl\":\"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2875812309," +
                "3386562784&fm=58\",\"region\":\"北京\"},\"noteId\":\"0001\"," +
                "\"noteTitle\":\"拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼拼了了了\",\"updateTime\":\"2018-08-01 08:05\",\"permission\":\"0\",\"likeCount\":\"100\",\"hasLike\":\"1\",\"picUrls\":[\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533718877525&di=ee003770244efbdd71303032f92f20e6&imgtype=0&src=http%3A%2F%2Fs10.sinaimg.cn%2Fmw690%2F0024bWVagy6QsYZD5cl29%26690\",\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534313664&di=b905eb785671c948864121131edf66f3&imgtype=jpg&er=1&src=http%3A%2F%2Fimg4q.duitang.com%2Fuploads%2Fitem%2F201505%2F03%2F20150503185151_GZQyT.jpeg\",\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533718985781&di=55ef50e4144ed2406457d30d346c0b06&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01594b5541bad5000001e78c9c37d3.jpg\",\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534314743&di=ed0cd3abe36faa4dc824472907afc359&imgtype=jpg&er=1&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fblog%2F201308%2F17%2F20130817161051_Hxz5F.thumb.700_0.jpeg\"],\"noteType\":\"2\"},{\"userInfo\":{\"userId\":\"10001\",\"nickname\":\"小花儿7\",\"avatarUrl\":\"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2875812309,3386562784&fm=58\",\"region\":\"云南\"},\"noteId\":\"0001\",\"noteTitle\":\"摆个盘盘\",\"updateTime\":\"2018-08-01 08:05\",\"permission\":\"0\",\"likeCount\":\"100\",\"hasLike\":\"1\",\"picUrls\":[\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533718877525&di=ee003770244efbdd71303032f92f20e6&imgtype=0&src=http%3A%2F%2Fs10.sinaimg.cn%2Fmw690%2F0024bWVagy6QsYZD5cl29%26690\",\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534313664&di=b905eb785671c948864121131edf66f3&imgtype=jpg&er=1&src=http%3A%2F%2Fimg4q.duitang.com%2Fuploads%2Fitem%2F201505%2F03%2F20150503185151_GZQyT.jpeg\",\"\",\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534314743&di=ed0cd3abe36faa4dc824472907afc359&imgtype=jpg&er=1&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fblog%2F201308%2F17%2F20130817161051_Hxz5F.thumb.700_0.jpeg\"],\"noteType\":\"2\"},{\"userInfo\":{\"userId\":\"10001\",\"nickname\":\"小花儿8\",\"avatarUrl\":\"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2875812309,3386562784&fm=58\",\"region\":\"云南\"},\"noteId\":\"0001\",\"noteTitle\":\"虹之玉\",\"updateTime\":\"2018-08-02 10:05\",\"permission\":\"1\",\"likeCount\":\"1\",\"hasLike\":\"1\",\"picUrls\":[\"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=807985943,1351995441&fm=200&gp=0.jpg\"],\"noteType\":\"1\"},{\"userInfo\":{\"userId\":\"10001\",\"nickname\":\"小花儿9\",\"avatarUrl\":\"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2875812309,3386562784&fm=58\",\"region\":\"云南\"},\"noteId\":\"0001\",\"noteTitle\":\"婴儿手指\",\"updateTime\":\"2018-08-02 09:06\",\"permission\":\"0\",\"likeCount\":\"99\",\"hasLike\":\"1\",\"picUrls\":[\"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3497831567,3244393986&fm=58\"],\"noteType\":\"1\"},{\"userInfo\":{\"userId\":\"10001\",\"nickname\":\"小花儿10\",\"avatarUrl\":\"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2875812309,3386562784&fm=58\",\"region\":\"云南\"},\"noteId\":\"0001\",\"noteTitle\":\"雪莲\",\"updateTime\":\"2018-08-01 10:05\",\"permission\":\"1\",\"likeCount\":\"0\",\"hasLike\":\"0\",\"picUrls\":[\"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3638332329,1145224074&fm=27&gp=0.jpg\"],\"noteType\":\"1\"},{\"userInfo\":{\"userId\":\"10001\",\"nickname\":\"小花儿3\",\"avatarUrl\":\"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2875812309,3386562784&fm=58\"},\"noteId\":\"0001\",\"noteTitle\":\"拼个盆拼个盆拼个盆拼个盆拼个盆拼个盆拼个盆拼个盆拼个盆拼个盆拼个盆拼个盆拼个盆拼个盆\",\"updateTime\":\"2018-08-02 10:05\",\"permission\":\"1\",\"likeCount\":\"1\",\"hasLike\":\"1\",\"picUrls\":[\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533718815649&di=e0c909615935f966e33f370f7455198f&imgtype=0&src=http%3A%2F%2Fimgq.duitang.com%2Fuploads%2Fitem%2F201505%2F10%2F20150510225505_VarSj.jpeg\"],\"noteType\":\"2\"},{\"userInfo\":{\"userId\":\"10001\",\"nickname\":\"小花儿4\",\"avatarUrl\":\"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2875812309,3386562784&fm=58\"},\"noteId\":\"0001\",\"noteTitle\":\"摆盆\",\"updateTime\":\"2018-08-02 09:06\",\"permission\":\"0\",\"likeCount\":\"99\",\"hasLike\":\"1\",\"picUrls\":[\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533718815649&di=e0c909615935f966e33f370f7455198f&imgtype=0&src=http%3A%2F%2Fimgq.duitang.com%2Fuploads%2Fitem%2F201505%2F10%2F20150510225505_VarSj.jpeg\",\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533718844496&di=ecd2d03ba3096182e9527425acd8981c&imgtype=0&src=http%3A%2F%2Fimages.jhrx.cn%2Fattachment%2Fforum%2F201603%2F24%2F113022t2oza7xo3bzzhkbz.jpg\",\"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1099988064,3976727172&fm=27&gp=0.jpg\"],\"noteType\":\"2\"},{\"userInfo\":{\"userId\":\"10001\",\"nickname\":\"小花儿5\",\"avatarUrl\":\"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2875812309,3386562784&fm=58\",\"region\":\"云南\"},\"noteId\":\"0001\",\"noteTitle\":\"拼盘\",\"updateTime\":\"2018-08-01 10:05\",\"permission\":\"1\",\"likeCount\":\"0\",\"hasLike\":\"0\",\"picUrls\":[\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533718844496&di=ecd2d03ba3096182e9527425acd8981c&imgtype=0&src=http%3A%2F%2Fimages.jhrx.cn%2Fattachment%2Fforum%2F201603%2F24%2F113022t2oza7xo3bzzhkbz.jpg\",\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533718877525&di=ee003770244efbdd71303032f92f20e6&imgtype=0&src=http%3A%2F%2Fs10.sinaimg.cn%2Fmw690%2F0024bWVagy6QsYZD5cl29%26690\",\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533719912190&di=c6cd336973aa16ec3954a742237a371d&imgtype=0&src=http%3A%2F%2Fs3.sinaimg.cn%2Fmw690%2F003eiiLTgy70hQK8OnUf2%26690\",\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533718877525&di=ee003770244efbdd71303032f92f20e6&imgtype=0&src=http%3A%2F%2Fs10.sinaimg.cn%2Fmw690%2F0024bWVagy6QsYZD5cl29%26690\",\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533718877525&di=ee003770244efbdd71303032f92f20e6&imgtype=0&src=http%3A%2F%2Fs10.sinaimg.cn%2Fmw690%2F0024bWVagy6QsYZD5cl29%26690\",\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533718877525&di=ee003770244efbdd71303032f92f20e6&imgtype=0&src=http%3A%2F%2Fs10.sinaimg.cn%2Fmw690%2F0024bWVagy6QsYZD5cl29%26690\"],\"noteType\":\"2\"},{\"userInfo\":{\"userId\":\"10001\",\"nickname\":\"小花儿11\",\"avatarUrl\":\"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2875812309,3386562784&fm=58\",\"region\":\"云南\"},\"noteId\":\"0001\",\"noteTitle\":\"子持莲华子持莲华子持莲华子持莲华子持莲华子持莲华子持莲华子持莲华子持莲华子持莲华子持莲华子持莲华子持莲华子持莲华子持莲华子持莲华子持莲华子持莲华子持莲华子持莲华\",\"updateTime\":\"2018-08-01 08:05\",\"permission\":\"0\",\"likeCount\":\"100\",\"hasLike\":\"1\",\"picUrls\":[\"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1099988064,3976727172&fm=27&gp=0.jpg\"],\"noteType\":\"1\"}]}";
    }

    public static String getUserinfo() {
        return "{\"code\":\"200\",\"msg\":\"\",\"userInfo\":{\"userId\":\"用户id\"," +
                "\"nickname\":\"狂奔的陆龟\",\"avatarUrl\":\"\",\"region\":\"云南\"," +
                "\"momentsCoverUrl\":\"邻家肉园封面图\"}}";
    }
}
