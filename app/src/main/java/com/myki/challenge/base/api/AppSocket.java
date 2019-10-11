package com.myki.challenge.base.api;

import android.content.Context;
import android.support.annotation.NonNull;
import io.realm.RealmConfiguration;
import io.socket.client.IO;
import io.socket.client.Manager;
import io.socket.client.Socket;
import io.socket.engineio.client.Transport;
import io.socket.engineio.client.transports.WebSocket;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import timber.log.Timber;

public class AppSocket {

  @NonNull static Socket initSocket(@NonNull Context context, @NonNull URI serverURL,
      @NonNull RealmConfiguration realmConfiguration) {
    IO.Options opts = new IO.Options();
    opts.forceNew = true;
    opts.reconnection = true;
    opts.secure = true;
    opts.timeout = -1;
    opts.transports = new String[] { WebSocket.NAME };

    Socket socket;
    socket = IO.socket(serverURL, opts);
    Timber.d("Socket initialized");

    socket.io().on(Manager.EVENT_TRANSPORT, args -> {
      Transport transport = (Transport) args[0];
      transport.on(Transport.EVENT_REQUEST_HEADERS, args1 -> {
        Map<String, List<String>> headers = (Map<String, List<String>>) args1[0];
        headers.put("Origin", Arrays.asList("https://domain"));
      });
      transport.on(Transport.EVENT_ERROR, args12 -> {
        Exception e = (Exception) args12[0];
        Timber.e("transport error " + e);
        e.printStackTrace();
        e.getCause().printStackTrace();
      });
    });

    return socket;
  }

}
