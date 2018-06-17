package org.dddjava.jig.domain.model.declaration.type;

import org.dddjava.jig.domain.basic.Text;
import org.dddjava.jig.domain.model.declaration.namespace.PackageIdentifier;
import org.dddjava.jig.domain.model.declaration.namespace.PackageIdentifiers;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 型の識別子一覧
 */
public class TypeIdentifiers {

    List<TypeIdentifier> identifiers;

    public TypeIdentifiers(List<TypeIdentifier> identifiers) {
        this.identifiers = identifiers;
    }

    public List<TypeIdentifier> list() {
        ArrayList<TypeIdentifier> list = new ArrayList<>(this.identifiers);
        list.sort(Comparator.comparing(TypeIdentifier::fullQualifiedName));
        return list;
    }

    public Set<TypeIdentifier> set() {
        return new HashSet<>(identifiers);
    }

    public static Collector<TypeIdentifier, ?, TypeIdentifiers> collector() {
        return Collectors.collectingAndThen(Collectors.toList(), TypeIdentifiers::new);
    }

    public String asSimpleText() {
        return identifiers.stream()
                .distinct()
                .map(TypeIdentifier::asSimpleText)
                .sorted()
                .collect(Text.collectionCollector());
    }

    public boolean contains(TypeIdentifier typeIdentifier) {
        return identifiers.contains(typeIdentifier);
    }

    public boolean empty() {
        return identifiers.isEmpty();
    }

    public PackageIdentifiers packageIdentifiers() {
        List<PackageIdentifier> availablePackages = identifiers.stream()
                .map(TypeIdentifier::packageIdentifier)
                .distinct()
                .collect(Collectors.toList());
        return new PackageIdentifiers(availablePackages);
    }

    public TypeIdentifiers normalize() {
        return identifiers.stream().map(TypeIdentifier::normalize).distinct().collect(collector());
    }
}
