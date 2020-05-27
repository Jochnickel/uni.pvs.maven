package de.uulm.sp.pvs.sokoban;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

public class SokobanLevel {
    private static String xsdFileName = "sokoban.xsd";
    private static Validator validator = null;

    final char[][] board;
    final String name;
    Difficulty difficulty;

    public SokobanLevel(String pathToXML) throws FileNotFoundException, InvalidFileException, Exception {
        if (isValidXML(pathToXML)) {
            var domRoot = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder().parse(pathToXML).getDocumentElement(); // can throw 3 exceptions
            var children = domRoot.getChildNodes();
            for (int i = children.getLength()-1; 0 <= i; i--) {
                var child = children.item(i);
                if("Difficulty".equals(child.getNodeName()){
                    switch (child.getNodeValue()) {
                        case "EASY":
                            difficulty = Difficulty.EASY;
                            break;
                        case "MEDIUM":
                            difficulty = Difficulty.MEDIUM;
                            break;
                        case "HARD":
                            difficulty = Difficulty.HARD;
                            break;
                        case "IMPOSSIBLE":
                            difficulty = Difficulty.IMPOSSIBLE;
                            break;
                        default:
                            throw new InvalidFileException("Parsing difficulty went wrong.")
                    }
                } else if ("LevelName".equals(child.getNodeName())){
                    this.name = child.getNodeValue();
                } else if ("LevelData".equals(child.getNodeName())){
                    
                }
            }
            board = null;
            name = null;
            difficulty = null;
        } else {
            throw new InvalidFileException();
        }
    }

    public static void setXSDFile(String fileName) {
        xsdFileName = fileName;
    }

    private static Validator getValidator() throws InvalidFileException, FileNotFoundException {
        try {
            return SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(new StreamSource(new FileInputStream(xsdFileName))).newValidator();
        } catch (SAXException e) {
            throw new InvalidFileException("XSD file not valid");
        }
    }

    public static boolean isValidXML(String pathToXML) throws FileNotFoundException, InvalidFileException {
        var validator = getValidator();
        Source xmlFile = new StreamSource(pathToXML);
        try {
            validator.validate(xmlFile);
            return true;
        } catch (SAXException e) {
            return false;
        } catch (IOException e) {
            throw new InvalidFileException();
        }
    }

    public static class InvalidFileException extends Exception {
        public InvalidFileException() {
            super();
        }

        public InvalidFileException(String s) {
            super(s);
        }

        private static final long serialVersionUID = 1L;
    }
}