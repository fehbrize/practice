package com.validity.monolithstarter.service;

import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.text.similarity.LevenshteinDistance;
import com.opencsv.CSVReader;
import com.google.gson.Gson;

@Service
public class DuplicateFinderService {


    private int findMatches(ArrayList<ArrayList<String[]>> entries, String[] rowB) {
        LevenshteinDistance ld = new LevenshteinDistance();
        int numMatchingColumns = 0;
        for(int i = 0; i < entries.size(); i++) {
            String[] rowA = entries.get(i).get(0);
            for(int j = 1; j < rowB.length; j++) {
                int lDistance = ld.apply(rowA[j], rowB[j]);
                if(lDistance < (0.4 * rowA[j].length())) {
                    numMatchingColumns += 1;
                }
            }
            if(numMatchingColumns > 6) {
                return i;
            }
            numMatchingColumns = 0;
        }
        return -1;
    }

    public String findDuplicates(String filename) throws FileNotFoundException {
        ArrayList<ArrayList<String[]>> entries = new ArrayList<>();
        try(FileReader fr = new FileReader("src/main/java/" + filename)) {
            String[] line;
            CSVReader reader = new CSVReader(fr);
            String[] headers = reader.readNext();
            String[] firstLine = reader.readNext();
            ArrayList<String[]> firstEntry = new ArrayList<>();
            firstEntry.add(firstLine);
            entries.add(firstEntry);
            String[] row;
            while((row = reader.readNext()) != null) {
                int matchIndex = findMatches(entries, row);

                if(matchIndex < 0) {
                    ArrayList<String[]> newEntry = new ArrayList<>();
                    newEntry.add(row);
                    entries.add(newEntry);
                }
                else {
                    entries.get(matchIndex).add(row);
                }
            }

        }
        catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        return gson.toJson(entries);
    }
}

