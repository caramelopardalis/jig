package org.dddjava.jig.domain.model.jigdocument.specification;

import org.dddjava.jig.domain.model.jigdocument.documentformat.DocumentName;
import org.dddjava.jig.domain.model.jigdocument.documentformat.JigDocument;
import org.dddjava.jig.domain.model.jigdocument.stationery.*;
import org.dddjava.jig.domain.model.models.architectures.PackageBasedArchitecture;
import org.dddjava.jig.domain.model.parts.package_.PackageComment;
import org.dddjava.jig.domain.model.parts.package_.PackageIdentifier;
import org.dddjava.jig.domain.model.parts.relation.class_.ClassRelations;

import java.util.StringJoiner;
import java.util.function.Function;

/**
 * アーキテクチャ図
 */
public class ArchitectureDiagram {

    PackageBasedArchitecture packageBasedArchitecture;
    ClassRelations classRelations;

    public ArchitectureDiagram(PackageBasedArchitecture packageBasedArchitecture, ClassRelations classRelations) {
        this.packageBasedArchitecture = packageBasedArchitecture;
        this.classRelations = classRelations;
    }

    public DiagramSources sources(JigDocumentContext jigDocumentContext) {
        ArchitectureRelations architectureRelations = ArchitectureRelations.from(packageBasedArchitecture, classRelations);
        if (architectureRelations.worthless()) {
            return DiagramSource.empty();
        }

        Function<PackageIdentifier, String> architectureLabel = packageIdentifier -> {
            PackageComment packageComment = jigDocumentContext.packageComment(packageIdentifier);
            return packageComment.exists() ? packageComment.asText() : packageIdentifier.simpleName();
        };

        DocumentName documentName = DocumentName.of(JigDocument.ArchitectureDiagram);

        StringJoiner graph = new StringJoiner("\n", "digraph \"" + documentName.label() + "\" {", "}")
                .add("label=\"" + documentName.label() + "\";")
                .add("node [shape=component,style=filled];")
                .add("graph[splines=ortho];"); // 線を直角にしておく

        // プロダクト
        graph.add("subgraph clusterArchitecture {")
                .add("label=\"\";")
                .add("graph[style=filled,color=lightgoldenrod,fillcolor=lightyellow];")
                .add("node [fillcolor=lightgoldenrod,fontsize=20];");
        for (PackageIdentifier packageIdentifier : packageBasedArchitecture.architecturePackages()) {
            graph.add(Node
                    .packageOf(packageIdentifier)
                    .label(architectureLabel.apply(packageIdentifier))
                    .asText());
        }
        graph.add("}");

        // 関連
        graph.add(RelationText.fromPackageRelations(architectureRelations.packageRelations()).asText());

        return DiagramSource.createDiagramSource(documentName, graph.toString());
    }
}
