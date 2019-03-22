package com.alejandrorios.poststest.utils;

import com.alejandrorios.poststest.models.PostRealm;

import io.realm.annotations.RealmModule;

@RealmModule(classes = {PostRealm.class})
public class RealmDbModule {
}
