package jx.android.staff.utils.aliyunoss;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import java.math.BigDecimal;
import java.util.UUID;

public class AliOss {

    public static void uploadPic(Context context, int type, String baseFolder, String picPath, final LocalCallBack localCallBack) {
        String endpoint = "oss-cn-huhehaote.aliyuncs.com";
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(Config.OSS_ACCESS_KEY_ID, Config.OSS_ACCESS_KEY_SECRET, "");
        //该配置类如果不设置，会有默认配置，具体可看该类
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000);
        // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000);
        // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5);
        // 最大并发请求数，默认5个
        conf.setMaxErrorRetry(2);
        // 失败后最大重试次数，默认2次 //通过OSSClient发起上传、下载请求是线程安全的，您可以并发执行多个任务。
        OSS oss = new OSSClient(context, endpoint, credentialProvider);
        String picName = UUID.randomUUID() + ".png";
        String picPartUrl = baseFolder + "/" + picName;
        // PutObjectRequest put = new PutObjectRequest("<bucketName>", "<objectKey>", "<uploadFilePath>");
        PutObjectRequest put = new PutObjectRequest(Config.BUCKET_NAME, picPartUrl, picPath);
        // 异步
        put.setProgressCallback((request, currentSize, totalSize) -> {
            //上传资源的URL是定死的。http:// + bucketName+ .服务器中心地址 + /你上传的资源objectKey
            // 这个url是阿里返回的，iOS是直接给返回值，Android根据公式拼接，可以直接在网页中打开图片，可以作为String参数保存到服务器
            String url = "http://" + Config.BUCKET_NAME + " .oss-cn-shanghai.aliyuncs.com";
            // 这里可以写上保存到服务器的网络请求
            Log.i("url......", ">>>>1111" + url);
            BigDecimal totalDecimal = new BigDecimal(totalSize);
            BigDecimal currentDecimal = new BigDecimal(currentSize);
            BigDecimal progressDecimal = currentDecimal.divide(totalDecimal, 2, BigDecimal.ROUND_UP).multiply(new BigDecimal(100));
            localCallBack.onUploading(progressDecimal.intValue());
        });

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("------------PutObject", "UploadSuccess");
                Log.d("------------ETag", result.getETag());
                Log.d("------------RequestId", result.getRequestId());

                String uploadPath = "";
                switch (type) {
                    case Config.TYPE_NO_RESIZE:
                        uploadPath = jx.android.staff.app.Config.getAbsoluteFullLengthPortraitImagePath("/" + picPartUrl);
                        break;
                    case Config.TYPE_NO_WATER_400:
                        uploadPath = jx.android.staff.app.Config.getAbsoluteImagePath("/" + picPartUrl);
                        break;
                    default:
                        break;
                }

                localCallBack.onSuccess(uploadPath, picName);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                localCallBack.onFail();

                // 请求异常。
                if (clientException != null) {
                    // 本地异常，如网络异常等。
                    clientException.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常。
                    Log.e("------------ErrorCode", serviceException.getErrorCode());
                    Log.e("------------RequestId", serviceException.getRequestId());
                    Log.e("------------HostId", serviceException.getHostId());
                    Log.e("------------RawMessage", serviceException.getRawMessage());
                }
            }
        });
        task.waitUntilFinished(); // 等待任务完成。
    }

    public interface LocalCallBack {
        void onUploading(int progress);

        void onSuccess(String url, String name);

        void onFail();
    }

}
