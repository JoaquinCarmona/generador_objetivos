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

        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        for(CoreMap sentence : sentences){
            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
                String word = token.get(TextAnnotation.class);
                String pos = token.get(PartOfSpeechAnnotation.class);

                strBuilder.append(String.format("[ %1$s, %2$s] ",word, pos));
            }
        }

        //TODO: Crear respuesta
        String objective = strBuilder.toString();

        return objective;
    }
}
