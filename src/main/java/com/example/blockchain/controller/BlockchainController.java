package com.example.blockchain.controller;

import com.example.blockchain.model.Transaction;
import com.example.blockchain.service.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
public class BlockchainController {

    @Autowired
    private BlockchainService blockchainService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("chain", blockchainService.getChain());
        return "index";
    }

    @PostMapping("/addTransaction")
    public String addTransaction(
            @RequestParam String sender,
            @RequestParam String receiver,
            @RequestParam double amount,
            @RequestParam String type
    ) {
        blockchainService.addTransaction(sender, receiver, amount, type);
        return "redirect:/";
    }
}
