package com.ly.baseapp.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AppOpsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.http.SslCertificate;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.FileProvider;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout.LayoutParams;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.TELEPHONY_SERVICE;

@SuppressLint("SimpleDateFormat")
public class Utils {
    private static final String ALGORITHM = "MD5";
    public static final Pattern EMOTION_URL = Pattern
            .compile("\\[f:\\d{1,3}\\]");
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String[] getResources(Context context, int id) {
        return context.getResources().getStringArray(id);
    }

    public static LayoutInflater getInflater(Context context) {
        return (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public static boolean isMobileNO(String mobiles) {

        Pattern p = Pattern
                .compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isHkMobileNO(String mobiles) {

        Pattern p = Pattern
                .compile("^(6|9)[0-9]{7}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isAomenMobileNO(String mobiles) {

        Pattern p = Pattern
                .compile("^6[0-9]{7}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isTaiwanMobileNO(String mobiles) {

        Pattern p = Pattern
                .compile("^9[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 是否是正确的手机号
     *
     * @param area
     * @return 不是正确的手机号  false
     */
    public static boolean isPhoneNoRight(Context context, String input_phone, String area) {
        if (TextUtils.isEmpty(input_phone)) {
            Utils.showToast(context, "手机号码不能为空");
            return false;
        }
        if ("大陆".equals(area) || "86".equals(area) || "+86".equals(area)) {
            if (!Utils.isMobileNO(input_phone)) {
                Utils.showToast(context, "请填写正确的大陆手机号");
                return false;
            }
        } else if ("香港".equals(area) || "852".equals(area)) {

            if (!Utils.isHkMobileNO(input_phone)) {
                Utils.showToast(context, "请填写正确的香港手机号");
                return false;
            }
        } else if ("澳门".equals(area) || "853".equals(area)) {
            if (!Utils.isAomenMobileNO(input_phone)) {
                Utils.showToast(context, "请填写正确的澳门手机号");
                return false;
            }
        } else if ("台湾".equals(area) || "886".equals(area)) {
            if (!Utils.isTaiwanMobileNO(input_phone)) {
                Utils.showToast(context, "请填写正确的台湾手机号");
                return false;
            }
        }
        return true;
    }

    public static boolean isPhone(Context context, String input_phone, String area) {
        if (TextUtils.isEmpty(input_phone)) {
            Utils.showToast(context, "手机号码不能为空");
            return false;
        }
        return true;
    }

    /**
     * @param area
     * @return 不同地区需要拼不同验证码发送
     */
    public static String areaToCode(String area) {
        if ("大陆".equals(area)) {
            return "86";
        } else if ("香港".equals(area)) {
            return "852";
        } else if ("澳门".equals(area)) {
            return "853";
        } else if ("台湾".equals(area)) {
            return "886";
        }
        return "";
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static boolean isPasswordNo(String password) {
        Pattern p = Pattern.compile("[a-zA-Z0-9_]{8,16}$");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * 判断是否是纯数字的密码
     */
    public static boolean isJustNum(String password) {
        Pattern p = Pattern.compile("[0-9]$");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * 判断是否为正整数
     *
     * @param mobiles
     * @return
     * @author 01 2016-5-5 下午7:15:34
     */
    public static boolean isIntegerNO(String mobiles) {
        String rex = "/^[1-9]+\\d*$";
        Pattern p = Pattern.compile(rex);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static String FromYearToDay(long time) {
        long timer = time * 1000L;
        String beginDate = String.valueOf(timer);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd");

        String sd = sdf.format(new Date(Long.parseLong(beginDate)));

        return sd;
    }

    public static String FromYearToHourMinute(long time) {

        long timer = time * 1000L;
        String beginDate = String.valueOf(timer);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        String sd = sdf.format(new Date(Long.parseLong(beginDate)));

        return sd;
    }


    public static Bitmap bitmapScale(Bitmap bitmap, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

//    public static void createBitmap(View view, Bitmap bitmap, float height,
//                                    float width, Context context, int numColumns) {
//        float ll_width = context.getResources().getDimension(
//                R.dimen.gridview_width);
//        float height_scale = height / (width / (ll_width / numColumns));
//        float width_scale = ll_width / numColumns;
//        ((ImageView) view).setImageBitmap(Bitmap.createScaledBitmap(bitmap,
//                (int) width_scale, (int) height_scale, true));
//    }

    /**
     * @param context
     * @param clazz
     */
    @SuppressWarnings("rawtypes")
    public static void startActivity(Context context, Class clazz) {
        Intent intent = new Intent();
        intent.setClass(context, clazz);
        context.startActivity(intent);
    }

    /**
     * @param context
     * @param clazz
     */
    @SuppressWarnings("rawtypes")
    public static void startClearAllActivity(Context context, Class clazz) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setClass(context, clazz);
        context.startActivity(intent);
    }

    @SuppressWarnings("rawtypes")
    public static void startClearAllActivity(Context context, Class clazz,
                                             Bundle bundle) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setClass(context, clazz);

        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * @param context
     * @param dp
     * @return
     */
    public static int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * @param context
     * @param px
     * @return
     */
    public static int px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * @param context
     * @param clazz
     * @param bundle
     */
    @SuppressWarnings("rawtypes")
    public static void startActivity(Context context, Class clazz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, clazz);

        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    public static void startActivityPush(Context context, Class clazz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(context, clazz);

        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * @param activity
     * @param clazz
     * @param bundle
     * @param resultCode
     */
    @SuppressWarnings("rawtypes")
    public static void startActivityForResult(Activity activity, Class clazz,
                                              Bundle bundle, int resultCode) {
        Intent intent = new Intent();
        intent.setClass(activity, clazz);

        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, resultCode);
    }

    /**
     * @param activity
     * @param clazz
     * @param resultCode
     */
    @SuppressWarnings("rawtypes")
    public static void startActivityForResult(Activity activity, Class clazz, int resultCode) {
        Intent intent = new Intent();
        intent.setClass(activity, clazz);
        activity.startActivityForResult(intent, resultCode);
    }

    // 获取屏幕的宽度
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }

    // 获取屏幕的高度
    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }

    public static float getDimen(Context context, int id) {
        return context.getResources().getDimension(id);
    }

    public static float getVideoHeight(Context context) {
        return getScreenWidth(context) / 16 * 9;
    }

    public static String getCurrentTime() {
        Date nowTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        String nowTimeStr = dateFormat.format(nowTime);
        return nowTimeStr;
    }

    public static String getCurrentTimeByyyyMMdd() {
        Date nowTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String nowTimeStr = dateFormat.format(nowTime);
        return nowTimeStr;
    }

    public static String getCurrentTimeOnlyHHMM() {
        Date nowTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String nowTimeStr = dateFormat.format(nowTime);
        return nowTimeStr;
    }

    public static String getEventTime(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
        String nowTimeStr = dateFormat.format(time);
        return nowTimeStr;
    }

    public static String parseTime(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        String nowTimeStr = dateFormat.format(time * 1000L);
        return nowTimeStr;
    }

    /**
     * encode By MD5
     *
     * @param str
     * @return String
     */
    public static String encodeByMD5(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Takes the raw bytes from the digest and formats them correct.
     *
     * @param bytes the raw bytes from the digest.
     * @return the formatted bytes.
     */
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    public static View inflater(Context context, int id) {
        return LayoutInflater.from(context).inflate(id, null);
    }

    public static float getDrawableWidth(Context context, int id) {
        Bitmap bitmap = BitmapFactory
                .decodeResource(context.getResources(), id);
        return bitmap.getWidth();
    }

    public static Bitmap getScaleBitmap(Bitmap bitMap) {
        int width = bitMap.getWidth();
        int height = bitMap.getHeight();
        // 设置想要的大小
        int newWidth = 50;
        int newHeight = 50;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        return bitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height,
                matrix, true);
    }

    public static String facePathToString(String str) {
        List<String> faceList = new ArrayList<String>();
        List<String> numList = new ArrayList<String>();
        String replaceStr = "<img src=\"http://static.huomaotv.com/static/face/x-x-x.gif\"/>";
        Pattern p = Pattern.compile("[\"\'](http.+\\.(jpg|gif|png))[\"\']");
        Matcher m = p.matcher(str);
        while (m.find()) {
            String numberString = m.group(1).substring(
                    m.group(1).lastIndexOf("/") + 1,
                    m.group(1).lastIndexOf("."));
            faceList.add(replaceStr.replace("x-x-x", numberString));
            numList.add(numberString);
            return str.replace(
                    replaceStr.replace("x-x-x", numberString),
                    "[f:"
                            + m.group(1).substring(
                            m.group(1).lastIndexOf("/") + 1,
                            m.group(1).lastIndexOf(".")) + "]");
        }
        return str;
    }

    public static String replaceFace(String str) {
        String replaceStr = "<img src=\"http://static.huomaotv.com/static/face/x-x-x.gif\"/>";
        Pattern p = Pattern.compile("[\"\'](http.+\\.(jpg|gif|png))[\"\']");
        Matcher m = p.matcher(str);
        while (m.find()) {
            String numberString = m.group(1).substring(
                    m.group(1).lastIndexOf("/") + 1,
                    m.group(1).lastIndexOf("."));

            return str.replace(replaceStr.replace("x-x-x", numberString), "");
        }
        return str;
    }

    public static String replaceAllShow(String str, String regex, String newstr) {

        return str.replaceAll(regex, newstr);
    }

    public static String replaceAllFace(String info) {
        // Pattern p = Pattern.compile(Global.FACE_PATTERN);
        // Matcher m = p.matcher(info);
        // while (m.find()) {
        // info.replaceFirst(m.group(1), "");
        // }
        return info.replaceAll("\\[f:\\d{1,3}\\]", "");
    }

    /**
     * MD5 加密
     */
    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString();
    }


    /**
     * @param context
     */
    public static void hideKeyboard(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * @param context
     * @param content
     */
    public static void showToast(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    public static void showToastShort(Activity activity, String content) {
        Toast.makeText(activity, content, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param activity
     */
    public static void showToast(Activity activity, int resId) {
        Toast.makeText(activity, activity.getString(resId), Toast.LENGTH_LONG)
                .show();
    }

    /**
     * 判断网络是否连接上
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断wifi是否连接上
     *
     * @param context
     * @return
     */
    public static boolean checkNetworkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final NetworkInfo wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final NetworkInfo mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isAvailable() || mobile.isAvailable()) {
            return true;
        } else {
            return false;
        }

    }



    public static String getLocalIp(Context context) {
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        if (ipAddress == 0)
            return null;
        return ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "."
                + (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
    }

    public static String getWifiMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        return wifi.getConnectionInfo().getMacAddress();
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
//			Log.e("WifiPreference IpAddress", ex.getMessage().toString());
        }
        return null;
    }

    public Bitmap stringtoBitmap(String string) {

        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;

    }

    @SuppressLint("SdCardPath")
    public void saveMyBitmap(String bitName, Bitmap mBitmap) {
        File f = new File("/sdcard/" + bitName + ".png");
        try {
            f.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String bitmap2String(Bitmap bitmap) {
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public static String mapDesc(Map<String, String> map1) {
        String str = "";
        String value = "";
        Map<String, String> map = new TreeMap<String, String>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        // 降序排序
                        return obj2.compareTo(obj1);
                    }
                });
        map.putAll(map1);
        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            str = str + key;
            String value1 = map.get(key);
            value = value + value1;
        }
        return value;

    }

    public static String getVersionName(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            // 当前应用的版本名称
            return info.versionName;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static int getVersionCode(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            // 当前应用的版本名称
            return info.versionCode;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public static String[] getAllowContentType() {
        String[] allowContentTypes = {"application/octet-stream"};
        return allowContentTypes;
    }

    public static void installApk(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        File file = new File(url);

        if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri =
                    FileProvider.getUriForFile(context, "com.huomaotv.mobile.fileprovider", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    public static String getVersionRelease() {
        return android.os.Build.VERSION.RELEASE;
    }

    public static int getVersionSDK() {
        return Integer.valueOf(android.os.Build.VERSION.SDK);
    }

    public static String getPhoneFirm() {
        return android.os.Build.MANUFACTURER;
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static void WriteTxtFile(String strcontent) {
        // 每次写入时，都换行写
        String strContent = strcontent + "\n";
        try {
            String strFilePath = getHuoMaoTVApkPath() + "/result.txt";
            File file = new File(strFilePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getHuoMaoTVApkPath() {
        String path = Environment.getExternalStorageDirectory().getPath()
                + "/huomaotv";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    public static void setGridViewHeightBasedOnChildren(GridView gridView) {
        // 获取GridView对应的Adapter
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int rows;
        int columns = 0;
        int horizontalBorderHeight = 0;
        Class<?> clazz = gridView.getClass();
        try {
            // 利用反射，取得每行显示的个数
            Field column = clazz.getDeclaredField("mRequestedNumColumns");
            column.setAccessible(true);
            columns = (Integer) column.get(gridView);
            // 利用反射，取得横向分割线高度
            Field horizontalSpacing = clazz
                    .getDeclaredField("mRequestedHorizontalSpacing");
            horizontalSpacing.setAccessible(true);
            horizontalBorderHeight = (Integer) horizontalSpacing.get(gridView);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        // 判断数据总数除以每行个数是否整除。不能整除代表有多余，需要加一行
        if (listAdapter.getCount() % columns > 0) {
            rows = listAdapter.getCount() / columns + 1;
        } else {
            rows = listAdapter.getCount() / columns;
        }
        int totalHeight = 0;
        for (int i = 0; i < rows; i++) { // 只计算每项高度*行数
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight + horizontalBorderHeight * (rows - 1);// 最后加上分割线总高度
        gridView.setLayoutParams(params);
    }

    // 将Unicode字符串转换成bool型数组
    public static boolean[] StrToBool(String input) {
        boolean[] output = Binstr16ToBool(BinstrToBinstr16(StrToBinstr(input)));
        return output;
    }

    // 将bool型数组转换成Unicode字符串
    public static String BoolToStr(boolean[] input) {
        String output = BinstrToStr(Binstr16ToBinstr(BoolToBinstr16(input)));
        return output;
    }

    // 将字符串转换成二进制字符串，以空格相隔
    public static String StrToBinstr(String str) {
        char[] strChar = str.toCharArray();
        String result = "";
        for (int i = 0; i < strChar.length; i++) {
            result += Integer.toBinaryString(strChar[i]) + " ";
        }
        return result;
    }

    // 将二进制字符串转换成Unicode字符串
    public static String BinstrToStr(String binStr) {
        String[] tempStr = StrToStrArray(binStr);
        char[] tempChar = new char[tempStr.length];
        for (int i = 0; i < tempStr.length; i++) {
            tempChar[i] = BinstrToChar(tempStr[i]);
        }
        return String.valueOf(tempChar);
    }

    // 将二进制字符串格式化成全16位带空格的Binstr
    public static String BinstrToBinstr16(String input) {
        StringBuffer output = new StringBuffer();
        String[] tempStr = StrToStrArray(input);
        for (int i = 0; i < tempStr.length; i++) {
            for (int j = 16 - tempStr[i].length(); j > 0; j--)
                output.append('0');
            output.append(tempStr[i] + " ");
        }
        return output.toString();
    }

    // 将全16位带空格的Binstr转化成去0前缀的带空格Binstr
    public static String Binstr16ToBinstr(String input) {
        StringBuffer output = new StringBuffer();
        String[] tempStr = StrToStrArray(input);
        for (int i = 0; i < tempStr.length; i++) {
            for (int j = 0; j < 16; j++) {
                if (tempStr[i].charAt(j) == '1') {
                    output.append(tempStr[i].substring(j) + " ");
                    break;
                }
                if (j == 15 && tempStr[i].charAt(j) == '0')
                    output.append("0" + " ");
            }
        }
        return output.toString();
    }

    // 二进制字串转化为boolean型数组 输入16位有空格的Binstr
    public static boolean[] Binstr16ToBool(String input) {
        String[] tempStr = StrToStrArray(input);
        boolean[] output = new boolean[tempStr.length * 16];
        for (int i = 0, j = 0; i < input.length(); i++, j++)
            if (input.charAt(i) == '1')
                output[j] = true;
            else if (input.charAt(i) == '0')
                output[j] = false;
            else
                j--;
        return output;
    }

    // boolean型数组转化为二进制字串 返回带0前缀16位有空格的Binstr
    public static String BoolToBinstr16(boolean[] input) {
        StringBuffer output = new StringBuffer();
        for (int i = 0; i < input.length; i++) {
            if (input[i])
                output.append('1');
            else
                output.append('0');
            if ((i + 1) % 16 == 0)
                output.append(' ');
        }
        output.append(' ');
        return output.toString();
    }

    // 将二进制字符串转换为char
    public static char BinstrToChar(String binStr) {
        int[] temp = BinstrToIntArray(binStr);
        int sum = 0;
        for (int i = 0; i < temp.length; i++) {
            sum += temp[temp.length - 1 - i] << i;
        }
        return (char) sum;
    }

    // 将初始二进制字符串转换成字符串数组，以空格相隔
    public static String[] StrToStrArray(String str) {
        return str.split(" ");
    }

    // 将二进制字符串转换成int数组
    public static int[] BinstrToIntArray(String binStr) {
        char[] temp = binStr.toCharArray();
        int[] result = new int[temp.length];
        for (int i = 0; i < temp.length; i++) {
            result[i] = temp[i] - 48;
        }
        return result;
    }

    /**
     * 表情过滤
     *
     * @return
     * @author ghrt 2015-3-17 下午3:15:48
     */
    public static InputFilter getInputFilter() {
        InputFilter emojiFilter = new InputFilter() {
            Pattern emoji = Pattern
                    .compile(
                            "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                            Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                // TODO Auto-generated method stub
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    return "";
                }
                return null;
            }

        };
        return emojiFilter;
    }

    /**
     * 全屏显示
     *
     * @author ghrt 2015-3-16 上午10:28:30
     */
    public static void setFullScreen(Activity activity) {
        activity.getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        activity.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        activity.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        activity.getWindow().addFlags(
//                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
    }


    public static void cancelFullScreen(Activity activity) {
        activity.getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        activity.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    }

    public static void showBtnFullScrean(Activity activity) {
        activity.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 切换到竖屏时,固定显示状态栏
     *
     * @author ghrt 2015-3-16 上午10:28:50
     */
    public static void setFullScreen1(Activity activity) {
        setFullScreen(activity);
        activity.getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR);
    }

    /**
     * 计算状态栏的高度
     *
     * @return
     * @author ghrt 2015-3-16 上午10:28:57
     */
    public static int getStatusHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 横屏时，给top_content_rl设置一个margin值，值的高度为状态栏的高度
     *
     * @param height
     * @author ghrt 2015-3-16 上午10:29:07
     */
    public static void marginTop(int height, RelativeLayout top_content_rl) {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(0, height, 0, 0);
        top_content_rl.setLayoutParams(params);
    }

    public static String secondToHHMMSS(int second) {
        int h = 0;
        int d = 0;
        int s = 0;
        int temp = second % 3600;
        if (second > 3600) {
            h = second / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            d = second / 60;
            if (second % 60 != 0) {
                s = second % 60;
            }
        }

        return h + "时" + d + "分" + s + "秒";
    }

    public static double decimalFormat(float count) {
        return (double) (Math.round(count / 100) / 100.0);
    }

    /**
     * 判断某个界面是否在前台
     *
     * @param context
     * @param className 某个界面名称
     */
    public static boolean activityIsForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context
                .getSystemService(ACTIVITY_SERVICE);
        List<RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param list
     * @param cid
     * @return 将cid保存到list中，返回新的list
     */
    public static List addCidToList(List list, String cid) {
        List<String> cidList = new ArrayList<String>();
        List<String> tempList = new ArrayList<String>();

        tempList.addAll(list);
        int index = isListCanAddCid(list, cid);
        if (index != -1) {
            tempList = deleteCidOnList(list, index);
        }
        try {
            //不能直接操作list,否则在remove的时候会抛异常，所以用个新的tempList
            if (tempList.size() == 20) {    //最多显示20条
                tempList.remove(19);
            }
        } catch (Exception e) {

        }
        cidList.add(cid);
        cidList.addAll(tempList);
        return cidList;
    }

    public static List addTimeToList(List list, String cid, List tlist) {
        List<String> timelist = new ArrayList<String>();
        List<String> tempList = new ArrayList<String>();

        tempList.addAll(tlist);
        int index = isListCanAddCid(list, cid);
        if (index != -1) {
            tempList = deleteCidOnList(tlist, index);
        }
        try {
            //不能直接操作list,否则在remove的时候会抛异常，所以用个新的tempList
            if (tempList.size() == 20) {    //最多显示20条
                tempList.remove(19);
            }
        } catch (Exception e) {

        }
        timelist.add(System.currentTimeMillis() + "");
        timelist.addAll(tempList);
        return timelist;
    }

    /**
     * @param list
     * @param position
     * @return 将指定位置的cid删除，返回新的list
     */
    public static List deleteCidOnList(List list, int position) {
        List<String> cidList = new ArrayList<String>();
        List<String> tempList = new ArrayList<String>();
        try {
            tempList.addAll(list);

            //不能直接操作list,否则在remove的时候会抛异常，所以用个新的tempList
            tempList.remove(position);
            cidList.addAll(tempList);
        } catch (Exception e) {
        }
        return cidList;
    }

    /**
     * @param list
     * @return 返回字符串如：23，234，345
     */
    public static String getCidListString(List list) {
        String cid = null;
        StringBuilder cidBuild = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                cidBuild.append(list.get(i));
            } else {
                cidBuild.append(list.get(i) + ",");
            }
        }
        cid = cidBuild.toString();
        return cid;
    }

    /**
     * @param list
     * @param cid
     * @return 如果List不包含此cid则返回true
     */
    public static int isListCanAddCid(List list, String cid) {
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (cid.equals(list.get(i))) {
                index = i;
            }
        }
        return index;
    }

    public static String getImgName(String imgUrl) {
        if (imgUrl == null || imgUrl.equals("")) {
            return "";
        }
        String[] strs = imgUrl.split("/");
        return strs[strs.length - 1];
    }

    public static void deleteFile(String path) {
        deleteFile(new File(path));
    }

    public static void deleteFile(File oldPath) {
        try {
            if (oldPath.isDirectory()) {
                File[] files = oldPath.listFiles();
                for (File file : files) {
                    deleteFile(file);
                    file.delete();
                }
            } else {
                oldPath.delete();
            }
        } catch (Exception e) {

        }
    }

    /**
     * 自适应宽度加载图片。保持图片的长宽比例不变，通过修改imageView的高度来完全显示图片。
     */


    /**
     * 绘制角签,输出bitmap
     *
     * @param color
     * @param height
     * @return
     */
    public static Bitmap getBitmap(Context context, String color, int width, int height) {
        //绘制区域
        Bitmap b = Bitmap.createBitmap(Utils.dp2Px(context, height / 2) + width,
                Utils.dp2Px(context, height), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);
        //绘制背景
//        canvas.drawColor(Color.BLUE);
        //创建画笔
        Paint paint = new Paint();
        //设置画笔颜色
        paint.setColor(Color.parseColor(color));

        /*画一个梯形*/
        Path path1 = new Path();
        path1.moveTo(0, dp2Px(context, height));
        path1.lineTo(width, dp2Px(context, height));
        path1.lineTo(width + dp2Px(context, 6), 0);
        path1.lineTo(0, 0);
        path1.close();
        //
        canvas.drawPath(path1, paint);
        return b;
    }

    public static int measureWidth(View view) {
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(spec, spec);
        int width = view.getMeasuredWidth();
        return width;
    }

    /**
     * 判断应用是否已经启动
     *
     * @param context     一个context
     * @param packageName 要判断应用的包名
     * @return boolean
     */
    public static boolean isAppAlive(Context context, String packageName) {
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for (int i = 0; i < processInfos.size(); i++) {
            if (processInfos.get(i).processName.equals(packageName)) {
                Log.i("NotificationLaunch",
                        String.format("the %s is running, isAppAlive return true", packageName));
                return true;
            }
        }
        Log.i("NotificationLaunch",
                String.format("the %s is not running, isAppAlive return false", packageName));
        return false;
    }


    public static boolean isStart(Context context, Class c) {
        Intent intent = new Intent(context, c);
        ComponentName cmpName = intent.resolveActivity(context.getPackageManager());
        boolean isStart = false;
        if (cmpName != null) { // 说明系统中存在这个activity
            ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
            List<RunningTaskInfo> taskInfoList = am.getRunningTasks(1);
            for (RunningTaskInfo taskInfo : taskInfoList) {
                if (taskInfo.baseActivity.equals(cmpName)) { // 说明它已经启动了
                    isStart = true;
                    return isStart;
                }
            }
        }
        return isStart;

    }

    public static boolean isClsRunning(String pkg, String cls, Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        ActivityManager.RunningTaskInfo task = tasks.get(0);
        if (task != null) {
            return TextUtils.equals(task.topActivity.getPackageName(), pkg) && TextUtils.equals(task.topActivity.getClassName(), cls);
        }
        return false;
    }


    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length / 1024 > 35) { // 循环判断如果压缩后图片是否大于35kb,大于继续压缩
            output.reset();// 重置baos即清空baos
            bmp.compress(Bitmap.CompressFormat.JPEG, options, output);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * json转list
     *
     * @param strJson
     * @param <T>
     * @return
     */
    public static <T> List<T> getDataList(String strJson) {
        List<T> datalist = new ArrayList<T>();
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
        }.getType());
        return datalist;
    }

    public static String getUnikey() {
        String[] data = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "a", "b", "c", "d", "e", "f"};
        String unikey = "010011";
        for (int j = 0; j < 26; j++) {
            unikey = unikey + data[(int) ((Math.random() * 16))];
        }
        return unikey;
    }

    /**
     * 判断当前应用程序处于前台还是后台
     */
    public static boolean isApplicationBroughtToBackground(Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 用来返回聊天发言
     */


    /**
     * 横向拼接
     * <功能详细描述>
     *
     * @param first
     * @param second
     * @return
     */
    public static Bitmap addHorizontalBitmap(Bitmap first, Bitmap second) {
        int width = first.getWidth() + second.getWidth();
        int height = Math.max(first.getHeight(), second.getHeight());
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(second, first.getWidth() + 10, 0, null);
        return result;
    }


    /**
     * 纵向拼接
     * <功能详细描述>
     *
     * @param first
     * @param second
     * @return
     */
    public static Bitmap addVerticalBitmap(Bitmap first, Bitmap second) {
        int width = Math.max(first.getWidth(), second.getWidth());
        int height = first.getHeight() + second.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(second, 0, first.getHeight() + 10, null);
        return result;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }


    /**
     * 多图 横向拼接
     * <功能详细描述>
     *
     * @param array
     * @return
     */

    public static Bitmap addHorizontalBitmaps(ArrayList<Bitmap> array, int width, int height) {
        if (array != null && array.size() >= 2) {
            Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            int pWidth = 0;
            Canvas canvas = new Canvas(result);
            for (int j = 0; j < array.size(); j++) {
                canvas.drawBitmap(array.get(j), pWidth, j == 0 ? (array.get(1).getHeight() - array.get(0).getHeight()) / 2 : 0, null);

                pWidth += array.get(j).getWidth();

            }
            return result;
        }
        return null;

    }


    /**
     * @author weiless
     * @date 2017/11/23
     * @description 解决listview和scrollview的冲突
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }



    public static boolean isSSLCertOk(SslCertificate cert, String sha256Str) {
        byte[] SSLSHA256 = hexToBytes(sha256Str);
        Bundle bundle = SslCertificate.saveState(cert);
        if (bundle != null) {
            byte[] bytes = bundle.getByteArray("x509-certificate");
            if (bytes != null) {
                try {
                    CertificateFactory cf = CertificateFactory.getInstance("X.509");
                    Certificate ca = cf.generateCertificate(new ByteArrayInputStream(bytes));
                    MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
                    byte[] key = sha256.digest(((X509Certificate) ca).getEncoded());
                    return Arrays.equals(key, SSLSHA256);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * hexString转byteArr
     * <p>例如：</p>
     * hexString2Bytes("00A8") returns { 0, (byte) 0xA8 }
     *
     * @param hexString
     * @return 字节数组
     */
    public static byte[] hexToBytes(String hexString) {

        if (hexString == null || hexString.trim().length() == 0)
            return null;

        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] bytes = new byte[length];
        String hexDigits = "0123456789abcdef";
        for (int i = 0; i < length; i++) {
            int pos = i * 2; // 两个字符对应一个byte
            int h = hexDigits.indexOf(hexChars[pos]) << 4; // 注1
            int l = hexDigits.indexOf(hexChars[pos + 1]); // 注2
            if (h == -1 || l == -1) { // 非16进制字符
                return null;
            }
            bytes[i] = (byte) (h | l);
        }
        return bytes;
    }

    //判断微信是否可用
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        // 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        // 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    //判断weibo是否可用
    public static boolean isWeiBoAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        // 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        // 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.sina.weibo")) {
                    return true;
                }
            }
        }
        return false;
    }


    //判断QQ是否可用
    public static boolean isQQAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断 Uri是否有效
     */
    public static boolean isValidIntent(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return !activities.isEmpty();
    }


    /**
     * 将毫秒数格式化为"##:##"的时间
     *
     * @param milliseconds 毫秒数
     * @return ##:##
     */
    public static String formatTime(long milliseconds) {
        if (milliseconds <= 0 || milliseconds >= 24 * 60 * 60 * 1000) {
            return "00:00";
        }
        long totalSeconds = milliseconds / 1000;
        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;
        StringBuilder stringBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(stringBuilder, Locale.getDefault());
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    /*
    * 将时分秒转为秒数
    * */
    public static long formatTurnSecond(String time) {
        long back = 0;
        String a[] = null;
        if (!"".equals(time)) {
            a = time.split(":");
            if (a != null) {
                for (int i = 0; i < a.length; i++) {
                    back += Integer.parseInt(a[i]) * Math.pow(60, a.length - i - 1);
                }
            }
        }
        return back;
    }

    /**
     * 系统通知
     *
     * @param context
     * @return
     */
    public static boolean isNotificationEnabled(Context context) {
        boolean isOpened = false;
        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;
        Class appOpsClass = null;
   /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
            int value = (Integer) opPostNotificationValue.get(Integer.class);

            NotificationManagerCompat manager = NotificationManagerCompat.from(context);
            isOpened = manager.areNotificationsEnabled();

            Log.v("Nancy", "isOpen is value : " + isOpened);
//            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
            return isOpened;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }


    //判断view是否被遮盖
    public static boolean isCover(View view) {
        boolean cover = false;
        Rect rect = new Rect();
        cover = view.getGlobalVisibleRect(rect);
        if (cover) {
            if (rect.width() >= view.getMeasuredWidth() && rect.height() >= view.getMeasuredHeight()) {
                return !cover;
            }
        }
        return true;
    }



}
