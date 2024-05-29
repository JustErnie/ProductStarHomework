package com.product.star.homework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ContactReader {

    public List<Contact> readFromFile(Path filePath) {
        List<Contact> list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()));
            while (reader.ready()) {
                String line = reader.readLine();
                String[] split = line.split(",");
                list.add(new Contact(
                        split[0],
                        split[1],
                        split[2],
                        split[3]
                ));
            }
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
