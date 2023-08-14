package com.geek.libskin.skindemo.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.geek.libbase.R;
import com.geek.libskin.skinbase.SkinLog;
import com.geek.libskin.skinbase.SkinManager;

import java.util.Objects;

/**
 * @ClassName: SkinDialogFragment
 * @Author: 史大拿
 * @CreateDate: 1/5/23$ 10:20 AM$
 * TODO
 */
public class SkinDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SkinLog.i("szjDialogFragment", "onCreateView");
        return inflater.inflate(R.layout.skin_fragment_dialog, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Objects.requireNonNull(getDialog()).getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SkinManager.getInstance().tryInitSkin(requireActivity());
    }

    @Override
    public void onDestroy() {
        SkinLog.i("szjDialogFragment", "onDestroy");
        super.onDestroy();
    }
}
