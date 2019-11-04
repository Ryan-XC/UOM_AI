package uk.ac.manchester.cs.msc.ssd.domcalc;

import com.thaiopensource.relaxng.jaxp.CompactSyntaxSchemaFactory;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class SchemaValidator {

    public static boolean validate(File schemaFile, File testFile) {

		try {

			createValidator(schemaFile).validate(new StreamSource(testFile));

			return true;
		}
		catch (SAXException e) {

			return false;
		}
		catch (IOException e) {

			throw new RuntimeException("Error reading test file: " + e);
		}
	}

    static private Validator createValidator(File schemaFile) {

		try {

			SchemaFactory schemaFactory = new CompactSyntaxSchemaFactory();
			Schema schema = schemaFactory.newSchema(schemaFile);

			return schema.newValidator();
		}
		catch (SAXException e) {

			throw new RuntimeException("Error reading schema file: " + e);
		}
	}
}