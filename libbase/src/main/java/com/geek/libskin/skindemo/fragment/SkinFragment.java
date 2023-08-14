package com.geek.libskin.skindemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.geek.libbase.R;
import com.geek.libskin.skinbase.SkinLog;
import com.geek.libskin.skinbase.SkinManager;
import com.geek.libskin.skinbase.callback.SkinStateListener;
import com.geek.libskin.skindemo.SkinConfig;
import com.geek.libskin.skindemo.activity.SkinBaseActivity;


/**
 * @ClassName: SkinFragment
 * @Author: 史大拿
 * @CreateDate: 1/4/23$ 2:41 PM$
 * TODO
 */
public class SkinFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.skin_fragment_skin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /// TODO 避免第一次换肤
        SkinManager.getInstance().tryInitSkin(requireActivity());

        Button btReSkin = view.findViewById(R.id.bt_re_skin);
        TextView btReset = view.findViewById(R.id.bt_reset);

        SkinLog.i("szjFragment已加载", hashCode());

        btReSkin.setOnClickListener(v -> {
            SkinManager.getInstance().loadSkin(SkinConfig.getPath(requireContext()), requireActivity());
            setInfo();
        });

        btReset.setOnClickListener(v -> {
            SkinManager.getInstance().reset(requireActivity());
            setInfo();
        });

        /// 状态监听回调
        SkinManager.getInstance().setStateListener(this, new SkinStateListener() {
            @Override
            public void skinStateResultCallBack(SkinManager.State state) {
                SkinLog.i("szj当前回调状态3", state.name() + "\t" + hashCode());
            }
        });
    }

    private void setInfo() {
        if (getActivity() instanceof SkinBaseActivity) {
            ((SkinBaseActivity) getActivity()).setInfoState();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
