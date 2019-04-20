package team.redrock.tyre.util.analyzer;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import team.redrock.tyre.util.response.EmptyResponse;


import java.util.ArrayList;
import java.util.List;

@Component
public class EmptyAnalyzer {

    public List<String> getEmptyRoomt(Document document, EmptyResponse response){
        List<String> rooms = new ArrayList<>();
        Elements table = document.body().getElementsByTag("tbody").get(0).children();
        table.forEach(tr->{
            tr.children().forEach(td->{
                String room  = td.getElementsByTag("input").attr("value");

                rooms.add(room);

            });
        });

        return rooms;
    }
}
