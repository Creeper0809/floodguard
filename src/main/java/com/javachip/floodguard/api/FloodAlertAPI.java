package com.javachip.floodguard.api;

import com.javachip.floodguard.dto.CCTVRequest;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
@Component
@RequiredArgsConstructor
public class FloodAlertAPI {
    @Value("${floodalert.publickey}")
    private String publickey;
    public ArrayList<CCTVRequest> getFloodAlert(){
        ArrayList<CCTVRequest> result = new ArrayList<>();
        try {
            URL url = new URL("https://api.hrfco.go.kr/"+publickey+"/fldfct/list.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "text/json;charset=UTF-8");

            BufferedReader rd;
            System.out.println(conn.getResponseCode());
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }

            JSONObject responseJson = (JSONObject) new JSONParser().parse(sb.toString());
            System.out.println(sb);
            rd.close();
            conn.disconnect();
        }
        catch (IOException e){
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
