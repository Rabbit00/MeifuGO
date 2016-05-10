package com.xyj.mefugou.utils;

public class UType {

    //0:热门 1:旅游便购 2:信誉专购 3:同城速购 4:夏日护肤课 5:眉毛专题 6:速效美瞳适用手册 7:如何挑选自己的面膜
    public static String getType(int type){
        String temp = "";
        switch(type){
            case 0:
                temp = "热门";
                break;
            case 1:
                temp = "旅游便购";
                break;
            case 2:
                temp = "信誉专购";
                break;
            case 3:
                temp = "同城速购";
                break;
            case 4:
                temp = "夏日护肤课";
                break;
            case 5:
                temp = "眉毛专题";
                break;
            case 6:
                temp = "速效美瞳适用手册";
                break;
            case 7:
                temp = "如何挑选自己的面膜";
                break;
        }
            return temp;

    }

}
