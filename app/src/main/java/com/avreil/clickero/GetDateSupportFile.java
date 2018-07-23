package com.avreil.clickero;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.net.time.TimeTCPClient;

public final class GetDateSupportFile {

    public static long currentSeconds;

    public static final void main(String[] args) {
        try {
            TimeTCPClient client = new TimeTCPClient();
            try { client.setDefaultTimeout(60000);
                client.connect("time.nist.gov");
                currentSeconds = client.getTime();
            } finally { client.disconnect(); }
        } catch (IOException e) { e.printStackTrace(); } }}