package com.qzh.articleservice.client;


import com.qzh.articleservice.entity.UcenterMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    @PostMapping("/educenter/member/getMemberInfoById/{memberId}")
    public UcenterMemberVo getInfo(@PathVariable String memberId);
}
