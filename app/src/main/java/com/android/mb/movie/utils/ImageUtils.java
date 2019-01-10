package com.android.mb.movie.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.android.mb.movie.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by cgy on 2017/7/17
 */
public class ImageUtils {

    public static String saveBitMapToFile(Context context, String fileName, Bitmap bitmap) {
        if(null == context || null == bitmap) {
            return null;
        }
        if(TextUtils.isEmpty(fileName)) {
            return null;
        }
        FileOutputStream fOut = null;
        try {
            File file = null;
            String fileDstPath = "";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                // 保存到sd卡
                fileDstPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator + "MyFile" + File.separator + fileName;

                File homeDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator + "MyFile" + File.separator);
                if (!homeDir.exists()) {
                    homeDir.mkdirs();
                }
            } else {
                // 保存到file目录
                fileDstPath = context.getFilesDir().getAbsolutePath()
                        + File.separator + "MyFile" + File.separator + fileName;

                File homeDir = new File(context.getFilesDir().getAbsolutePath()
                        + File.separator + "MyFile" + File.separator);
                if (!homeDir.exists()) {
                    homeDir.mkdir();
                }
            }

            file = new File(fileDstPath);
            if (file.exists()){
                file.delete();
            }

            fOut = new FileOutputStream(file);
            if (fileName.endsWith(".jpg")) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, fOut);
            } else {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            }
            fOut.flush();
            Log.i("FileSave", "saveDrawableToFile " + fileName
                    + " success, save path is " + fileDstPath);
            return fileDstPath;
        } catch (Exception e) {
            Log.e("FileSave", "saveDrawableToFile: " + fileName + " , error", e);
            return null;
        } finally {
            if(null != fOut) {
                try {
                    fOut.close();
                } catch (Exception e) {
                    Log.e("FileSave", "saveDrawableToFile, close error", e);
                }
            }
        }
    }


    /**
     * base64 转图片
     * @param string
     * @return
     */
    public static Bitmap stringToBitmap(String string){
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 通过Base32将Bitmap转换成Base64字符串
     * @param bit
     * @return
     */
    public static String bitmap2Base64(Bitmap bit){
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 100, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }


    public static void displayAvatar(Context context, String url, ImageView imageView){
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_avatar_default)// 正在加载中的图片
                .error(R.mipmap.ic_avatar_default) // 加载失败的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略
        Glide.with(context).load(url).apply(options).into(imageView);
    }

}
