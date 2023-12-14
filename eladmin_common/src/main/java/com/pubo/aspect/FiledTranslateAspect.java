package com.pubo.aspect;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pubo.annotation.Dict;
import com.pubo.service.ISysDictDetailService;
import com.pubo.service.ISysDictService;
import com.pubo.utils.BaseResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

@Aspect
@Component
public class FiledTranslateAspect {
    private static final Logger log = LoggerFactory.getLogger(FiledTranslateAspect.class);

    @Autowired
    private ISysDictService dictService;

    @Autowired
    private ISysDictDetailService dictDetailService;

    @Pointcut("execution(* com.pubo..*Controller.*(..))")
    public void pointcut(){

    }

    @Around("pointcut()")
    public Object translate(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        parseDict(result);
       return   result;
    }

    private void parseDict(Object result) throws IllegalAccessException {
        if(result instanceof BaseResponse){
            Object body = ((BaseResponse<?>) result).getData();
            if(body != null){
                ObjectMapper objectMapper = new ObjectMapper();
                String json = "{}";
                try {
                    log.info("进行数据字典翻译，返回值：{}", objectMapper.writeValueAsString(result));
                    json = objectMapper.writeValueAsString(body);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Json转换失败"+e.getMessage());
                }
                JSONObject jsonObject = JSONObject.parseObject(json);
                Field[] fields = body.getClass().getDeclaredFields();

                    if(fields!=null && fields.length!=0){
                        Iterator<Field> iterator = Arrays.stream(fields).iterator();
                        //获取标注dict注解的字段
                        while (iterator.hasNext()){
                            Field field = iterator.next();
                            field.setAccessible(true);
                            Dict dict = field.getAnnotation(Dict.class);
                            if(dict!=null){
                                String dictType = dict.dictName();
                                String convertValue = dictService.getLabelByDictName(dictType, (String) field.get(body));
                                jsonObject.put(field.getName(), convertValue);

                            }
                        }

                }


                ((BaseResponse<?>) result).setData(jsonObject);
            }
        }
    }
}
