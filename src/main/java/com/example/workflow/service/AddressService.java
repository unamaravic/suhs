package com.example.workflow.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddressService {
    private final Map<String, String> placePostalCodeMap = new HashMap<>();

    @PostConstruct
    public void init() {
        try {
            PDDocument document = Loader.loadPDF(new File("C:/una/fer/piis/infsus/dz4/MjestaRH.pdf"));
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            document.close();

            parsePdfText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parsePdfText(String text) {
        String[] lines = text.split("\\r?\\n");
        for (String line : lines) {
            //System.out.println(line);
            String[] parts = line.split("\\s+");
            if (parts.length >= 2) {
                String postalCode = parts[0];
                int index = -1;
                for (int i = parts.length - 1; i >= 1; i--) {
                    if (parts[i].matches("\\d+")) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    StringBuilder placeNameBuilder = new StringBuilder();
                    for (int i = index + 1; i < parts.length - 1; i++) {
                        placeNameBuilder.append(parts[i] + " ");

                    }
                    String placeName = placeNameBuilder.toString().trim();
                    if (placeName.equals("Podravska Slatina")) System.out.println(placeName + " " + postalCode);
                    placePostalCodeMap.put(placeName, postalCode);
                }
            }
        }
    }

    public boolean isMjestoPbrValid(String mjesto, String pbr) {
        System.out.println("is mjesto valid");
        System.out.println(placePostalCodeMap.get(mjesto));
        System.out.println(pbr);
        return pbr.equals(placePostalCodeMap.get(mjesto));
    }
}
