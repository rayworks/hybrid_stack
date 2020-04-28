package com.rayworks.hybridstack;

import android.os.Bundle;

import io.flutter.embedding.android.FlutterActivity;

public class MyFlutterActivity extends FlutterActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static class EngineIntentBuilder extends FlutterActivity.CachedEngineIntentBuilder {

        /**
         * Constructor that allows this {@code CachedEngineIntentBuilder} to be used by subclasses of
         * {@code FlutterActivity}.
         * <p>
         * Subclasses of {@code FlutterActivity} should provide their own static version of
         * {@link #withNewEngine()}, which returns an instance of {@code CachedEngineIntentBuilder}
         * constructed with a {@code Class} reference to the {@code FlutterActivity} subclass,
         * e.g.:
         * <p>
         * {@code
         * return new CachedEngineIntentBuilder(MyFlutterActivity.class, engineId);
         * }
         *
         * @param activityClass
         * @param engineId
         */
        protected EngineIntentBuilder(Class<? extends FlutterActivity> activityClass, String engineId) {
            super(activityClass, engineId);
        }
    }
}
