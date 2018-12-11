package com.mojota.succulent.utils;

/**
 * Created by mojota on 18-8-31.
 */
public class UrlConstants {
    public static final String SERVER = "http://10.42.0.1:8099/";
//    public static final String SERVER = "http://crazysucculent.top:8099/";

    private static final String HTTP = "http://";
    private static final String ENDPOINT = "oss-cn-beijing.aliyuncs.com";
    public static final String ENDPOINT_URL = HTTP + ENDPOINT;
    public static final String BUCKET = "succulent-mojota";

    public static final String OSS_SERVER = HTTP + BUCKET + "." + ENDPOINT+"/";

    public static final String STS_SERVER = SERVER + "oss/getSts";

    public static final String REGISTER_URL = SERVER + "user/register";
    public static final String LOGIN_URL = SERVER + "user/login";
    public static final String USER_EDIT_URL = SERVER + "user/edit";
    public static final String AVATAR_EDIT_URL = SERVER + "user/editAvatar";
    public static final String COVER_EDIT_URL = SERVER + "user/editCover";

    public static final String DIARY_ADD_URL = SERVER + "note/diaryAdd";
    public static final String NOTE_ADD_URL = SERVER + "note/noteAdd";
    public static final String DIARY_DETAIL_ADD_URL = SERVER + "note/diaryDetailAdd";
    public static final String DIARY_DETAIL_EDIT_URL = SERVER + "note/diaryDetailEdit";
    public static final String NOTE_TITLE_EDIT_URL = SERVER + "note/noteTitleEdit";
    public static final String NOTE_PERMISSION_CHANGE_URL = SERVER + "note/notePermissionChange";
    public static final String NOTE_LIKE_URL = SERVER + "note/noteLike";
    public static final String NOTE_DETAIL_DELETE_URL = SERVER + "note/deleteNoteDetail";
    public static final String NOTE_DELETE_URL = SERVER + "note/deleteNote";

    public static final String GET_NOTE_LIST_URL = SERVER + "note/getNoteList";
    public static final String GET_MOMENTS_URL = SERVER + "note/getMoments";
    public static final String GET_USER_MOMENTS_URL = SERVER + "note/getUserMoments";
    public static final String GET_DIARY_DETAILS_URL = SERVER + "note/getDiaryDetails";

    public static final String QA_ADD_URL = SERVER + "qa/qaAdd";
    public static final String GET_QA_LIST_URL = SERVER + "qa/getQaList";
    public static final String ANSWER_ADD_URL = SERVER + "qa/answerAdd";
    public static final String GET_ANSWER_LIST_URL = SERVER + "qa/getAnswerList";
    public static final String ANSWER_UP_URL = SERVER + "qa/answerUp";
    public static final String QUESTION_DELETE_URL = SERVER + "qa/deleteQuestion";
    public static final String ANSWER_DELETE_URL = SERVER + "qa/deleteAnswer";


}
