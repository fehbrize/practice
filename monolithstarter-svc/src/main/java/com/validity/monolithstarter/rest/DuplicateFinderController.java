package com.validity.monolithstarter.rest;

import com.validity.monolithstarter.service.DuplicateFinderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api")
public class DuplicateFinderController {

        @Inject
        private DuplicateFinderService duplicateFinder;

        @GetMapping("/find-dupes")
        public String getDuplicates() throws FileNotFoundException {
            return duplicateFinder.findDuplicates("normal.csv");
        }

}
