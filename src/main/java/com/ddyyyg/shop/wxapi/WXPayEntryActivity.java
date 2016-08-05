package com.ddyyyg.shop.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ddyyyg.app.Constants;
import com.ddyyyg.shop.R;
import com.ddyyyg.shop.ui.adapter.ViewPagerAdapter;
import com.ddyyyg.shop.utils.LogUtil;
import com.ddyyyg.shop.utils.PayWebChromeClient;
import com.ddyyyg.shop.utils.SPUtils;
import com.ddyyyg.shop.utils.ToastUtil;
import com.ddyyyg.shop.utils.URLSetUtil;
import com.ddyyyg.shop.view.CustomViewPager;
import com.ddyyyg.shop.view.MainView;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.ArrayList;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
    private static final String ERROR_PAGE = "data:text/html,chromewebdata";
    private static final String APP_CACAHE_DIRNAME = "/webcache";
    private CustomViewPager pager;
    private ArrayList<View> viewContainter;
    private View guideView0;
    private View guideView1;
    private MainView mainView;
    private IWXAPI iwxapi;
    private boolean lock;

    @Override
    public void onReq(BaseReq req) {}

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
           mainView.getWebView().loadUrl(Constants.HOST+"/?/mobile/home");
        }
        if (resp instanceof com.tencent.mm.sdk.modelmsg.SendAuth.Resp){
             LogUtil.d("login",""+resp.getType());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        regToWx();
        getWxapi().handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        getWxapi().handleIntent(intent, this);
    }

    /**
     * 返回文件选择
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent intent) {
        if (requestCode == PayWebChromeClient.FILECHOOSER_RESULTCODE) {
            if (null == PayWebChromeClient.mUploadMessage){//TODO
                return;
            }
            Uri result = intent == null || resultCode != RESULT_OK ? null: intent.getData();
            PayWebChromeClient.mUploadMessage.onReceiveValue(result);
            PayWebChromeClient.mUploadMessage = null;

        }
    }

    public boolean isPaySupported(){
        boolean isPaySupported = getWxapi().getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if (!isPaySupported) {
            ToastUtil.makeText(WXPayEntryActivity.this, "微信版本不支持支付功能");
        }
        return  isPaySupported;
    }

    /**
     * 点击两次返回键退出应用程序
     */
    private long mExitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (URLSetUtil.getInstance().tabUrls.contains(mainView.getWebView().getUrl()) ||
                    !mainView.getWebView().canGoBack()) {
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    mExitTime = System.currentTimeMillis();
                } else {
                    this.finish();
                    System.exit(0);
                }
            } else {
                mainView.getWebView().goBack();
            }
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean isLock() {
        return !lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public IWXAPI getWxapi() {
        if (iwxapi == null){
            regToWx();
        }
        return iwxapi;
    }

    private void regToWx(){
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        iwxapi = WXAPIFactory.createWXAPI(this, getResources().getString(R.string.APP_ID_WX),true);
        iwxapi.registerApp(getResources().getString(R.string.APP_ID_WX));
    }

    private void setupViews(){
        this.pager = (CustomViewPager) this.findViewById(R.id.viewpager);

        //this.pager.setPageTransformer();

        LayoutInflater inflater = LayoutInflater.from(this);

        this.guideView0 = inflater.inflate(R.layout.current_image_view, null);

        ((ImageView)guideView0).setImageResource(R.mipmap.launchs);

        this.guideView1 = inflater.inflate(R.layout.view_main, null);

        this.mainView = (MainView)guideView1;

        this.viewContainter = new ArrayList<View>();

        if (!SPUtils.getBoolean(this,"isFirst",false)){/*地址启动引导图*/}

        viewContainter.add(guideView0);

        viewContainter.add(guideView1);

        pager.setAdapter(new ViewPagerAdapter(this, viewContainter));

        pager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;//返回true拦截手动滑动
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pager.setCurrentItem(1);
            }
        }, 3*1000);
    }

    @Override
    protected void onResume() {//在Activity显示后执行
        super.onResume();
    }

    @Override
    protected void onPause() {//在activity将要消失后执行
        super.onPause();
    }

    @Override
    protected void onStop() {//在Activity消失后执行
        super.onStop();
        pager.setScanScroll(true);
        pager.setCurrentItem(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
