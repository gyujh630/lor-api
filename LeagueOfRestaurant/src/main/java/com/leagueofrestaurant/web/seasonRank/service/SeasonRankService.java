package com.leagueofrestaurant.web.seasonRank.service;

import com.leagueofrestaurant.web.common.CommonService;
import com.leagueofrestaurant.web.exception.ErrorCode;
import com.leagueofrestaurant.web.exception.LORException;
import com.leagueofrestaurant.web.seasonRank.domain.SeasonRank;
import com.leagueofrestaurant.web.seasonRank.dto.SeasonRankDto;
import com.leagueofrestaurant.web.seasonRank.respository.SeasonRankRepository;
import com.leagueofrestaurant.web.store.domain.Store;
import com.leagueofrestaurant.web.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SeasonRankService {
    private final SeasonRankRepository seasonRankRepository;
    private final StoreRepository storeRepository;
    private final CommonService commonService;

    /**
     * 현재 시즌 마무리, 현재 도시별 랭크를 명예의 전당에 등록한다.
     */
    @Transactional
    public void saveSeasonRank() {
        //store에 저장되어 있는 city 종류 불러온다.
        List<String> allCity = storeRepository.findAllCity();
        //city별 top10 store 조회
        for (String city : allCity) {
            List<Store> RankStores = storeRepository.findTopSCore10ByCity(city);
            //rank 1부터 10까지
            int rank = 1;
            //city별 top10 store를 SeasonRank에 저장, 현재 시즌으로 저장
            for (Store store : RankStores) {
                SeasonRank seasonRank = new SeasonRank(store.getName(), store.getId(), store.getCity(), commonService.getSeason(), rank);
                seasonRankRepository.save(seasonRank);
                rank++;
            }
        }
    }

    /**
     * @param storeId
     * @return 해당 store의 시즌 랭킹이력 조회
     */
    public List<SeasonRankDto> getSeasonRankByStoreId(Long storeId) {
        List<SeasonRank> seasonRank = seasonRankRepository.findSeasonRankByStoreId(storeId);
        if (seasonRank.isEmpty()) {
            throw new LORException(ErrorCode.NO_EXIST_PRE_RANKING);
        }
        return getSeasonRanks(seasonRank);
    }

    /**
     * @param season
     * @param city
     * @return city별 특정 시즌의 랭킹 조회
     */
    public List<SeasonRankDto> getSeasonRankByCity(String season, String city) {
        List<SeasonRank> seasonRank = seasonRankRepository.findSeasonRankByCity(season, city);
        return getSeasonRanks(seasonRank);
    }
    public List<String> getSeasonName(){
        return seasonRankRepository.getSeasonName();
    }

    private static List<SeasonRankDto> getSeasonRanks(List<SeasonRank> seasonRank) {
        List<SeasonRankDto> seasonRankDtoList = seasonRank.stream()
                .map(s -> new SeasonRankDto(
                        s.getStoreName(),
                        s.getStoreId(),
                        s.getCity(),
                        s.getSeason(),
                        s.getRanking()
                )).collect(Collectors.toList());
        return seasonRankDtoList;
    }

}
