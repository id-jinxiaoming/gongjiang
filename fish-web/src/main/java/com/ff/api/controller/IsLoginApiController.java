package com.ff.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ff.common.base.BaseController;
import com.ff.common.model.ResponseData;
import com.ff.common.util.StringUtils;
import com.ff.shop.model.CraftsmanAchievement;
import com.ff.shop.service.CraftsmanAchievementService;
import com.ff.user.model.Usered;
import com.ff.user.service.UseredService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/isLogin")
public class IsLoginApiController extends BaseController {

    @Reference
    private UseredService useredService;

    @Reference
    private CraftsmanAchievementService craftsmanAchievementService;

    @CrossOrigin(value = "*", maxAge = 3600)
    @RequestMapping(value = "/judge",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "判断是否登入", notes = "判断是否登入")
    public ResponseData judye(
            @ApiParam(required = true,name = "token",value = "token")@RequestParam(required = false,value = "token")String token
    ){
        ResponseData result = new ResponseData();
        if(StringUtils.isEmpty(token)){
            result.setState(250);
            result.setMessage("参数为空");
            return result;
        }
        Usered usered = useredService.findUserByToken(token);
        Integer foremanStatus = usered.getForemanStatus();
        Integer teamInformationStatus = usered.getTeamInformationStatus();
        if(foremanStatus!=3) {
            switch (foremanStatus) {
                case 0:
                    result.setState(100);
                    result.setMessage("首次登入");
                    break;
                case 1:
                    result.setState(300);
                    result.setMessage("工长审核未通过");
                    break;
                case 2:
                    result.setState(500);
                    result.setMessage("工长正在审核中");
                    break;
                default:
                    System.out.println("出错了");
            }
            return result;
        }

        if(teamInformationStatus!=4){
            switch (teamInformationStatus) {
                case 1:
                    result.setState(600);
                    result.setMessage("工长队伍信息未组建");
                    break;
                case 2:
                    result.setState(700);
                    result.setMessage("队伍信息审核未通过");
                    break;
                case 3:
                    result.setState(800);
                    result.setMessage("队伍信息审核中");
                    break;
                default:
                    System.out.println("出错了");
            }
            return result;
        }

        CraftsmanAchievement craftsmanAchievement = new CraftsmanAchievement();
        craftsmanAchievement.setUseredId(usered.getId());
        craftsmanAchievement.setStatus(2);
        List<CraftsmanAchievement> list = craftsmanAchievementService.selectByT(craftsmanAchievement);
        if(list.size()==0){
            result.setMessage("暂无可用工程业绩");
            result.setState(900);
            return result;
        }else {
            result.setState(200);
            result.setMessage("成功");
            return result;
        }
    }
}
