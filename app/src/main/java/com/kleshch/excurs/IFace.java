package com.kleshch.excurs;


public interface IFace {

    public void beginExcursion(int id);

    public void onUserSelectValue(int num, String str);

    public void nextPoint(int id);

    public void getExcursionsList();
}
