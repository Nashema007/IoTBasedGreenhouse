package com.example.iotbasedgreenhouse.utilities;

import android.app.Application;

public class SciChartApp extends Application {

    private static SciChartApp sInstance;

    public static SciChartApp getInstance() {
        return sInstance;
    }

@Override
public void onCreate() {
        super.onCreate();
        sInstance = this;

        setUpSciChartLicense();
        }

private void setUpSciChartLicense() {
    // Set your license code here to license the SciChart Android Examples app
    // You can get a trial license key from https://www.scichart.com/licensing-scichart-android/
    // Purchased license keys can be viewed at https://www.scichart.com/profile
    //
    // e.g.
    //
    try {
        com.scichart.charting.visuals.SciChartSurface.setRuntimeLicenseKey(
                "<LicenseContract>" +
                        "  <Customer>tinotendagarikayi@gmail.com</Customer>" +
                        "  <OrderId>Trial</OrderId>" +
                        "  <LicenseCount>1</LicenseCount>" +
                        "  <IsTrialLicense>true</IsTrialLicense>" +
                        "  <SupportExpires>06/04/2019 00:00:00</SupportExpires>" +
                        "  <ProductCode>SC-ANDROID-2D-ENTERPRISE-SRC</ProductCode>" +
                        "  <KeyCode>d4d86722659837252f465ec6820a529f10f3d1bf27f89af57abc237bc569d4fcba5deac0a98ad25a9da78d9bc6b1b296a3dffdc02674e3c1e3fe8fac4575b4e7bb0d2778e21ea304c8a9a548122c37cf74ce43771ca8e10f953fb9b98dcca109e926c12e474d2ea0df5b701468cc5ecfe1f6f031e1bf86d924883445dd7b8b56ddf2aa710a69e1589fe77b935e718875ebac6761faca15ae22b4ead458954088939ce183233bba47dbdeb94fd94899388ccbcff335</KeyCode>" +
                        "</LicenseContract>");

    } catch (Exception e) {
        e.printStackTrace();
    }

}

}
