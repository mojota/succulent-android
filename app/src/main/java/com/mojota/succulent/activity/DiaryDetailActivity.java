package com.mojota.succulent.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.mojota.succulent.R;
import com.mojota.succulent.TestUtil;
import com.mojota.succulent.adapter.DiaryDetailAdapter;
import com.mojota.succulent.model.DiaryDetail;
import com.mojota.succulent.model.DiarysResponseInfo;
import com.mojota.succulent.model.NoteInfo;
import com.mojota.succulent.utils.ActivityUtil;
import com.mojota.succulent.utils.CodeConstants;

import java.util.ArrayList;
import java.util.List;

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
    private List<DiaryDetail> mList = new ArrayList<DiaryDetail>();
    private NoteInfo mNoteInfo;
    private MenuItem mActionPermission;

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
                finish();
                return true;
            case R.id.action_permission:
                if (mNoteInfo.getPermission() == 1) {
                    mNoteInfo.setPermission(0);
                } else {
                    mNoteInfo.setPermission(1);
                }
                setPermissionMemu();
                setResult(CodeConstants.RESULT_DETAIL);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add:
                Intent intent = new Intent(this, DiaryAddActivity.class);
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
    public void onImageClick(ImageView view, String title, ArrayList<String> picUrls, int
            picPos) {
        ActivityUtil.startImageBrowserActivity(this, view, title, picUrls, picPos);
    }

    @Override
    public void onEdit(DiaryDetail diary) {
        Intent intent = new Intent(this, DiaryAddActivity.class);
        intent.putExtra(DiaryAddActivity.KEY_TITLE, mNoteInfo.getNoteTitle());
        intent.putExtra(DiaryAddActivity.KEY_DIARY, diary);
        startActivityForResult(intent, CodeConstants.REQUEST_EDIT);
    }

    @Override
    public void onDelete(final DiaryDetail diary, final int position) {
        deleteData(position);
    }

    private void deleteData(final int position) {
        final DiaryDetail diaryDetail = mList.get(position);
        mList.remove(position);
        mDetailAdapter.notifyItemRemoved(position);
        mDetailAdapter.notifyItemRangeChanged(0, mList.size());
        Snackbar.make(mRvDiarys, "已删除一个笔记", Snackbar.LENGTH_LONG).setAction(R.string
                .str_undo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.add(position, diaryDetail);
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
                        requestDelete(diaryDetail);
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
    private void requestDelete(DiaryDetail diaryDetail) {

    }
}
