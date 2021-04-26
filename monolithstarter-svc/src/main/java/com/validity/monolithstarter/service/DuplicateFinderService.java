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
        for(int i = 0; i < entries.size(); i++) {
            Entry rowA = entries.get(i).get(0);
            for(int j = 1; j < getters.length; j++) {
                Method rowAGetter = rowA.getClass().getDeclaredMethod(getters[j]);
                Method rowBGetter = rowB.getClass().getDeclaredMethod(getters[j]);
                int lDistance = ld.apply((String)rowAGetter.invoke(rowA), (String)rowBGetter.invoke(rowB));
                if(lDistance < (0.4 *  ((String) rowAGetter.invoke(rowA)).length())) {
                    numMatchingColumns += 1;
                }
            }
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
        try(FileReader fr = new FileReader("../test-files/" + filename)) {
            CSVReader reader = new CSVReader(fr);
            String[] headers = reader.readNext();
            String[] firstLine = reader.readNext();
            Entry entrifiedFirstLine = convertToEntryObject(firstLine);
            ArrayList<Entry> firstEntry = new ArrayList<>();
            firstEntry.add(entrifiedFirstLine);
            entries.add(firstEntry);
            String[] row;
            while((row = reader.readNext()) != null) {
                Entry entrifiedRow = convertToEntryObject(row);
                int matchIndex = findMatches(entries, entrifiedRow);

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

