package com.mojota.succulent.network;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.mojota.succulent.SucculentApplication;
import com.mojota.succulent.utils.AppLog;
import com.mojota.succulent.utils.UrlConstants;

/**
 * oss操作, 上传等
 * Created by wangjing on 18-11-19.
 */
public class OssRequest {
    private static final String TAG = "OssRequest";

    private OSS mOss;

    private Handler mMainHandler = new Handler(Looper.getMainLooper()); // 主线程handler

    public interface OssOperateListener {
//        void onProgress(String objectKey, String progress);// 进度

        void onSuccess(String objectKey, String objectUrl); // 成功

        void onFailure(String objectKey, String errMsg); // 失败
    }

    public OssRequest() {
        this.mOss = getOss();
    }

    /**
     * 从server获取临时token更安全
     */
    private OSS getOss() {
        String endpoint = UrlConstants.ENDPOINT_URL;
        //推荐使用OSSAuthCredentialsProvider,token过期可以及时更新
        OSSCredentialProvider credentialProvider = new OSSAuthCredentialsProvider
                (UrlConstants.STS_SERVER);
        return new OSSClient(SucculentApplication.getInstance(), endpoint,
                credentialProvider);
    }

    /**
     * 上传
     * 返回的task可以
     * task.cancel(); 可以取消任务
     * task.waitUntilFinished(); 可以等待任务完成
     */
    public OSSAsyncTask upload(final String objectKey, byte[] uploadData,
                       final OssOperateListener ossOperateListener) {
        OSSAsyncTask task = null;
        if (TextUtils.isEmpty(objectKey)) {
            AppLog.d(TAG, "objectKey is empty");
            return task;
        }
        if (uploadData == null || uploadData.length <= 0) {
            AppLog.d(TAG, "uploadData is empty");
            if (ossOperateListener != null) {
                ossOperateListener.onFailure(objectKey, "上传内容是空的");
            }
            return task;
        }
        if (uploadData.length > 1024000) { // 上传不允许超过1M
            AppLog.d(TAG, "uploadData is large");
            if (ossOperateListener != null) {
                ossOperateListener.onFailure(objectKey, "超过上传大小限制");
            }
            return task;
        }
        AppLog.d(TAG, "objectKey:" + objectKey + ", uploadlength:" + uploadData.length);
        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(UrlConstants.BUCKET, objectKey,
                uploadData);

        // 异步上传时可以设置进度回调
//        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
//            @Override
//            public void onProgress(PutObjectRequest request, long currentSize, long
//                    totalSize) {
//                String progress = String.valueOf(((float) currentSize / totalSize) *
//                        100) + "%";
//                AppLog.d(TAG, "PutObject: progress: " + progress);
//                if (ossOperateListener != null) {
//                    ossOperateListener.onProgress(objectKey, progress);
//                }
//            }
//        });

        task = mOss.asyncPutObject(put, new
                OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {

            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                //还在线程里，居然没有回到UI线程
                final String objectUrl = mOss.presignPublicObjectURL(UrlConstants.BUCKET,
                        objectKey);
                AppLog.d(TAG, "asyncPutObject: UploadSuccess");
                AppLog.d(TAG, "objectKey:" + request.getObjectKey());
                AppLog.d(TAG, "objectUrl:" + objectUrl);
                // 回到UI线程
                mMainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (ossOperateListener != null) {
                            ossOperateListener.onSuccess(objectKey, objectUrl);
                        }
                    }
                });
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException
                    clientExcepion, ServiceException serviceException) {
                //还在线程里，居然没有回到UI线程
                // 请求异常
                AppLog.e(TAG, "asyncPutObject failure");
                // 本地异常如网络异常等
                if (clientExcepion != null) {
                    clientExcepion.printStackTrace();
                }
                // 服务异常
                if (serviceException != null) {
                    AppLog.e(TAG, "ErrorCode:" + serviceException.getErrorCode());
                    AppLog.e(TAG, "RequestId:" + serviceException.getRequestId());
                    AppLog.e(TAG, "HostId:" + serviceException.getHostId());
                    AppLog.e(TAG, "RawMessage:" + serviceException.getRawMessage());
                }
                // 回到UI线程
                mMainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (ossOperateListener != null) {
                            ossOperateListener.onFailure(objectKey, "上传失败");
                        }
                    }
                });
            }
        });
        return task;
    }

}
