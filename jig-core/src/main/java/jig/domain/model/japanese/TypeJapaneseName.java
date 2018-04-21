package jig.domain.model.japanese;

import jig.domain.model.identifier.type.TypeIdentifier;

public class TypeJapaneseName {
    TypeIdentifier typeIdentifier;
    JapaneseName japaneseName;

    public TypeJapaneseName(TypeIdentifier typeIdentifier, JapaneseName japaneseName) {
        this.typeIdentifier = typeIdentifier;
        this.japaneseName = japaneseName;
    }

    public TypeIdentifier typeIdentifier() {
        return typeIdentifier;
    }

    public JapaneseName japaneseName() {
        return japaneseName;
    }
}
