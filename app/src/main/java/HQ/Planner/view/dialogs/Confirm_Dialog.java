package HQ.Planner.view.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import HQ.Planner.R;

public class Confirm_Dialog extends AppCompatDialogFragment {

    TextView textView;
    ActionMenuItemView ok;
    ActionMenuItemView cancel;
    Toolbar toolbar;
    private String msg;
    private String title;

    DialogFragment dialogFragment;
    Fragment fragment;

    View.OnClickListener listener;

    public void setDialogFragment(DialogFragment dialogFragment) {
        this.dialogFragment = dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle SavedInstanceState) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_confirm, null);
        toolbar = view.findViewById(R.id.dialog_confirm_toolbar);
        toolbar.setTitle(title);
        toolbar.inflateMenu(R.menu.dialog_menu_new_event);


        textView = view.findViewById(R.id.confrim_msg);
        textView.setText(msg);
        ok = view.findViewById(R.id.dialog_save_event);
        cancel = view.findViewById(R.id.dialog_cancel_save);
        ok.setOnClickListener(listener);
//        ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (dialogFragment instanceof Event_Detail_Dialog){
//                    ((Event_Detail_Dialog)dialogFragment).setRemove();
//                    dismiss();
//                }
//
//            }
//        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        builder.setView(view);


        return builder.create();
    }

    public void setMsg(String t, String s) {
        this.title = t;
        this.msg = s;
    }


    public interface New_Event_Dialog_Listener {
        void applyTexts(String title, String venue, String location);
    }

    public View getOK(){
        return ok;
    }


    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }
}
