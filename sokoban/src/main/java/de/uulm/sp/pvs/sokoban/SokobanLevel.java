package de.uulm.sp.pvs.sokoban;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class SokobanLevel {
    private static String xsdFileName = "sokoban.xsd";
    private static Validator validator = null;

    final List<String> authors = new ArrayList<String>();
    final char[][] board;
    final String name;
    final Difficulty difficulty;

    public static void main(String[] args) throws FileNotFoundException, InvalidFileException {
        var a = new SokobanLevel("Sokoban-B.xml");
    }

    public SokobanLevel(String pathToXML) throws FileNotFoundException, InvalidFileException {
        // load XSD
        try {
            validator = loadValidator();
        } catch (FileNotFoundException | InvalidFileException e) {
            if (null == validator) {
                throw e;
            } else {
                System.err.printf("Error reading %s again. Using known validator\n", xsdFileName);
            }
        }
        // load XML
        var domRoot = loadLevelFromXML(validator, pathToXML);
        // parse the XML
        difficulty = (null != domRoot.getElementsByTagName("Difficulty").item(0))
                ? Difficulty.valueOf(domRoot.getElementsByTagName("Difficulty").item(0).getTextContent())
                : null;
        final var authorList = domRoot.getElementsByTagName("Author");
        if (null != authorList) {
            for (int i = 0; i < authorList.getLength(); authors.add(authorList.item(i++).getTextContent())) {
            }
        }
        name = (null != domRoot.getElementsByTagName("LevelName").item(0))
                ? domRoot.getElementsByTagName("LevelName").item(0).getTextContent()
                : null;
        board = domNodeToBoard(domRoot.getElementsByTagName("LevelData").item(0));
    }

    private char[][] domNodeToBoard(Node node) throws InvalidFileException {
        final int height = Integer.parseInt(node.getAttributes().getNamedItem("height").getTextContent());
        final int width = Integer.parseInt(node.getAttributes().getNamedItem("width").getTextContent());
        final String cleanLevelString = node.getTextContent().replace("\n", "");
        final char[][] board = new char[height][width];
        if (cleanLevelString.length() != width * height)
            throw new InvalidFileException("width*height doesnt match String");
        for (int i = 0, h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                board[h][w] = cleanLevelString.charAt(i++);
            }
        }
        return board;
    }

    private Element loadLevelFromXML(Validator validator, String pathToXML)
            throws InvalidFileException, FileNotFoundException {
        try {
            validator.validate(new StreamSource(pathToXML));
            return DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder().parse(pathToXML)
                    .getDocumentElement();
        } catch (SAXException e) {
            throw new InvalidFileException(pathToXML + " doesn't fulfill the schema." + e);
        } catch (ParserConfigurationException e) {
            throw new InvalidFileException("ParserConfigurationException occurred.");
        } catch (IOException e) {
            throw new FileNotFoundException(e.getMessage());
        }
    }

    public static void setXSDFile(String fileName) {
        xsdFileName = fileName;
    }

    private static Validator loadValidator() throws InvalidFileException, FileNotFoundException {
        try {
            return SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(new StreamSource(new FileInputStream(xsdFileName))).newValidator();
        } catch (SAXException e) {
            throw new InvalidFileException("XSD file not valid");
        }
    }

}