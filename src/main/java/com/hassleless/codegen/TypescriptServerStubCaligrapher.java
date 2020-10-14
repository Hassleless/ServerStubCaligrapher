package com.hassleless.codegen;

import io.swagger.codegen.*;
import io.swagger.models.properties.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.io.File;

public class TypescriptServerStubCaligrapher extends DefaultCodegen implements CodegenConfig {

  // source folder where to write the files
  protected String sourceFolder = "src";
  protected String apiVersion = "1.0.0";

  protected static Logger LOGGER = LoggerFactory.getLogger(TypescriptServerStubCaligrapher.class);

  protected boolean withXml = false;

  protected String packageName = "swagger";
  public static final String TAGGED_UNIONS ="taggedUnions";
  protected HashSet<String> languageGenericTypes;

  private boolean taggedUnions = false;


  /**
   * Configures the type of generator.
   * 
   * @return  the CodegenType for this generator
   * @see     io.swagger.codegen.CodegenType
   */
  public CodegenType getTag() {
    return CodegenType.SERVER;
  }

  /**
   * Configures a friendly name for the generator.  This will be used by the generator
   * to select the library with the -l flag.
   * 
   * @return the friendly name for the generator
   */
  public String getName() {
    return "TypescriptServerStubCaligrapher";
  }

  /**
   * Returns human-friendly help for the generator.  Provide the consumer with help
   * tips, parameters here
   * 
   * @return A string value for the help message
   */
  public String getHelp() {
    return "Generates a TypescriptServerStubCaligrapher client library.";
  }

  public TypescriptServerStubCaligrapher() {
    super();

    // set the output folder here
    outputFolder = "server-stub/typescript-express";

    /**
     * Models.  You can write model files using the modelTemplateFiles map.
     * if you want to create one template for file, you can do so here.
     * for multiple files for model, just put another entry in the `modelTemplateFiles` with
     * a different extension
     */
    modelTemplateFiles.put(
      "model.mustache", // the template to use
      ".ts");       // the extension for each file to write

    /**
     * Api classes.  You can write classes for each Api file with the apiTemplateFiles map.
     * as with models, add multiple entries with different extensions for multiple files per
     * class
     */
    apiTemplateFiles.put(
      "api.mustache",   // the template to use
      ".ts");       // the extension for each file to write

    /**
     * Template Location.  This is the location which templates will be read from.  The generator
     * will use the resource stream to attempt to read the templates.
     */
    templateDir = "TypescriptServerStubCaligrapher";

    /**
     * Api Package.  Optional, if needed, this can be used in templates
     */
    apiPackage = "io.hassleless.server.controller";

    /**
     * Model Package.  Optional, if needed, this can be used in templates
     */
    modelPackage = "io.hassleless.server.model";

    /**
     * Language specific types
     */

    languageGenericTypes = new HashSet<String>(Arrays.asList(
            "Array","Map"
    ));

    languageSpecificPrimitives = new HashSet<String>(
            Arrays.asList(
                    "boolean",
                    "number",
                    "Boolean",
                    "string",
                    "Map",
                    "tuple",
                    "Array",
                    "enum",
                    "any",
                    "void",
                    "object",
                    "bigint",
                    "Date",
                    "File"
            ));

    instantiationTypes.clear();

    typeMapping.clear();

    typeMapping.put("integer", "number");
    typeMapping.put("array","Array");
    typeMapping.put("long", "bigint");
    typeMapping.put("number", "number");
    typeMapping.put("float", "number");
    typeMapping.put("double", "number");
    typeMapping.put("boolean", "Boolean");
    typeMapping.put("map", "Map");
    typeMapping.put("string", "string");
    typeMapping.put("UUID", "string");
    typeMapping.put("date", "Date");
    typeMapping.put("DateTime", "Date");
    typeMapping.put("password", "string");
    typeMapping.put("File", "File");
    typeMapping.put("file", "File");
    // map binary to string as a workaround
    // the correct solution is to use []byte
    typeMapping.put("binary", "string");
    typeMapping.put("ByteArray", "string");
    typeMapping.put("object", "object");
    /**
     * Reserved words.  Override this with reserved words specific to your language
     */
    reservedWords = new HashSet<String>(
            Arrays.asList(
                    "break",
                    "case",
                    "catch",
                    "class",
                    "const",
                    "continue",
                    "debugger",
                    "default",
                    "delete",
                    "do",
                    "else",
                    "enum",
                    "export",
                    "extends",
                    "false",
                    "finally",
                    "for",
                    "function",
                    "if",
                    "import",
                    "in",
                    "instanceof",
                    "new",
                    "null",
                    "return",
                    "super",
                    "switch",
                    "this",
                    "throw",
                    "true",
                    "try",
                    "typeof",
                    "var",
                    "void",
                    "while",
                    "with",
                    "as",
                    "implements",
                    "interface",
                    "let",
                    "package",
                    "private",
                    "protected",
                    "public",
                    "static",
                    "yield",
                    "any",
                    "boolean",
                    "constructor",
                    "declare",
                    "get",
                    "module",
                    "require",
                    "number",
                    "set",
                    "string",
                    "symbol",
                    "type",
                    "from",
                    "of",
                    "namespace",
                    "async",
                    "await"
            )
    );

    /**
     * Additional Properties.  These values can be passed to the templates and
     * are available in models, apis, and supporting files
     */
    additionalProperties.put("apiVersion", apiVersion);

    /**
     * Supporting Files.  You can write single files for the generator with the
     * entire object tree available.  If the input file has a suffix of `.mustache
     * it will be processed by the template engine.  Otherwise, it will be copied
     */
//    supportingFiles.add(new SupportingFile("myFile.mustache",   // the input template or file
//      "",                                                       // the destination folder, relative `outputFolder`
//      "myFile.sample")                                          // the output file
//    );

  }

