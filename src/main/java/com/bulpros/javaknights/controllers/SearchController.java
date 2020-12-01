package com.bulpros.javaknights.controllers;

import com.bulpros.javaknights.models.dto.SearchDto;
import com.bulpros.javaknights.services.contracts.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class SearchController {
    private SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping(value="/search")
    public String showSearch(Model model, @ModelAttribute("search") SearchDto search){
        model.addAttribute("search", search);
        model.addAttribute("users",searchService.searchUser(search.getText()));
        model.addAttribute("comments",searchService.searchComment(search.getText()));
        model.addAttribute("posts",searchService.searchPost(search.getText()));
        model.addAttribute("forums",searchService.searchForum(search.getText()));
        model.addAttribute("communities",searchService.searchCommunity(search.getText()));

        return "search";
    }
}
