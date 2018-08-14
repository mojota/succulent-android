package com.mojota.succulent.model;

import java.util.List;

/**
 * 笔记详情返回数据结构
 * Created by mojota on 18-8-1.
 */
public class DiarysResponseInfo extends ResponseInfo {

    private NoteInfo noteInfo;
    private List<DiaryDetail> diarys;

    public NoteInfo getNoteInfo() {
        return noteInfo;
    }

    public void setNoteInfo(NoteInfo noteInfo) {
        this.noteInfo = noteInfo;
    }

    public List<DiaryDetail> getDiarys() {
        return diarys;
    }

    public void setDiarys(List<DiaryDetail> diarys) {
        this.diarys = diarys;
    }
}
