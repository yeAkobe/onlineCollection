package com.zzx.msmservice.service;

import java.util.HashMap;

public
interface MsmService {
    boolean sendMsm(HashMap<String, Object> map, String phone);
}
