package org.dddjava.jig.presentation.view.graphvizj;

import guru.nidi.graphviz.engine.Graphviz;
import org.dddjava.jig.presentation.view.JigDocumentWriter;
import org.dddjava.jig.presentation.view.JigView;

public class GraphvizjView<T> implements JigView<T> {
    DotTextEditor<T> editor;
    DiagramFormat diagramFormat;

    public GraphvizjView(DotTextEditor<T> editor, DiagramFormat diagramFormat) {
        this.editor = editor;
        this.diagramFormat = diagramFormat;
    }

    @Override
    public void render(T model, JigDocumentWriter jigDocumentWriter) {
        DotTexts dotTexts = editor.edit(model);

        if (dotTexts.isEmpty()) {
            jigDocumentWriter.skip();
            return;
        }
        for (DotText dot : dotTexts.list()) {
            jigDocumentWriter.writeDebugText(dot.text(), dot.documentSuffix());
            jigDocumentWriter.writeDiagram(
                    outputStream ->
                            Graphviz.fromString(dot.text())
                                    .render(diagramFormat.graphvizjFormat())
                                    .toOutputStream(outputStream),
                    diagramFormat,
                    dot.documentSuffix());
        }
    }
}
