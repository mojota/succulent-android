//package com.mojota.succulent.activity;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//
//import com.mojota.succulent.utils.CodeConstants;
//
//
//public class CoverAddActivity extends PhotoChooseSupportActivity {
//
//
//    public static final String KEY_LOCAL_URI = "KEY_LOCAL_URI";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        showPicDialog(null, null);
//        setOnChoosedListener(new OnChoosedListener() {
//            @Override
//            public void onChoosed(Uri localUploadUri) {
//                Intent data = new Intent();
//                data.putExtra(KEY_LOCAL_URI, localUploadUri);
//                setResult(CodeConstants.RESULT_COVER, data);
//                finish();
//            }
//
//            @Override
//            public void onCanceled() {
//                finish();
//            }
//        });
//
//
//    }
//}
