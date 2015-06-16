package com.kleshch.excurs;


public interface IFace {

    void beginExcursion(int id);

    void onUserSelectValue(int num);

    void nextPoint(int id);

    void getExcursionsList();

    boolean isEng();
}
