package qwy.anenda.com.baselib.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import qwy.anenda.com.baselib.R;


public class DialogProgress extends Dialog {

    Context context;

    private String text;

    private TextView textV;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_flower);
        textV = (TextView) findViewById(R.id.title);
    }

    public void setTitleText(String text){
        this.text = text;
    }

    public DialogProgress(Context context){
        super(context);
        this.context = context;
    }

    public DialogProgress(Context context, boolean cancelable,
                          OnCancelListener cancelListener){
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    public DialogProgress(Context context, int theme){
        super(context, theme);
    }

    public DialogProgress(Context context, int theme, String text){
        super(context, theme);
        this.context = context;
        this.text = text;
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onStop(){
        super.onStop();
    }
}
