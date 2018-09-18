package com.mojota.succulent.utils;

/**
 * Created by mojota on 18-8-31.
 */
public class UrlConstants {
    public static final String SERVER = "http://192.168.43.99:8080/";

    public static final String REGISTER_URL = SERVER + "succulent/user/register";
    public static final String LOGIN_URL = SERVER + "succulent/user/login";
    public static final String USER_EDIT_URL = SERVER + "succulent/user/edit";
    public static final String AVATAR_EDIT_URL = SERVER + "succulent/user/editAvatar";

    public static final String DIARY_ADD_URL = SERVER + "succulent/note/diaryAdd";
    public static final String NOTE_ADD_URL = SERVER + "succulent/note/noteAdd";
    public static final String DIARY_DETAIL_ADD_URL = SERVER + "succulent/note/diaryDetailAdd";
    public static final String DIARY_DETAIL_EDIT_URL = SERVER + "succulent/note/diaryDetailEdit";
    public static final String NOTE_TITLE_EDIT_URL = SERVER + "succulent/note/noteTitleEdit";
    public static final String NOTE_PERMISSION_CHANGE_URL = SERVER + "succulent/note/notePermissionChange";
    public static final String NOTE_LIKE_URL = SERVER + "succulent/note/noteLike";
    public static final String NOTE_DETAIL_DELETE_URL = SERVER + "succulent/note/deleteNoteDetail";
    public static final String NOTE_DELETE_URL = SERVER + "succulent/note/deleteNote";

    public static final String GET_NOTE_LIST_URL = SERVER + "succulent/note/getNoteList";
    public static final String GET_MOMENTS_URL = SERVER + "succulent/note/getMoments";
    public static final String GET_DIARY_DETAILS_URL = SERVER + "succulent/note/getDiaryDetails";
}
