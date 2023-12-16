package main;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import entities.AllData;
import entities.Description;
import entities.Emission;
import entities.XML;


public class Parsing {

    public static void main(String[] args) {
        List<XML> xmlParsed = parseXMLData();
        List<Description> descriptions = parseDescriptions();
        List<Emission> emissionsFromJSON = parseJSONData();
        persistAllData(xmlParsed, emissionsFromJSON, descriptions);
    }

    private static String formatValue(String value) {
        double numericValue = Double.parseDouble(value);
        DecimalFormat decimalFormat = new DecimalFormat("#.#############");
        return decimalFormat.format(numericValue);
    }

    private static List<Description> parseDescriptions() {
        List<Description> descriptions = new ArrayList<>();

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.ipcc-nggip.iges.or.jp/EFDB/find_ef.php"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String content = response.body();

            String[] lines = content.split("ipccTree.add");

            for (String line : lines) {
                if (line.contains("'")) {
                    String[] parts = line.split(",");
                    if (parts.length >= 4) {
                        String description = parts[2].trim().replaceAll("[^0-9A-Za-z. ]", "");
                        Description category = new Description(description);
                        descriptions.add(category);
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return descriptions;
    }

    private static List<XML> parseXMLData() {
        List<XML> xmls = new ArrayList<>();

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://cdr.eionet.europa.eu/ie/eu/mmr/art04-13-14_lcds_pams_projections/projections/envvxoklg/MMR_IRArticle23T1_IE_2016v2.xml"))
                    .GET()
                    .build();

            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(response.body());

            NodeList rowList = document.getElementsByTagName("Row");

            for (int i = 0; i < rowList.getLength(); i++) {
                Node rowNode = rowList.item(i);

                if (rowNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element rowElement = (Element) rowNode;

                    String year = getElementValue(rowElement, "Year");
                    String scenario = getElementValue(rowElement, "Scenario");
                    String value = getElementValue(rowElement, "Value");

                    if ("2023".equals(year) && "WEM".equals(scenario) && isValidValue(value)) {
                        value = formatValue(value);
                        String category = getElementValue(rowElement, "Category__1_3");
                        String gasUnits = getElementValue(rowElement, "Gas___Units");
                        String nk = getElementValue(rowElement, "NK");

                        XML xmlparse = new XML(category, year, scenario, gasUnits, nk, value);
                        xmls.add(xmlparse);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xmls;
    }

    private static boolean isValidValue(String value) {
        try {
            double val = Double.parseDouble(value);
            return val > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static String getElementValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName).item(0).getChildNodes();
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            if (node != null) {
                return node.getNodeValue();
            }
        }
        return "";
    }

    private static List<Emission> parseJSONData() {
        List<Emission> emissions = new ArrayList<>();

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("classpath:/GreenhouseGasEmissions2023.json"))
                    .GET()
                    .build();

            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.body());
            JsonNode emissionsNode = rootNode.get("Emissions");

            for (JsonNode emissionNode : emissionsNode) {
                String category = emissionNode.get("Category").asText();
                String gasUnits = emissionNode.get("Gas Units").asText();
                double value = emissionNode.get("Value").asDouble();
                String formattedValue = formatValue(String.valueOf(value));

                Emission e = new Emission(category, gasUnits, formattedValue);
                emissions.add(e);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return emissions;
    }

	
	private static void persistAllData(List<XML> xmls, List<Emission> emissions, List<Description> descriptions) {
	    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("gasesPU");
	    EntityManager entityManager = entityManagerFactory.createEntityManager();
	    EntityTransaction transaction = null;

	    try {
	        transaction = entityManager.getTransaction();
	        transaction.begin();

	        //XML DATA 
	        for (XML xml : xmls) {
	            boolean matched = false;

	            //if matching data is in the json file
	            for (Emission emission : emissions) {
	                if (xml.getCategory().equals(emission.getCategory()) && xml.getGasUnits().equals(emission.getGasUnits())) {
	                    String variance = calculateVariance(xml.getValue(), String.valueOf(emission.getValue()));

	                   AllData entity = new AllData(
	                        xml.getCategory(),
	                        null,
	                        xml.getGasUnits(),
	                        xml.getValue(),
	                        String.valueOf(emission.getValue()),
	                        variance
	                    );
	                   
	                   for (Description d : descriptions) {
	                	    if (d.getDescription().contains(xml.getCategory())) {
	                	        entity.setDescription(d.getDescription());
	                	        break;
	                	    }
	                	}
	                    entityManager.persist(entity);
	                    matched = true;
	                    break;
	                }
	            }

	            // If no match found, add the XML data
	            if (!matched) {
	                AllData entity = new AllData(
	                    xml.getCategory(),
	                   null,
	                    xml.getGasUnits(),
	                    xml.getValue(),
	                    null,
	                    null
	                );
	                
	                for (Description d : descriptions) {
                	    if (d.getDescription().contains(xml.getCategory())) {
                	        entity.setDescription(d.getDescription());
                	        break;
                	    }
                	}
	                entityManager.persist(entity);
	            }
	        }
	        
	        for (Emission emission : emissions) {
	            boolean matched = false;
	            for (XML xml : xmls) {
	                if (emission.getCategory().equals(xml.getCategory()) && emission.getGasUnits().equals(xml.getGasUnits())) {
	                	matched = true;
	                    break;
	                }
	            }
	            if (!matched) {
	                AllData entity = new AllData(
	                    emission.getCategory(),
	                    null,
	                    emission.getGasUnits(),
	                    null,
	                    String.valueOf(emission.getValue()),
	                    null
	                );
	                
	                for (Description d : descriptions) {
                	    if (d.getDescription().contains(emission.getCategory())) {
                	        entity.setDescription(d.getDescription());
                	        break;
                	    }
                	}
	                entityManager.persist(entity);
	            }
	        }

	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null && transaction.isActive()) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        entityManager.close();
	        entityManagerFactory.close();
	    }
	}


	private static String calculateVariance(String predicted, String readings) {
	    BigDecimal predictedVal = new BigDecimal(predicted);
	    BigDecimal readingsVal = new BigDecimal(readings);

	    BigDecimal variance = readingsVal.subtract(predictedVal);
	    DecimalFormat decimalFormat = new DecimalFormat("#.#############");
	    String formattedVariance = decimalFormat.format(variance);

	    return formattedVariance;
	}

}