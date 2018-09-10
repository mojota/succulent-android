package com.mojota.succulent.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.mojota.succulent.R;
import com.mojota.succulent.TestUtil;
import com.mojota.succulent.adapter.DiaryDetailAdapter;
import com.mojota.succulent.model.NoteDetail;
import com.mojota.succulent.model.DiarysResponseInfo;
import com.mojota.succulent.model.NoteInfo;
import com.mojota.succulent.utils.ActivityUtil;
import com.mojota.succulent.utils.CodeConstants;
import com.mojota.succulent.utils.RequestUtils;
import com.mojota.succulent.utils.UrlConstants;
import com.mojota.succulent.utils.UserUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 笔记详情
 * Created by mojota on 18-8-14
 */
public class DiaryDetailActivity extends PhotoChooseSupportActivity implements View
        .OnClickListener, OnImageClickListener, DiaryDetailAdapter.OnItemOperateListener {

    public static final String KEY_DIARY = "KEY_DIARY";
    private Toolbar mToolBar;
    private RecyclerView mRvDiarys;
    private FloatingActionButton mFabAdd;
    private DiaryDetailAdapter mDetailAdapter;
    private List<NoteDetail> mList = new ArrayList<NoteDetail>();
    private NoteInfo mNoteInfo;
    private MenuItem mActionPermission;
    private MenuItem mActivonEditTitle;
    private int mNewPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition(); // 延迟转场动画
        setContentView(R.layout.activity_diary_detail);

        mToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRvDiarys = findViewById(R.id.rv_diarys);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRvDiarys.setLayoutManager(llm);
        mRvDiarys.setItemAnimator(new DefaultItemAnimator());
        mDetailAdapter = new DiaryDetailAdapter(this, mList);
        mDetailAdapter.setOnImageClickListener(this);
        mDetailAdapter.setOnItemOperateListener(this);
        mRvDiarys.setAdapter(mDetailAdapter);
        mFabAdd = findViewById(R.id.fab_add);
        mFabAdd.setOnClickListener(this);

        mNoteInfo = (NoteInfo) getIntent().getSerializableExtra(KEY_DIARY);
        mNewPermission = mNoteInfo.getPermission();
        getSupportActionBar().setTitle(mNoteInfo.getNoteTitle());

        getData();
    }

    private void getData() {

        DiarysResponseInfo resInfo = new Gson().fromJson(TestUtil.getDiryDetails(),
                DiarysResponseInfo.class);
        mList = resInfo.getDiarys();

        setDataToView();
    }


    private void setDataToView() {
        mDetailAdapter.setList(mList);
        mDetailAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        mActionPermission = menu.findItem(R.id.action_permission);
        mActivonEditTitle = menu.findItem(R.id.action_edit_title);

        setPermissionMemu();

        return super.onCreateOptionsMenu(menu);
    }

    private void setPermissionMemu() {
        if (mNoteInfo.getPermission() == 1) {
            mActionPermission.setTitle(R.string.str_public);
        } else {
            mActionPermission.setTitle(R.string.str_self);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
            case R.id.action_permission:
                mNewPermission = mNoteInfo.getPermission() == 1 ? 0 : 1;
                submitPermission(mNewPermission);
                return true;
            case R.id.action_edit_title:
                showEditDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showEditDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_title, null);
        final EditText etTitle = view.findViewById(R.id.et_title);
        etTitle.setError(null);
        etTitle.setText(mNoteInfo.getNoteTitle());
        final AlertDialog dialog = new AlertDialog.Builder(this).setTitle(R.string
                .str_edit_title).setView(view).setPositiveButton(R.string.str_edit_submit, null)
                .setNegativeButton(R.string.str_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setCancelable(false).create();
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View
                .OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etTitle.getText())) {
                    submitTitle(etTitle.getText().toString());
                    dialog.dismiss();
                } else {
                    etTitle.setError("标题不可以为空");
                }
            }
        });
    }

    /**
     * 修改标题请求
     */
    private void submitTitle(String title) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", UserUtil.getCurrentUserId());
        map.put("noteId", mNoteInfo.getNoteId());
        map.put("noteTitle", title);
        loadingRequestSubmit(UrlConstants.NOTE_TITLE_EDIT_URL, map, CodeConstants.REQUEST_NOTE_TITLE_EDIT);
    }

    /**
     * 修改权限请求
     */
    private void submitPermission(int permission) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", UserUtil.getCurrentUserId());
        map.put("noteId", mNoteInfo.getNoteId());
        map.put("permission", String.valueOf(permission));
        loadingRequestSubmit(UrlConstants.NOTE_PERMISSION_CHANGE_URL, map, CodeConstants
                .REQUEST_PERMISSION_CHANGE);
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode == CodeConstants.REQUEST_PERMISSION_CHANGE) {
            mNoteInfo.setPermission(mNewPermission);
            setPermissionMemu();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add:
                Intent intent = new Intent(this, DiaryAddActivity.class);
                intent.putExtra(DiaryAddActivity.KEY_MODE, CodeConstants.NOTE_DETAIL_ADD);
                intent.putExtra(DiaryAddActivity.KEY_NOTE_ID, mNoteInfo.getNoteId());
                intent.putExtra(DiaryAddActivity.KEY_TITLE, mNoteInfo.getNoteTitle());
                startActivityForResult(intent, CodeConstants.REQUEST_ADD);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CodeConstants.REQUEST_ADD:
                if (resultCode == CodeConstants.RESULT_ADD) {
                    getData();
                }
        }
    }

    @Override
    public void onImageClick(ImageView view, String title, ArrayList<String> picUrls, int picPos) {
        ActivityUtil.startImageBrowserActivity(this, view, title, picUrls, picPos);
    }

    @Override
    public void onEdit(NoteDetail diary) {
        Intent intent = new Intent(this, DiaryAddActivity.class);
        intent.putExtra(DiaryAddActivity.KEY_MODE, CodeConstants.NOTE_DETAIL_EDIT);
        intent.putExtra(DiaryAddActivity.KEY_NOTE_ID, mNoteInfo.getNoteId());
        intent.putExtra(DiaryAddActivity.KEY_TITLE, mNoteInfo.getNoteTitle());
        intent.putExtra(DiaryAddActivity.KEY_DIARY, diary);
        startActivityForResult(intent, CodeConstants.REQUEST_EDIT);
    }

    @Override
    public void onDelete(final NoteDetail diary, final int position) {
        deleteData(position);
    }

    private void deleteData(final int position) {
        final NoteDetail noteDetail = mList.get(position);
        mList.remove(position);
        mDetailAdapter.notifyItemRemoved(position);
        mDetailAdapter.notifyItemRangeChanged(0, mList.size());
        Snackbar.make(mRvDiarys, "已删除一个笔记", Snackbar.LENGTH_LONG).setAction(R.string.str_undo,
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.add(position, noteDetail);
                mDetailAdapter.notifyItemInserted(position);
                mDetailAdapter.notifyItemRangeChanged(0, mList.size());
            }
        }).addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                switch (event) {
                    case Snackbar.Callback.DISMISS_EVENT_CONSECUTIVE:
                    case Snackbar.Callback.DISMISS_EVENT_MANUAL:
                    case Snackbar.Callback.DISMISS_EVENT_SWIPE:
                    case Snackbar.Callback.DISMISS_EVENT_TIMEOUT:
                        requestDelete(noteDetail);
                        break;
                    case Snackbar.Callback.DISMISS_EVENT_ACTION:
                        break;
                }
            }
        }).show();
    }

    /**
     * 请求网络删除
     */
    private void requestDelete(NoteDetail noteDetail) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", UserUtil.getCurrentUserId());
        map.put("detailId", noteDetail.getDetailId());

        RequestUtils.commonRequest(UrlConstants.NOTE_DETAIL_DELETE_URL, map, CodeConstants
                .REQUEST_NOTE_DETAIL_DELETE, null);
    }
}
