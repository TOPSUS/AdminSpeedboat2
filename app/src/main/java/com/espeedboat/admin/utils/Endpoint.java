package com.espeedboat.admin.utils;

public class Endpoint {
    public static final String URL = "http://speedboat.devmptr.com";
    public static final String LOGIN_ADMIN = "admin/login";
    public static final String REVIEW_LIST = "review";
    public static final String REVIEW_DETAIL = "review/{id}";
    public static final String KAPAL_LIST = "kapal";
    public static final String KAPAL_VIEW = "kapal/show/{kapal}";
    public static final String KAPAL_DELETE = "kapal/{kapal}";
    public static final String KAPAL_CREATE = "kapal";
    public static final String KAPAL_UPDATE = "kapal/update/{kapal}";
    public static final String KAPAL_USER_LIST = "get/user/kapal";
    public static final String LIST_NAMA_PELABUHAN = "pelabuhan/get/name";
    public static final String PELABUHAN_LIST = "get/pelabuhan";
    public static final String GOLONGAN_BY_PELABUHAN = "golongan/get/{id}";
    public static final String EDIT_USER = "user/get/{id}";
    public static final String UPDATE_USER = "user/update/{id}";
    public static final String GET_CODES = "admin/getcode";
    public static final String CEK_CODES = "admin/cekcode";
    public static final String FORGOT_PASS = "admin/forgotpass";
    public static final String JADWAL_LIST = "jadwal";
    public static final String JADWAL_CREATE = "jadwal";
    public static final String JADWAL_DELETE = "jadwal/{jadwal}";
    public static final String JADWAL_VIEW = "jadwal/{jadwal}";
    public static final String GANTI_PASS = "gantipass";
}
