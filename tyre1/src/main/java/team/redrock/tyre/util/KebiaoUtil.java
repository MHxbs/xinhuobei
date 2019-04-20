package team.redrock.tyre.util;

import team.redrock.tyre.entity.CourseInfo;
import team.redrock.tyre.domain.KebiaoTime;
import team.redrock.tyre.domain.Time;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KebiaoUtil {


    /**
     * 检查stuNum是否包含标点符号
     * @param s
     * @return
     */
    public static boolean check(String s) {
        boolean b = false;

        String tmp = s;
        tmp = tmp.replaceAll("\\p{P}", "");
        if (s.length() != tmp.length()) {
            b = true;
        }

        return b;
    }

    /**
     * 得到现在是第几周，是哪一个学期
     * @param data jwzx html
     * @return KebiaoTime
     */
    public static KebiaoTime getKebiaoTime(String data){
        KebiaoTime kebiaoTime=new KebiaoTime();
        Pattern pattern=Pattern.compile("今天是第 (\\d*?) 周");

        Matcher matcher=pattern.matcher(data);
        if (matcher.find()){
            kebiaoTime.setNowWeek(Integer.parseInt(matcher.group(1)));
        }

        Pattern pattern1=Pattern.compile("<li>〉〉(.*?) 学生课表>>(.*?)</li>");
        Matcher matcher1=pattern1.matcher(data);
        if (matcher1.find()){
            kebiaoTime.setTerm(matcher1.group(1));
        }

        return kebiaoTime;
    }

    /**
     * 从教务在线上拿课表
     * 通过列表爬，先爬主行，在通过courseNum，rowspan爬剩下的
     * @param data
     * @return
     * @throws IOException
     */

    public static List<CourseInfo> getTimeTableFromJWZX(String data) throws IOException {

        // 因为按列表查询每个课程可能有几行
        // 所以先查第一个，得到rowspan，然后拼接restRegex得到完整的正则表达式

        // 最开始的匹配式
        String firstRegex="<tr ><td rowspan='(.*?)' align='center'>(.*?)</td>  \t\t\t\t\t<td (.*?)>(.*?)</td>\t\t\t\t\t<td (.*?)>(.*?)</td>\t\t\t\t\t<td (.*?)>(.*?)</td><td (.*?)>(.*?)</td><td>(.*?)</td> \t\t\t\t\t<td>(.*?)</td><td>(.*?)</td>\t\t\t\t\t<td (.*?)><a href='(.*?)' target=_blank>名单</a></td>\t\t\t\t\t<td (.*?)></td>\t\t\t\t\t</tr>";
        // 每行的匹配式
        String restRegex="<tr><td >(.*?)</td>\t\t\t\t\t<td>(.*?) </td><td>(.*?)</td></tr>";

        Pattern pattern=Pattern.compile(firstRegex);
        Matcher matcher=pattern.matcher(data);

        List<CourseInfo> courseInfoList=new ArrayList<>();
        while (matcher.find()){

            CourseInfo courseInfo=new CourseInfo();

            // 因为课程number和课程名连接在一起的，所以通过字符串分割
            String courseStr=matcher.group(4);
            String[] courseArray=courseStr.split("-");
            courseInfo.setCourse(courseArray[1]);
            courseInfo.setCourse_num(courseArray[0]);

            // 直接得到
            courseInfo.setType(matcher.group(8));
            courseInfo.setTeacher(matcher.group(11));
            courseInfo.setClassroom(matcher.group(13));

            //matcher.group(12)的格式如：星期一第9-10节1-16周，调用
            // getTime将其解析成需要的时间参数
            Time time=TimeUtil.getTime(matcher.group(12));
            courseInfo.setDay(time.getDay());
            courseInfo.setLesson(time.getLesson());
            courseInfo.setPeriod(time.getPeriod());
            courseInfo.setHash_day(time.getHash_day());
            courseInfo.setHash_lesson(time.getHash_lesson());
            List<Integer> totalWeek =time.getWeek();
            if(totalWeek.size()>0) {
                courseInfo.setWeek(totalWeek);

                courseInfo.setWeekBegin(totalWeek.get(0));

                courseInfo.setWeekEnd(totalWeek.get(totalWeek.size() - 1));
            }

            //得到是那几周
            courseInfo.setRawWeek(TimeUtil.getWeekName(matcher.group(12)));
            courseInfo.setWeekModel(TimeUtil.getWeekModel(matcher.group(12)));
            // 添加进List
            courseInfoList.add(courseInfo);



            String courseNum=matcher.group(6);
            int rowspan= Integer.parseInt(matcher.group(1));
            // 将得到的courseNum拼接到regex中
            String regex="<tr ><td rowspan='(.*?)' align='center'>(.*?)</td>  \t\t\t\t\t<td (.*?)>(.*?)</td>\t\t\t\t\t<td (.*?)>"+courseNum+"</td>\t\t\t\t\t<td (.*?)>(.*?)</td><td (.*?)>(.*?)</td><td>(.*?)</td> \t\t\t\t\t<td>(.*?)</td><td>(.*?)</td>\t\t\t\t\t<td (.*?)><a href='(.*?)' target=_blank>名单</a></td>\t\t\t\t\t<td (.*?)></td>\t\t\t\t\t</tr>";
            // 将剩下的几行正则拼接上
            for (int i=1;i<rowspan;i++){
                regex=regex+restRegex;
            }

            Pattern restPattern=Pattern.compile(regex);
            Matcher restMatcher=restPattern.matcher(data);

            if (restMatcher.find()){
                // 循环得到剩下行的课表信息
                for (int i=16;i<=14+(rowspan-1)*3;i=i+3){
                    // 将主行的信息放入次行中
                    CourseInfo restCourseInfo=CourseInfoUtil.exchange(courseInfo);

                    restCourseInfo.setTeacher(restMatcher.group(i));
                    restCourseInfo.setClassroom(restMatcher.group(i+2));

                    // 通过getTime解析需要的时间信息
                    Time restCourseTime=TimeUtil.getTime(restMatcher.group(i+1));
                    restCourseInfo.setDay(restCourseTime.getDay());
                    restCourseInfo.setLesson(restCourseTime.getLesson());
                    restCourseInfo.setHash_lesson(restCourseTime.getHash_lesson());
                    restCourseInfo.setHash_day(restCourseTime.getHash_day());
                    List<Integer> totalWeek1 =restCourseTime.getWeek();
                    restCourseInfo.setWeek(totalWeek1);
                    restCourseInfo.setWeekBegin(totalWeek1.get(0));
                    restCourseInfo.setWeekEnd(totalWeek1.get(totalWeek1.size()-1));

                    //得到是那几周
                    restCourseInfo.setRawWeek(TimeUtil.getWeekName(restMatcher.group(i+1)));
                    restCourseInfo.setWeekModel(TimeUtil.getWeekModel(restMatcher.group(i+1)));

                    // 添加进list
                    courseInfoList.add(restCourseInfo);

                }
            }

        }

        // 因为是按列表爬的，所以要通过hash_day,hash_lesson排序
        courseInfoList.sort(new Comparator<CourseInfo>() {
            @Override
            public int compare(CourseInfo o1, CourseInfo o2) {
                int a1=(o1.getHash_day()+1)*10+o1.getHash_lesson();
                int a2=(o2.getHash_day()+1)*10+o2.getHash_lesson();
                return a1-a2;
            }
        });

        return courseInfoList;
    }

}
