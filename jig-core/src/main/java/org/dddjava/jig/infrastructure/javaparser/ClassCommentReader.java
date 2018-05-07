package org.dddjava.jig.infrastructure.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import com.github.javaparser.javadoc.Javadoc;
import org.dddjava.jig.domain.model.identifier.type.TypeIdentifier;
import org.dddjava.jig.domain.model.japanese.JapaneseName;
import org.dddjava.jig.domain.model.japanese.TypeJapaneseName;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.Optional;

class ClassCommentReader {

    Optional<TypeJapaneseName> read(Path path) {
        try {
            CompilationUnit cu = JavaParser.parse(path);

            Optional<TypeJapaneseName> result = cu.accept(new GenericVisitorAdapter<Optional<TypeJapaneseName>, Void>() {

                @Override
                public Optional<TypeJapaneseName> visit(ClassOrInterfaceDeclaration node, Void arg) {
                    return node.getJavadoc()
                            .map(javadoc -> javadocToTypeJapaneseName(node, javadoc));
                }

                @Override
                public Optional<TypeJapaneseName> visit(EnumDeclaration node, Void arg) {
                    return node.getJavadoc()
                            .map(javadoc -> javadocToTypeJapaneseName(node, javadoc));
                }

                private TypeJapaneseName javadocToTypeJapaneseName(NodeWithSimpleName node, Javadoc javadoc) {
                    String javadocText = javadoc.getDescription().toText();
                    JapaneseName japaneseName = new JapaneseName(javadocText);

                    String packageName = cu.getPackageDeclaration()
                            .map(PackageDeclaration::getNameAsString)
                            .map(name -> name + ".")
                            .orElse("");
                    String className = node.getNameAsString();
                    TypeIdentifier typeIdentifier = new TypeIdentifier(packageName + className);

                    return new TypeJapaneseName(typeIdentifier, japaneseName);
                }
            }, null);

            return result != null ? result : Optional.empty();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
