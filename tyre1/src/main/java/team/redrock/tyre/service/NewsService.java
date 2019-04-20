package team.redrock.tyre.service;


import team.redrock.tyre.util.response.NewsContentResponse;
import team.redrock.tyre.util.response.NewslistResponse;

public interface NewsService {
    public NewslistResponse getListFromIp(int page);

    public NewslistResponse getListFromDB(int page);

    public NewsContentResponse getContentFromIp(Long id);

    public NewsContentResponse getContentFromDb(Long id);

    public void deleteNewslist();

}
