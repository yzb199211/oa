package com.huanxin.oa.tencent;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.huanxin.oa.BaseActivity;
import com.huanxin.oa.R;
import com.huanxin.oa.permission.PermissionListener;
import com.huanxin.oa.utils.Toasts;
import com.huanxin.oa.utils.net.LoadFileModel;
import com.huanxin.oa.utils.net.Md5Tool;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Used 调用腾讯浏览服务预览文件
 */

public class DisplayFileActivity extends BaseActivity {

    private static final String TAG = DisplayFileActivity.class.getSimpleName();

    private TbsReaderView mTbsReaderView;//用于预览文件5-1

    private String filePath = "";
    private String fileName = "";

    public static void openDispalyFileActivity(Context context, String filePath, String fileName) {
        Intent intent = new Intent(context, DisplayFileActivity.class);
        intent.putExtra("filepath", filePath);
        intent.putExtra("filename", fileName);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayfile);

        initTbsReaderView();//用于预览文件5-2

        Intent intent = getIntent();
        filePath = intent.getStringExtra("filepath");
        if (filePath.contains("http")) {
            fileName = getFileName(filePath);
        } else {
            fileName = intent.getStringExtra("filename");
        }
        onePermission();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTbsReaderView.onStop();//用于预览文件5-5
    }

    //初始化TbsReaderView 5-3
    private void initTbsReaderView() {
        mTbsReaderView = new TbsReaderView(DisplayFileActivity.this, new TbsReaderView.ReaderCallback() {
            @Override
            public void onCallBackAction(Integer integer, Object o, Object o1) {
                //ReaderCallback 接口提供的方法可以不予处理（目前不知道有什么用途，但是一定要实现这个接口类）
            }
        });
        RelativeLayout rootRl = (RelativeLayout) findViewById(R.id.root_layout);
        rootRl.addView(mTbsReaderView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }
    //预览文件5-4

    /**
     * filePath :文件路径。格式为 android 本地存储路径格式，例如：/sdcard/Download/xxx.doc. 不支持 file:///格式。暂不支持在线文件。
     * fileName : 文件的文件名（含后缀）
     */
    private void displayFile(String filePath, String fileName) {
        Bundle bundle = new Bundle();
        bundle.putString("filePath", filePath);
        bundle.putString("tempPath", Environment.getExternalStorageDirectory().getPath());
        Log.e("file", parseFormat(fileName));
        if (mTbsReaderView == null) {
            Log.e("view", "false");
        }
        boolean result = mTbsReaderView.preOpen(parseFormat(fileName), false);

        if (result) {
            mTbsReaderView.openFile(bundle);
        }
    }

    private String parseFormat(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 只有一个运行时权限申请的情况
     */
    private void onePermission() {
        requestRunPermisssion(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionListener() {

            @Override
            public void onGranted() {
                displayFile(filePath, fileName);
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                Toasts.showShort(DisplayFileActivity.this, "授权失败");
            }
        });
    }

    private void getFilePathAndShowFile() {


        if (filePath.contains("http")) {//网络地址要先下载

            downLoadFromNet(filePath);

        } else {
            displayFile(filePath, fileName);
        }
    }

    private void downLoadFromNet(final String url) {

        //1.网络下载、存储路径、
        File cacheFile = getCacheFile(url);
        if (cacheFile.exists()) {
            if (cacheFile.length() <= 0) {
                Log.d(TAG, "删除空文件！！");
                cacheFile.delete();
                return;
            }
        }


        LoadFileModel.loadPdfFile(url, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "下载文件-->onResponse");
                boolean flag;
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    ResponseBody responseBody = response.body();
                    is = responseBody.byteStream();
                    long total = responseBody.contentLength();

                    File file1 = getCacheDir(url);
                    if (!file1.exists()) {
                        file1.mkdirs();
                        Log.d(TAG, "创建缓存目录： " + file1.toString());
                    }


                    //fileN : /storage/emulated/0/pdf/kauibao20170821040512.pdf
                    File fileN = getCacheFile(url);//new File(getCacheDir(url), getFileName(url))

                    Log.d(TAG, "创建缓存文件： " + fileN.toString());
                    if (!fileN.exists()) {
                        boolean mkdir = fileN.createNewFile();
                    }
                    fos = new FileOutputStream(fileN);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        Log.d(TAG, "写入缓存文件" + fileN.getName() + "进度: " + progress);
                    }
                    fos.flush();
                    Log.d(TAG, "文件下载成功,准备展示文件。");
                    //2.ACache记录文件的有效期
                    displayFile(filePath, fileName);
                } catch (Exception e) {
                    Log.d(TAG, "文件下载异常 = " + e.toString());
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "文件下载失败");
                File file = getCacheFile(url);
                if (!file.exists()) {
                    Log.d(TAG, "删除下载失败文件");
                    file.delete();
                }
            }
        });


    }

    /***
     * 获取缓存目录
     *
     * @param url
     * @return
     */
    private File getCacheDir(String url) {

        return new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/007/");

    }

    /***
     * 绝对路径获取缓存文件
     *
     * @param url
     * @return
     */
    private File getCacheFile(String url) {
        File cacheFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/007/"
                + getFileName(url));
        Log.d(TAG, "缓存文件 = " + cacheFile.toString());
        return cacheFile;
    }

    /***
     * 根据链接获取文件名（带类型的），具有唯一性
     *
     * @param url
     * @return
     */
    private String getFileName(String url) {
        String fileName = Md5Tool.hashKey(url) + "." + getFileType(url);
        return fileName;
    }

    /***
     * 获取文件类型
     *
     * @param paramString
     * @return
     */
    private String getFileType(String paramString) {
        String str = "";

        if (TextUtils.isEmpty(paramString)) {
            Log.d(TAG, "paramString---->null");
            return str;
        }
        Log.d(TAG, "paramString:" + paramString);
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            Log.d(TAG, "i <= -1");
            return str;
        }


        str = paramString.substring(i + 1);
        Log.d(TAG, "paramString.substring(i + 1)------>" + str);
        return str;
    }
}