  /**
   * Escapes a reserved word as defined in the `reservedWords` array. Handle escaping
   * those terms here.  This logic is only called if a variable matches the reserved words
   * 
   * @return the escaped term
   */
  @Override
  public String escapeReservedWord(String name) {
    return "_" + name;  // add an underscore to the name
  }

  /**
   * Location to write model files.  You can use the modelPackage() as defined when the class is
   * instantiated
   */
  public String modelFileFolder() {
    return outputFolder + "/" + sourceFolder + "/" + modelPackage().replace('.', File.separatorChar);
  }

  @Override
  public void processOpts() {
    super.processOpts();
    supportingFiles.add(new SupportingFile("models.mustache", sourceFolder + "/" + modelPackage().replace('.', File.separatorChar), "models.ts"));
    if (additionalProperties.containsKey(TAGGED_UNIONS)) {
      taggedUnions = Boolean.parseBoolean(additionalProperties.get(TAGGED_UNIONS).toString());
    }
  }

  /**
   * Location to write api files.  You can use the apiPackage() as defined when the class is
   * instantiated
   */
  @Override
  public String apiFileFolder() {
    return outputFolder + "/" + sourceFolder + "/" + apiPackage().replace('.', File.separatorChar);
  }

  /**
   * Optional - type declaration.  This is a String which is used by the templates to instantiate your
   * types.  There is typically special handling for different property types
   *
   * @return a string value used as the `dataType` field for model templates, `returnType` for api templates
   */
  @Override
  public String getTypeDeclaration(Property p) {
    if (p instanceof ArrayProperty) {
      ArrayProperty ap = (ArrayProperty) p;
      Property inner = ap.getItems();
      return getSwaggerType(p) + "<" + getTypeDeclaration(inner) + ">";
    } else if (p instanceof MapProperty) {
        MapProperty mp = (MapProperty) p;
        Property inner = mp.getAdditionalProperties();
        return "Map<string, " + getTypeDeclaration(inner) + ">";
      } else if (p instanceof FileProperty) {
          return "any";
    }
    return super.getTypeDeclaration(p);
  }

  /**
   * Optional - swagger type conversion.  This is used to map swagger types in a `Property` into
   * either language specific types via `typeMapping` or into complex models if there is not a mapping.
   *
   * @return a string value of the type or complex model for this property
   * @see io.swagger.models.properties.Property
   */

  @Override
  public String getSwaggerType(Property p) {
    String swaggerType = super.getSwaggerType(p);
    String type = null;
    if (typeMapping.containsKey(swaggerType)) {
      type = typeMapping.get(swaggerType);
      if (languageSpecificPrimitives.contains(type))
        return type;
    } else
      type = swaggerType;
    return toModelName(type);
  }


  @Override
  public String toModelName(String name) {
    LOGGER.warn("Model name : " +  name);
    name = sanitizeName(name); // FIXME: a parameter should not be assigned. Also declare the methods parameters as 'final'.

    if (!StringUtils.isEmpty(modelNamePrefix)) {
      name = modelNamePrefix + "_" + name;
    }

    if (!StringUtils.isEmpty(modelNameSuffix)) {
      name = name + "_" + modelNameSuffix;
    }

    // model name cannot use reserved keyword, e.g. return
    if (isReservedWord(name)) {
      String modelName = camelize("model_" + name);
      LOGGER.warn(name + " (reserved word) cannot be used as model name. Renamed to " + modelName);
      return modelName;
    }

    // model name starts with number
    if (name.matches("^\\d.*")) {
      String modelName = camelize("model_" + name); // e.g. 200Response => Model200Response (after camelize)
      LOGGER.warn(name + " (model name starts with number) cannot be used as model name. Renamed to " + modelName);
      return modelName;
    }

    if (languageSpecificPrimitives.contains(name)) {
      String modelName = camelize("model_" + name);
      LOGGER.warn(name + " (model name matches existing language type) cannot be used as a model name. Renamed to " + modelName);
      return modelName;
    }

    // camelize the model name
    // phone_number => PhoneNumber
    String convertedModelName = camelize(name);
    LOGGER.warn("Converted Model Name : " + convertedModelName);
    return convertedModelName;
  }

  @Override
  public String toModelFilename(String name) {
    // should be the same as the model name
    return toModelName(name);
  }

  @Override
  public Map<String, Object> postProcessAllModels(Map<String, Object> objs) {
    Map<String, Object> result = super.postProcessAllModels(objs);

    for (Map.Entry<String, Object> entry : result.entrySet()) {
      Map<String, Object> inner = (Map<String, Object>) entry.getValue();
      List<Map<String, Object>> models = (List<Map<String, Object>>) inner.get("models");
      for (Map<String, Object> mo : models) {
        CodegenModel cm = (CodegenModel) mo.get("model");
        if (taggedUnions) {
          mo.put(TAGGED_UNIONS, true);
          if (cm.discriminator != null && cm.children != null) {
            for (CodegenModel child : cm.children) {
              cm.imports.add(child.classname);
            }
          }
          if (cm.parent != null) {
            cm.imports.remove(cm.parent);
          }
        }
        // Add additional filename information for imports
        mo.put("tsImports", toTsImports(cm, cm.imports));
      }
    }
    return result;
  }

  private List<Map<String, String>> toTsImports(CodegenModel cm, Set<String> imports) {
    List<Map<String, String>> tsImports = new ArrayList<>();
    for (String im : imports) {
      if (!im.equals(cm.classname)) {
        HashMap<String, String> tsImport = new HashMap<>();
        tsImport.put("classname", im);
        tsImport.put("filename", toModelFilename(im));
        tsImports.add(tsImport);
      }
    }
    return tsImports;
  }

}