package com.mojota.succulent.utils;

import android.text.TextUtils;
import android.widget.ToggleButton;

import com.android.volley.Response;
import com.mojota.succulent.R;
import com.mojota.succulent.SucculentApplication;
import com.mojota.succulent.model.NoteInfo;
import com.mojota.succulent.model.ResponseInfo;
import com.mojota.succulent.network.GsonPostRequest;
import com.mojota.succulent.network.VolleyErrorListener;
import com.mojota.succulent.network.VolleyUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mojota on 18-9-10.
 */
public class RequestUtils {


    public interface RequestListener {

        void onRequestSuccess(int requestCode);

        void onRequestFailure(int requestCode);
    }


    /**
     * 通用请求，仅返回code,msg
     *
     * @param url
     * @param paramMap
     * @param requestCode     请求名称,用于标记请求、提示
     * @param requestListener 回调
     */
    public static void commonRequest(String url, Map<String, String> paramMap, final int
            requestCode, final RequestListener requestListener) {
        final int requestNameResId = CodeConstants.REQUEST_MAP.get(requestCode);
        final String requestName = SucculentApplication.getInstance().getString(requestNameResId);
        final GsonPostRequest request = new GsonPostRequest(url, null, paramMap, ResponseInfo
                .class, new Response.Listener<ResponseInfo>() {

            @Override
            public void onResponse(ResponseInfo responseInfo) {

                if (responseInfo == null || !"0".equals(responseInfo.getCode())) {
                    if (requestListener != null) {
                        requestListener.onRequestFailure(requestCode);
                    }
                    if (!TextUtils.isEmpty(requestName)) {
                        if (responseInfo != null && !TextUtils.isEmpty(responseInfo
                                .getMsg())) {
                            GlobalUtil.makeToast(requestName + SucculentApplication
                                    .getInstance().getString(R.string.str_failed) + ":"
                                    + responseInfo.getMsg());
                        } else {
                            GlobalUtil.makeToast(requestName + SucculentApplication
                                    .getInstance().getString(R.string.str_failed));
                        }
                    }
                } else {
                    if (requestListener != null) {
                        requestListener.onRequestSuccess(requestCode);
                    }
//                    if (!TextUtils.isEmpty(requestName)){
//                        GlobalUtil.makeToast(requestName + SucculentApplication
//                                    .getInstance().getString(R.string.str_successful));
//                    }
                }
            }
        }, new VolleyErrorListener(new VolleyErrorListener.RequestErrorListener() {
            @Override
            public void onError(String error) {
                if (requestListener != null) {
                    requestListener.onRequestFailure(requestCode);
                }
                if (!TextUtils.isEmpty(requestName)) {
                    GlobalUtil.makeToast(R.string.str_network_error);
                }
            }
        }));
        VolleyUtil.execute(request);
    }


    /**
     * 请求网络赞
     */
    public static void requestLike(String noteId, int isLikey) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", UserUtil.getCurrentUserId());
        map.put("noteId", noteId);
        map.put("isLikey", String.valueOf(isLikey));
        commonRequest(UrlConstants.NOTE_LIKE_URL, map, CodeConstants.REQUEST_LIKE, null);
    }


    /**
     * 请求网络修改权限
     */
    public static void requestPermission(final NoteInfo note, final int newPermission, final
    ToggleButton tbPermission) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", UserUtil.getCurrentUserId());
        map.put("noteId", note.getNoteId());
        map.put("permission", String.valueOf(newPermission));
        commonRequest(UrlConstants.NOTE_PERMISSION_CHANGE_URL, map, CodeConstants
                .REQUEST_PERMISSION_CHANGE, new RequestUtils.RequestListener() {

            @Override
            public void onRequestSuccess(int requestCode) {
                note.setPermission(newPermission);
            }

            @Override
            public void onRequestFailure(int requestCode) {
                int oldPermission = note.getPermission();
                note.setPermission(oldPermission);
                tbPermission.setChecked(oldPermission == 1);
            }
        });
    }


    /**
     * 请求网络删除
     */
    public static void requestDelete(NoteInfo note) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", UserUtil.getCurrentUserId());
        map.put("noteId", note.getNoteId());
        RequestUtils.commonRequest(UrlConstants.NOTE_DELETE_URL, map, CodeConstants
                .REQUEST_NOTE_DELETE, null);
    }


    /**
     * 回答顶, 不关心成功失败
     *
     * @param answerId 回答ID， 传值时表示操作回答
     * @param questionId 所属问题ID
     * @param isUp     点赞操作状态：0-取消点赞，1-点赞
     */
    public static void answerUp(String answerId, String questionId, int isUp) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", UserUtil.getCurrentUserId());
        map.put("answerId", answerId);
        map.put("questionId", questionId);
        map.put("isUp", String.valueOf(isUp));
        commonRequest(UrlConstants.ANSWER_UP_URL, map, CodeConstants.REQUEST_ANSWER_UP, null);
    }
}
