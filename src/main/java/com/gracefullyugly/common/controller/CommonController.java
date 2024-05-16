package com.gracefullyugly.common.controller;

import com.gracefullyugly.domain.item.dto.ItemWithImageUrlResponse;
import com.gracefullyugly.domain.item.service.ItemSearchService;
import com.gracefullyugly.domain.qna.dto.QnADto;
import com.gracefullyugly.domain.qna.service.QnASearchService;
import com.gracefullyugly.domain.report.dto.ReportResponse;
import com.gracefullyugly.domain.report.service.ReportSearchService;
import com.gracefullyugly.domain.review.dto.ReviewWithImageResponse;
import com.gracefullyugly.domain.review.service.ReviewSearchService;
import com.gracefullyugly.domain.user.dto.ProfileResponse;
import com.gracefullyugly.domain.user.dto.SellerDetailsResponse;
import com.gracefullyugly.domain.user.entity.User;
import com.gracefullyugly.domain.user.service.SellerDetailsService;
import com.gracefullyugly.domain.user.service.UserSearchService;
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
    private final SellerDetailsService sellerDetailsService;
    private final ReportSearchService reportSearchService;

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
        List<ReviewWithImageResponse> reviews = reviewSearchService.getReviewsWithImagesByItemId(itemId);
        List<QnADto> qnaList = qnASearchService.getQnAList(itemId);
        List<SellerDetailsResponse> sellerDetails = sellerDetailsService.getSellerDetails(itemId);

        model.addAttribute("reviews", reviews);
        model.addAttribute("QnAs", qnaList);
        model.addAttribute("sellerDetails", sellerDetails);
        model.addAttribute("itemId", itemId);
        return "sellerDetails";
    }

    @GetMapping("/create-review/{itemId}")
    public String createReview(@PathVariable Long itemId, Model model) {
        ItemWithImageUrlResponse itemResponse = itemSearchService.findOneItem(itemId);
        Float starPoint = reviewSearchService.findAverageStarPointsByItemId(itemId);

        model.addAttribute("starPoint", starPoint);
        model.addAttribute("item", itemResponse);
        return "create-review";
    }

    @GetMapping("/create-item-report/{itemId}")
    public String createItemReport(@PathVariable Long itemId, Model model) {
        ItemWithImageUrlResponse itemResponse = itemSearchService.findOneItem(itemId);
        Float starPoint = reviewSearchService.findAverageStarPointsByItemId(itemId);

        model.addAttribute("starPoint", starPoint);
        model.addAttribute("item", itemResponse);
        return "create-item-report";
    }

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

    @GetMapping("/question/{itemId}")
    public String question(@PathVariable Long itemId, Model model) {
        List<QnADto> qnaList = qnASearchService.getQnAList(itemId);

        ItemWithImageUrlResponse item = itemSearchService.findOneItem(itemId);

        model.addAttribute("qnaList", qnaList);
        model.addAttribute("item", item);
        return "productAsk";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/admin-report")
    public String adminReport(Model model) {
        List<ReportResponse> reports = reportSearchService.getReports();

        model.addAttribute("reports", reports);
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

    @GetMapping("/verification-email")
    public String verificationEmail(@Valid @NotNull @AuthenticationPrincipal(expression = "userId") Long userId) {
        return "verification-email";
    }

    @GetMapping("/update-user-info")
    public String updateUserInfo(@Valid @NotNull @AuthenticationPrincipal(expression = "userId") Long userId,
                                 Model model) {
        User user = userSearchService.findById(userId);
        model.addAttribute("User", user);

        return "update-user-info";
    }

}
