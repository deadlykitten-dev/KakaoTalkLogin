package com.kestrel9.android.kakaotalklogin;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.HashMap;

/**
 * KakaoTalkLogin
 * Class: SignUpViewModel
 * Created by kestr on 2018-09-18.
 * <p>
 * Description:
 */
public class SignUpViewModel extends ViewModel {

    private  static final String NAME_KEY = "birthday";
    private  static final String AGE_KEY = "age_range";
    private  static final String GENDER_KEY = "gender";

    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> age = new MutableLiveData<>();
    public MutableLiveData<String> gender = new MutableLiveData<>();

    public HashMap<String, String> getProperties(){
        HashMap<String, String> properties = new HashMap<>();
        properties.put(NAME_KEY, name.getValue());
        properties.put(AGE_KEY, age.getValue());
        properties.put(GENDER_KEY, gender.getValue());
        return properties;
    }
}
