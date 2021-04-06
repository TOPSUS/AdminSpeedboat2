package com.espeedboat.admin.utils;

public class Endpoint {
    public static final String LOGIN_ADMIN = "admin/login";
    public static final String REVIEW_LIST = "review";
    public static final String REVIEW_DETAIL = "review/{id}";
    public static final String TRANSAKSI_LIST_PROSES = "transaksi/proses";
    public static final String TRANSAKSI_LIST_SELESAI = "transaksi/done";
    public static final String TRANSAKSI_DETAIL = "transaksi/detail/{id}";
    public static final String TRANSAKSI_APPROVE = "transaksi/approve/{id}";
    public static final String LOGOUT = "admin/logout";
    public static final String TRANSAKSI_TIKET = "transaksi/tiket/{kode_tiket}";
    public static final String TRANSAKSI_TIKET_APPROVE = "transaksi/tiket/approve/{id}";
}
