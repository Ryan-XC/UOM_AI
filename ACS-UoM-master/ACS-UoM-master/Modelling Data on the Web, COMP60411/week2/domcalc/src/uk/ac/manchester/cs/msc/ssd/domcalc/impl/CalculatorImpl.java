package uk.ac.manchester.cs.msc.ssd.domcalc.impl;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import uk.ac.manchester.cs.msc.ssd.domcalc.SchemaValidator;

import java.io.File;


public class CalculatorImpl {

    /**
     * ZERO ARGUMENT CONSTRUCTOR - VERY IMPORTANT! DO NOT ALTER
     */
    public CalculatorImpl() {
    }

    /**
     * Computes the result to a calculation that is specified by an XML document
     * @param schemaFile File containing RNC schema
     * @param testFile File containing test XML document
     * @param testDoc is the DOM representation of the test XML document
     * @return The result of the calculation.
     */
    public int computeResult(File schemaFile, File testFile, Document testDoc) throws NumberFormatException, SAXException {
        System.out.println("Running calculator...");
        // Validate test-file against schema using SchemaValidator class
        // File validation
        SchemaValidator sv = new SchemaValidator();
        sv.validate(schemaFile, testFile);
        //Compute result
        testDoc.getDocumentElement().normalize();
        String root = testDoc.getDocumentElement().getNodeName();
        NodeList nList = testDoc.getElementsByTagName(root);
        int result = breathFirstSearch(nList, nList.item(0).getNodeName());
        System.out.println("    .... computed result");
        System.out.println(result);
        return result; // TODO: Return result or throw exception
    }

    public int breathFirstSearch(NodeList root, String lastExpression ) throws SAXException {
        int result = 0;
        for (int i = 0; i < root.getLength(); i++) {
            Node tempNode = root.item(i);
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                if (tempNode.hasChildNodes()) {
                    if (i == 0 || i == 1) {
                        result = breathFirstSearch(tempNode.getChildNodes(),
                                tempNode.getNodeName());
                    } else {
                        result = calculate(lastExpression, result,
                                breathFirstSearch(tempNode.getChildNodes(),
                                        tempNode.getNodeName()));
                    }
                }
                if(tempNode.hasAttributes()){
                    if (tempNode.getNodeName() != "int") {
                        throw new SAXException();
                    }
                    NamedNodeMap nodeMap = tempNode.getAttributes();
                    for (int j = 0; j < nodeMap.getLength(); j++) {
                        Node node = nodeMap.item(j);
                        if (i == 1) {
                            result = Integer.parseInt(node.getNodeValue());
                        } else {
                            result = calculate(lastExpression, result,
                                    Integer.parseInt(node.getNodeValue()));
                        }
                    }
                }
            }
        }
        return result;
    }

    public int calculate(String expression, int value, int value2) throws SAXException {
        if (expression == "plus") {
            return value + value2;
        } else if (expression == "times") {
            return  value * value2;
        } else if (expression == "expression") {
            return value2;
        } else if (expression == "minus") {
            return value - value2;
        } else {
            throw new SAXException();
        }
    }
}
