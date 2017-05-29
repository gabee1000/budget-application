package com.example.gabor.mybudget.Model.Constants;

/**
 * Created by Gabor on 2017. 05. 20..
 */

public final class Constants {
    public static final class Extra {
        public static final String REGISTER_NAME = "register_name";
        public static final String LAYOUT_RES = "layout_res";
        public static final String ITEM_NAME = "item_name";
        public static final String CATEGORY_NAME = "category_name";
        public static final String VALUE = "value";
        public static final String IS_INCOME = "is_income";
        public static final String ITEM = "item";
        public static final String EDIT_ITEM = "edit_item";
        public static final String CATEGORY_ID = "category_id";
        public static final String ITEM_ID = "item_id";
        public static final String LOGGED_IN_USER = "logged_in_user";
        public static final String SELECT_YEAR_AND_MONTH = "select_year_and_month";
        public static final String SELECT_YEAR = "select_year";
        public static final String SELECT_MONTH = "select_month";
        public static final String TRANSACTION = "transaction";
    }

    public static final class Dialog {
        public static final int ERROR_DIALOG = -1;
        public static final int INFO_DIALOG = 1;
        public static final int APPROVE_DIALOG = 2;
    }

    public static final class ResultCodes {
        public static final int NEW_ITEM_REQUEST = 3;
        public static final int EMPTY_EDIT_TEXTS = 4;
        public static final int EDIT_ITEM_REQUEST = 7;
        public static final int TRANSACTION_ADDED = 8;
    }

    public static final class RequestCodes {
        public static final int INFLATE_ITEM_EDITOR = 5;
        public static final int INFLATE_ADD_NEW_ITEM = 6;
    }
}
