package lf.com.android.blackfishdemo.listener;

import android.os.Bundle;

public interface OnMangerAddressListener {
    void addAddress(String name, String phone, String address, boolean isDefault, boolean idCreate);

    void editAddress(Bundle bundle);

    void deleteAddress(int index);

    void setDefaultAddress(int index);
}
