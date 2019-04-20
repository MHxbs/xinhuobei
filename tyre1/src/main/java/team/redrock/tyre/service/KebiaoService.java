package team.redrock.tyre.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.redrock.tyre.entity.CourseInfo;
import team.redrock.tyre.entity.KebiaoResult;
import team.redrock.tyre.domain.KebiaoTime;
import team.redrock.tyre.util.KebiaoUtil;
import team.redrock.tyre.util.SendUrl;

import java.io.IOException;
import java.util.List;

@Service
public class KebiaoService {

    @Value("${jwzx.courseInfo}")
    private String courseInfoURL;

    public KebiaoResult getKebiao(String stu_num) throws IOException {

        KebiaoResult kebiaoResult=new KebiaoResult();

        // 向教务在线发送请求
        String param="xh="+stu_num;
        String data= SendUrl.getDataByGet(courseInfoURL,param);

        // 调用getTimeTableFromJWZX得到课表
        List<CourseInfo> courseInfoList=KebiaoUtil.getTimeTableFromJWZX(data);
        // 调用getKebiaoTime来解析一些需要的时间参数
        KebiaoTime kebiaoTime=KebiaoUtil.getKebiaoTime(data);

        // data转换成String类型
        Gson gson=new Gson();
        String list=gson.toJson(courseInfoList,new TypeToken<List<CourseInfo>>(){}.getRawType());

        // kebiaoResult中放入相关参数
        kebiaoResult.setData(list);
        kebiaoResult.setNowWeek(kebiaoTime.getNowWeek());
        kebiaoResult.setStuNum(stu_num);
        kebiaoResult.setTerm(kebiaoTime.getTerm());
        kebiaoResult.setSuccess(true);
        kebiaoResult.setVersion("1.0.1");
        kebiaoResult.setStatus(200);


       return kebiaoResult;
    }
}
