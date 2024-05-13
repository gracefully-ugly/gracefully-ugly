package com.gracefullyugly.common.controller;

import com.gracefullyugly.common.wrapper.ApiResponse;
import com.gracefullyugly.domain.groupbuy.dto.GroupBuyListResponse;
import com.gracefullyugly.domain.groupbuy.service.GroupBuySearchService;
import com.gracefullyugly.domain.item.dto.ItemWithImageUrlResponse;
import com.gracefullyugly.domain.item.service.ItemSearchService;
import com.gracefullyugly.domain.qna.dto.QnADto;
import com.gracefullyugly.domain.qna.dto.QnADtoUtil;
import com.gracefullyugly.domain.qna.service.QnASearchService;
import com.gracefullyugly.domain.review.dto.ReviewWithImageResponse;
import com.gracefullyugly.domain.review.service.ReviewSearchService;
import com.gracefullyugly.domain.user.dto.ProfileResponse;
import com.gracefullyugly.domain.user.service.UserSearchService;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
@Slf4j
public class CommonController {

    private final UserSearchService userSearchService;
    private final ItemSearchService itemSearchService;
    private final ReviewSearchService reviewSearchService;
    private final QnASearchService qnASearchService;

    @GetMapping("/")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/join2")
    public String join2Page(@RequestParam String loginId, Model model) {
        model.addAttribute("loginId", loginId);
        return "join2";
    }

    @GetMapping("/join")
    public String joinPage() {
        return "join";
    }

    @GetMapping("/my-page")
    public String myPagePage(@Valid @NotNull @AuthenticationPrincipal(expression = "userId") Long userId, Model model) {
        ProfileResponse response = userSearchService.getProfile(userId);
        model.addAttribute("Profile", response);

        return "my-page";
    }

    @GetMapping("/salesPost")
    public String sales_post() {
        return "salesPost";
    }

    @GetMapping("/sellerList")
    public String sellerList(@Valid @NotNull @AuthenticationPrincipal(expression = "userId") Long userId, Model model) {
        List<ItemWithImageUrlResponse> items = itemSearchService.findAllItems();

        List<ItemWithImageUrlResponse> filteredItems = items.stream()
                .filter(item -> item.getUserId().equals(userId))
                .collect(Collectors.toList());

        model.addAttribute("items", filteredItems);

        return "sellerList";
    }

    @GetMapping("/sellerDetails/{itemId}")
    public String sellerDetails(@PathVariable Long itemId, Model model) {
        ItemWithImageUrlResponse oneItems = itemSearchService.findOneItem(itemId);
        List<ReviewWithImageResponse> reviews = reviewSearchService.getReviewsWithImagesByItemId(itemId);
        ApiResponse<List<QnADto>> QnAs = qnASearchService.getQnAList(itemId);

        model.addAttribute("oneItems", oneItems);
        model.addAttribute("reviews", reviews);
        model.addAttribute("QnAs", QnAs);
        return "sellerDetails";
    }

//    @GetMapping("/create-review/{itemId}")
//    public String createReview(@PathVariable Long itemId, Model model) {
//        ItemWithImageUrlResponse itemResponse = itemSearchService.findOneItem(itemId);
//        Float starPoint = reviewSearchService.findAverageStarPointsByItemId(itemId);
//    }

//    @GetMapping("/create-order")
//    public String createOrder() {
//        return "create-order";
//    }
//
//        model.addAttribute("starPoint", starPoint);
//        model.addAttribute("item", itemResponse);
//        return "create-review";
//    }

    @GetMapping("/group-buying/{itemId}")
    public String groupBuying(@PathVariable Long itemId, Model model) {
        List<ReviewWithImageResponse> reviews = reviewSearchService.getReviewsWithImagesByItemId(itemId);
        ItemWithImageUrlResponse itemResponse = itemSearchService.findOneItem(itemId);

        Float starPoint = reviewSearchService.findAverageStarPointsByItemId(itemId);

        model.addAttribute("starPoint", starPoint);
        model.addAttribute("reviews", reviews);
        model.addAttribute("item", itemResponse);
        return "group-buying";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/admin-report")
    public String adminReport() {
        return "admin-report";
    }

    @GetMapping("/log")
    public String log() {
        return "login";
    }

    @GetMapping("/cart-list")
    public String cart_list() {
        return "cart-list";
    }

    @GetMapping("/modify-order")
    public String modify_order() {
        return "modify-order";
    }

    @GetMapping("/purchase_history")
    public String purchase_history() {
        return "purchase_history";
    }

    @GetMapping("/productAsk")
    public String productAsk() {
        return "productAsk";
    }

}
