package com.mojota.succulent.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mojota on 18-8-2.
 */
public class CodeConstants {

    public static final int TYPE_DIARY = 1; // 日记类型
    public static final int TYPE_LANDSCAPE = 2; // 造景类型

    public static final int NOTE_ADD = 1;//添加笔记
    public static final int NOTE_DETAIL_ADD = 2;// 添加笔记条目
    public static final int NOTE_DETAIL_EDIT = 3;// 编辑笔记条目

    public static final int REQUEST_NOTE_ADD = 1000;
    public static final int REQUEST_DIARY_DETAIL_ADD = 1001;
    public static final int REQUEST_DIARY_DETAIL_EDIT = 1002;
    public static final int REQUEST_NOTE_TITLE_EDIT = 1003;
    public static final int REQUEST_PERMISSION_CHANGE = 1004;
    public static final int REQUEST_LIKE = 1005;
    public static final int REQUEST_NOTE_DETAIL_DELETE = 1006;
    public static final int REQUEST_NOTE_DELETE = 1007;
    public static final int REQUEST_QA_ADD = 1008;
    public static final int REQUEST_ANSWER_ADD = 1009;
    public static final int REQUEST_ANSWER_UP = 1010;
    public static final int REQUEST_QUESTION_DELETE = 1011;
    public static final int REQUEST_ANSWER_DELETE = 1012;
    public static final int REQUEST_EDIT_AVATAR = 1013;
    public static final int REQUEST_EDIT_COVER = 1014;
    public static final int REQUEST_RESET_PWD = 1015;
    public static final int REQUEST_SEND_CODE = 1016;


    public static final Map<Integer, String> REQUEST_MAP = new HashMap<>();

    static {
        REQUEST_MAP.put(REQUEST_NOTE_ADD, "添加笔记");
        REQUEST_MAP.put(REQUEST_DIARY_DETAIL_ADD, "添加笔记条目");
        REQUEST_MAP.put(REQUEST_DIARY_DETAIL_EDIT, "编辑笔记条目");
        REQUEST_MAP.put(REQUEST_NOTE_TITLE_EDIT, "修改标题");
        REQUEST_MAP.put(REQUEST_PERMISSION_CHANGE, "修改可见范围");
        REQUEST_MAP.put(REQUEST_LIKE, "");
        REQUEST_MAP.put(REQUEST_NOTE_DETAIL_DELETE, "");
        REQUEST_MAP.put(REQUEST_NOTE_DELETE, "");
        REQUEST_MAP.put(REQUEST_QA_ADD, "提问");
        REQUEST_MAP.put(REQUEST_ANSWER_ADD, "回答");
        REQUEST_MAP.put(REQUEST_ANSWER_UP, "");
        REQUEST_MAP.put(REQUEST_QUESTION_DELETE, "");
        REQUEST_MAP.put(REQUEST_ANSWER_DELETE, "");
        REQUEST_MAP.put(REQUEST_EDIT_AVATAR, "修改头像");
        REQUEST_MAP.put(REQUEST_EDIT_COVER, "修改封面");
        REQUEST_MAP.put(REQUEST_RESET_PWD, "重置密码");
        REQUEST_MAP.put(REQUEST_SEND_CODE, "验证码已发送");
    }


    public static final int REQUEST_ADD = 100;
    public static final int REQUEST_TAKE_PHOTO = 101;
    public static final int REQUEST_CHOOSE_PHOTO = 102;
    public static final int REQUEST_DETAIL = 103;
    public static final int REQUEST_EDIT = 104;
    public static final int REQUEST_QA_ASK = 105;
    public static final int REQUEST_QA_DETAIL = 106;
    public static final int REQUEST_LOGIN = 107;
    public static final int REQUEST_USER_CHANGE = 108;
    public static final int REQUEST_COVER = 110;


    public static final int RESULT_ADD = 500;
    public static final int RESULT_DETAIL = 503;
    public static final int RESULT_EDIT = 504;
    public static final int RESULT_QA = 505;
    public static final int RESULT_LOGIN = 507;
    public static final int RESULT_USER_CHANGE = 508;
    public static final int RESULT_REFRESH = 509;
    public static final int RESULT_COVER = 510;
    public static final int RESULT_EDIT_REFRESH = 511;
    public static final int RESULT_ADD_REFRESH = 512;
}
