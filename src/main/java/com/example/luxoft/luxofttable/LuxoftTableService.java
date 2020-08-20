package com.example.luxoft.luxofttable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Service
public class LuxoftTableService {
    private LuxoftTableRepository luxoftTableRepository;

    @Autowired
    LuxoftTableService(LuxoftTableRepository luxoftTableRepository) {
        this.luxoftTableRepository = luxoftTableRepository;
    }

    public void saveTableDataFromFile(MultipartFile file) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            // Skip header
            reader.readLine();
            while (reader.ready()) {
                String[] line = reader.readLine().split(",");
                saveOrUpdate(parse(line));
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException("Exception while processing a file: ", ex);
        }
    }

    private LuxoftTable parse(String[] line) {
        String primaryKey;
        String name;
        String description;
        Instant instant;

        try {
            primaryKey = line[0].trim();
            if (primaryKey.isEmpty()) {
                throw new IllegalArgumentException("Primary key cannot be empty");
            }

            name = line[1].trim();
            description = line[2].trim();

            try {
                instant = Instant.parse(line[3].trim());
            } catch (DateTimeParseException ex) {
                throw new IllegalArgumentException("Wrong timestamp format: " + ex);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new IllegalArgumentException("Missing a parameter when processing a file: " + ex);
        }

        return new LuxoftTable(primaryKey, name, description, instant);
    }

    public void saveOrUpdate(LuxoftTable person) {
        luxoftTableRepository.save(person);
    }

    public Optional<LuxoftTable> getById(String id) {
        return luxoftTableRepository.findById(id);
    }

    public void deleteSingleRecord(String id) {
        luxoftTableRepository.deleteById(id);
    }
}
