package com.example.iotbasedgreenhouse.utilities;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class AppSingleton {

    private static AppSingleton myAppSingletonInstance;
    private RequestQueue myRequestQueue;
    private final ImageLoader myImageLoader;
    private static Context myContext;

    private AppSingleton(Context context) {
        myContext = context;
        myRequestQueue = getRequestQueue();

        myImageLoader = new ImageLoader(myRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });

    }

    public static synchronized AppSingleton getInstance(Context context) {
        if (myAppSingletonInstance == null) {
            myAppSingletonInstance = new AppSingleton(context);
        }
        return myAppSingletonInstance;
    }

    public RequestQueue getRequestQueue() {
        if (myRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            myRequestQueue = Volley.newRequestQueue(myContext.getApplicationContext());
        }
        return myRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        myRequestQueue.add(req);
    }

    public ImageLoader getImageLoader() {
        return myImageLoader;
    }

    public void cancelPendingRequests(Object tag) {
        if (myRequestQueue != null) {
            myRequestQueue.cancelAll(tag);
        }
    }
}
