package com.paymybuddy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.dto.ActivePage;

@Controller
@RolesAllowed("USER")
public class TransactionController {

	@RolesAllowed("USER")
	@GetMapping("/mytransaction")
	public String getCon(ActivePage activePage, BindingResult bindingResult){
		//activePage.setPage(activePage.getPage());
		return "mytransaction";
	}
	
/*	
	@RolesAllowed("USER")
	@PostMapping("/mytransaction")
	public ModelAndView seeConnection(ActivePage activePage, BindingResult bindingResult, Model model) {

		int displayedRows = 3;

		System.out.println(activePage);
		
		List<Econnection> econex = new ArrayList<>();

*//*		Econnection econ1 = new Econnection(1, "user", "user2");
		econex.add(econ1);
		Econnection econ2 = new Econnection(2, "user", "user3");
		econex.add(econ2);
		Econnection econ3 = new Econnection(3, "user", "user4");
		econex.add(econ3);
		Econnection econ4 = new Econnection(4, "user", "user5");
		econex.add(econ4);
		Econnection econ5 = new Econnection(5, "user", "user6");
		econex.add(econ5);*//*

		PagedListHolder<Econnection> pagedConex = new PagedListHolder<>(econex);

		pagedConex.setPageSize(displayedRows);
		pagedConex.setPage(activePage.getPage());

		List<Integer> pagesList = IntStream.rangeClosed(1, pagedConex.getPageCount()).boxed()
				.collect(Collectors.toList());

		Map<Integer, List<Econnection>> pConex = new HashMap<>();

		for (Integer i : pagesList) {
			pagedConex.setPage(i - 1);
			pConex.put(i, pagedConex.getPageList());
		}

		pConex.get(activePage.getPage());
		
		model.addAttribute("pConex", pConex);
		model.addAttribute("pagedConex", pagedConex);
		model.addAttribute("connections", econex);
		model.addAttribute("pagesList", pagesList);

		return new ModelAndView("redirect:/mytransaction?size="+displayedRows+"&page="+activePage.getPage());
	}*/
	
}
