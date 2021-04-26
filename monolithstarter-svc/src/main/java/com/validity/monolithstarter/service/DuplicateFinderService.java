package com.validity.monolithstarter.service;

import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.text.similarity.LevenshteinDistance;
import com.opencsv.CSVReader;
import com.google.gson.Gson;

@Service
public class DuplicateFinderService {


    private int findMatches(ArrayList<ArrayList<Entry>> entries, Entry rowB) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        LevenshteinDistance ld = new LevenshteinDistance();
        int numMatchingColumns = 0;
        String[] getters = {"getFirstName", "getLastName", "getCompany", "getEmail", "getAddress1", "getAddress2",
            "getZip", "getCity", "getLongState", "getShortState", "getPhone"};

        /*Check the row against all previously read rows*/
        for(int i = 0; i < entries.size(); i++) {
            Entry rowA = entries.get(i).get(0);
            for(int j = 1; j < getters.length; j++) {
                Method rowAGetter = rowA.getClass().getDeclaredMethod(getters[j]);
                Method rowBGetter = rowB.getClass().getDeclaredMethod(getters[j]);
                int lDistance = ld.apply((String)rowAGetter.invoke(rowA), (String)rowBGetter.invoke(rowB));

                /*If the strings are at least 60% similar (levenshtein distance of 40% of the length or less, they're
                 a match*/
                if(lDistance < (0.4 *  ((String) rowAGetter.invoke(rowA)).length())) {
                    numMatchingColumns += 1;
                }
            }
            /*If over half the columns match, the entries are considered a match*/
            if(numMatchingColumns >= 5) {
                return i;
            }
            numMatchingColumns = 0;
        }
        return -1;
    }

    public Entry convertToEntryObject(String[] entryList) {
        return  new Entry(entryList[0], entryList[1], entryList[2], entryList[3], entryList[4], entryList[5], entryList[6],
            entryList[7], entryList[8], entryList[9], entryList[10], entryList[11]);
    }

    public String findDuplicates(String filename) {
        ArrayList<ArrayList<Entry>> entries = new ArrayList<>();
        /* Open File */
        try(FileReader fr = new FileReader("../test-files/" + filename)) {
            CSVReader reader = new CSVReader(fr);

            /*Pull out the column headers and add the first line to match against*/
            String[] headers = reader.readNext();
            String[] firstLine = reader.readNext();
            Entry entrifiedFirstLine = convertToEntryObject(firstLine);
            ArrayList<Entry> firstEntry = new ArrayList<>();
            firstEntry.add(entrifiedFirstLine);
            entries.add(firstEntry);
            String[] row;

            /* Match each row against all previously read rows to check for duplicates*/
            while((row = reader.readNext()) != null) {
                Entry entrifiedRow = convertToEntryObject(row);
                int matchIndex = findMatches(entries, entrifiedRow);

                /*If there's a match, add it to its match's list, otherwise add it as a new entry*/
                if(matchIndex < 0) {
                    ArrayList<Entry> newEntry = new ArrayList<>();
                    newEntry.add(entrifiedRow);
                    entries.add(newEntry);
                }
                else {
                    entries.get(matchIndex).add(entrifiedRow);
                }
            }

        }
        catch (IOException | CsvValidationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        return gson.toJson(entries);
    }
}

