package HQ.Planner.view.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import HQ.Planner.R;
import HQ.Planner.utilities.MSharedPref;
import HQ.Planner.utilities.MToast;

public class Setting_Dialog extends AppCompatDialogFragment {

    EditText remindTime;
    EditText thresholdTime;
    EditText periodTime;

    SeekBar remindTimeBar;
    SeekBar thresholdBar;
    SeekBar remindPeriodBar;

    ActionMenuItemView ok;
    ActionMenuItemView cancel;
    ActionMenuItemView back;
    Context context;
    Toolbar toolbar;

    int remind_time_int;
    int threshold_int;
    int remind_period_int;
    MSharedPref mSharedPref;

    @Override
    public Dialog onCreateDialog(Bundle SavedInstanceState) {
        context = getActivity();
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_setting_time, null);
        toolbar = view.findViewById(R.id.dialog_add_event_toolbar);
        toolbar.setTitle("Settings");
        toolbar.inflateMenu(R.menu.dialog_menu_new_event);

        mSharedPref = new MSharedPref(getContext());
        ok = view.findViewById(R.id.dialog_save_event);
        cancel = view.findViewById(R.id.dialog_cancel_save);
        back = view.findViewById(R.id.dialog_back);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSharedPref.save(
                        readValue(remindTime),
                        readValue(thresholdTime),
                        readValue(periodTime));
                say("Setting Saved");
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setUI(1, 60, 1);

                say("Setting reset");
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        builder.setView(view);
        remindTime = view.findViewById(R.id.dialog_input_remind_time);
        thresholdTime = view.findViewById(R.id.dialog_input_threshold);
        periodTime = view.findViewById(R.id.dialog_input_remind_period);

        remindTimeBar = view.findViewById(R.id.setting_seekbar_remind_time);
        thresholdBar = view.findViewById(R.id.setting_seekbar_threshold);
        remindPeriodBar = view.findViewById(R.id.setting_seekbar_remind_period);

        setUI(MSharedPref.read_Remind_time(),
                MSharedPref.read_threshold(),
                MSharedPref.read_Remind_period());

        remindTime.setOnFocusChangeListener(new changeValueListener(remindTimeBar, remindTime, "Set Remind Time"));
        thresholdTime.setOnFocusChangeListener(new changeValueListener(thresholdBar, thresholdTime, "Set Threshold"));
        periodTime.setOnFocusChangeListener(new changeValueListener(remindPeriodBar, periodTime, "Set Remind Period"));

        remindTimeBar.setOnSeekBarChangeListener(new seekBarListener(remindTime, "Set Remind Time"));
        thresholdBar.setOnSeekBarChangeListener(new seekBarListener(thresholdTime, "Set Threshold"));
        remindPeriodBar.setOnSeekBarChangeListener(new seekBarListener(periodTime, "Set Remind Period"));
        return builder.create();
    }

    private void setUI(int remind_time, int threshold, int remind_period) {
        remindTime.setText(String.valueOf(remind_time));
        thresholdTime.setText(String.valueOf(threshold));
        periodTime.setText(String.valueOf(remind_period));
        remindTimeBar.setProgress(remind_time);
        thresholdBar.setProgress(threshold);
        remindPeriodBar.setProgress(remind_period);
    }

    private int readValue(EditText editText) {
        String s = editText.getText().toString();
        if (s.length() < 1) {
            return 1;
        } else {
            return Integer.parseInt(s);
        }
    }

    private class changeValueListener implements EditText.OnFocusChangeListener {

        SeekBar seekBar;
        EditText editText;
        String string;

        public changeValueListener(SeekBar seekBar,
                                   EditText editText,
                                   String s) {
            this.seekBar = seekBar;
            this.editText = editText;
            this.string = s;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                say(string);
            }
            String str = editText.getText().toString();
            if (str.length() < 1) {
                str = "1";
            }
            int pro = Integer.parseInt(str);
            int max = seekBar.getMax();
            if (pro <= max && pro >= 0) {
                seekBar.setProgress(pro);
            } else if (pro > max) {
                seekBar.setProgress(max);
            }
        }
    }

    private class seekBarListener implements SeekBar.OnSeekBarChangeListener {

        EditText editText;
        String string;

        private seekBarListener(EditText editText, String s) {
            this.editText = editText;
            this.string = s;
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            editText.setText(String.valueOf(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            say(string);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

            int pro = Integer.parseInt(editText.getText().toString());
            int max = seekBar.getMax();

            if (pro <= max && pro >= 0) {
                seekBar.setProgress(pro);
            } else if (pro > max) {
                seekBar.setProgress(max);
            }


        }
    }

    private void say(String msg) {
        MToast.say(context, msg);
    }

    public EditText getRemindTime() {
        return remindTime;
    }

    public EditText getThresholdTime() {
        return thresholdTime;
    }

    public EditText getPeriodTime() {
        return periodTime;
    }
}
