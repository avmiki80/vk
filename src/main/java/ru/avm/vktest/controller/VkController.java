package ru.avm.vktest.controller;

import com.vk.api.sdk.client.actors.UserActor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.avm.vktest.dto.VkGroup;
import ru.avm.vktest.search.VkSearch;
import ru.avm.vktest.service.VkApiClientGetter;
import ru.avm.vktest.service.group.GroupService;
import ru.avm.vktest.service.VkConfig;
import ru.avm.vktest.service.VkSearchService;

import java.util.Set;

import static ru.avm.vktest.util.PageRequestUtil.createPageRequest;

@RestController
@RequiredArgsConstructor
public class VkController {

    @Value("${vk.redirect_url}")
    private String REDIRECT_URI;
    @Value("${vk.app_id}")
    private Integer APP_ID;

    private final VkApiClientGetter vkApiClientGetter;
    private final VkConfig vkConfig;
    private UserActor userActor;
    private final VkSearchService vkSearchService;
    private final GroupService groupService;

    @GetMapping("/auth")
    public RedirectView auth(RedirectAttributes attributes){
        final String URL = "https://oauth.vk.com/authorize";
        attributes.addAttribute("client_id" , APP_ID);
//        attributes.addAttribute("redirect_uri" , "https://oauth.vk.com/blank.html");
        attributes.addAttribute("redirect_uri" , REDIRECT_URI);
        attributes.addAttribute("display " , "page");
        attributes.addAttribute("scope" , "offline,friends,groups");
        attributes.addAttribute("response_type" , "code");
        attributes.addAttribute("state" , 123456);
        return new RedirectView(URL);
    }
    @GetMapping("/get-code")
    public ResponseEntity<String> getCode(@RequestParam(name = "code") String code){
        userActor = vkConfig.getActor(vkApiClientGetter.getClient(), code);
        return ResponseEntity.ok(code);
    }
    @PostMapping("/user")
    public ResponseEntity<Set<VkGroup>> findUserGroupByTitle(@RequestBody String title){
        return ResponseEntity.ok(vkSearchService.findUserGroupByTitle(userActor, title));
    }

    @PostMapping("/friend")
    public ResponseEntity<Set<VkGroup>> findUserAndFriendGroupByTitle(@RequestBody String title){
        return ResponseEntity.ok(vkSearchService.findUserAndFriendGroupByTitle(userActor, title));
    }
    @PostMapping("/search")
    public ResponseEntity<Page<VkGroup>> search(@RequestBody VkSearch search){
        return ResponseEntity.ok(groupService.search(search));
    }
}
