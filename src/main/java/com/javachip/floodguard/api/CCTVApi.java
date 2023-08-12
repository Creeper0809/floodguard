package com.javachip.floodguard.api;

import com.javachip.floodguard.dto.CCTVRequestDTO;
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
public class CCTVApi{
    @Value("${cctv.publickey}")
    private String publickey;
    public ArrayList<CCTVRequestDTO> getCCTV(String minCoordx, String minCoordy, String maxCoordx, String maxCoordy){
        ArrayList<CCTVRequestDTO> result = new ArrayList<>();
        try {
            StringBuilder urlBuilder = new StringBuilder("https://openapi.its.go.kr:9443/cctvInfo") /*URL*/
                                                    .append("?" + URLEncoder.encode("apiKey", "UTF-8") + "=" + URLEncoder.encode(publickey, "UTF-8")) /*공개키*/
                                                    .append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("all", "UTF-8")) /*도로유형*/
                                                    .append("&" + URLEncoder.encode("cctvType","UTF-8") + "=" + URLEncoder.encode("2", "UTF-8")) /*CCTV유형*/
                                                    .append("&" + URLEncoder.encode("minX","UTF-8") + "=" + URLEncoder.encode(minCoordx, "UTF-8")) /*최소경도영역*/
                                                    .append("&" + URLEncoder.encode("maxX","UTF-8") + "=" + URLEncoder.encode(maxCoordx, "UTF-8")) /*최대경도영역*/
                                                    .append("&" + URLEncoder.encode("minY","UTF-8") + "=" + URLEncoder.encode(minCoordy, "UTF-8")) /*최소위도영역*/
                                                    .append("&" + URLEncoder.encode("maxY","UTF-8") + "=" + URLEncoder.encode(maxCoordy, "UTF-8")) /*최대위도영역*/
                                                    .append("&" + URLEncoder.encode("getType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*출력타입*/
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "text/xml;charset=UTF-8");

            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }

            JSONObject responseJson = (JSONObject) new JSONParser().parse(sb.toString());
            JSONObject responseJson2 = (JSONObject) responseJson.get("response");

            if(responseJson2 == null)
                return result;

            JSONArray arr = (JSONArray) responseJson2.get("data");
            if (arr.size() > 0) {
                for (int i = 0; i < arr.size(); i++) {
                    JSONObject jsonObj = (JSONObject) arr.get(i);
                    CCTVRequestDTO temp = new CCTVRequestDTO();
                    temp.videoURL = (String) jsonObj.get("cctvurl");
                    temp.coordx = (double) jsonObj.get("coordx");
                    temp.coordy = (double) jsonObj.get("coordy");
                    temp.name = (String) jsonObj.get("cctvname");
                    result.add(temp);
                }
            }
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
