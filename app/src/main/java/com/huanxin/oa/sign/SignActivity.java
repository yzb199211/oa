package com.huanxin.oa.sign;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.huanxin.oa.BaseActivity;
import com.huanxin.oa.R;
import com.huanxin.oa.application.BaseApplication;
import com.huanxin.oa.dialog.LoadingDialog;
import com.huanxin.oa.interfaces.ResponseListener;
import com.huanxin.oa.lookup.LookUpActivity;
import com.huanxin.oa.permission.PermissionListener;
import com.huanxin.oa.service.LocationService;
import com.huanxin.oa.utils.ImageLoaderUtil;
import com.huanxin.oa.utils.SharedPreferencesHelper;
import com.huanxin.oa.utils.StringUtil;
import com.huanxin.oa.utils.TimeUtil;
import com.huanxin.oa.utils.Toasts;
import com.huanxin.oa.utils.net.NetConfig;
import com.huanxin.oa.utils.net.NetParams;
import com.huanxin.oa.utils.net.NetUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignActivity extends BaseActivity {
    private final static String TAG = "SignActivity";
    private final static int CUSTOMERCODE = 500;
    private final static int TAKE_PICTURE = 501;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.btn_camera)
    ImageView btnCamera;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.fl_img)
    FrameLayout flImg;
    @BindView(R.id.tv_agree)
    TextView tvAgree;
    @BindView(R.id.tv_disagree)
    TextView tvDisagree;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.bottom_review)
    RelativeLayout bottomReview;
    @BindView(R.id.tv_customer)
    TextView tvCustomer;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.et_remark)
    EditText etRemark;

    private LocationService locationService;
    SharedPreferencesHelper preferencesHelper;
    String customers;
    String customerid;
    String userid;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        userid = (String) preferencesHelper.getSharedPreference("userid", "");
        initView();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        // -----------location config ------------
        locationService = ((BaseApplication) getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
        permission();
    }

    /***
     * Stop location service
     */
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
    }

    private void initView() {
        tvDisagree.setVisibility(View.GONE);
        line.setVisibility(View.GONE);
        bottomReview.setVisibility(View.VISIBLE);
        tvTitle.setText("考勤");
        tvTime.setText(TimeUtil.getTime());
    }

    /*获取客户数据*/
    private void getCustomer() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getCustomerParams(), NetConfig.url + NetConfig.MobileOA_Method, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    JSONObject data = new JSONObject(string);
                    boolean isSuccess = data.optBoolean("success");
                    if (isSuccess) {
                        String old_data = data.optString("tables");
                        customers = old_data.replaceAll("iBscDataCustomerRecNo", "id")
                                .replaceAll("sProductName", "name")
                                .replaceFirst("\\[", "")
                                .replaceFirst("]", "");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LoadingDialog.cancelDialogForLoading();
                                goLookUp(CUSTOMERCODE, "客户选择", customers);
                            }
                        });
                    } else {
                        loadFail(data.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    loadFail("json解析报错");
                } catch (Exception e) {
                    e.printStackTrace();
                    loadFail("数据解析错误");
                }
            }

            @Override
            public void onFail(IOException e) {
                loadFail("获取数据失败");
            }
        });
    }

    /*获取客户数据参数*/
    private List<NetParams> getCustomerParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", "getProjectList"));
        params.add(new NetParams("filters", ""));
        return params;
    }

    @OnClick({R.id.iv_back, R.id.tv_customer, R.id.btn_camera, R.id.tv_agree, R.id.tv_disagree})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_customer:
                if (customers == null) {
                    getCustomer();
                } else {
                    goLookUp(CUSTOMERCODE, "客户选择", customers);
                }
                break;
            case R.id.btn_camera:
                canCamera();
                break;
            case R.id.tv_agree:
                upLoad();
                break;
            case R.id.tv_disagree:
                break;
        }
    }

    private void upLoad() {

//        //创建文件对象
        File file = new File(mTempPhotoPath);
        Log.e("file", file.exists() + "" + file.getName());
        //创建RequestBody封装参数
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        //创建MultiparBody,给RequestBody进行设置
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", file.getName(), fileBody)
                .addFormDataPart("otype", "doSignIn")
                .addFormDataPart("formid", "5030")
                .addFormDataPart("platform", "android")
                .addFormDataPart("userid", userid)
                .addFormDataPart("iBscDataCustomerRecNo", customerid)
                .addFormDataPart("address", address)
                .addFormDataPart("sRemark", etRemark.getText().toString())
                .addFormDataPart("senddate", tvTime.getText().toString().replaceAll("-", "").replaceAll(":", "").replaceAll(" ", ""))
                .build();
        //创建Request
        Request request = new Request.Builder()
                .url(NetConfig.url1 + NetConfig.MobileOA_Method)
                .post(multipartBody)
                .build();
        //创建okHttpClient
        OkHttpClient client = new NetUtil().getClient();
        //创建call对象
        Call call = client.newCall(request);
        LoadingDialog.showDialogForLoading(this);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("wjh", "upLoad 、  e=" + e);
                e.printStackTrace();
                loadFail("上传失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Log.e("wjh", "upLoad 、  response = " + response.body().string());
                try {
                    String data = response.body().string();
                    JSONObject jsonObject = new JSONObject(data);
                    boolean isSuccess = jsonObject.optBoolean("success");
                    if (isSuccess) {
                        loadFail("签到成功");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        });
                    } else {
                        loadFail(jsonObject.optString("message"));
                        Log.e(TAG, jsonObject.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    loadFail("json数据解析错误");
                } catch (Exception e) {
                    e.printStackTrace();
                    loadFail("数据解析错误");
                }

            }
        });

    }

    /*获取拍照权限*/
    private void canCamera() {
        requestRunPermisssion(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void onGranted() {
                takePhoto();
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                Toasts.showShort(SignActivity.this, "授权失败");
            }
        });
    }


    private String mTempPhotoPath;
    private Uri imageUri;

    /*拍照*/
    private void takePhoto() {
        Intent intentToTakePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File fileDir = new File(Environment.getExternalStorageDirectory() + File.separator + "huanxinOA");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        File photoFile = new File(fileDir, System.currentTimeMillis() + "huanxinoa.jpeg");
        mTempPhotoPath = photoFile.getAbsolutePath();

        imageUri = FileProvider.getUriForFile(this, "com.huanxin.oa.fileProvider", photoFile);
        intentToTakePhoto.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intentToTakePhoto, TAKE_PICTURE);
    }

    /*选择客户*/
    private void goLookUp(int code, String title, String data) {
        Intent intent = new Intent();
        intent.setClass(this, LookUpActivity.class);
        intent.putExtra("code", code);
        intent.putExtra("title", title);
        intent.putExtra("data", data);
        startActivityForResult(intent, code);
    }

    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            String address = "";

