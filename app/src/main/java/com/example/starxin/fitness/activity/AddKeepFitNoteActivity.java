package com.example.starxin.fitness.activity;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.example.starxin.fitness.domain.KeepNoteEntity;
import com.liuguangqiang.ipicker.IPicker;
import com.wang.avi.AVLoadingIndicatorView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import com.example.starxin.fitness.R;

public class AddKeepFitNoteActivity extends Activity implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "AddKeepFitNoteActivity";

    @Bind(R.id.add_keep_fit_date_tv)
    TextView addKeepFitDateTv;
    @Bind(R.id.add_keep_fit_time_length)
    EditText addKeepFitTimeLength;
    @Bind(R.id.add_keep_fit_run_length)
    EditText addKeepFitRunLength;
    @Bind(R.id.add_keep_fit_sitUps)
    EditText addKeepFitSitUps;
    @Bind(R.id.add_keep_fit_sports_apparatus_times)
    EditText addKeepFitSportsApparatusTimes;
    @Bind(R.id.add_keep_fit_others)
    EditText addKeepFitOthers;
    @Bind(R.id.add_keep_fit_submit)
    TextView addKeepFitSubmit;
    @Bind(R.id.picture_label_ll)
    LinearLayout pictureLabelLl;
    @Bind(R.id.add_keep_fit_picture)
    ImageView addKeepFitPicture;
    @Bind(R.id.keep_fit_picture)
    ImageView keepFitPicture;
    @Bind(R.id.back_iv)
    ImageView backIv;

    @Bind(R.id.content_rl)
    RelativeLayout contentRl;

    @Bind(R.id.avi)
    AVLoadingIndicatorView avi;
    private Context mContext;

    private List<String> pictureList;

    private boolean isEdit = false;

    private String objectId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_keep_fit_note);
        ButterKnife.bind(this);
        mContext = this;
        init();
    }

    private void init() {

        addKeepFitDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AddKeepFitNoteActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
        if (getIntent().getStringExtra("objectId") != null) {
            isEdit = true;
            objectId = getIntent().getStringExtra("objectId");
        }
        addKeepFitSubmit.setText(isEdit ? "更新" : "保存");
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        keepFitPicture.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(mContext).setMessage("你确定要删除该图片吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pictureList = null;
                                keepFitPicture.setVisibility(View.GONE);
                                addKeepFitPicture.setVisibility(View.VISIBLE);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                return false;
            }
        });
        if (isEdit) {
            queryData();
        } else {
            avi.setVisibility(View.GONE);
            contentRl.setVisibility(View.VISIBLE);
            Date todayDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = dateFormat.format(todayDate);
            addKeepFitDateTv.setText(dateString);
            keepFitPicture.setVisibility(View.GONE);
        }
        IPicker.setOnSelectedListener(new IPicker.OnSelectedListener() {
            @Override
            public void onSelected(List<String> paths) {
                final String picPath = paths.get(0);
                pictureList = new ArrayList<String>();
                pictureList.add(picPath);
                keepFitPicture.setVisibility(View.VISIBLE);
                Glide.with(AddKeepFitNoteActivity.this).load(picPath).into(keepFitPicture);
                addKeepFitPicture.setVisibility(View.GONE);
                new AlertDialog.Builder(mContext).setMessage("需要压缩图片吗？")
                        .setPositiveButton("需要", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Luban.get(AddKeepFitNoteActivity.this)
                                        .load(new File(picPath))                     //传人要压缩的图片
                                        .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                                        .setCompressListener(new OnCompressListener() { //设置回调

                                            @Override
                                            public void onStart() {
                                                // TODO 压缩开始前调用，可以在方法内启动 loading UI
                                            }

                                            @Override
                                            public void onSuccess(File file) {
                                                Log.d(TAG, "onSuccess: " + file.getAbsolutePath());
                                                upLoad(file);
                                                // TODO 压缩成功后调用，返回压缩后的图片文件
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                // TODO 当压缩过去出现问题时调用
                                                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }).launch();    //启动压缩
                            }
                        }).setNegativeButton("不需要", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        upLoad(new File(picPath));
                    }
                }).show();
            }
        });
    }


    /**
     * 查询本条数据
     */
    private void queryData() {
        BmobQuery<KeepNoteEntity> query = new BmobQuery<>();
        query.getObject(objectId, new QueryListener<KeepNoteEntity>() {
            @Override
            public void done(KeepNoteEntity object, BmobException e) {
                if (e == null) {
                    fillData(object);
                } else {
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    /**
     * 填充页面数据
     *
     * @param object
     */
    private void fillData(KeepNoteEntity object) {

        addKeepFitDateTv.setText(object.getDate().toString());
        addKeepFitTimeLength.setText(object.getExerciseDuration().toString());
        addKeepFitRunLength.setText(object.getRunLength().toString());
        addKeepFitSitUps.setText(object.getSitUps().toString());
        addKeepFitSportsApparatusTimes.setText(object.getSportsApparatusTimes().toString());
        addKeepFitOthers.setText(object.getOthers());
        if (!TextUtils.isEmpty(object.getPictures().get(0))) {
            pictureList = new ArrayList<String>();
            pictureList.add(object.getPictures().get(0));
            keepFitPicture.setVisibility(View.VISIBLE);
            Glide.with(AddKeepFitNoteActivity.this).load(object.getPictures().get(0)).into(keepFitPicture);
            addKeepFitPicture.setVisibility(View.GONE);
        }
        avi.setVisibility(View.GONE);
        contentRl.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.keep_fit_picture, R.id.add_keep_fit_picture, R.id.add_keep_fit_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_keep_fit_picture:
                IPicker.setLimit(1);
                IPicker.open(this);
                break;
            case R.id.keep_fit_picture:
                startActivity(new Intent(mContext, PhotoViewActivity.class).putExtra("pictureUrl", pictureList.get(0)));
                break;
            case R.id.add_keep_fit_submit:
                KeepNoteEntity bean = new KeepNoteEntity();
                bean.setDate(addKeepFitDateTv.getText().toString());
                bean.setExerciseDuration(Float.valueOf(addKeepFitTimeLength.getText().toString()));
                bean.setRunLength(Float.valueOf(addKeepFitRunLength.getText().toString()));
                bean.setSitUps(Integer.valueOf(addKeepFitSitUps.getText().toString()));
                bean.setSportsApparatusTimes(Integer.valueOf(addKeepFitSportsApparatusTimes.getText().toString()));
                bean.setOthers(addKeepFitOthers.getText().toString());
                bean.setPictures(pictureList);
                bean.setUserId(BmobUser.getCurrentUser().getObjectId());
                if (isEdit) {
                    bean.update(objectId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(AddKeepFitNoteActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Log.d(TAG, "done: " + e.getMessage());
                                Toast.makeText(AddKeepFitNoteActivity.this, "更新失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    bean.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if (e == null) {
                                Toast.makeText(AddKeepFitNoteActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(AddKeepFitNoteActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                break;
        }
    }


    private void upLoad(File file) {
        final BmobFile bmobFile = new BmobFile(file);
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        final View view = getLayoutInflater().inflate(R.layout.dialog_view, null);
        dialog.setView(view, 0, 0, 0, 0);
        dialog.setCancelable(false);
        if (!dialog.isShowing()) {
            dialog.show();
        }
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    pictureList = new ArrayList<String>();
                    pictureList.add(bmobFile.getFileUrl());//getFileUrl()--返回的上传文件的完整地址
                    dialog.dismiss();
                    Toast.makeText(mContext, "上传成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddKeepFitNoteActivity.this, "上传失败!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onProgress(Integer value) {
                NumberProgressBar numberProgressBar = (NumberProgressBar) view.findViewById(R.id.number_progress_bar);
                numberProgressBar.setProgress(value);
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + formatSingle(monthOfYear) + formatSingle(dayOfMonth);
        addKeepFitDateTv.setText(date);
    }

    private String formatSingle(int num) {
        return num >= 10 ? String.valueOf(num) : ("0" + num);
    }
}
