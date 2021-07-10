package org.example.mirai.plugin;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.example.mirai.plugin.annotation.UserCommand;
import org.example.mirai.plugin.dao.FundDao;
import org.example.mirai.plugin.netword.FundCrawler;
import org.example.mirai.plugin.pojo.User;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Mu Yuchen
 * @date 2021/7/8 15:21
 */
public class CommandController {

    private FundCrawler fundCrawler = FundCrawler.getInstance();

    private FundDao fundDao = FundDao.getInstance();

    @UserCommand(".今日板块")
    public String dailyIndustry(){
        JSONObject industry = fundCrawler.getIndustry();
        JSONArray data = industry.getJSONArray("data");
        List<JSONObject> arrayLists = data.toList(JSONObject.class);
        //排序
        arrayLists.sort(Comparator.comparingDouble(x -> x.getDouble("changePercent")));
        StringBuilder sb = new StringBuilder();
        sb.append("领涨行业：").append("\n");
        sb.append(arrayLists.get(arrayLists.size() - 1).getStr("name")).append(":").append(arrayLists.get(arrayLists.size() - 1).getStr("changePercent")).append("\n");
        sb.append(arrayLists.get(arrayLists.size() - 2).getStr("name")).append(":").append(arrayLists.get(arrayLists.size() - 2).getStr("changePercent")).append("\n");
        sb.append(arrayLists.get(arrayLists.size() - 3).getStr("name")).append(":").append(arrayLists.get(arrayLists.size() - 3).getStr("changePercent")).append("\n");

        sb.append("领跌行业：").append("\n");

        sb.append(arrayLists.get(0).getStr("name")).append(":").append(arrayLists.get(0).getStr("changePercent")).append("\n");
        sb.append(arrayLists.get(1).getStr("name")).append(":").append(arrayLists.get(1).getStr("changePercent")).append("\n");
        sb.append(arrayLists.get(2).getStr("name")).append(":").append(arrayLists.get(2).getStr("changePercent"));
        return sb.toString();
    }

    @UserCommand(".基金")
    public String fund(String code){
        JSONObject fund = fundCrawler.getFund(code).getJSONArray("data").toList(JSONObject.class).get(0);
        StringBuilder sb = new StringBuilder();
        sb.append("基金编号: ").append(code).append("\n");
        sb.append("基金名称: ").append(fund.getStr("name")).append("\n");
        sb.append("今日预估: ").append(fund.getStr("expectGrowth")).append("\n");
        sb.append("本周涨跌: ").append(fund.getStr("lastWeekGrowth")).append("\n");
        sb.append("本月涨跌: ").append(fund.getStr("lastMonthGrowth")).append("\n");
        sb.append("三月涨跌: ").append(fund.getStr("lastThreeMonthsGrowth")).append("\n");
        sb.append("半年涨跌: ").append(fund.getStr("lastSixMonthsGrowth")).append("\n");
        sb.append("一年涨跌: ").append(fund.getStr("lastYearGrowth"));
        return sb.toString();
    }

    @UserCommand(".我的基金")
    public String myFund(String id){
        Optional<User> query = fundDao.query(id);
        //如果用户为空则抛出异常
        User user = query.orElseThrow(RuntimeException::new);
        StringBuilder sb = new StringBuilder();
        List<JSONObject> funds = fundCrawler.getFunds(user.getFundList().toArray(new String[0])).getJSONArray("data").toList(JSONObject.class);
        for (JSONObject fund : funds){
            sb.append(fund.getStr("name")).append(": ").append(fund.getStr("expectGrowth")).append("\n");
        }
        return sb.toString();
    }

    @UserCommand(".添加自选")
    public String insertFund(String codes, String id){
        List<String> fundList = Arrays.stream(codes.split(",")).collect(Collectors.toList());
        User user = new User();
        user.setId(id);
        user.setFundList(fundList);
        fundDao.add(user);
        return "添加成功";
    }

    @UserCommand(".删除自选")
    public String deleteFund(String code, long id){
        return "开发中";
    }
}
