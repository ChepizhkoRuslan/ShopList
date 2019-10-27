package com.indieprogress.shopinglisttest.shoplist.view.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.indieprogress.shopinglisttest.R;
import java.util.Objects;
import javax.inject.Inject;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GetImageDialog extends DialogFragment {

    private GetImageDialog.CallBack callBack;

    @Inject
    public GetImageDialog() {
    }

    @OnClick(R.id.btn_camera)
    void onBtnCreateAlbumClick() {
        callBack.onCamera();
        dismiss();
    }

    @OnClick(R.id.btn_gallery)
    void onBtnCancelClick() {
        callBack.onGallery();
        dismiss();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                Objects.requireNonNull(getActivity()));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.dialog_get_image, null);
        ButterKnife.bind(this, view);
        AlertDialog dialog = builder.create();
        dialog.setView(view);
        return dialog;
    }

    public void setCallBack(GetImageDialog.CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack {
        void onCamera();
        void onGallery();
    }
}
