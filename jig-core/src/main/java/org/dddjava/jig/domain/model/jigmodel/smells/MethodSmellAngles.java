package org.dddjava.jig.domain.model.jigmodel.smells;

import org.dddjava.jig.domain.model.jigmodel.businessrules.BusinessRules;
import org.dddjava.jig.domain.model.jigmodel.declaration.field.FieldDeclarations;
import org.dddjava.jig.domain.model.jigmodel.relation.method.MethodRelations;
import org.dddjava.jig.domain.model.jigmodel.richmethod.Method;
import org.dddjava.jig.domain.model.jigmodel.richmethod.Methods;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * メソッドの不吉なにおい一覧
 */
public class MethodSmellAngles {
    List<MethodSmellAngle> list;

    public MethodSmellAngles(Methods methods, FieldDeclarations fieldDeclarations, MethodRelations methodRelations, BusinessRules businessRules) {
        this.list = new ArrayList<>();
        for (Method method : methods.list()) {
            if (businessRules.contains(method.declaration().declaringType())) {
                MethodSmellAngle methodSmellAngle = new MethodSmellAngle(
                        method,
                        fieldDeclarations.filterDeclareTypeIs(method.declaration().declaringType()),
                        methodRelations
                );
                if (methodSmellAngle.hasSmell()) {
                    list.add(methodSmellAngle);
                }
            }
        }
    }

    public List<MethodSmellAngle> list() {
        return list.stream()
                .sorted(Comparator.comparing(methodSmellAngle -> methodSmellAngle.methodDeclaration().asFullNameText()))
                .collect(Collectors.toList());
    }
}
