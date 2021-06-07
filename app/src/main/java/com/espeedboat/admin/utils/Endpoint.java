package com.espeedboat.admin.utils;

public class Endpoint {
    public static final String URL = "http://speedboat.devmptr.com";
    public static final String URL_ADMIN = "http://admin.espeedboat.xyz";
    public static final String URL_DEV = "http://dev.espeedboat.xyz";
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
    public static final String TRANSAKSI_LIST_PROSES = "transaksi/proses";
    public static final String TRANSAKSI_LIST_SELESAI = "transaksi/done";
    public static final String TRANSAKSI_DETAIL = "transaksi/detail/{id}";
    public static final String TRANSAKSI_APPROVE = "transaksi/approve/{id}";
    public static final String TRANSAKSI_TIKET = "transaksi/tiket/{kode_tiket}";
    public static final String TRANSAKSI_IS_TIKET = "transaksi/is_tiket/{kode_tiket}";
    public static final String TRANSAKSI_TIKET_APPROVE = "transaksi/tiket/approve/{id}";
    public static final String BERITA_LIST = "berita/pelabuhan";
    public static final String DASHBOARD_DATA = "dashboard/admin";
    public static final String REWARD_INDEX = "reward";
    public static final String REWARD_DELETE = "reward/{reward}";
    public static final String REWARD_CREATE = "reward";
    public static final String USER_REWARD_INDEX = "reward/get/user/{id}";
    public static final String USER_REWARD_SEND = "reward/send/user/{id_detail}";
    public static final String USER_REWARD_DONE = "reward/done/user/{id_detail}";
}
