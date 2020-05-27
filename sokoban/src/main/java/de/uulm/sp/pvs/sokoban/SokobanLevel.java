package de.uulm.sp.pvs.sokoban;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class SokobanLevel {
    private static String xsdFileName = "sokoban.xsd";
    private static Validator validator = null;

    List<String> authors = new ArrayList<String>();
    char[][] board;
    String name;
    Difficulty difficulty;

    public SokobanLevel(String pathToXML) throws FileNotFoundException, InvalidFileException, Exception {
        if (isValidXML(pathToXML)) {
            var domRoot = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder().parse(pathToXML).getDocumentElement(); // can throw 3 exceptions
            var children = domRoot.getChildNodes();
            for (int iNode = children.getLength()-1; 0 < iNode; iNode--) {
                var child = children.item(iNode);
                if("Difficulty".equals(child.getNodeName())) {
                    switch (child.getTextContent()) {
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
                            throw new InvalidFileException("Parsing difficulty went wrong.");
                    }
                } else if ("LevelName".equals(child.getNodeName())){
                    this.name = child.getNodeValue();
                } else if ("LevelData".equals(child.getNodeName())){
                    var attrs = child.getAttributes();
                    var width = Integer.parseInt(attrs.getNamedItem("width").getNodeValue());
                    var height = Integer.parseInt(attrs.getNamedItem("height").getNodeValue());
                    var levelString = child.getTextContent().replace("\n", "");
                    try{
                        board = new char[width][height];
                        for (int i = 0, w = 0; w < board.length; w++) {
                            for (int h = 0; h < board.length; h++) {
                                board[w][h] = levelString.charAt(i);
                            }
                        }
                    } catch (IndexOutOfBoundsException e){
                        System.err.println("Board height/width corrupted");
                    }
                } else if ("Author".equals(child.getNodeName())){
                    authors.add(child.getTextContent());
                }
            }
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