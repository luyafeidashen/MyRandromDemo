package com.yafei.demo.myrandromdemo;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView image;
    private EditText et;
    private Button btn,submit,btn_weixin;
    private String codeStr;
    private CodeUtils codeUtils;
    private IWXAPI api;
    private static final String tag = MainActivity.class.getSimpleName();
    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.image);
        et = (EditText) findViewById(R.id.et);
        btn = (Button) findViewById(R.id.btn);
        submit = (Button) findViewById(R.id.btn_submit);
        btn_weixin = (Button) findViewById(R.id.btn_weixin);
        btn.setOnClickListener(this);
        submit.setOnClickListener(this);
        btn_weixin.setOnClickListener(this);
        this.mWebView = (WebView)this.findViewById(R.id.webview);
        String url = "此处设置为自己的url";
        this.mWebView.loadUrl(url);
//        "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
        this.mWebView.setWebViewClient(new MyWebViewClient());

    }


    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            Log.d(tag, "shouldOverrideUrlLoading: url="+url);
            if(url!= null){
                //要跳转到的界面
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("ekang://TestH5Activity")));
                return true;
            }else{
                return super.shouldOverrideUrlLoading(view, url);
            }
        }
    }
//    @OnClick({R.id.btn,R.id.btn_submit})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                codeUtils = CodeUtils.getInstance();
                Bitmap bitmap = codeUtils.createBitmap();
                image.setImageBitmap(bitmap);
                break;
            case R.id.btn_submit:
                codeStr = et.getText().toString().trim();
                Log.e("codeStr", codeStr);
                if (null == codeStr || TextUtils.isEmpty(codeStr)) {
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = codeUtils.getCode();
                Log.e("code", code);
                if (code.equalsIgnoreCase(codeStr)) {
                    Toast.makeText(this, "验证码正确", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_weixin:
//               TODO: 微信登录
                System.out.println("微信登录进口");
                //api注册
                api = WXAPIFactory.createWXAPI(this, "wx1ade6d249c33f178", true);
                api.registerApp("APP_ID");

                SendAuth.Req req = new SendAuth.Req();

//授权读取用户信息
                req.scope = "snsapi_userinfo";

//自定义信息
                req.state = "wechat_sdk_demo_test";

//向微信发送请求

                api.sendReq(req);
                break;
            default:
                break;
        }
    }
}
