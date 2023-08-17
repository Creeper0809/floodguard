package com.javachip.floodguard.api;

import com.javachip.floodguard.dto.CCTVRequestDTO;
import com.javachip.floodguard.dto.FloodAPIRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class FloodAlertAPI {
    @Value("${floodalert.publickey}")
    private String publickey;
    LocalDateTime currentDate = LocalDateTime.now();
    public ArrayList<FloodAPIRequestDTO> getFloodAlert(){
        ArrayList<FloodAPIRequestDTO> result = new ArrayList<>();
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
            log.info(String.valueOf(responseJson));
            JSONArray arr = (JSONArray) responseJson.get("content");
            if (arr != null && arr.size() > 0) {
                for (int i = 0; i < arr.size(); i++) {
                    JSONObject jsonObj = (JSONObject) arr.get(i);
                    FloodAPIRequestDTO temp = new FloodAPIRequestDTO();
                    temp.setKind((String) jsonObj.get("kind"));
                    temp.setWhere((String) jsonObj.get("obsnm"));
                    temp.setDate((String) jsonObj.get("sttcurdt"));
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
                    LocalDateTime inputDate = LocalDateTime.parse(temp.getDate(), formatter);
                    if(inputDate.isBefore(currentDate))
                        break;
                    result.add(temp);
                }
            }
            currentDate = LocalDateTime.now();
            rd.close();
            conn.disconnect();
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