//            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
//                    address = address + location.getCity();
                    address = address + location.getDistrict();
                    address = address + location.getStreet();
                    address = address + location.getPoiList().get(0).getName();
                    SignActivity.this.address = location.getProvince() + location.getCity() + address;
                } else {
                    address = location.getAddrStr();
                    SignActivity.this.address = location.getAddrStr();
                }
                logMsg(address);
            } else {
                switch (location.getLocType()) {
                    case BDLocation.TypeServerError:
                        LocFail("服务端网络定位失败");
                        break;
                    case BDLocation.TypeNetWorkException:
                        LocFail("网络不同导致定位失败");
                        break;
                    case BDLocation.TypeCriteriaException:
                        LocFail("无法获取有效定位依据导致定位失败");
                        break;
                    default:
                        break;
                }
            }
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == TAKE_PICTURE) {
                Log.e("Uri", mTempPhotoPath);
                ImageLoaderUtil.loadImg(ivPhoto, mTempPhotoPath);
            }
        } else {
            Log.e(TAG, resultCode + "");
        }
        if (data != null) {
            if (resultCode == CUSTOMERCODE) {
                customerid = data.getStringExtra("id");
                tvCustomer.setText(TextUtils.isEmpty(data.getStringExtra("name")) ? "" : data.getStringExtra("name"));
            } else {
            }
        }
    }

    /**
     * 显示请求字符串
     *
     * @param str
     */
    public void logMsg(String str) {

        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (StringUtil.isNotEmpty(str))
                        tvLocation.setText(str);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void LocFail(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toasts.showShort(SignActivity.this, msg);
            }
        });

    }


    /**
     * 扫描权限申请和扫描逻辑处理
     */
    private void permission() {
        requestRunPermisssion(new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.CHANGE_WIFI_STATE,
                        Manifest.permission.READ_PHONE_STATE
                },
                new PermissionListener() {
                    @Override
                    public void onGranted() {
                        locationService.start();// 定位SDK
                    }

                    @Override
                    public void onDenied(List<String> deniedPermission) {
//                Toast.makeText(LoginActivity.this, "授权失败", Toast.LENGTH_LONG).show();
                        Toasts.showShort(SignActivity.this, "授权失败");
                    }
                });
    }

    private void loadFail(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.cancelDialogForLoading();
                Toasts.showShort(SignActivity.this, message);
            }
        });
    }
}
