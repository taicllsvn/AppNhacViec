package a20156410.chuthetai.com.timeanddatedialog;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class MainActivity extends Activity {
    private NotificationCompat.Builder notBuilder1;
    private static final int MY_REQUEST_CODE = 100;
    private static final int MY_NOFITICATION_ID = 12345;
    Notification notification;
    NotificationManager notificationService;

    SharedPreferences sharedPreferences;

    Button btnChangeTime,btnHenGio,btnTatBT,btnSleepdialog,btnTatBTdialog,btnOK,btnThoat;
    TextView txtTime,txtChiTiet;
    TextView txtTenCV,txtNdCV,txtNdCVdialog;
    EditText txtEditdialog;
    CheckBox Checkbox;
    Calendar cal;
    int GioCuaTP,PhutCuaTP,GioPr,PhutPr;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Intent intent;
    String string_phut,TenCv,NdCv,NDCV,Ndcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnHenGio       =   findViewById(R.id.btnHenGio);
        txtTime         =   findViewById(R.id.txtTime);
        btnTatBT        =   findViewById(R.id.btnTatBT);
        btnChangeTime   =   findViewById(R.id.btnChangeTime);
        txtTenCV        =   findViewById(R.id.txtTenCV);
        txtNdCV         =   findViewById(R.id.txtNdCV);
        txtChiTiet      =   findViewById(R.id.txtChiTiet);
        btnChangeTime.setOnClickListener(showTimepicker);

        this.notBuilder1 = new NotificationCompat.Builder(this);
        MainActivity.this.notBuilder1.setSmallIcon(R.raw.notifi_icon);
        MainActivity.this.notBuilder1.setTicker("Báo thức đã đến");
        MainActivity.this.notBuilder1.setContentTitle("BÁO THỨC");
        MainActivity.this.notBuilder1.setContentText("Nhấn để tắt báo thức");
        this.notBuilder1.setAutoCancel(true);
        sharedPreferences = getSharedPreferences("ChiTietCV",MODE_PRIVATE);

        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);

        //Set ngày giờ hiện tại khi mới chạy lần đầu
        cal=Calendar.getInstance();
        SimpleDateFormat dft=null;

        //Định dạng giờ phút am/pm
        dft = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String strTime = dft.format(cal.getTime());
        //đưa lên giao diện
        txtTime.setText("Bây giờ là "+strTime);
        //lấy giờ theo 24 để lập trình theo Tag
        dft = new SimpleDateFormat("HH:mm", Locale.getDefault());
        txtTime.setTag(dft.format(cal.getTime()));
        AddEvents();
    }

    private void AddEvents() {
        btnHenGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal=Calendar.getInstance();
                intent = new Intent(MainActivity.this, AlarmReceiver.class);
                intent.putExtra("extra","on");
                pendingIntent =PendingIntent.getBroadcast(MainActivity.this,232323,intent,0);
                long ThoiGianHenGio;
                long GioHen,PhutHen;

                //Toast.makeText(MainActivity.this,"Báo thức được đặt đổ chuông sau " +" giờ "+" phút " +" nữa tính từ bây giờ",Toast.LENGTH_LONG).show();
                if (GioCuaTP==cal.get(Calendar.HOUR_OF_DAY)) {
                    GioHen = (GioCuaTP - cal.get(Calendar.HOUR_OF_DAY));
                    PhutHen = (PhutCuaTP - 1 - cal.get(Calendar.MINUTE));   long PhutHenMili= TimeUnit.MINUTES.toMillis(PhutHen);
                    int GiayHen = (60 - cal.get(Calendar.SECOND)); long GiayHenMili=TimeUnit.SECONDS.toMillis(GiayHen);
                    ThoiGianHenGio = PhutHenMili+GiayHenMili;
                    alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+  ThoiGianHenGio, pendingIntent);
                    MainActivity.this.notBuilder1.setWhen(System.currentTimeMillis()+ThoiGianHenGio);
                    Intent intentNotifi= new Intent(MainActivity.this,MainActivity.class);
                    PendingIntent pendingIntent123=PendingIntent.getActivity(MainActivity.this,MY_REQUEST_CODE,intentNotifi,PendingIntent.FLAG_UPDATE_CURRENT);
                    MainActivity.this.notBuilder1.setContentIntent(pendingIntent123);
                    // Lấy ra dịch vụ thông báo (Một dịch vụ có sẵn của hệ thống).
                    notificationService = (NotificationManager)MainActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
                    // Xây dựng thông báo và gửi nó lên hệ thống.
                    notification =notBuilder1.build();
                    notificationService.notify(MY_NOFITICATION_ID,notification);

                        if (GioCuaTP==0 && PhutCuaTP==0 || GioCuaTP==cal.get(Calendar.HOUR_OF_DAY) && PhutCuaTP==cal.get(Calendar.MINUTE))
                        {

                        }
                        else {
                        Toast.makeText(MainActivity.this, "Báo thức sẽ đổ chuông sau " + GioHen + " giờ " + (PhutHen + 1) + " phút nữa", Toast.LENGTH_SHORT).show();
                        }
                    }

                else
                {
                    GioHen = (GioCuaTP-1 - cal.get(Calendar.HOUR_OF_DAY)); long GioHenMili=TimeUnit.HOURS.toMillis(GioHen);
                    PhutHen = (60 + PhutCuaTP - cal.get(Calendar.MINUTE)); long PhutHenMili=TimeUnit.MINUTES.toMillis(PhutHen);
                    int GiayHen = (60 - cal.get(Calendar.SECOND)); long GiayHenMili=TimeUnit.SECONDS.toMillis(GiayHen);
                    ThoiGianHenGio = GioHenMili+PhutHenMili+GiayHenMili;
                    alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+ ThoiGianHenGio, pendingIntent);

                    MainActivity.this.notBuilder1.setWhen(System.currentTimeMillis()+ThoiGianHenGio);
                    Intent intentNotifi= new Intent(MainActivity.this,MainActivity.class);
                    PendingIntent pendingIntent123=PendingIntent.getActivity(MainActivity.this,MY_REQUEST_CODE,intentNotifi,PendingIntent.FLAG_UPDATE_CURRENT);
                    MainActivity.this.notBuilder1.setContentIntent(pendingIntent123);
                    // Lấy ra dịch vụ thông báo (Một dịch vụ có sẵn của hệ thống).
                    notificationService = (NotificationManager)MainActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
                    // Xây dựng thông báo và gửi nó lên hệ thống.
                    notification =notBuilder1.build();
                    notificationService.notify(MY_NOFITICATION_ID,notification);


                    if (PhutHen==60) {
                        GioHen=GioHen+1;
                    }
                    if (PhutHen>60){
                        GioHen=GioHen+1;
                    }

                    if (GioCuaTP==0 && PhutCuaTP==0 || GioCuaTP==cal.get(Calendar.HOUR_OF_DAY) && PhutCuaTP==cal.get(Calendar.MINUTE))
                    {

                    }
                    else if (PhutHen==60)
                    {
                        Toast.makeText(MainActivity.this,"Báo thức sẽ đổ chuông sau " + GioHen + " giờ "+ "nữa" , Toast.LENGTH_SHORT).show();
                    }


                    else  if(PhutHen>60)  {
                        PhutHen=PhutHen-60;
                        String string_chuoi= String.valueOf(PhutHen);
                        if (PhutHen<10)
                        {
                            string_chuoi="0"+String.valueOf(PhutHen);
                        }
                        Toast.makeText(MainActivity.this,"Báo thức sẽ đổ chuông sau " + GioHen + " giờ " + string_chuoi + " phút nữa" , Toast.LENGTH_SHORT).show();


                    }

                    else {
                        Toast.makeText(MainActivity.this, "Báo thức sẽ đổ chuông sau " + GioHen + " giờ " + (PhutHen) + " phút nữa", Toast.LENGTH_SHORT).show();
                    }
                }

            if (GioCuaTP!=0 && PhutCuaTP != 0)
            txtChiTiet.setText(txtTenCV.getText().toString()+" lúc "+GioCuaTP+" giờ "+PhutCuaTP+" phút với nội dung là: "+txtNdCV.getText().toString());
            else if (GioCuaTP==0 && PhutCuaTP==0)
            {txtChiTiet.setText("Báo thức đang reo");
            }
            else if (TenCv.equals("") && NdCv.equals(""))
            {

            }

            NDCV=txtNdCV.getText().toString();
            }
        });


    btnTatBT.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_dialog);
            txtNdCVdialog = dialog.findViewById(R.id.txtNdCVdialog);
            Checkbox = dialog.findViewById(R.id.checkbox);
            btnSleepdialog =dialog.findViewById(R.id.btnSleepdialog);
            btnTatBTdialog=dialog.findViewById(R.id.btnTatBTdialog);


                btnTatBTdialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (txtNdCVdialog.getText().toString().equals(NDCV) || txtNdCVdialog.getText().toString().equals(Ndcv) && Checkbox.isChecked()) {
                            notificationService.cancelAll();
                            intent.putExtra("extra", "off");
                            alarmManager.cancel(pendingIntent);
                            sendBroadcast(intent);
                            dialog.cancel();
                        }  else
                        {
                            Toast.makeText(MainActivity.this, "Bạn chưa hoàn thành công việc", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            btnSleepdialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notificationService.cancelAll();
                    intent.putExtra("extra","off");
                    alarmManager.cancel(pendingIntent);
                    sendBroadcast(intent);

                    Intent intent= new Intent(MainActivity.this,AlarmManager.class);
                    intent.putExtra("extra","on");
                    PendingIntent pendingIntent= PendingIntent.getBroadcast(MainActivity.this,10,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(1*1000),pendingIntent);
                    dialog.cancel();
                }

            });
            dialog.show();



        }
    });
    }


    View.OnClickListener showTimepicker = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    //Xử lý lưu giờ và AM,PM
                    String s = hour + ":" + minute;
                    GioCuaTP = hour;
                    PhutCuaTP = minute;
                    string_phut=String.valueOf(minute);
                    if (minute < 10 )
                    {
                        string_phut= "0" + String.valueOf(minute);
                    }
                    int hourTam = hour;
                    if (hourTam > 12)
                        hourTam = hourTam - 12;
                    txtTime.setText("Đã chọn "+hourTam + ":" + string_phut + (hour > 12 ? " PM" : " AM"));
                    //lưu giờ thực vào tag
                    txtTime.setTag(s);
                    //lưu vết lại giờ
                    //cal.set(Calendar.HOUR_OF_DAY, hour);
                    //cal.set(Calendar.MINUTE, minute);
                    //date = cal.getTime();
                }

            };

            String s = txtTime.getTag() + "";
            String strArr[] = s.split(":");
            int gio = Integer.parseInt(strArr[0]);
            int phut = Integer.parseInt(strArr[1]);
            TimePickerDialog time = new TimePickerDialog(
                    MainActivity.this,
                    callback, gio, phut, true);
            time.setTitle("Chọn giờ nhắc việc");
            time.show();
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        //tạo đối tượng editor để lưu giá trị
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String TenCV = txtTenCV.getText().toString();
        String NdCV= txtNdCV.getText().toString();


        editor.putString("TenCv",TenCV);
        editor.putString("NdCv",NdCV);
        editor.putInt("Gio",GioCuaTP);
        editor.putInt("Phut",PhutCuaTP);
        editor.commit();
    }



    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences.Editor editor=sharedPreferences.edit();
        TenCv = sharedPreferences.getString("TenCv","");
        NdCv =sharedPreferences.getString("NdCv","");
        GioPr =sharedPreferences.getInt("Gio",0);
        PhutPr = sharedPreferences.getInt("Phut",0);
        if (GioPr == 0 && PhutPr == 0)
        {
            txtChiTiet.setText("Chưa có công việc nào");
        }
        else if (GioPr!=0 && PhutPr!=0)
        {
            txtChiTiet.setText(TenCv+" lúc "+GioPr+" giờ "+PhutPr+" phút với nội dung là: "+NdCv);
        }
        else  if (TenCv.equals("")&&NdCv.equals(""))
        {
            editor.clear();
        }
    }

    public void btnNDCVdialog(View view) {


        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setTitle("Nội dung công việc");
        dialog.setContentView(R.layout.dialog_edit);
        dialog.setCancelable(false);
        btnOK = dialog.findViewById(R.id.btnOK);
        btnThoat= dialog.findViewById(R.id.btnThoat);
        txtEditdialog = dialog.findViewById(R.id.txtNhapdialog);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtNdCV.setText(txtEditdialog.getText().toString());
                dialog.cancel();
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();



    }

    public void btnTenCVdialog(View view) {


        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setTitle("Tên công việc");

        dialog.setContentView(R.layout.dialog_edit);

        dialog.setCancelable(false);
        btnOK = dialog.findViewById(R.id.btnOK);
        btnThoat= dialog.findViewById(R.id.btnThoat);
        txtEditdialog = dialog.findViewById(R.id.txtNhapdialog);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtTenCV.setText(txtEditdialog.getText().toString());
                dialog.cancel();
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();


    }
}
