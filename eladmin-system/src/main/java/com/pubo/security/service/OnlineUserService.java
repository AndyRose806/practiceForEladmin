package com.pubo.security.service;

import com.pubo.security.config.bean.SecurityProperties;
import com.pubo.security.security.TokenProvider;
import com.pubo.security.service.dto.AuthUsrDto;
import com.pubo.utils.PageUtils;
import com.pubo.utils.RedisUtils;
import com.pubo.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description:com.pubo.security.service
 * @Author:pubo
 * @Date:2023/12/422:30
 */
@Service
@Slf4j
@AllArgsConstructor
public class OnlineUserService {

    private final SecurityProperties properties;
    private final TokenProvider tokenProvider;
    private final RedisUtils redisUtils;

    /**
     * 保存在线用户信息
     * @param jwtUserDto /
     * @param token /
     * @param request /
     */
//    public void save(JwtUserDto jwtUserDto, String token, HttpServletRequest request){
//        String dept = jwtUserDto.getUser().getDept().getName();
//        String ip = StringUtils.getIp(request);
//        String browser = StringUtils.getBrowser(request);
//        String address = StringUtils.getCityInfo(ip);
//        OnlineUserDto onlineUserDto = null;
//        try {
//            onlineUserDto = new OnlineUserDto(jwtUserDto.getUsername(), jwtUserDto.getUser().getNickName(), dept, browser , ip, address, EncryptUtils.desEncrypt(token), new Date());
//        } catch (Exception e) {
//            log.error(e.getMessage(),e);
//        }
//        String loginKey = tokenProvider.loginKey(token);
//        redisUtils.set(loginKey, onlineUserDto, properties.getTokenValidityInSeconds(), TimeUnit.MILLISECONDS);
//    }

    public void save(AuthUsrDto jwtUserDto, String token, HttpServletRequest request){
        String dept = jwtUserDto.getUsername();
        String ip = StringUtils.getIp(request);
        String browser = StringUtils.getBrowser(request);
        String address = StringUtils.getCityInfo(ip);
//        OnlineUserDto onlineUserDto = null;
//        try {
//            onlineUserDto = new OnlineUserDto(jwtUserDto.getUsername(), jwtUserDto.getUser().getNickName(), dept, browser , ip, address, EncryptUtils.desEncrypt(token), new Date());
//        } catch (Exception e) {
//            log.error(e.getMessage(),e);
//        }
        String loginKey = tokenProvider.loginKey(token);
        redisUtils.set(loginKey, jwtUserDto, properties.getTokenValidityInSeconds(), TimeUnit.MILLISECONDS);
    }

    /**
     * 查询全部数据
     * @param username /
     * @param pageable /
     * @return /
     */
//    public PageResult<OnlineUserDto> getAll(String username, Pageable pageable){
//        List<OnlineUserDto> onlineUserDtos = getAll(username);
//        return PageUtil.toPage(
//                PageUtil.paging(pageable.getPageNumber(),pageable.getPageSize(), onlineUserDtos),
//                onlineUserDtos.size()
//        );
//    }

    /**
     * 查询全部数据，不分页
     * @param username /
     * @return /
     */
//    public List<OnlineUserDto> getAll(String username){
//        String loginKey = properties.getOnlineKey() +
//                (StringUtils.isBlank(username) ? "" : "*" + username);
//        List<String> keys = redisUtils.scan(loginKey + "*");
//        Collections.reverse(keys);
//        List<OnlineUserDto> onlineUserDtos = new ArrayList<>();
//        for (String key : keys) {
//            onlineUserDtos.add((OnlineUserDto) redisUtils.get(key));
//        }
//        onlineUserDtos.sort((o1, o2) -> o2.getLoginTime().compareTo(o1.getLoginTime()));
//        return onlineUserDtos;
//    }

    /**
     * 退出登录
     * @param token /
     */
    public void logout(String token) {
        String loginKey = tokenProvider.loginKey(token);
        redisUtils.del(loginKey);
    }

    /**
     * 导出
     * @param all /
     * @param response /
     * @throws IOException /
     */
//    public void download(List<OnlineUserDto> all, HttpServletResponse response) throws IOException {
//        List<Map<String, Object>> list = new ArrayList<>();
//        for (OnlineUserDto user : all) {
//            Map<String,Object> map = new LinkedHashMap<>();
//            map.put("用户名", user.getUserName());
//            map.put("部门", user.getDept());
//            map.put("登录IP", user.getIp());
//            map.put("登录地点", user.getAddress());
//            map.put("浏览器", user.getBrowser());
//            map.put("登录日期", user.getLoginTime());
//            list.add(map);
//        }
//        FileUtil.downloadExcel(list, response);
//    }

    /**
     * 查询用户
     * @param key /
     * @return /
     */
//    public OnlineUserDto getOne(String key) {
//        return (OnlineUserDto)redisUtils.get(key);
//    }

    /**
     * 根据用户名强退用户
     * @param username /
     */
    @Async
    public void kickOutForUsername(String username) {
        String loginKey = properties.getOnlineKey() + username + "*";
        redisUtils.scanDel(loginKey);
    }
}
