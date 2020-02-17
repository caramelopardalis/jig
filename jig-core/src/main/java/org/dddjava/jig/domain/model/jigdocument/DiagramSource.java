package org.dddjava.jig.domain.model.jigdocument;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class DiagramSource {

    DocumentName documentName;
    String text;
    AdditionalText additionalText;
    List<DiagramSource> compositeDiagramSources;

    private DiagramSource(DocumentName documentName, String text, AdditionalText additionalText) {
        this.documentName = documentName;
        this.text = text;
        this.additionalText = additionalText;
        this.compositeDiagramSources = Collections.emptyList();
    }

    private DiagramSource(List<DiagramSource> diagramSources) {
        this.documentName = null;
        this.text = null;
        this.additionalText = null;
        this.compositeDiagramSources = diagramSources;
    }

    public static DiagramSource createDiagramSource(DocumentName documentName, String text) {
        return new DiagramSource(documentName, text, AdditionalText.empty());
    }

    public static DiagramSource createDiagramSource(DocumentName documentName, String text, AdditionalText additionalText) {
        return new DiagramSource(documentName, text, additionalText);
    }

    public static DiagramSource createDiagramSource(List<DiagramSource> diagramSources) {
        return new DiagramSource(diagramSources);
    }

    public String text() {
        if (noValue()) throw new NullPointerException();
        return text;
    }

    public AdditionalText additionalText() {
        return additionalText;
    }

    public DocumentName documentName() {
        return documentName;
    }

    public boolean noValue() {
        return text == null && compositeDiagramSources.isEmpty();
    }

    public static DiagramSource empty() {
        return createDiagramSource(null, null);
    }

    public void each(Consumer<DiagramSource> consumer) {
        if (compositeDiagramSources.isEmpty()) {
            consumer.accept(this);
        }
        compositeDiagramSources.forEach(consumer);
    }
}
