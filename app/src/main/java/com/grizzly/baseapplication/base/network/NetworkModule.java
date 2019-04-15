package com.grizzly.baseapplication.base.network;

import android.support.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import java.util.List;
import javax.inject.Singleton;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

@Module public class NetworkModule {

  @Provides @NonNull @Singleton public OkHttpClient provideOkHttpClient(
      @OkHttpInterceptors @NonNull List<Interceptor> interceptors,
      @OkHttpNetworkInterceptors @NonNull List<Interceptor> networkInterceptors) {
    final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

    for (Interceptor interceptor : interceptors) {
      okHttpBuilder.addInterceptor(interceptor);
    }

    for (Interceptor networkInterceptor : networkInterceptors) {
      okHttpBuilder.addNetworkInterceptor(networkInterceptor);
    }

    return okHttpBuilder.build();
  }
}