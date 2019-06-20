package com.test;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.List;
import java.util.Properties;

public class nlpHandler {

    private static StanfordCoreNLP pipeline = null;

    protected static StanfordCoreNLP getPipeline() {

        if(pipeline == null) {
            Properties props = new Properties();
            props.put("annotators", "tokenize, ssplit, pos, lemma, ner");

            props.setProperty("tokenize.language", "es");
            props.setProperty("pos.model", "edu/stanford/nlp/models/pos-tagger/spanish/spanish-distsim.tagger");
            props.setProperty("ner.model", "edu/stanford/nlp/models/ner/spanish.ancora.distsim.s512.crf.ser.gz");

            pipeline = new StanfordCoreNLP(props);
        }

        return pipeline;
    }

    public static String generateObjective(String[] roles, String activity){
        Annotation document = new Annotation(activity);
        getPipeline().annotate(document);

        StringBuilder strBuilder = new StringBuilder();

        boolean firstWord = true;
        boolean changeVerb = true;

        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        for(CoreMap sentence : sentences){
            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
                String word = token.get(TextAnnotation.class);

                if(firstWord) {
                    // Se cambia la "A" por "O" para identificarlo como un objetivo
                    strBuilder.append(word.replace("A", "O"));
                    firstWord = false;
                } else {
                    String pos = token.get(PartOfSpeechAnnotation.class);

                    if(pos.equals("vmn0000") && changeVerb){
                        // TODO: Agregar lista de roles solo una vez??
                        strBuilder.append(getNoun(roles));
                        strBuilder.append(" " + word.toLowerCase() + (roles.length > 1 ? "án" : "á"));

                    } else {
                        if (!pos.equals("cc") && !pos.equals("fc") && !pos.equals("fp"))
                            changeVerb = false;
                        else
                            changeVerb = true;

                        // para regresar los parentesis a su representacion normal
                        if (word.equals("=LRB="))
                            word = "(";
                        if (word.equals("=RRB="))
                            word = ")";

                        // comas y puntos no llevan espacio precedente
                        strBuilder.append(((!pos.equals("fc") && !pos.equals("fp") ? " " : "") + word));
                    }
                }
            }
        }

        String objective = strBuilder.toString();

        return objective;
    }

    protected static String getNoun(String[] roles){
        StringBuilder strBldr = new StringBuilder();

        for ( int i = 0; i <= roles.length - 1; i++) {
            if( i == 0){
                strBldr.append(String.format(" Él %1$s", roles[i]));
            } else {
                if (i == roles.length - 1)
                    strBldr.append(String.format(" y %1$s", roles[i]));
                else
                    strBldr.append(String.format(", %1$s", roles[i]));
            }
        }

        return strBldr.toString();
    }
}
