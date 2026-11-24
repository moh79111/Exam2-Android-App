package com.example.exam2;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PageFragment extends Fragment {
    private static final String ARG_TITLE = "tab_title";

    public static PageFragment newInstance(String title) {
        PageFragment fragment = new PageFragment();
        Bundle arguments = new Bundle();
        arguments.putString(ARG_TITLE, title);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        TextView content = new TextView(requireContext());
        content.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        content.setGravity(Gravity.CENTER);
        content.setTextSize(28);
        content.setText(getArguments() == null
                ? "Tab"
                : getArguments().getString(ARG_TITLE, "Tab") + " Tab");
        return content;
    }
}
