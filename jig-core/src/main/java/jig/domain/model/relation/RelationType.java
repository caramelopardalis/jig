package jig.domain.model.relation;

import jig.domain.model.identifier.MethodIdentifier;
import jig.domain.model.identifier.TypeIdentifier;
import jig.domain.model.specification.Specification;

public enum RelationType {
    DEPENDENCY,
    FIELD,
    METHOD,
    METHOD_RETURN_TYPE,
    METHOD_PARAMETER,
    METHOD_USE_TYPE,
    IMPLEMENT,
    METHOD_USE_METHOD;

    public static void register(RelationRepository repository, Specification specification) {

        specification.fieldTypeIdentifiers().list().forEach(fieldTypeIdentifier ->
                repository.registerField(specification.typeIdentifier, fieldTypeIdentifier));

        specification.methodSpecifications.forEach(methodSpecification -> {
            MethodIdentifier methodIdentifier = methodSpecification.identifier;
            repository.registerMethod(methodIdentifier);
            repository.registerMethodParameter(methodIdentifier);
            repository.registerMethodReturnType(methodIdentifier, methodSpecification.getReturnTypeName());

            for (TypeIdentifier interfaceTypeIdentifier : specification.interfaceIdentifiers.list()) {
                repository.registerImplementation(methodIdentifier, methodIdentifier.with(interfaceTypeIdentifier));
            }

            methodSpecification.usingFieldTypeIdentifiers.forEach(fieldTypeName ->
                    repository.registerMethodUseType(methodIdentifier, fieldTypeName));

            methodSpecification.usingMethodIdentifiers.forEach(methodName -> {
                repository.registerMethodUseMethod(methodIdentifier, methodName);
                repository.registerMethodUseType(methodIdentifier, methodName.typeIdentifier());
            });
        });
    }
}
