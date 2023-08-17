package com.javachip.floodguard.service;

import com.javachip.floodguard.dto.SearchPostRequestDTO;
import com.javachip.floodguard.entity.Search;
import com.javachip.floodguard.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchService {
    private final SearchRepository searchRepository;
    public void addSearch(SearchPostRequestDTO searchPostRequestDTO){
        Search temp = new Search();
        temp.setSearchVal(searchPostRequestDTO.getSearchval());
        temp.setSearchdate(LocalDateTime.now());
        searchRepository.save(temp);
    }
    public List<String> getRank(){
        List<Search> searches = searchRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        HashMap<String, Double> score = new HashMap<>();
        for(var i : searches){
            // 데이터 베이스가 커지면 시간이 오래 걸릴 가능성이 높음
            if(i.getSearchdate().isBefore(now.minusDays(1))){
                continue;
            }
            if(!score.containsKey(i.getSearchVal())){
                System.out.println(i.getSearchVal() + " " + searchRepository.findAllByHourTimeAndVal(now,i.getSearchVal()).size());
                score.put(i.getSearchVal(),(double)searchRepository.findAllByHourTimeAndVal(now,i.getSearchVal()).size() / searchRepository.findAllByDayTimeAndVal(now,i.getSearchVal()).size());
            }
            if(i.getSearchdate().getHour() < 12){
                score.put(i.getSearchVal(),score.get(i.getSearchVal()) + 0.8);
            }
            else {
                score.put(i.getSearchVal(),score.get(i.getSearchVal()) + 1);
            }
        }
        List<String> keySet = new ArrayList<>(score.keySet());
        System.out.println(keySet);
        keySet.sort((o1, o2) -> score.get(o2).compareTo(score.get(o1)));
        System.out.println(keySet);
        List<String> result = new ArrayList<>();
        for(String key : keySet) {
            if (result.size() == 5) {
                break;
            }
            result.add(key);
        }
        return result;
    }
}
