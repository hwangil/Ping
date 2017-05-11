package com.example.gilsoo.ping.Show_More;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gilsoo.ping.DialogResource.VersionDialog;
import com.example.gilsoo.ping.DialogResource.WithdrawDialog;
import com.example.gilsoo.ping.IntroActivities.InitActivity;
import com.example.gilsoo.ping.R;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.util.helper.log.Logger;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by gilsoo on 2016-06-27.
 */

public class ShowMoreFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Alert alert = new Alert();
        View root = inflater.inflate(R.layout.show_more, container, false);
        ListView listview = (ListView) root.findViewById(R.id.showMoreList);
        ListView listview2 = (ListView) root.findViewById(R.id.showMoreList2);
        ArrayList<showMore_item> data = new ArrayList<>();
        data.add(new showMore_item("버전 정보"));
        data.add(new showMore_item("이용가이드"));
        ArrayList<showMore_item> data2 = new ArrayList<>();
        data2.add(new showMore_item("비밀번호 변경"));
        data2.add(new showMore_item("로그아웃"));
        data2.add(new showMore_item("탈퇴하기"));


        showMore_adapter adapter = new showMore_adapter(getActivity(), data);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        alert.versionInfoMessage();
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), Guide.class));
                        break;
                    default:
                        break;
                }


            }
        });

        showMore_adapter adapter2 = new showMore_adapter(getActivity(), data2);
        listview2.setAdapter(adapter2);
        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        if(Session.getCurrentSession().isOpened())
                            Toast.makeText(getActivity(), "해당기능은 일반 회원만 가능합니다.", Toast.LENGTH_SHORT).show();
                        else
                            startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
                        break;
                    case 1:
                        alert.logoutMessage();

                        break;
                    case 2:
                        if (Session.getCurrentSession().isOpened())
                            kakaoWithdraw();
                        else
                            alert.withdrawMessage();
                        break;
                    default:
                        break;
                }
            }
        });


        return root;
    }

    class Alert {
        public void serviceInfoMessage() {
            AlertDialog.Builder alert_version = new AlertDialog.Builder(getActivity());
            alert_version.setMessage("시스템 정보");
            AlertDialog alert = alert_version.create();
            alert.show();
        }

        public void versionInfoMessage() {
            final VersionDialog verdig = new VersionDialog(getActivity());
            verdig.setContentView(R.layout.dialog_version);
            verdig.show();
            final Timer timer2 = new Timer();
            timer2.schedule(new TimerTask() {
                public void run() {
                    verdig.dismiss();
                    timer2.cancel(); //this will cancel the timer of the system
                }
            }, 1500);
        }

        public void logoutMessage() {
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(getActivity());
            alert_confirm.setMessage("로그아웃 하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            UserManagement.requestLogout(new LogoutResponseCallback() {
                                @Override
                                public void onCompleteLogout() {
                                    startActivity(new Intent(getActivity(), InitActivity.class));
                                    getActivity().finish(); //로그아웃 되면서 종료시킴
                                }
                            });

                        }
                    }).setNegativeButton("취소",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 'No'
                            return;
                        }
                    });
            AlertDialog alert = alert_confirm.create();
            alert.show();

        }

        public void withdrawMessage() {
            WithdrawDialog withdrawDialog = new WithdrawDialog(getActivity());
            withdrawDialog.show();

        }
    }

    // kakao 탈퇴
    void kakaoWithdraw() {
        final String appendMessage = getResources().getString(R.string.com_kakao_confirm_unlink);
        new AlertDialog.Builder(getActivity())
                .setMessage(appendMessage)
                .setPositiveButton(getResources().getString(R.string.com_kakao_ok_button),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UserManagement.requestUnlink(new UnLinkResponseCallback() {
                                    @Override
                                    public void onFailure(ErrorResult errorResult) {
                                        Logger.e(errorResult.toString());
                                    }

                                    @Override
                                    public void onSessionClosed(ErrorResult errorResult) {
//                                        redirectLoginActivity();
                                    }

                                    @Override
                                    public void onNotSignedUp() {
//                                        redirectSignupActivity();
                                    }

                                    @Override
                                    public void onSuccess(Long userId) {
                                        // Todo. 탈퇴하면 핑 서버에서도 삭제해주는거 해야함
                                        startActivity(new Intent(getActivity(), InitActivity.class));
                                        getActivity().finish();
//                                        redirectLoginActivity();
                                    }
                                });
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton(getResources().getString(R.string.com_kakao_cancel_button),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

    }

}
