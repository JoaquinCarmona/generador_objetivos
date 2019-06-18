package com.test;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.tools.PDFText2HTML;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// para cargar como pagina de inicio
//@RequestMapping("")
@Controller
public class LoadController {

    @RequestMapping(value= "/", method = RequestMethod.GET)
    public String printHello(ModelMap model){

        model.addAttribute( "title", "Carga de Archivo");
        return "load";
    }

    @RequestMapping(value = "/loadFile", method = RequestMethod.POST)
    public String loadFile(@RequestParam("file")MultipartFile file, Model model)
        throws IOException {

        if (!file.getOriginalFilename().isEmpty()) {
            //  To read file using PDFBox
            PDDocument pdfDoc =PDDocument.load(file.getBytes());

            PDFText2HTML converter = new PDFText2HTML();
            converter.setSortByPosition(true);
            String txtRead = converter.getText(pdfDoc);

            // para obtener solo la seccion de actividades
            int startInd = txtRead.indexOf("<p> \r\n \r\nActividades \r\n \r\n</p>");
            int endInd = txtRead.indexOf("<p> \r\nDiagrama de  \r\nflujo de trabajo  \r\n</p>");

              // Para leerse como texto
//            PDFTextStripper tStripper = new PDFTextStripper();
//            tStripper.setSortByPosition(true);
//            String txtRead = tStripper.getText(pdfDoc);
//
//            // para obtener solo la seccion de actividades
//            int startInd = txtRead.indexOf(" \r\n \r\nActividades \r\n \r\n");
//            int endInd = txtRead.indexOf(" \r\nDiagrama de  \r\nflujo de trabajo  \r\n");

            pdfDoc.close();

            String txtActividades = txtRead.substring(startInd, endInd - 1);

            // para eliminar la representacionn html de los acentos en vocales y la ñ
            String noAccText = noAccents(txtActividades);

            String clearedText = clearText(noAccText);

            // Para eliminar los encabezados de la tabla
            String noTableHdrText = clearedText.replace("<p>Rol Descripción \r\n</p>\r\n","");

            // para darle un formato mas visual al texto
            String html = parseAsTable(noTableHdrText);

            model.addAttribute("msg", "File uploaded successfully.");
            model.addAttribute("html", html);
        } else {
            model.addAttribute("msg", "Please select a valid file..");
        }

        return "load";
    }

    // si se devuelve un objeto, agregar "produces = {MediaType.APPLICATION_JSON_VALUE}" al PostMapping
    @PostMapping(value = "/generateObjective")
    @ResponseBody
    public GenerateObjectiveResponse generateObjective(@RequestBody GenerateObjectiveRequest request){

        // acceder a los valores por medio de los getter y setters de la clase
        String[] roles = request.getRoles();
        String activity = request.getActivity();

        String generatedObjective = nlpHandler.generateObjective(roles, activity);

        // devuelve el objetivo generado
        GenerateObjectiveResponse  response= new GenerateObjectiveResponse();
        response.setMessage("Objetivo Generado...");
        response.setObjective( generatedObjective);

        return response;
    }

    private String noAccents(String inText){
        String outText = "";

        outText = inText.replace("&#209;", "Ñ");
        outText = inText.replace("&#225;", "á");
        outText = outText.replace("&#233;", "é");
        outText = outText.replace("&#237;", "í");
        outText = outText.replace("&#241;", "ñ");
        outText = outText.replace("&#243;", "ó");
        outText = outText.replace("&#250;", "ú");

        return outText;
    }

    // Para eliminar los textos del footer y el header de pagina
    private String clearText(String inText){

        String endStr ="Proyectos Específicos \r\n</p>\r\n";

        int offset = 0;

        StringBuilder strBuilder = new StringBuilder();
        while(offset <= inText.length()) {
            int startInd = inText.indexOf("<p>MoProSoft", offset);
            int endInd = inText.indexOf(endStr, offset);
            if (endInd != -1){
                strBuilder.append(inText.substring(offset, startInd - 1));
                offset = endInd + endStr.length();
            } else{
                strBuilder.append(inText.substring(offset, startInd - 1));
                offset = inText.length() + 1;
            }
        }

        return strBuilder.toString();
    }

    private String parseAsTable(String inText){
        String endStr ="</p>";

        StringBuilder strBuilder = new StringBuilder();
        int offset = 0;
        Pattern pttrn = Pattern.compile("A[\\d+.]+");
        Matcher matcher = pttrn.matcher(inText);

        while(offset <= inText.length()) {
            String innerText = "";
            String tags = "";
            String strBase = "";

            int startInd = inText.indexOf("<p>", offset);
            // se obtiene el indice para la siguiente actividad (las actividades empiezan con "A##.##."
            int startIndNextAct = matcher.find() ? matcher.start() : -1;

            String innerString = ( startIndNextAct != -1 ? inText.substring(offset, startIndNextAct) : inText.substring(offset));

            int endInd = offset + innerString.lastIndexOf(endStr);
            if (startInd != -1 && endInd != -1){
                //se obtiene el contenido de texto eliminando saltos y espacios en blanco al inicio y final
                //al igual que las etiquetas inyectadas por el lector PDF
                innerText = inText.substring(startInd + 3 , endInd - 1).replace("\r", "").replace("\n", "").replace("<p>", "").replace("</p>", "").trim();

                if (innerText.equals("Actividades")){
                    tags = "<div class=\"tableTitle col-md-12\">" + innerText + "</div>";
                } else if (innerText.contains("(O1")){ // significa que es una actividad principal
                    tags = "<div class=\"mainActivity col-md-12\">" + innerText + "</div>";
                } else {
                    strBase = "<div class=\"tableCell %1$s\">%2$s</div>";
                    tags = prepareRow(strBase, innerText);
                }

                strBuilder.append(tags);

                offset = endInd + endStr.length();
            } else {
                offset = inText.length() + 1;
            }

        }

        return strBuilder.toString();
    }

    private String prepareRow(String strBase, String inText){

        String strStyle = "";
        String strText = "";

        String[] roles = new  String[] { "RGPY", "RAPE", "CL", "RSC", "RDM", "ET"};

        String strRolesInTxt = "";
        for ( String role : roles ) {
            if(inText.contains(role + " ") ) {
                strRolesInTxt += role + "<br />";
                inText = inText.replace(role + " ", "");
            } else if (inText.contains(" " + role)){
                strRolesInTxt += role + "<br />";
                inText = inText.replace(" " + role, "");
            }
        }

        String cellRoles = String.format(strBase,"cellRol col-md-2", strRolesInTxt.trim());
        String cellActivity = String.format(strBase,"cellActivity col-md-9", inText.trim());
        String cellAction = String.format(strBase,"cellAction col-md-1", "<button id=\"btnFA\" class=\"btn btn-primary\"><i class=\"fa fa-cog fa-2x\"></i></button>");

        return String.format("<div class=\"rowActivity col-md-12\">%1$s</div>", cellRoles + cellActivity + cellAction);
    }

}
